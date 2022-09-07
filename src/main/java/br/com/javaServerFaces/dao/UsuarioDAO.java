package br.com.javaServerFaces.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.javaServerFaces.beans.Usuario;
import br.com.javaServerFaces.util.JPAUtil;

public class UsuarioDAO {
	
	public Usuario consultarUsuario(String login, String senha) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		String sql = "select u from Usuario u where u.login = '" + login + "' and u.senha = '" + senha + "'";
		Usuario usuario = (Usuario) entityManager.createQuery(sql).getSingleResult();
		transaction.commit();
		return usuario;	
	}

}
