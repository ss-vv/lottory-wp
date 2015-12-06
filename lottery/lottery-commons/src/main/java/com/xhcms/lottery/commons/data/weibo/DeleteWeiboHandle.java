package com.xhcms.lottery.commons.data.weibo;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.xhcms.commons.mq.XHMessage;

/**
 * 删除微博处理对象
 * @author haoxiang.jiang@unison.net.cn
 * @time 2014-5-13 下午1:53:02
 */
public class DeleteWeiboHandle implements XHMessage {

	private static final long serialVersionUID = -7447475123240417540L;

	public DeleteWeiboHandle(){
	}
	
	private long postid;
	public long ownerId;
	private double score;
	private String content;
	
	public DeleteWeiboHandle(long postid, long ownerId, double score,String content){
		this.postid = postid;
		this.ownerId = ownerId;
		this.score = score;
		this.content = content;
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


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}
}
