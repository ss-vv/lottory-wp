package com.unison.lottery.api.protocol.response.model;

import java.math.BigDecimal;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown=true) 
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class SendRedEnvalopeResponse extends HaveReturnStatusResponse {
	private Long envalopeId;
	//描述 如 余额不足
	private String resultDesc;
	//祝福语
	private String greetings;
	private String fund;
	private String free;
	private String frozenFund;
	private String resultStatus;//0:成功，1：余额不足，请充值
	public Long getEnvalopeId() {
		return envalopeId;
	}

	public void setEnvalopeId(Long envalopeId) {
		this.envalopeId = envalopeId;
	}

	public String getResultStatus() {
		return resultStatus;
	}
	
	public String getFrozenFund() {
		return frozenFund;
	}

	public void setFrozenFund(String frozenFund) {
		this.frozenFund = frozenFund;
	}

	public void setResultStatus(String resultStatus) {
		this.resultStatus = resultStatus;
	}

	public String getResultDesc() {
		return resultDesc;
	}
	
	public String getFund() {
		return fund;
	}

	public void setFund(String fund) {
		this.fund = fund;
	}

	public String getFree() {
		return free;
	}

	public void setFree(String free) {
		this.free = free;
	}

	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}

	public String getGreetings() {
		return greetings;
	}

	public void setGreetings(String greetings) {
		this.greetings = greetings;
	}

	
	
}
