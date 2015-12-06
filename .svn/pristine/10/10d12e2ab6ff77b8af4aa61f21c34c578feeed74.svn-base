package com.unison.lottery.weibo.data;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.xhcms.commons.mq.XHMessage;

/**
 * 微博消息传送对象
 * 
 * @author Wang Lei
 * 
 */
public class WeiboMsgCommond implements XHMessage {

	private static final long serialVersionUID = 3614372292630846109L;
	/**
	 * 微博内容
	 */
	private WeiboMsg weiboMsg;
	
	/**
	 * 大V彩用户ID，只在大V彩系统发微博时使用
	 */
	private long lacaiUserId;

	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	public String getType() {
		return this.getClass().getSimpleName();
	}

	@Override
	public void setPriority(int arg0) {

	}

	public WeiboMsg getWeiboMsg() {
		return weiboMsg;
	}

	public void setWeiboMsg(WeiboMsg weiboMsg) {
		this.weiboMsg = weiboMsg;
	}

	public long getLacaiUserId() {
		return lacaiUserId;
	}

	public void setLacaiUserId(long lacaiUserId) {
		this.lacaiUserId = lacaiUserId;
	}

}
