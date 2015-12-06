package com.davcai.lottery.platform.client.anruizhiying.util;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.StringUtils;

public class AnRuiZhiYingBatchIdUtil {
	
	private static AtomicInteger suffix=new AtomicInteger(0);

	public static BigInteger getBatchId(Date date) {
		if(null==date){
			return null;
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String prefix = sdf.format(date);
		String suffix=getSuffix();
		BigInteger result=new BigInteger(prefix+suffix);
		return result;
	}

	private static String getSuffix() {
		int i=suffix.getAndIncrement();
		if(i==999){
			suffix=new AtomicInteger(0);
		}
		return StringUtils.leftPad(Integer.toString(suffix.get()), 3, "0");
	}

}

