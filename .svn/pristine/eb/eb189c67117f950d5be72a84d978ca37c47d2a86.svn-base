/**
 * 
 */
package com.xhcms.ucenter.sso.util.generator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author bean
 *
 */
public class DefaultUniqueTicketIdGeneratorTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.xhcms.ucenter.sso.util.generator.DefaultUniqueTicketIdGenerator#getNewTicketId(java.lang.String)}.
	 */
	@Test
	public void testGetNewTicketId() {
		DefaultUniqueTicketIdGenerator generator = new DefaultUniqueTicketIdGenerator("xhcms");
		for(int i = 0;  i < 10; i++) {
			System.out.println(generator.getNewTicketId("www"));
			System.out.println(generator.getNewTicketId("wwww"));
		}
	}

}
