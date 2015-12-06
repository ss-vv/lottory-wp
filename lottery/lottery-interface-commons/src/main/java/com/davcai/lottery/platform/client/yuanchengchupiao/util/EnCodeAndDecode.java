package com.davcai.lottery.platform.client.yuanchengchupiao.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

/**
 * 参数加密解密
 * @author Next
 *
 */
public class EnCodeAndDecode {
	
	public static String Key ="123456";
	/**
	 * 加密
	 * @param sString 源字符串
	 * @param sKey  密钥
	 * @return 加密后的字符串
	 */
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
		sString=Base64.encodeBase64URLSafeString(sCode.getBytes());
		return sString;
	}

	/**
	 * 解密方法
	 * @param sString 加密字符串
	 * @param sKey 密钥
	 * @return 解密后的字符串
	 */
	public static String decode(String sString, String sKey) {
		sString=new String(Base64.decodeBase64(sString));
	//	sString= new String(Base64.decodeBase64("SERAW1wSCRUVVARABgxUA1JTWV1GVkxTCRNWRwwEW1EAUhEeGlVBRQlCFQ8BShNdElRaFgg/Gz8XWV5WVWhFDAdUBhAUEm9CUlVTBm0TBlYHABVpTw=="));
		//sString="SERAW1wSCRUVVARABgxUA1JTWV1GVkxTCRNWRwwEW1EAUhEeGlVBRQlCFQ8BShNdElRaFgg/Gz8XWV5WVWhFDAdUBhAUEm9CUlVTBm0TBlYHABVpTw==";
		//sKey="123456sd3u7je74bnit2u0krnui0ke34";
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
	
	
	public static void main(String[] args) {
		System.out.println(decode("HhNVExYMQRsOG09QBAlRUlBHGRQIVUZEB1VVRAIaegsTUFwIACxBXVFLQBw=", EnCodeAndDecode.Key));
	}
}
