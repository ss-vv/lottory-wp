package com.unison.lottery.weibo.common.nosql.impl;

import java.util.Date;

import org.apache.commons.beanutils.Converter;

public class RedisPropertyConverter implements Converter {

	@SuppressWarnings("rawtypes")
	@Override
	public Object convert(Class type, Object value) {
		
		if(type.equals(String.class)){
			if(value instanceof Date){
				Date date=(Date) value;
				return ""+date.getTime();
			}
			else{
				return null==value?"":value.toString();
			}
		}
		return null;
	}

}
