package com.unison.lottery.weibo.msg.model;

import java.io.Serializable;
import java.util.List;

import redis.clients.johm.CollectionList;
import redis.clients.johm.Id;
import redis.clients.johm.Model;
import redis.clients.johm.Reference;
import redis.clients.johm.utils.ObjectHelper;

/**
 * 微博用户的评论关系类。
 * 
 * @author Yang Bo
 */
@Model
public class UserComments implements Serializable {
	
	private static final long serialVersionUID = -2333514840634068090L;

	@Id
	private Long id;
	
	@Reference
	private WeiboUser user;
	
	/** 所有评论我的 */
	@CollectionList(of=Comment.class)
	private List<Comment> allComments;
	
	/** 直接回复我的 */
	@CollectionList(of=Comment.class)
	private List<Comment> directReplies;
	
	/** 我关注的人评论我的 */
	@CollectionList(of=Comment.class)
	private List<Comment> followingComments;

	public WeiboUser getUser() {
		return user;
	}

	public void setUser(WeiboUser user) {
		this.user = user;
	}

	public List<Comment> getAllComments() {
		return allComments;
	}

	public void setAllComments(List<Comment> allComments) {
		this.allComments = allComments;
	}

	public List<Comment> getDirectReplies() {
		return directReplies;
	}

	public void setDirectReplies(List<Comment> directReplies) {
		this.directReplies = directReplies;
	}

	public List<Comment> getFollowingComments() {
		return followingComments;
	}

	public void setFollowingComments(List<Comment> followingComments) {
		this.followingComments = followingComments;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    @Override
    public int hashCode() {
    	Object[] thisFields = new Object[]{
    			id,
    			allComments,
    			directReplies,
    			followingComments,
    			user
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
        UserComments other = (UserComments) obj;

    	Object[] thisFields = new Object[]{
    			id,
    			allComments,
    			directReplies,
    			followingComments,
    			user
    	};
    	
    	Object[] otherFields = new Object[]{
    			other.id,
    			other.allComments,
    			other.directReplies,
    			other.followingComments,
    			other.user
    	};
    	return ObjectHelper.equals(thisFields, otherFields, this, obj);
    }
}
