package model.spring;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.XmlViewResolver;

@Configuration
@ComponentScan(basePackages={"controller"})		//<context:component-scan base-package="controller"></context:component-scan>
@EnableWebMvc									//<mvc:annotation-driven></mvc:annotation-driven>
public class SpringMvcJavaConfiguration implements WebMvcConfigurer {
/*	
	<mvc:interceptors>
		<mvc:interceptor>
			<mvc:mapping path="/secure/*"/>
			<bean class="controller.DemoInterceptor"></bean>	
		</mvc:interceptor>
	</mvc:interceptors>
*/
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new controller.DemoInterceptor())
				.addPathPatterns("/secure/*");
	}
	
	@Autowired
	private ServletContext application;
	
/*
	<bean class="org.springframework.web.servlet.view.XmlViewResolver">
		<property name="location" value="/WEB-INF/views.xml"></property>
	</bean>
*/
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		XmlViewResolver viewResolver = new XmlViewResolver();
		Resource resource = new ServletContextResource(application, "/WEB-INF/views.xml");
		viewResolver.setLocation(resource);		
		
		registry.viewResolver(viewResolver);
	}
}
