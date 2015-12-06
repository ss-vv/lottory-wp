package com.xhcms.lottery.mc.sms;

import java.util.List;
import java.util.Map;

public class SMSSendRequest {
	
	public static final String VERIFY_CODE_KEY = "verifyCode";
	
	private Map<String,Object> params;
	private List<String> destPhones;
	public Map<String, Object> getParams() {
		return params;
	}
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
	public List<String> getDestPhones() {
		return destPhones;
	}
	public void setDestPhones(List<String> destPhones) {
		this.destPhones = destPhones;
	}

}
