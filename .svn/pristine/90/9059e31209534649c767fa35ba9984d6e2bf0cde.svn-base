package com.unison.lottery.api.framework.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unison.lottery.api.protocol.common.Constants;



public class PhoneNumberUtil {

	private static final Logger logger=LoggerFactory.getLogger(PhoneNumberUtil.class);

	public static String normalPhoneNumber(String userPhone) {
		String result=userPhone;
		if(StringUtils.isNotBlank(userPhone)){
			if(userPhone.length()>Constants.PHONE_NO_LEN){
				int length=userPhone.length();
				result=userPhone.substring(length-Constants.PHONE_NO_LEN, length);
				logger.debug("手机号"+userPhone+"被规范化为"+result);
			}
		}
		else{
			result="N/A";
		}
		return result;
	}

}
