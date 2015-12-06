package com.unison.lottery.mc.uni;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xhcms.lottery.utils.EncodingUtils;

/**
 * 中民的响应消息.
 * @author Yang Bo
 *
 */
public class ResponseMessage {
	private static Logger logger = LoggerFactory.getLogger(ResponseMessage.class);
	
	private String transcode;
	private String msg;
	private String key;
	private String parterid;
	
	
	public ResponseMessage(String transcode, String msg, String key,
			String parterid) {
		super();
		this.transcode = transcode;
		this.msg = msg;
		this.key = key;
		this.parterid = parterid;
	}
	
	public ResponseMessage() {
	}

	public String getTranscode() {
		return transcode;
	}
	public void setTranscode(String transcode) {
		this.transcode = transcode;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getParterid() {
		return parterid;
	}
	public void setParterid(String parterid) {
		this.parterid = parterid;
	}
	
	public String toString(){
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public static String digestMessage(String transcode, String msg, String apiKey){
		StringBuilder builder = new StringBuilder();
		builder.append(transcode).append(msg).append(apiKey);
		try {
			return EncodingUtils.md5StringWithUTF8(builder.toString());
		} catch (UnsupportedEncodingException e) {
			logger.error("Can not digest message: {}", msg, e);
			return StringUtils.EMPTY;
		}
	}

}
