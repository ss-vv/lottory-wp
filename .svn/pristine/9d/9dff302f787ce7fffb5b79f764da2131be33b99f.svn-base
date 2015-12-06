package com.xhcms.lottery.commons.persist.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

@Entity
@Table(name = "lt_bet_scheme")
public class BetSchemePO extends BetSchemePOBase {

	private static final long serialVersionUID = 6861608300564486230L;

	public BetSchemePO() {
	}

	/**
	 * 是否预兑奖 0 否 1 是
	 */
	@Column(name = "preset_award")
	private int isPresetAward;

	/**
	 * 是否发不过晒单
	 */
	@Column(name = "is_publish_show")
	private int isPublishShow;

	public int getIsPresetAward() {
		return isPresetAward;
	}

	public void setIsPresetAward(int isPresetAward) {
		this.isPresetAward = isPresetAward;
	}

	public int getIsPublishShow() {
		return isPublishShow;
	}

	public void setIsPublishShow(int isPublishShow) {
		this.isPublishShow = isPublishShow;
	}
	
	@Formula("DATE_FORMAT(created_time,'%Y-%m-%d %H:%i')")
	private String newCreatedTime;

	public String getNewCreatedTime() {
		return newCreatedTime;
	}

	public void setNewCreatedTime(String newCreatedTime) {
		this.newCreatedTime = newCreatedTime;
	}
}
