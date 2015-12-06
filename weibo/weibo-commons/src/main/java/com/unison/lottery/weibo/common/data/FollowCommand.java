package com.unison.lottery.weibo.common.data;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.xhcms.commons.mq.XHMessage;

/**
 * 关注、取消关注命令
 * 
 * @author Yang Bo
 */
public class FollowCommand implements XHMessage {

	private static final long serialVersionUID = 8043884855743349834L;

	/** 粉丝的微博用户id */
	long followerId;
	
	/** 被关注人的微博用户id */
	long followingId;
	
	/** true 加关注，false 取消关注 */
	boolean isFollowing;
	
	public FollowCommand(long followerId, long followingId, boolean isFollowing) {
		this.followerId = followerId;
		this.followingId = followingId;
		this.isFollowing = isFollowing;
	}

	public long getFollowerId() {
		return followerId;
	}

	public void setFollowerId(long followerId) {
		this.followerId = followerId;
	}

	public long getFollowingId() {
		return followingId;
	}

	public void setFollowingId(long followingId) {
		this.followingId = followingId;
	}

	public boolean isFollowing() {
		return isFollowing;
	}

	public void setFollowing(boolean isFollowing) {
		this.isFollowing = isFollowing;
	}

	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	public String getType() {
		return this.getClass().getSimpleName();
	}

	@Override
	public void setPriority(int arg0) {
	}

	public String toString(){
    	return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
