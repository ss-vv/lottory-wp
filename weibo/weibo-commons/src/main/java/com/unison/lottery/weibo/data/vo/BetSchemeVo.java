package com.unison.lottery.weibo.data.vo;

import com.xhcms.lottery.commons.data.BetScheme;

public class BetSchemeVo extends BetScheme{

	private static final long serialVersionUID = -2147331208987926772L;
	private String nikeName;
	private Integer commentCount;
	private Integer likeCount;
	private String weiboUserId;
	private String weiboId;
	public String getNikeName() {
		return nikeName;
	}
	public void setNikeName(String nikeName) {
		this.nikeName = nikeName;
	}
	public Integer getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}
	public Integer getLikeCount() {
		return likeCount;
	}
	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}
	public String getWeiboUserId() {
		return weiboUserId;
	}
	public void setWeiboUserId(String weiboUserId) {
		this.weiboUserId = weiboUserId;
	}
	public String getWeiboId() {
		return weiboId;
	}
	public void setWeiboId(String weiboId) {
		this.weiboId = weiboId;
	}
	
	
}
