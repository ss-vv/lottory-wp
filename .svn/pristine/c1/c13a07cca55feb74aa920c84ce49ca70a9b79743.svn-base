package com.xhcms.lottery.commons.data.weibo;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.xhcms.commons.mq.XHMessage;

/**
 * 发送关注我的人传送对象
 * 
 * @author Wang Lei
 * 
 */
public class PublishFollowersHandle implements XHMessage {

	private static final long serialVersionUID = -7447475478240417540L;

	public PublishFollowersHandle(){
	}
	
	private long postid;
	public long ownerId;
	private double score;
	
	public PublishFollowersHandle(long postid, long ownerId, double score){
		this.postid = postid;
		this.ownerId = ownerId;
		this.score = score;
	}
	
	
	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.MULTI_LINE_STYLE);
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


	public long getPostid() {
		return postid;
	}


	public void setPostid(long postid) {
		this.postid = postid;
	}


	public long getOwnerId() {
		return ownerId;
	}


	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}


	public double getScore() {
		return score;
	}


	public void setScore(double score) {
		this.score = score;
	}
}
