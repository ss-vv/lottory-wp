package com.davcai.lottery.platform.client.yuanchengchupiao.model;

/**
 * 订单奖金信息
 * @author Next
 *
 */
public class OrderInfo {
	/**
	 * 订单ID
	 */
	private String OrderId;
	/**
	 * 奖金
	 */
	private double Amount;
	
	
	
	public String getOrderId() {
		return OrderId;
	}
	public void setOrderId(String orderId) {
		OrderId = orderId;
	}
	public double getAmount() {
		return Amount;
	}
	public void setAmount(double amount) {
		Amount = amount;
	}
	
	
	
	
}
