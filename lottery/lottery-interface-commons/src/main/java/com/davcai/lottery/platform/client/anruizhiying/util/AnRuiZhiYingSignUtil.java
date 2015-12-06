package com.davcai.lottery.platform.client.anruizhiying.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;


public class AnRuiZhiYingSignUtil {

	public static String sign(List<String> toSignStrings, String key) {
		String result=null;
		if(null!=toSignStrings&&!toSignStrings.isEmpty()&&StringUtils.isNotBlank(key)){
			toSignStrings.add(key);
			String source=StringUtils.join(toSignStrings,"");
//			result=Text.MD5Encode(StringUtils.join(toSignStrings));
			result=DigestUtils.md5Hex(source);
			if(StringUtils.isNotBlank(result)){
				result=result.toUpperCase();
			}
		}
		return result;
	}

	public static boolean verifySign4OrderTicketResult(String signToCheck,
			String ticketOrderResultNotifyContent, String key, String channelId) {
		boolean result=false;
		if (StringUtils.isNotBlank(signToCheck)
				&& StringUtils.isNotBlank(ticketOrderResultNotifyContent)
				&& StringUtils.isNotBlank(key)
				&& StringUtils.isNotBlank(channelId)) {
			List<String> toSignStrings=new ArrayList<String>();
			toSignStrings.add(channelId);
			toSignStrings.add(ticketOrderResultNotifyContent);
			String sign= sign(toSignStrings,key);
			result=StringUtils.equals(signToCheck, sign);
		}
		return result;
	}

}
