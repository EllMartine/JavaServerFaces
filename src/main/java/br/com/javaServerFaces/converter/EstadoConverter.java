package br.com.javaServerFaces.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.javaServerFaces.beans.Cidade;
import br.com.javaServerFaces.beans.Estado;
import br.com.javaServerFaces.util.JPAUtil;

@FacesConverter(forClass = Estado.class, value = "estadoConverter")
public class EstadoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String codigoEstado) {
		if (!codigoEstado.equals("[SELECIONE]")) {
			EntityManager entityManager = JPAUtil.getEntityManager();
			EntityTransaction transaction = entityManager.getTransaction();
			transaction.begin();
			Estado estado = entityManager.find(Estado.class, Integer.parseInt(codigoEstado));
			entityManager.close();
			return estado;
		} else {
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object estado) {
		if (estado == null) {
			return null;
		} else if (estado instanceof Object) {
			return ((Estado) estado).getId().toString();
		} else {
			return estado.toString();
		}
	}

}
