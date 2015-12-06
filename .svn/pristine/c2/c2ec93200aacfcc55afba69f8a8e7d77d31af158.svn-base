package com.unison.lottery.pm.utils;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

public class UtilDatePropertiesEditor extends PropertyEditorSupport {

	private String format;

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		try {
			Date value = DateUtils.parseDate(text, new String[]{"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd"});
			this.setValue(value);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
}
