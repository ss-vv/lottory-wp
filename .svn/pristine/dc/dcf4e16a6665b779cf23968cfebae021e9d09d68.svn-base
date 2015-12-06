package com.unison.lottery.weibo.bbs.data;

import java.io.Serializable;
import java.util.Date;

/**
 * 微博评论及回复
 * 
 * @author Wang Lei, Yang Bo
 */
public class Comment implements Serializable{

	private static final long serialVersionUID = 8177874967495400989L;

	private long id;			// 评论id
	private long authorId;		// 评论人uid
	private String content;		// 内容
	private long weiboMsgId;	// 评论所属的微博id
	private long repliedCommentId;	// 被回复的评论id,(非空时说明是评论的回复)
	private long praisedCount;		// 被赞的次数。
	
	private long replyedUserId;		// 被回复的用户id，是一个冗余字段，实际可以从 repliedCommentId 
									// 或者 weiboMsgId 对应的作者id得到。
									// 如果 repliedCommentId <=0 就表明是对微博的回复，
									// 所以被回复的用户id取 weiboMsgId 的作者id。
	
	private Date createTime; 		// 评论、回复的创建时间
	
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public long getWeiboMsgId() {
		return weiboMsgId;
	}
	public void setWeiboMsgId(long weiboMsgId) {
		this.weiboMsgId = weiboMsgId;
	}
	public long getRepliedCommentId() {
		return repliedCommentId;
	}
	public void setRepliedCommentId(long repliedCommentId) {
		this.repliedCommentId = repliedCommentId;
	}
	public long getPraisedCount() {
		return praisedCount;
	}
	public void setPraisedCount(long praisedCount) {
		this.praisedCount = praisedCount;
	}
	public long getAuthorId() {
		return authorId;
	}
	public void setAuthorId(long authorId) {
		this.authorId = authorId;
	}
	public long getReplyedUserId() {
		return replyedUserId;
	}
	public void setReplyedUserId(long replyedUserId) {
		this.replyedUserId = replyedUserId;
	}
}
