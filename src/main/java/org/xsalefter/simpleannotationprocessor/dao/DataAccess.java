package org.xsalefter.simpleannotationprocessor.dao;

import org.springframework.beans.factory.annotation.Autowired;

public class DataAccess<E> {

	private Class<E> entityClass;
	
	@Autowired
	private Printer printer;
	
	public DataAccess(Class<?> entityClass) {
		@SuppressWarnings("unchecked")
		final Class<E> clazz = (Class<E>) entityClass;
		this.entityClass = clazz;
	}
	
	public Class<E> getEntityClass() {
		return this.entityClass;
	}
	
	public void printMessage() {
		System.out.println(">>> " + this.printer.print(this.entityClass));
	}
	
	@Override
	public String toString() {
		return this.entityClass == null ? 
				null : 
				getClass().getSimpleName() + "<" + this.entityClass.getSimpleName() + ">";
	}
}
