package com.unison.lottery.weibo.msg.model;

import java.io.Serializable;
import java.util.Date;

import redis.clients.johm.Attribute;
import redis.clients.johm.Id;
import redis.clients.johm.Model;
import redis.clients.johm.Reference;
import redis.clients.johm.utils.ObjectHelper;

/**
 * 微博内容
 * 
 * @author Yang Bo
 */
@Model
public class Post implements Serializable {
	
	private static final long serialVersionUID = 1516629359206094070L;

	@Id
	/** 微博id */
	private Long id;
	
	@Reference
	/** 原微博 */
	private Post originalPost;
	
	@Reference
	/** 作者 */
	private WeiboUser author;
	
	@Attribute
	/** 标题 */
	private String title;
	
	@Attribute
	/** 内容 */
	private String content;
	
	@Attribute
	/** 转发次数 */
	private int forwardCount;
	
	@Attribute
	/** 评论次数 */
	private int commentCount;
	
	@Attribute
	/** 收藏次数 */
	private int favoriateCount;
	
	@Attribute
	/** 分享次数 */
	private int shareCount;
	
	@Attribute
	/** 赞数量 */
	private int praiseCount;
	
	@Attribute
	/** 来源 */
	private String from;
	
	@Attribute
	/** 创建时间 */
	private Date createdTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Post getOriginalPost() {
		return originalPost;
	}

	public void setOriginalPost(Post originalPost) {
		this.originalPost = originalPost;
	}

	public WeiboUser getAuthor() {
		return author;
	}

	public void setAuthor(WeiboUser author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getForwardCount() {
		return forwardCount;
	}

	public void setForwardCount(int forwardCount) {
		this.forwardCount = forwardCount;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	public int getFavoriateCount() {
		return favoriateCount;
	}

	public void setFavoriateCount(int favoriateCount) {
		this.favoriateCount = favoriateCount;
	}

	public int getShareCount() {
		return shareCount;
	}

	public void setShareCount(int shareCount) {
		this.shareCount = shareCount;
	}

	public int getPraiseCount() {
		return praiseCount;
	}

	public void setPraiseCount(int praiseCount) {
		this.praiseCount = praiseCount;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	
    @Override
    public int hashCode() {
    	Object[] thisFields = new Object[]{
    			id,
    			author,
    			commentCount,
    			content,
    			createdTime,
    			favoriateCount,
    			forwardCount,
    			from,
    			originalPost,
    			praiseCount,
    			shareCount,
    			title
    	};
    	return ObjectHelper.hashCode(thisFields);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Post other = (Post) obj;

    	Object[] thisFields = new Object[]{
    			id,
    			author,
    			commentCount,
    			content,
    			createdTime,
    			favoriateCount,
    			forwardCount,
    			from,
    			originalPost,
    			praiseCount,
    			shareCount,
    			title
    	};
    	
    	Object[] otherFields = new Object[]{
    			other.id,
    			other.author,
    			other.commentCount,
    			other.content,
    			other.createdTime,
    			other.favoriateCount,
    			other.forwardCount,
    			other.from,
    			other.originalPost,
    			other.praiseCount,
    			other.shareCount,
    			other.title
    	};
    	return ObjectHelper.equals(thisFields, otherFields, this, obj);
    }
}
