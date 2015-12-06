package com.unison.lottery.api;

import com.xhcms.commons.util.Text;

public class PasswordUtil {
	
	public static void main(String[] args){
		String pwdMD5=Text.MD5Encode("password");
		System.out.println("pwdMD5="+pwdMD5);
	}

}
