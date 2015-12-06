package com.xhcms.lottery.commons.data.weibo;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.xhcms.commons.mq.XHMessage;

public class PushPrivateMsgToAllHandle implements XHMessage{
	
	private static final long serialVersionUID = 1L;
	private String privateMsgId;
	
	public PushPrivateMsgToAllHandle(String privateMsgId){
		this.privateMsgId = privateMsgId;
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
	
	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

	public String getPrivateMsgId() {
		return privateMsgId;
	}
	public void setPrivateMsgId(String privateMsgId) {
		this.privateMsgId = privateMsgId;
	}
}
