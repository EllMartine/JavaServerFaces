package br.com.javaServerFaces.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.javaServerFaces.beans.Estado;
import br.com.javaServerFaces.util.JPAUtil;

public class EstadoDAO {
	
	public List<Estado> getListEstados() {
		List<Estado> estados = null;
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		estados = entityManager.createQuery("from Estado").getResultList();
		transaction.commit();
		return estados;
	}

}
