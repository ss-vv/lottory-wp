package com.davcai.lottery.platform.client.yuanchengchupiao.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class YuanChengChuPiaoQueryBalanceResponse extends YuanChengChuPiaoResponse{
   @JsonProperty(value="Balance")
   private String Balance;
	

	public String getBalance() {
		return Balance;
	}

	public void setBalance(String balance) {
		Balance = balance;
	}
}
