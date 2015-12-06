package com.xhcms.lottery.commons.data;

import java.io.Serializable;
import java.util.Date;

public class CacheUserScore implements Serializable {

	private static final long serialVersionUID = 4749523155924366826L;
	
	// 发起人晒单战绩
	private long showScore;
	// 发起人合买战绩
	private long groupScore;
	
	private Date createdTime;
	
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public long getShowScore() {
		return showScore;
	}
	public void setShowScore(long showScore) {
		this.showScore = showScore;
	}
	public long getGroupScore() {
		return groupScore;
	}
	public void setGroupScore(long groupScore) {
		this.groupScore = groupScore;
	}
}
