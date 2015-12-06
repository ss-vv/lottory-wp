package com.davcai.lottery.push.common.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnore;

public  class PushMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1692603538353719679L;
	@JsonIgnore
	private boolean shouldUnsubscribe;
	
	/**
	 * 转换为json需要使用的dataMap对象
	 * @return
	 */
	public Map<String, Object> toJsonDataMap() {
		Map<String, Object> data = new HashMap<String, Object>();
		makeBasicDataMap(data);
		makeExtendDataMap(data);
		return data;
	}
	
	/**
	 * 转换为json需要subType属性
	 * @return
	 */
	public String toSubType() {
		return "1";//默认是竞足类型
	}

	protected  void makeExtendDataMap(Map<String, Object> data){
		
	}

	private void makeBasicDataMap(Map<String, Object> data) {
		data.put("type", getType());
		data.put("shouldUnsubscribe", shouldUnsubscribe);
	}

	public PushMessage() {
		super();
	}

	public  MessageType getType(){
		return null;
	}



	public boolean isShouldUnsubscribe() {
		return shouldUnsubscribe;
	}

	
	public String toString(){
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	/**
	 * 生成channel的名字
	 * @return
	 */
	public  String makeChannelName(){
		return null;
	}

	/**
	 * 检查是否在该条消息发出后，要求客户端取消订阅该消息所属的channel
	 * @return
	 */
	public  void checkAndSetShouldUnsubscribe(){
		
	}

	protected void setShouldUnsubscribe(boolean shouldUnsubscribe) {
		this.shouldUnsubscribe = shouldUnsubscribe;
	}

	public  String getId(){
		return null;
	}
	/**
	 * 检查是否消息的内容是一致的
	 * @param oldPushMessage
	 * @return
	 */
	public  boolean isSame(PushMessage oldPushMessage){
		return true;
	}

	public boolean isSameForHuanxin(PushMessage oldPushMessage) {
		return false;
	}

}