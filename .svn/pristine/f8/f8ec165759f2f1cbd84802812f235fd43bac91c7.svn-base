package com.unison.lottery.weibo.data;

import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @desc 简单私信对象
 * @author lei.li@unison.net.cn
 * @createTime 2013-10-24
 * @version 1.0
 * @updatedTime 2014-6-19
 */
public class PrivateMessage {

	// 私信ID
	private long id;

	// 发信人ID
	private long ownerId;

	// 收信人ID
	private long peer;

	// 私信的内容
	private String content;

	/*
	 * 私信类型（提款，派奖，普通私信，系统 消息）
	 * 属性值，参考：PrivateMessageType 类
	 */
	private int type;

	// 创建时间
	private Date createTime;

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

	public long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public long getPeer() {
		return peer;
	}

	public void setPeer(long peer) {
		this.peer = peer;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
