package com.xhcms.lottery.commons.data.weibo;

import java.util.Date;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.xhcms.commons.mq.XHMessage;

/**
 * @desc 中奖方案消息对象
 * @author lei.li@unison.net.cn
 * @createTime 2014-7-3
 * @version 1.0
 */
public class WinningMessageHandler implements XHMessage {

	private static final long serialVersionUID = 1L;

	private int messageType;

	private Date createTime;

	/** 中奖方案ID */
	private Long schemeId;

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

	@Override
	public String getType() {
		return this.getClass().getSimpleName();
	}

	public int getMessageType() {
		return messageType;
	}

	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}

	public Long getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(Long schemeId) {
		this.schemeId = schemeId;
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