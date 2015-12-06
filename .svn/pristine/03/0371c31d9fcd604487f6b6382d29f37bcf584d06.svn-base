package com.unison.lottery.api.registe.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckParamsUtil {

	/**
	   * 校验昵称
	   * @param username
	   * @return
	   */
	  public static boolean isCheckNickname(String nickname)
	  {
		 // String strPattern = "[A-Za-z0-9_-\u4e00-\u9fa5]{2,24}";
		  String strPattern = "[\u4e00-\u9fa5_a-zA-Z0-9_-]{2,24}";
		  
		  Pattern p = Pattern.compile(strPattern);
		  Matcher m = p.matcher(nickname);
		  return m.matches();

	  }
}
