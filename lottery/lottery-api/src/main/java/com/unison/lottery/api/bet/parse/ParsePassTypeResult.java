package com.unison.lottery.api.bet.parse;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ParsePassTypeResult {

	private String sourcePassType;
	private String convertPassType;
	private boolean convertSuccess;
	private boolean skipLimit;
	private String betType;

	public ParsePassTypeResult() {
		super();
	}
	
	public ParsePassTypeResult(String sourcePassType, String convertPassType) {
		super();
		this.sourcePassType = sourcePassType;
		this.convertPassType = convertPassType;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
	
	public String getSourcePassType() {
		return sourcePassType;
	}

	public void setSourcePassType(String sourcePassType) {
		this.sourcePassType = sourcePassType;
	}

	public String getConvertPassType() {
		return convertPassType;
	}

	public void setConvertPassType(String convertPassType) {
		this.convertPassType = convertPassType;
	}

	public boolean isConvertSuccess() {
		return convertSuccess;
	}

	public void setConvertSuccess(boolean convertSuccess) {
		this.convertSuccess = convertSuccess;
	}

	public boolean isSkipLimit() {
		return skipLimit;
	}

	public void setSkipLimit(boolean skipLimit) {
		this.skipLimit = skipLimit;
	}

	public String getBetType() {
		return betType;
	}

	public void setBetType(String betType) {
		this.betType = betType;
	}
}