package com.unison.lottery.weibo.msg.model;

import java.util.List;

import redis.clients.johm.CollectionList;
import redis.clients.johm.Id;
import redis.clients.johm.Reference;
import redis.clients.johm.utils.ObjectHelper;

/**
 * 用户的微博关系类。
 * 
 * @author Yang Bo
 */
public class UserPosts {

	@Id
	private Long id;
	
	@Reference
	private WeiboUser user;
	
	/** 我的所有微博 */
	@CollectionList(of=Post.class)
	private List<Post> myPosts;
	
	/** 我好友和我的所有微博 */
	@CollectionList(of=Post.class)
	private List<Post> allPosts;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public WeiboUser getUser() {
		return user;
	}

	public void setUser(WeiboUser user) {
		this.user = user;
	}

	public List<Post> getMyPosts() {
		return myPosts;
	}

	public void setMyPosts(List<Post> myPosts) {
		this.myPosts = myPosts;
	}

	public List<Post> getAllPosts() {
		return allPosts;
	}

	public void setAllPosts(List<Post> allPosts) {
		this.allPosts = allPosts;
	}
	
	@Override
    public int hashCode() {
    	Object[] thisFields = new Object[]{
    			id,
    			allPosts,
    			myPosts,
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
        UserPosts other = (UserPosts) obj;

    	Object[] thisFields = new Object[]{
    			id,
    			allPosts,
    			myPosts,
    			user
    	};
    	
    	Object[] otherFields = new Object[]{
    			other.id,
    			other.allPosts,
    			other.myPosts,
    			other.user
    	};
    	return ObjectHelper.equals(thisFields, otherFields, this, obj);
    }

}
