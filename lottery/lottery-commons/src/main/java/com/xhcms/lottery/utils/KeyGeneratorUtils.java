package com.xhcms.lottery.utils;

import org.apache.commons.lang.StringUtils;

public class KeyGeneratorUtils {
	

	private static final int MIN_LENGTH_OF_SEED = 32;
	private static final int MIN_LENGTH_OF_INIT_KEY = 8;

	/**
	 * 
	 * 原始密钥+混淆串生成最终密钥的方式如下：
	 *	a、取混淆串的前32位作为初始串A
	 *	b、将初始串A转为字符数组B
	 *	c、将字符数组B分为两部分，前16字符为第一部分C1，后16字符为第二部分C2
	 *	d、依次取C1的第3、7、11位字符作为新字符数组D1
	 *	e、依次取C2的第5、8位字符作为新字符数组D2
	 *	f、将D1和D2合成一个新的8位字符数组E：
	 *		E={D1[0],D2[1],D1[1],D2[0],D1[2]}
	 *	g、将数组E转化为字符串F
	 *	h、将原始密钥依次取出第5、2、7位字符转为字符串K
	 *	I、将F+K作为最终密钥
	 * @param initKey 原始的密钥
	 * @param seed 种子
	 * @return
	 */
	public static String generateKeyByInitKeyAndSeed (String initKey, String seed) throws KeyGenerateException {
		if(StringUtils.isBlank(initKey)||StringUtils.isBlank(seed)){
			throw new KeyGenerateException("initKey and seed shouldn't blank!");
		}
		if(seed.length()<MIN_LENGTH_OF_SEED){
			throw new KeyGenerateException("seed length is too short!should greater or enqual to "+MIN_LENGTH_OF_SEED);
		}
		if(initKey.length()<MIN_LENGTH_OF_INIT_KEY){
			throw new KeyGenerateException("initKey length is too short!should greater or enqual to "+MIN_LENGTH_OF_INIT_KEY);
		}
		return makeExpectedKey(initKey, seed);
	}
	
	/**
	 * 按照设计的算法，实际上最终密钥的头就是由seed的
	 * 第3、24、7、21、11位字符组成的字符串
	 * @param initKey
	 * @param seed
	 * @return
	 */
	private static String makeExpectedKey(String initKey, String seed) {
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
