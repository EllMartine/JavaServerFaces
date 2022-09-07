package br.com.javaServerFaces.backingBeans;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.bind.DatatypeConverter;

import com.google.gson.Gson;

import br.com.javaServerFaces.beans.Cidade;
import br.com.javaServerFaces.beans.Estado;
import br.com.javaServerFaces.beans.Usuario;
import br.com.javaServerFaces.dao.CidadeDAO;
import br.com.javaServerFaces.dao.EstadoDAO;
import br.com.javaServerFaces.dao.GenericDAO;
import br.com.javaServerFaces.dao.UsuarioDAO;
import br.com.javaServerFaces.util.JPAUtil;

@ViewScoped
@ManagedBean(name = "backing_usuario")
public class UsuarioBackingBean {

	private Usuario usuario = new Usuario();
	private GenericDAO<Usuario> dao = new GenericDAO<Usuario>();
	private UsuarioDAO usuarioDAO = new UsuarioDAO();
	private EstadoDAO estadoDAO = new EstadoDAO();
	private CidadeDAO cidadeDAO = new CidadeDAO();
	private List<Usuario> usuarios = new ArrayList<Usuario>();
	
	private Part arquivoImg;
	
	private List<SelectItem> cidades;

	public String logar() {
		if (!usuario.getLogin().isEmpty() && !usuario.getSenha().isEmpty()) {
			Usuario usuarioConsultado = usuarioDAO.consultarUsuario(usuario.getLogin(), usuario.getSenha());
			if (usuarioConsultado != null) {
				FacesContext context = FacesContext.getCurrentInstance();
				ExternalContext externalContext = context.getExternalContext();
				externalContext.getSessionMap().put("usuarioAutenticado", usuarioConsultado);
			}
			return "usuario?faces-redirect=true";
		} else {
			return "erro?faces-redirect=true";
		}
	}
	
	public String deslogar() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		externalContext.getSessionMap().remove("usuarioAutenticado");
		HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
		request.getSession().invalidate();
		return "index?faces-redirect=true";
	}

	public Boolean acessoPermitido(String perfil) {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		Usuario usuario = (Usuario) externalContext.getSessionMap().get("usuarioAutenticado");
		return usuario.getPerfil().equals(perfil);
	}

	public void mostrarMensagem(String mensagem) {
		FacesMessage message = new FacesMessage(mensagem);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);
	}

	public String salvar() throws IOException {
		processaImg();
		usuario = dao.salvarEAtualizar(usuario);
		mostrarMensagem("INSERÇÃO CONCLUÍDA!");
		carregaLista();
		return "";
	}
	
	public String editar() {
		atualizaForm();
		return "";
	}

	public String deletar() {
		dao.deletarPorID(usuario);
		mostrarMensagem("REMOÇÃO CONCLUÍDA!");
		usuario = new Usuario();
		carregaLista();
		return "";
	}

	public String novo() {
		usuario = new Usuario();
		return "";
	}

	@PostConstruct
	public void carregaLista() {
		usuarios = dao.getEntityList(usuario);
	}

	public void carregaCEP(AjaxBehaviorEvent event) {
		try {
			URL url = new URL("https://viacep.com.br/ws/" + usuario.getCep() + "/json/");
			URLConnection urlConnection = url.openConnection();
			InputStream is = urlConnection.getInputStream();
			BufferedReader bf = new BufferedReader(new InputStreamReader(is, "UTF-8"));

			String cep = null;
			StringBuilder json = new StringBuilder();

			while ((cep = bf.readLine()) != null) {
				json.append(cep);
			}

			Usuario usuarioAux = new Gson().fromJson(json.toString(), Usuario.class);
			usuario.setBairro(usuarioAux.getBairro());

		} catch (Exception e) {
			e.printStackTrace();
			mostrarMensagem(e.getMessage());
		}
	}
	
	public List<SelectItem> getEstados() {
		List<SelectItem> selectItens = new ArrayList<SelectItem>();
		List<Estado> estados = estadoDAO.getListEstados();
		for (Estado estado : estados) {
			selectItens.add(new SelectItem(estado, estado.getNome()));
		}
		return selectItens;
	}
	
	public void carregaCidades(AjaxBehaviorEvent event) {
		//String codigoEstado = (String) event.getComponent().getAttributes().get("submittedValue");
		Estado estado = (Estado) ( (HtmlSelectOneMenu) event.getSource()).getValue();
			if (estado != null) {
				List<Cidade> cidades = cidadeDAO.getListCidadesPorEstado(estado.getId().toString());
				List<SelectItem> selectItens = new ArrayList<SelectItem>();
				for (Cidade cidade : cidades) {
					selectItens.add(new SelectItem(cidade, cidade.getNome()));
				}
				setCidades(selectItens);
			}
		}
	
	public void atualizaForm() {
		if (usuario.getCidade() != null) {
			Estado estado = usuario.getCidade().getEstado();
			usuario.setEstado(estado);
			List<Cidade> cidades = cidadeDAO.getListCidadesPorEstado(estado.getId().toString());
			List<SelectItem> selectItens = new ArrayList<SelectItem>();
			for (Cidade cidade : cidades) {
				selectItens.add(new SelectItem(cidade, cidade.getNome()));
			}
			setCidades(selectItens);
		}
	}
	
	private byte[] getByte(InputStream is) throws IOException {
		int length;
		int size = 1024;
		byte[] buf = null;
		
		if (is instanceof ByteArrayInputStream) {
			size = is.available();
			buf = new byte[size];
			length = is.read(buf, 0, size);
		} else {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			buf = new byte[size];
			while ((length = is.read(buf, 0, size)) != -1) {
				bos.write(buf, 0, size);
			}
			buf = bos.toByteArray();
		}
		return buf;
	}
	
	private void processaImg() throws IOException {
		if (arquivoImg != null) {
			byte[] imgByte = getByte(arquivoImg.getInputStream());
			usuario.setImg(imgByte);
			BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imgByte));
			int imgType = bufferedImage.getType() == 0 ? bufferedImage.TYPE_INT_ARGB : bufferedImage.getType();
			int width = 200;
			int height = 200;
			
			BufferedImage bufferedImageMini = new BufferedImage(width, height, imgType);
			Graphics2D graphics2d = bufferedImageMini.createGraphics();
			graphics2d.drawImage(bufferedImage, 0, 0, width, height, null);
			graphics2d.dispose();
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			String extImg = arquivoImg.getContentType().split("\\/")[1];
			ImageIO.write(bufferedImage, extImg, baos);
			
			String miniImg = "data:" + arquivoImg.getContentType() 
			+ ";base64," + DatatypeConverter.printBase64Binary(baos.toByteArray());
			
			usuario.setMiniImg(miniImg);
			usuario.setExtImg(extImg);
		}	
	}
	
	public void download() throws IOException {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String id = params.get("fileDownloadId");
		Usuario usuario = dao.pesquisarPorID(Usuario.class, Integer.parseInt(id));
		
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		response.addHeader("Content-Disposition", "attachment; filename=download." + usuario.getExtImg());
		response.setContentType("application/octet-stream");
		response.setContentLength(usuario.getImg().length);
		response.getOutputStream().write(usuario.getImg());
		response.getOutputStream().flush();
		
		FacesContext.getCurrentInstance().responseComplete();
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public List<SelectItem> getCidades() {
		return cidades;
	}

	public void setCidades(List<SelectItem> cidades) {
		this.cidades = cidades;
	}

	public Part getArquivoImg() {
		return arquivoImg;
	}

	public void setArquivoImg(Part arquivoImg) {
		this.arquivoImg = arquivoImg;
	}

}
