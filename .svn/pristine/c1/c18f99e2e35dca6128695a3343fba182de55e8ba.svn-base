package com.xhcms.lottery.dc.data;

import static org.junit.Assert.*;

import org.junit.Test;

public class OddsBaseTest {

	@Test
	public void testAddOdd() {
		OddsBase ob = new OddsBase();
		ob.addOdd("0000");
		ob.addOdd("12");
		ob.addOdd("100");
		ob.addOdd("2.0");
		ob.addOdd("12.34");
		ob.addOdd("56.790");
		ob.addOdd("56.791");
		System.out.println(ob.getOdds());
		assertEquals("0.00,12.00,100.00,2.00,12.34,56.79,56.79", ob.getOdds());
	}

}
