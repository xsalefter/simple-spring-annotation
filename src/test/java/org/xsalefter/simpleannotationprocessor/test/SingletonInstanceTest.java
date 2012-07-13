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
public class SingletonInstanceTest {
	
	transient Logger logger = LoggerFactory.getLogger(SingletonInstanceTest.class);

	@Autowired
	private PersonService service1;
	
	@Autowired 
	private PersonService service2;
	
	/**
	 * Test injected object should be not null.
	 */
	@Test
	public void injectionTest() {
		Assert.assertNotNull(service1);
		Assert.assertNotNull(service2);
	}
	
	/**
	 * Test whether injected object are equals (because they supposed to be singleton).
	 */
	@Test
	public void objectEqualsTest() {
		Assert.assertEquals(service1, service2);
		Assert.assertSame(service1, service2);
	}
	
	/**
	 * Test whether managed property {@link PersonService#getPersonDAO()} is 
	 * same and equals.
	 */
	@Test
	public void managedPropertyTest() {
		Assert.assertEquals(service1.getPersonDAO(), service2.getPersonDAO());
		Assert.assertSame(service1.getPersonDAO(), service2.getPersonDAO());
	}
	
	/**
	 * Test whether non-managed property {@link PersonService#getTempName()} is 
	 * same and equals. 
	 */
	public void nonManagedPropertyTest() {
		this.service1.setTempName("xsalefter ganteng");
		Assert.assertEquals(service1.getTempName(), service2.getTempName());
		Assert.assertSame(service1.getTempName(), service2.getTempName());
	}
}
