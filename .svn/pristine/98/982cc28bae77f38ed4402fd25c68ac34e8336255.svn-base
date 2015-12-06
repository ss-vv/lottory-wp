package com.unison.lottery.weibo.msg.model;

import java.util.Set;

import redis.clients.johm.CollectionSet;
import redis.clients.johm.Id;
import redis.clients.johm.Indexed;
import redis.clients.johm.Model;
import redis.clients.johm.Reference;
import redis.clients.johm.utils.ObjectHelper;

/**
 * 微博订阅关系.
 * 
 * @author Yang Bo
 */
@Model
public class UserRelationship {

	@Id
	private Long id;
	
	/** 关系主人 ，即“我” */
	@Reference
	@Indexed
	private WeiboUser host;
	
	/** 我的粉丝 */
	@CollectionSet(of=WeiboUser.class)
	private Set<WeiboUser> fans;
	
	/** 我关注的人 */
	@CollectionSet(of=WeiboUser.class)
	private Set<WeiboUser> followedUsers;

	public WeiboUser getHost() {
		return host;
	}

	public void setHost(WeiboUser host) {
		this.host = host;
	}

	public Set<WeiboUser> getFans() {
		return fans;
	}

	public void setFans(Set<WeiboUser> fans) {
		this.fans = fans;
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
    			fans,
    			followedUsers,
    			host
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
        UserRelationship other = (UserRelationship) obj;

    	Object[] thisFields = new Object[]{
    			id,
    			fans,
    			followedUsers,
    			host
    	};
    	
    	Object[] otherFields = new Object[]{
    			other.id,
    			other.fans,
    			other.followedUsers,
    			other.host
    	};
    	return ObjectHelper.equals(thisFields, otherFields, this, obj);
    }

	public Set<WeiboUser> getFollowedUsers() {
		return followedUsers;
	}

	public void setFollowedUsers(Set<WeiboUser> followedUsers) {
		this.followedUsers = followedUsers;
	}
}
