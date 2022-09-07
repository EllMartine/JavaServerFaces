package br.com.javaServerFaces.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.transaction.Transaction;

import br.com.javaServerFaces.beans.Lancamento;
import br.com.javaServerFaces.util.JPAUtil;

public class LancamentoDAO {
	
	public List<Lancamento> carregaLancamentoPorUsuario(Integer id) {
		List<Lancamento> lancamentos = null;
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		String sql = "from Lancamento where usuario.id = " + id;
		lancamentos = entityManager.createQuery(sql).getResultList();
		entityTransaction.commit();
		return lancamentos;		
	}

}
