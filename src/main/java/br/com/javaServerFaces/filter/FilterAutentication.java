package br.com.javaServerFaces.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.javaServerFaces.beans.Usuario;
import br.com.javaServerFaces.util.JPAUtil;

@WebFilter(urlPatterns = {"/*"})
public class FilterAutentication implements Filter{
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		JPAUtil.getEntityManager();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = (HttpSession) req.getSession();
		
		Usuario userAuthenticated = (Usuario) session.getAttribute("usuarioAutenticado");
		String url = req.getServletPath();
		
		if (!url.equalsIgnoreCase("/index.xhtml") && userAuthenticated == null) {
			RequestDispatcher requestDispatcher = req.getRequestDispatcher("/index.xhtml");
			requestDispatcher.forward(request, response);
		} else {
			chain.doFilter(request, response);
		}
		
	}

}
