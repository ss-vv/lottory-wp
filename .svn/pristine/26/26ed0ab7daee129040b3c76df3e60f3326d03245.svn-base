package com.davcai.lottery.platform.client.yuanchengchupiao.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;

public class MessageIdGenerator {
	private static SimpleDateFormat sdf=new SimpleDateFormat("YYYYMMddHHmmss");
	public static String generateId(String apiId) {
		String timestr=sdf.format(new Date());
		return StringUtils.left(apiId, 4)+timestr+RandomStringUtils.randomAlphabetic(6);
	}

}
