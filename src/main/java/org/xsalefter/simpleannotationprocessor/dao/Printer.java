package org.xsalefter.simpleannotationprocessor.dao;

import org.springframework.stereotype.Repository;

@Repository
public class Printer {

	public String print(Class<?> entityClass) {
		if (entityClass != null)
			return "Hello from entity named: " + entityClass.getSimpleName();
		else
			return "Unknown entity class.";
	}
}
