package br.com.javaServerFaces.beans;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Lancamento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String numeroNF;
	private String empresaOrigem;
	private String empresaDestino;
	@ManyToOne(optional = false)
	@org.hibernate.annotations.ForeignKey(name = "usuario_fk")
	private Usuario usuario;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNumeroNF() {
		return numeroNF;
	}
	public void setNumeroNF(String numeroNF) {
		this.numeroNF = numeroNF;
	}
	public String getEmpresaOrigem() {
		return empresaOrigem;
	}
	public void setEmpresaOrigem(String empresaOrigem) {
		this.empresaOrigem = empresaOrigem;
	}
	public String getEmpresaDestino() {
		return empresaDestino;
	}
	public void setEmpresaDestino(String empresaDestino) {
		this.empresaDestino = empresaDestino;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
