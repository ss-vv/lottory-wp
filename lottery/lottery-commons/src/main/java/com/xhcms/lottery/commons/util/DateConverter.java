package com.xhcms.lottery.commons.util;

import java.util.Date;

import org.apache.commons.beanutils.Converter;

public class DateConverter implements Converter {

	@Override
	public Object convert(Class arg0, Object value) {
		if (value instanceof Long) {
			Long longValue = (Long) value;
			return new Date(longValue.longValue());
		  }
		return null;
	}

}
