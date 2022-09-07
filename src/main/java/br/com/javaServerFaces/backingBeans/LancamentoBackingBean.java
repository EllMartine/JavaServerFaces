package br.com.javaServerFaces.backingBeans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import br.com.javaServerFaces.beans.Lancamento;
import br.com.javaServerFaces.beans.Usuario;
import br.com.javaServerFaces.dao.GenericDAO;
import br.com.javaServerFaces.dao.LancamentoDAO;

@ViewScoped
@ManagedBean(name = "backing_lancamento")
public class LancamentoBackingBean {
	
	private Lancamento lancamento = new Lancamento();
	private GenericDAO<Lancamento> dao = new GenericDAO<Lancamento>();
	private List<Lancamento> lancamentos = new ArrayList<Lancamento>();
	private LancamentoDAO lancamentoDAO = new LancamentoDAO();
	
	public Usuario getAuthenticatedUser() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		return (Usuario) externalContext.getSessionMap().get("usuarioAutenticado");
	}
	
	public Boolean acessoPermitido(String perfil) {
		Usuario usuario = getAuthenticatedUser();
		return usuario.getPerfil().equals(perfil);
	}
	
	public String salvar() {
		Usuario usuario = getAuthenticatedUser();
		lancamento.setUsuario(usuario);
		lancamento = dao.salvarEAtualizar(lancamento);
		carregaListaPorUsuario();
		return "";
	}
	
	public String deletar() {
		dao.deletarPorID(lancamento);
		lancamento = new Lancamento();
		carregaListaPorUsuario();
		return "";
	}
	
	public String novo() {
		lancamento = new Lancamento();
		return "";
	}
	
	/***
	 * Método substituído
	 * @PostConstruct
	public void carregaLista() {
		lancamentos = dao.getEntityList(lancamento);
	}*/
	
	@PostConstruct
	public void carregaListaPorUsuario() {
		Usuario usuario = getAuthenticatedUser();
		lancamentos = lancamentoDAO.carregaLancamentoPorUsuario(usuario.getId());
	}
	
	public Lancamento getLancamento() {
		return lancamento;
	}
	public void setLancamento(Lancamento lancamento) {
		this.lancamento = lancamento;
	}
	public GenericDAO<Lancamento> getDao() {
		return dao;
	}
	public void setDao(GenericDAO<Lancamento> dao) {
		this.dao = dao;
	}
	public List<Lancamento> getLancamentos() {
		return lancamentos;
	}
	public void setLancamentos(List<Lancamento> lancamentos) {
		this.lancamentos = lancamentos;
	}

}
