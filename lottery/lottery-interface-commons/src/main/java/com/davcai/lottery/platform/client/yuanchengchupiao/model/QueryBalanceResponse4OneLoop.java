package com.davcai.lottery.platform.client.yuanchengchupiao.model;

import java.util.List;

/**
 * 一轮查询余额的响应
 * @author liwen
 *
 * 2015年7月10日
 */
public class QueryBalanceResponse4OneLoop {
	private String error;
	private String message;
	private String Balance;
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getBalance() {
		return Balance;
	}
	public void setBalance(String balance) {
		Balance = balance;
	}
	
	
}
