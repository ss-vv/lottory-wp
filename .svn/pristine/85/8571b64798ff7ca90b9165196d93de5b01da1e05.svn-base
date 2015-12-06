package com.xhcms.lottery.utils;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;

public class DESTest {

	

	@Test
	public void testDES() throws Exception {
		String key="34125678";
		System.out.println("密钥："+key);
		String orignalString="我men是";
		System.out.println("原文："+orignalString);
		String encryptStr=DES.encryptDES(orignalString, key, "utf-8");
		System.out.println("密文："+encryptStr);
		String decryptStr=DES.decryptDES(encryptStr, key, "utf-8");
		System.out.println("解密后明文："+decryptStr);
	}

	
}
