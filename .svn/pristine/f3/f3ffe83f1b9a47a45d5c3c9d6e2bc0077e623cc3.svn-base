package com.unison.lottery.mc.uni.parser;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 封装一轮 QueryParser 解析过程的状态。<br/>
 * 
 * 目的是让 MessageParser 变为线程安全类。
 * 
 * @author Yang Bo
 */
abstract public class ParserStatus {
	// 被解析消息的错误码。来自中民接口中的 transcode 字段。
	private int errorCode;

	/**
	 * 在解析消息时被设置。正常应该是响应消息的transcode，
	 * @return
	 */
	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
	public String toString(){
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
