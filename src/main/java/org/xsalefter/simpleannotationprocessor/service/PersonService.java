package org.xsalefter.simpleannotationprocessor.service;

import org.springframework.stereotype.Service;
import org.xsalefter.simpleannotationprocessor.dao.DataAccess;
import org.xsalefter.simpleannotationprocessor.dao.JavaPersistenceDataAccess;
import org.xsalefter.simpleannotationprocessor.dao.Model;
import org.xsalefter.simpleannotationprocessor.entity.Address;
import org.xsalefter.simpleannotationprocessor.entity.Person;

@Service
public class PersonService {

	@Model(Person.class)
	private DataAccess<Person> personDAO;
	
	private String tempName;
	
	@Model(Address.class)
	private JavaPersistenceDataAccess<Address, Object> addressDAO;
	
	public DataAccess<Person> getPersonDAO() {
		return this.personDAO;
	}
	
	public DataAccess<Address> getAddressDAO() {
		return this.addressDAO;
	}

	public String getTempName() {
		return tempName;
	}

	public void setTempName(String tempName) {
		this.tempName = tempName;
	}
	
}
