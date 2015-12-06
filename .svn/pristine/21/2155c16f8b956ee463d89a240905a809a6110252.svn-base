package com.xhcms.lottery.mc.sms;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SMSSendResult {
	
	private SMSSendResultStatus status;
	private String message;
	private List<String> failDestPhones;
	private List<String> succDestPhones;
	private List<String> wrongFormatDestPhones;
	
	public String toString(){
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public SMSSendResultStatus getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setStatus(SMSSendResultStatus status) {
		this.status = status;
	}


	public List<String> getFailDestPhones() {
		return failDestPhones;
	}

	public void setFailDestPhones(List<String> failDestPhone) {
		this.failDestPhones = failDestPhone;
	}

	public List<String> getSuccDestPhones() {
		return succDestPhones;
	}

	public void setSuccDestPhones(List<String> succDestPhones) {
		this.succDestPhones = succDestPhones;
	}

	public List<String> getWrongFormatDestPhones() {
		return wrongFormatDestPhones;
	}

	public void setWrongFormatDestPhones(List<String> wrongFormatDestPhones) {
		this.wrongFormatDestPhones = wrongFormatDestPhones;
	}

}
