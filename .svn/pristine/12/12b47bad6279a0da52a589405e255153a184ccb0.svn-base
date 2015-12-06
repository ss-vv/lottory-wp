package com.xhcms.lottery.commons.data.weibo;

import java.util.Date;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.xhcms.commons.mq.XHMessage;

/**
 * @desc 推荐方案消息对象
 * @author lei.li@unison.net.cn
 * @createTime 2014-3-31
 * @version 1.0
 */
public class RecommendSchemeMessage implements XHMessage {

	private static final long serialVersionUID = 3614372232630846109L;

	private long schemeId;

	private long weiboUserId;

	private String weiboContent;
	private String lotteryId;

	private Date createdTime;

	public RecommendSchemeMessage() {
	}

	public RecommendSchemeMessage(long id) {
		this.setSchemeId(id);
	}

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

	public long getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(long schemeId) {
		this.schemeId = schemeId;
	}

	public long getWeiboUserId() {
		return weiboUserId;
	}

	public void setWeiboUserId(long weiboUserId) {
		this.weiboUserId = weiboUserId;
	}

	public String getWeiboContent() {
		return weiboContent;
	}

	public void setWeiboContent(String weiboContent) {
		this.weiboContent = weiboContent;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}
}