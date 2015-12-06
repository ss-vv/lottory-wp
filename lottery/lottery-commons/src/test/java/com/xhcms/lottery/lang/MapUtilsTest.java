package com.xhcms.lottery.lang;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class MapUtilsTest {

	@SuppressWarnings("rawtypes")
	@Test
	public void test() {
		HashMap<String, String> m = new HashMap<String, String>();
		m.put("yang", "bo");
		m.put("y", "b");
		Map rm = MapUtils.reverse(m);
		assertEquals("yang", rm.get("bo"));
		assertEquals("y", rm.get("b"));
	}

}
