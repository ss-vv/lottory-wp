package com.xhcms.lottery.commons.data.weibo;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.xhcms.commons.mq.XHMessage;

public class PublishWeiboNewsHandle implements XHMessage{
	private static final long serialVersionUID = 1L;
	private long postid;
	public long ownerId;
	private double score;
	private String newsType;
	private String teamId;
	public PublishWeiboNewsHandle(){
		
	}
	public PublishWeiboNewsHandle(long postid,long ownerId, double score,String newsType,String teamId){
		this.postid = postid;
		this.ownerId = ownerId;
		this.score = score;
		this.newsType = newsType;
		this.teamId = teamId;
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
	
	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.MULTI_LINE_STYLE);
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
	public String getNewsType() {
		return newsType;
	}
	public void setNewsType(String newsType) {
		this.newsType = newsType;
	}
	public String getTeamId() {
		return teamId;
	}
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
}
