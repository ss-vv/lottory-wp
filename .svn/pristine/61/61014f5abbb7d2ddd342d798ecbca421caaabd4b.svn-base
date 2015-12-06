package com.unison.lottery.api.framework.utils;

import java.security.MessageDigest;

public class MD5Utils {
	
	/**
	 * 将原始的字符串做MD5,字符串应该是utf8编码的
	 * @param orignalString
	 * @return
	 */
	public static String md5(String str) {
		try
	    {
	      byte[] res = str.getBytes("utf-8");
	      MessageDigest md = MessageDigest.getInstance("MD5".toUpperCase());
	      byte[] result = md.digest(res);
	      for (int i = 0; i < result.length; i++)
	      {
	        md.update(result[i]);
	      }
	      byte[] hash = md.digest();

	      StringBuffer d = new StringBuffer("");
	      for (int i = 0; i < hash.length; i++)
	      {
	        int v = hash[i] & 0xFF;
	        if (v < 16)
	          d.append("0");
	        d.append(Integer.toString(v, 16).toUpperCase() + "");
	      }
	      return d.toString();
	    }
	    catch (Exception e)
	    {
	      return null;
	    }
	}
	
	public static String md5(byte[] bytes){
		try
	    {
	      
	      MessageDigest md = MessageDigest.getInstance("MD5".toUpperCase());
	      byte[] result = md.digest(bytes);
	      for (int i = 0; i < result.length; i++)
	      {
	        md.update(result[i]);
	      }
	      byte[] hash = md.digest();

	      StringBuffer d = new StringBuffer("");
	      for (int i = 0; i < hash.length; i++)
	      {
	        int v = hash[i] & 0xFF;
	        if (v < 16)
	          d.append("0");
	        d.append(Integer.toString(v, 16).toUpperCase() + "");
	      }
	      return d.toString();
	    }
	    catch (Exception e)
	    {
	      return null;
	    }
	}

}
