package com.unison.lottery.api.framework.utils;



import com.xhcms.lottery.utils.DES;
import com.xhcms.lottery.utils.KeyGeneratorUtils;

public class DecryptAndEncryptService {
	
	private static final String INIT_KEY="wA1PW^T5";
	private static final String CHARSET = "utf-8";
	


	
	public static String decrypt(String orignalStr) {
		String result=null;
		try {
			//String key=KeyGeneratorUtils.generateKeyByInitKeyAndSeed(INIT_KEY, seed);
			result=DES.decryptDES(orignalStr, INIT_KEY, CHARSET);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new DecryptException(e.getMessage());
		}
		return result;
	}



	
	public static String encrypt(String orignalStr) {
		String result=null;
		try {
			//String key=KeyGeneratorUtils.generateKeyByInitKeyAndSeed(INIT_KEY, seed);
			result=DES.encryptDES(orignalStr, INIT_KEY, CHARSET);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new EncryptException(e.getMessage());
		}
		return result;
	}

}
