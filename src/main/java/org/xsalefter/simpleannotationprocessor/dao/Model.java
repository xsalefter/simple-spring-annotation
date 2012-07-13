package org.xsalefter.simpleannotationprocessor.dao;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Bean;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.CONSTRUCTOR})
@Documented
@org.springframework.beans.factory.annotation.Qualifier
@Bean
public @interface Model {
	public Class<?> value();
}
