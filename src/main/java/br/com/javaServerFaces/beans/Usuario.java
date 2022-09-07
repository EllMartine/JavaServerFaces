package br.com.javaServerFaces.beans;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String login;
	private String senha;
	
	@NotEmpty(message = "Preenchimento do Nome é obrigatório")
	private String nome;
	private String sobrenome;
	private Integer idade;
	@Temporal(TemporalType.DATE)
	private Date dataNascimento = new Date();
	
	private String sexo;
	private String nivel;
	private String[] habilidades;
	private Boolean ativo;
	private String perfil;
	
	private String cep;
	private String bairro;
	
	@Transient
	private Estado estado;
	@ManyToOne
	private Cidade cidade;
	
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] img;
	@Column(columnDefinition = "text")
	private String miniImg;
	private String extImg;
 	
	public Usuario() {	
	}

	public Usuario(String login, String senha) {
		this.login = login;
		this.senha = senha;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	
	public String getNivel() {
		return nivel;
	}
	
	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	public String[] getHabilidades() {
		return habilidades;
	}

	public void setHabilidades(String[] habilidades) {
		this.habilidades = habilidades;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	
	public Estado getEstado() {
		return estado;
	}
	
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
	public Cidade getCidade() {
		return cidade;
	}
	
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public byte[] getImg() {
		return img;
	}

	public void setImg(byte[] img) {
		this.img = img;
	}

	public String getMiniImg() {
		return miniImg;
	}

	public void setMiniImg(String miniImg) {
		this.miniImg = miniImg;
	}

	public String getExtImg() {
		return extImg;
	}

	public void setExtImg(String extImg) {
		this.extImg = extImg;
	}
	
}
