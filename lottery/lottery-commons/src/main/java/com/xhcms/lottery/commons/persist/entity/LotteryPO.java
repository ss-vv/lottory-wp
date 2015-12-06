package com.xhcms.lottery.commons.persist.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "lt_lottery")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LotteryPO implements Serializable {

	private static final long serialVersionUID = -1709920331564030425L;

	@Id
	private String id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false, name = "win_level")
	private int winLevel;

	@Column(name = "help_url")
	private String helpUrl;

	@Column(nullable = false, name = "created_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdTime;

	private String note;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWinLevel() {
		return winLevel;
	}

	public void setWinLevel(int winLevel) {
		this.winLevel = winLevel;
	}

	public String getHelpUrl() {
		return helpUrl;
	}

	public void setHelpUrl(String helpUrl) {
		this.helpUrl = helpUrl;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
