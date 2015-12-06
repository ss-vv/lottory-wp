package com.xhcms.lottery.commons.data.weibo;

import java.util.Date;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.xhcms.commons.mq.XHMessage;
import com.xhcms.lottery.lang.SchemeType;

/**
 * @desc 方案缓存更新消息
 * @createTime 2014-7-17
 * @author lei.li@unison.net.cn
 * @version 1.0
 */
public class SchemeCacheUpdateMessageHandler implements XHMessage {

	private static final long serialVersionUID = 1L;

	private long schemeId;

	private SchemeType schemeType;

	/** 方案显示类型：晒单，跟单，合买 */
	private int showType;

	private int messageType;

	private Date createTime;

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

	public long getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(long schemeId) {
		this.schemeId = schemeId;
	}

	public SchemeType getSchemeType() {
		return schemeType;
	}

	public void setSchemeType(SchemeType schemeType) {
		this.schemeType = schemeType;
	}

	public int getShowType() {
		return showType;
	}

	public void setShowType(int showType) {
		this.showType = showType;
	}
}