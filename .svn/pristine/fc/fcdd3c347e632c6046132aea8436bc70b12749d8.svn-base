package com.unison.lottery.weibo.data.vo;

import com.unison.lottery.weibo.common.redis.Reference;
import com.unison.lottery.weibo.data.Comment;
import com.unison.lottery.weibo.data.WeiboMsg;
import com.unison.lottery.weibo.data.WeiboUser;

/**
 * 评论及回复VO对象，包括关联属性，显示层用。
 * 
 * @author Yang Bo
 */
public class CommentVO extends Comment {

	private static final long serialVersionUID = 3358341518818117521L;

	@Reference
	private WeiboMsg weiboMsg;
	
	/** 被回复的用户，可以是原始微博的作者，也可能是评论的作者 */
	@Reference
	private WeiboUser repliedUser;
	
	/** 本条评论的作者 */
	@Reference
	private WeiboUser author;
	
	/** 被回复的评论 */
	@Reference
	private Comment repliedComment;
	
	/** 是否被本用户评论过,本用户指当前登陆用户 */
	private boolean praised;
	
	/** 被赞总数 */
	private long praisedCount;
	
	public WeiboUser getAuthor() {
		return author;
	}
	public void setAuthor(WeiboUser author) {
		this.author = author;
	}
	
	public Comment getRepliedComment() {
		return repliedComment;
	}
	public void setRepliedComment(Comment comment) {
		this.repliedComment = comment;
	}
	public WeiboUser getRepliedUser() {
		return repliedUser;
	}
	public void setRepliedUser(WeiboUser repliedUser) {
		this.repliedUser = repliedUser;
	}
	public WeiboMsg getWeiboMsg() {
		return weiboMsg;
	}
	public void setWeiboMsg(WeiboMsg weiboMsg) {
		this.weiboMsg = weiboMsg;
	}
	public boolean isPraised() {
		return praised;
	}
	public void setPraised(boolean praised) {
		this.praised = praised;
	}
	public long getPraisedCount() {
		return praisedCount;
	}
	public void setPraisedCount(long praiseCount) {
		this.praisedCount = praiseCount;
	}
}
