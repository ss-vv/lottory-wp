package com.xhcms.lottery.commons.data;

/**
 * @desc 活动公告数据实体
 * @createTime 2012-12-5
 * @author lei.li@unison.net.cn
 * @version 1.0
 */
public class ActivityNotify {
	
	private Long id;
	
	private String activityName;
	
	private String activityDesc;
	
	private int status;
	
	private Long sequenceNumber;
	
	private Long promotionId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getActivityDesc() {
		return activityDesc;
	}
	public void setActivityDesc(String activityDesc) {
		this.activityDesc = activityDesc;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Long getSequenceNumber() {
		return sequenceNumber;
	}
	public void setSequenceNumber(Long sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	public Long getPromotionId() {
		return promotionId;
	}
	public void setPromotionId(Long promotionId) {
		this.promotionId = promotionId;
	}
}