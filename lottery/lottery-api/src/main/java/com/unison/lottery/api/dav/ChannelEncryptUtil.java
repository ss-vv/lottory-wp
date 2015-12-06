package com.unison.lottery.api.dav;

import com.unison.lottery.api.vGroup.data.GroupSecretkey;
import com.xhcms.lottery.utils.DES;

public class ChannelEncryptUtil {
	
	public static void main(String[] args) throws Exception {
		String channel="1428484980147069+3815+ios-dav-1.0.0";
		String encryptStr=DES.encryptDES(channel, GroupSecretkey.secretKey, "utf-8");
		System.out.println("encryptStr="+encryptStr);
		
		encryptStr="to/6Uad8dbEpD4Z1u1Dnf7Q7Qmhqx5gk5l1OJZkfMMVgVYhFy5lz3Q==";
		channel=DES.decryptDES(encryptStr, GroupSecretkey.secretKey, "utf-8");
		System.out.println("channel="+channel);
		
		
	}

}
