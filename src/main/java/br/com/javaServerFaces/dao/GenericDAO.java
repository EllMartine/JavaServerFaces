package br.com.javaServerFaces.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.javaServerFaces.util.JPAUtil;

public class GenericDAO<E> {
	
	private EntityManager entityManager = JPAUtil.getEntityManager();
	
	public void salvar(E entity) {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(entity);
		transaction.commit();
	}
	
	public E salvarEAtualizar(E entity) {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		E entityMerged = entityManager.merge(entity);
		transaction.commit();
		return entityMerged;
	}
	
	public void deletarPorID(E entity) {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		Object id = JPAUtil.getPrimaryKey(entity);
		String sql = "DELETE FROM " + entity.getClass().getSimpleName() + " WHERE ID = " + id;
		entityManager.createQuery(sql).executeUpdate();
		transaction.commit();		
	}
	
	public List<E> getEntityList(E entity) {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		String sql = "FROM " + entity.getClass().getSimpleName() + " ORDER BY ID";
		List<E> entityList = entityManager.createQuery(sql).getResultList();
		transaction.commit();
		return entityList;
	}
	
	public E pesquisarPorID (Class<E> entity, Integer id) {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		E obj = (E) entityManager.find(entity, id);
		transaction.commit();
		return obj;
	}

}
