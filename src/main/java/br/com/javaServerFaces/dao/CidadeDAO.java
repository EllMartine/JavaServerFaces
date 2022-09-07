package br.com.javaServerFaces.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.javaServerFaces.beans.Cidade;
import br.com.javaServerFaces.util.JPAUtil;

public class CidadeDAO {
	
	public List<Cidade> getListCidades() {
		List<Cidade> cidades = null;
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		cidades = entityManager.createQuery("from Cidade").getResultList();
		transaction.commit();
		return cidades;
	}
	
	public List<Cidade> getListCidadesPorEstado(String codigoEstado) {
		List<Cidade> cidades = null;
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		String sql = "from Cidade where estado.id = " + codigoEstado;
		cidades = entityManager.createQuery(sql).getResultList();
		transaction.commit();
		return cidades;
	}

}
