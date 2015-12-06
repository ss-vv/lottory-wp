package com.unison.lottery.weibo.data;

import java.io.Serializable;
import java.util.Date;

/**
 * 话题、专题
 * 
 * @author Yang Bo
 */
public class Topic implements Serializable {

	private static final long serialVersionUID = 4442743671945283738L;

	private long id;
	
	// 排序序号，大数在前，默认为 topic的id
	private long sortIndex;
	private String title;
	private String description;
	
	// 话题的真正内容，即微博的id
	private long weiboId;
	private String picUrl;
	private String tag;
	private TopicType type;
	
	// topic 创建时间，不同与微博创建时间
	private Date createTime;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getWeiboId() {
		return weiboId;
	}
	public void setWeiboId(long weiboId) {
		this.weiboId = weiboId;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public TopicType getType() {
		return type;
	}
	public void setType(TopicType type) {
		this.type = type;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public long getSortIndex() {
		return sortIndex;
	}
	public void setSortIndex(long sortIndex) {
		this.sortIndex = sortIndex;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
}
