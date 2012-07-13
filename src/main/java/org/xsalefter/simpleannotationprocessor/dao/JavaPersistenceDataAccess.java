package org.xsalefter.simpleannotationprocessor.dao;

public class JavaPersistenceDataAccess<E, P> extends DataAccess<E> {

	public JavaPersistenceDataAccess(Class<?> entityClass) {
		super(entityClass);
	}

}
