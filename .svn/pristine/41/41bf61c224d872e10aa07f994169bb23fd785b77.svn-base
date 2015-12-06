package com.xhcms.lottery.utils;


import java.util.UUID;

import junit.framework.Assert;

import org.junit.Test;





public class KeyGeneratorUtilsTest {

	
	
	@Test
	public void testGenerateKey() throws KeyGenerateException {
		String initKey="suz56xT4";
		String seed=UUID.randomUUID().toString();
		String key=KeyGeneratorUtils.generateKeyByInitKeyAndSeed(initKey,seed);
		String expectedKey=makeExpectedKey(initKey,seed);
		Assert.assertTrue(key.equals(expectedKey));
		System.out.println("key="+key);
	}
	
	@Test(expected=KeyGenerateException.class)
	public void whenInitKeyIsBlankOrSeedIsBlankShouldThrowException() throws KeyGenerateException{
		String initKey="suz56xT4";
		String seed=null;
		KeyGeneratorUtils.generateKeyByInitKeyAndSeed(initKey,seed);
	}
	
	@Test(expected=KeyGenerateException.class)
	public void whenSeedIsTooShortShouldThrowException() throws KeyGenerateException{
		String initKey="suz56xT4";
		String seed="111";
		KeyGeneratorUtils.generateKeyByInitKeyAndSeed(initKey,seed);
	}
	
	@Test(expected=KeyGenerateException.class)
	public void whenInitKeyIsTooShortShouldThrowException() throws KeyGenerateException{
		String initKey="suz56xT";
		String seed="12345678123456781234567812345678";
		KeyGeneratorUtils.generateKeyByInitKeyAndSeed(initKey,seed);
	}

	private String makeExpectedKey(String initKey, String seed) {
		String result = null;
		char[] head = { seed.charAt(2), seed.charAt(23), seed.charAt(6),
				seed.charAt(20), seed.charAt(10)};
		char[] tail={initKey.charAt(4),initKey.charAt(1),initKey.charAt(6)};
		String headStr = new String(head);
		String tailStr= new String(tail);
		result = headStr + tailStr;
//		result = headStr;
		return result;
	}

	
}
