package com.davcai.lottery.platform.client.yuanchengchupiao.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class YjBase64Util {
	public static String encode(String sString, String sKey) {
		int iLength = sString.length();
		sKey = toMD5(sKey);
		String sCode = "";
		char[] chs = sString.toCharArray();
		char[] codes = sKey.toCharArray();
		
		int k = 0;
		for (int i = 0; i < chs.length; i++) {
			char r = (char) (((int) chs[i]) ^ ((int) codes[k]));
			sCode += r;
			k++;
			if (k == codes.length) {
				k = 0;
			}
		}
		sCode = sCode.substring(0, iLength );
		sString=Base64.encodeString(sCode);
		return sString;
	}

	
	public static String decode(String sString, String sKey) {
		sString=new String(Base64.decodeString(sString));
		int iLength = sString.length();
		System.out.println("sString="+sString.length());
		sKey = toMD5(sKey);
		String sCode = "";
		char[] chs = sString.toCharArray();
		char[] codes = sKey.toCharArray();
		int k = 0;
		System.out.println("skey="+sKey);
		System.out.println("chs.lenth="+chs.length);
		System.out.println("codes.lenth="+codes.length);
		for (int i = 0; i < chs.length; i++) {
			
			char r = (char) (((int) chs[i]) ^ ((int) codes[k]));
			sCode += r;
			k++;
			if (k == codes.length) {
				k = 0;
			}
		}
		sCode = sCode.substring(0, iLength);
		return sCode;
	}

	public static String toMD5(String string) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(string.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			System.out.println(buf.toString().length());
			return buf.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
}