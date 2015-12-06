package com.xhcms.lottery.commons.data.weibo;

import java.util.Date;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.xhcms.commons.mq.XHMessage;

/**
 * @desc 提现消息对象
 * @author lei.li@unison.net.cn
 * @createTime 2014-6-19
 * @version 1.0
 */
public class WithdrawMessageHandler implements XHMessage {

	private static final long serialVersionUID = 1L;

	private String content;

	private int messageType;

	private long ownerId;

	private long peer;

	private Date createTime;
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, 
				ToStringStyle.MULTI_LINE_STYLE);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String getType() {
		return this.getClass().getSimpleName();
	}

	public long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}

	public long getPeer() {
		return peer;
	}

	public void setPeer(long peer) {
		this.peer = peer;
	}

	public int getMessageType() {
		return messageType;
	}

	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	public void setPriority(int arg0) {
	}
}