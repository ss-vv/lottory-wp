package com.davcai.lottery.platform.client.yuanchengchupiao.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * 一次查询请求响应
 * @author Next
 *
 */
public class GetBonusListResponse4OneLoop extends YuanChengChuPiaoResponse{
	
	/**
	 * 订单信息
	 */
	@JsonProperty(value="OrderInfo")
	private List<OrderInfo> orderInfo;

	public List<OrderInfo> getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(List<OrderInfo> orderInfo) {
		this.orderInfo = orderInfo;
	}
	
	
	
}
