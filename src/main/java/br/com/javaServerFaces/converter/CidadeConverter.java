package br.com.javaServerFaces.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.javaServerFaces.beans.Cidade;
import br.com.javaServerFaces.util.JPAUtil;

@FacesConverter(forClass = Cidade.class, value = "cidadeConverter")
public class CidadeConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String codigoCidade) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		Cidade cidade = entityManager.find(Cidade.class, Integer.parseInt(codigoCidade));
		entityManager.close();
		return cidade;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object cidade) {
		if (cidade != null) {
			return ((Cidade) cidade).getId().toString();
		} else {
			return null;
		}
	}

}
