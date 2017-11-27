package model.spring;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class DispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return null;
	}
/*	
	<init-param>
		<param-name>contextConfigLocation</param-name>
		<param-value>model.spring.SpringMvcJavaConfiguration</param-value>
	</init-param>
*/
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {model.spring.SpringMvcJavaConfiguration.class};
	}
//	<url-pattern>*.controller</url-pattern>
	@Override
	protected String[] getServletMappings() {
		return new String[] {"*.controller"};
	}

}
