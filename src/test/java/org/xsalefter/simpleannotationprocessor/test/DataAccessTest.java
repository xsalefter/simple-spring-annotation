package org.xsalefter.simpleannotationprocessor.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.xsalefter.simpleannotationprocessor.service.PersonService;

@ContextConfiguration({"classpath*:/spring-config.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class DataAccessTest {
	
	private transient Logger logger = LoggerFactory.getLogger(DataAccessTest.class);

	@Autowired
	private PersonService personService;
	
	@Test
	public void injectionTest() {
		Assert.assertNotNull(this.personService);
		Assert.assertNotNull(this.personService.getPersonDAO());
	}
	
	@Test
	public void getClassNameTest() {
		logger.info(">>> DAO instance: {}", this.personService.getPersonDAO());
		this.personService.getPersonDAO().printMessage();
		logger.info(">>> DAO instance: {}", this.personService.getAddressDAO());
		this.personService.getAddressDAO().printMessage();
	}
}
