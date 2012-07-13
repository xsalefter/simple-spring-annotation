package org.xsalefter.simpleannotationprocessor;

import java.lang.reflect.Constructor;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.CustomAutowireConfigurer;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Service;
import org.xsalefter.simpleannotationprocessor.dao.DataAccess;

@Deprecated
public class ModelAnnotationConfigurer extends CustomAutowireConfigurer {
	
	private transient Logger logger = LoggerFactory.getLogger(ModelAnnotationConfigurer.class);
	
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
		try {
			logger.info(">>> postProcessBeanFactory(beanFactory);");
			this.proccessModelAnnotation(beanFactory);
		} catch (Exception e) { 
			e.printStackTrace(); 
		}
		
		super.postProcessBeanFactory(beanFactory);
	}
	
	private void proccessModelAnnotation(ConfigurableListableBeanFactory beanFactory) 
	throws SecurityException, NoSuchMethodException {
		AutowiredAnnotationBeanPostProcessor processor = new AutowiredAnnotationBeanPostProcessor();
		processor.setBeanFactory(beanFactory);
		Constructor<?>[] constructors = processor.determineCandidateConstructors(DataAccess.class, null);
		logger.info(">>> Constructor size: {}", constructors);
		// Object dataAccess = beanFactory.createBean(DataAccess.class, AutowireCapableBeanFactory.AUTOWIRE_CONSTRUCTOR, true);
		Map<String, Object> services = beanFactory.getBeansWithAnnotation(Service.class);
		
		logger.info(">>> DataAccess : {}", new Object());
		for (String beanName : services.keySet()) {
			Object existingBean = services.get(beanName);
			logger.info(">>> Bean Name: {} - Bean Instance: {}", beanName, existingBean);
			
			
		}
	}
}
