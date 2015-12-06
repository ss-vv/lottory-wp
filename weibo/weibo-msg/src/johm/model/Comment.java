package com.unison.lottery.weibo.msg.model;

import java.util.Date;

import redis.clients.johm.Attribute;
import redis.clients.johm.Id;
import redis.clients.johm.Model;
import redis.clients.johm.Reference;
import redis.clients.johm.utils.ObjectHelper;

/**
 * 评论及回复
 * 
 * @author Yang Bo
 */
@Model
public class Comment {
    @Id
    private Long id;					// 评论 nosql id.
    
    @Reference
    private WeiboUser author;			// 评论人
    
    @Attribute
	private Date createTime; 			// 评论、回复的创建时间
    
    @Attribute
	private String content;				// 内容
    
    @Reference
	private Post commentedPost;			// 被评论的微博
    
    @Reference
	private Comment repliedComment;		// 被回复的评论, (非空时说明是评论的回复)
    
    @Attribute
	private long praisedCount;			// 评论被赞的次数。

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public WeiboUser getAuthor() {
		return author;
	}

	public void setAuthor(WeiboUser author) {
		this.author = author;
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

	public Post getCommentedPost() {
		return commentedPost;
	}

	public void setCommentedPost(Post commentedPost) {
		this.commentedPost = commentedPost;
	}

	public long getPraisedCount() {
		return praisedCount;
	}

	public void setPraisedCount(long praisedCount) {
		this.praisedCount = praisedCount;
	}

	public Comment getRepliedComment() {
		return repliedComment;
	}

	public void setRepliedComment(Comment repliedComment) {
		this.repliedComment = repliedComment;
	}

    @Override
    public int hashCode() {
    	Object[] thisFields = new Object[]{
    			id,
    			author,
    			commentedPost,
    			content,
    			createTime,
    			praisedCount,
    			repliedComment
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
        Comment other = (Comment) obj;

    	Object[] thisFields = new Object[]{
    			id,
    			author,
    			commentedPost,
    			content,
    			createTime,
    			praisedCount,
    			repliedComment
    	};
    	
    	Object[] otherFields = new Object[]{
    			other.id,
    			other.author,
    			other.commentedPost,
    			other.content,
    			other.createTime,
    			other.praisedCount,
    			other.repliedComment
    	};
    	return ObjectHelper.equals(thisFields, otherFields, this, obj);
    }
}
