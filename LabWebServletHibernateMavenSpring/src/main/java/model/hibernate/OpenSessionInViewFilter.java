package model.hibernate;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(
		urlPatterns= {"/*"}
)
public class OpenSessionInViewFilter implements Filter {
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse reponse = (HttpServletResponse) resp;
		try {
			HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
			System.out.println("OpenSessionInViewFilter transaction begin");
			chain.doFilter(request, reponse);
			HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
			System.out.println("OpenSessionInViewFilter transaction commit");
		} catch (Exception e) {
			e.printStackTrace();
			HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().rollback();
			System.out.println("OpenSessionInViewFilter transaction rollback");
			chain.doFilter(request, reponse);
		}
	}
	private FilterConfig filterConfig;
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}
	@Override
	public void destroy() {

	}
}
