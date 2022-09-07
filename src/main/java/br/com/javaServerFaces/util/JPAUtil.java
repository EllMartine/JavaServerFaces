package br.com.javaServerFaces.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

	private static EntityManagerFactory entityManagerFactory = null;
	
	static {
		init();
	}
	
	private static void init() {
		if (entityManagerFactory == null) {
			entityManagerFactory = Persistence.createEntityManagerFactory("javaServerFaces");
		}
	}
	
	public static EntityManager getEntityManager() {
		return entityManagerFactory.createEntityManager();
	}
	
	public static Object getPrimaryKey(Object entity) {
		return entityManagerFactory.getPersistenceUnitUtil().getIdentifier(entity);
	}

}
