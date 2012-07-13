package org.xsalefter.simpleannotationprocessor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.Ordered;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;
import org.xsalefter.simpleannotationprocessor.dao.Model;

public class ModelAnnotationProcessor implements BeanPostProcessor, Ordered {
	
	private transient static Logger logger = LoggerFactory.getLogger(ModelAnnotationProcessor.class);
	
	private final ConfigurableListableBeanFactory beanFactory;
	
	@Autowired
	public ModelAnnotationProcessor(ConfigurableListableBeanFactory beanFactory) {
		this.beanFactory  = beanFactory;
	}

	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}

	@Override
	public Object postProcessBeforeInitialization(final Object bean, final String beanName)
	throws BeansException {
		this.scanDataAccessOnFields(bean, beanName);
        return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		return bean;
	}

	protected <E> void scanDataAccessOnFields(final Object bean, final String beanName) {
		Class<?> clazz = bean.getClass();
		
		ReflectionUtils.doWithFields(clazz, new FieldCallback() {
			@Override
			public void doWith(Field field) 
			throws IllegalArgumentException, IllegalAccessException {
				ReflectionUtils.makeAccessible(field);
				
				if (field.isAnnotationPresent(Model.class)) {
					logger.info(">>> Scanning @Model annotation in FIELD for bean: {}", beanName);
					
					Class<?> entity = field.getAnnotation(Model.class).value();
					
					// Get something that extends from DataAccess<E>.
					final Class<?> fieldDataType = field.getType();
					final Class<?> fieldGenericValue = field.getType().getTypeParameters()[0].getGenericDeclaration();
					logger.info("fieldDataType = {} - fieldGenericValue = {}", fieldDataType, fieldGenericValue);
					
					// FIXME: Why this never works?!
					// Validating super class.
//					if (!fieldDataType.equals(DataAccess.class) || !fieldDataType.getSuperclass().equals(DataAccess.class)) {
//						final String msg = "Field annotated with @Model annotation should extends from DataAccess<E> class.";
//						InvalidPropertyException ipe = new InvalidPropertyException(bean.getClass(), fieldDataType.getName(), msg);
//						logger.error(ipe.getMessage());
//						throw ipe;
//					}
					
					Object beanToRegister = null;
					try {
						Constructor<?> constructor = fieldDataType.getConstructor(Class.class);
						beanToRegister = constructor.newInstance(entity);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					final int mode = AutowireCapableBeanFactory.AUTOWIRE_BY_NAME;
					Object dao = beanFactory.initializeBean(beanToRegister, field.getName());
					beanFactory.autowireBeanProperties(dao, mode, true);
					
					beanFactory.registerSingleton(field.getName(), dao);
					field.set(bean, dao);
				}
			}
		});
	}
}
