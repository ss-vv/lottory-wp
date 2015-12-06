package com.xhcms.lottery.commons.persist.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "lt_lottery_open_sale")
public class LotteryOpenSalePO implements Serializable {

	private static final long serialVersionUID = 8970963115029568219L;

	@Id
	private Long id;

	@Temporal(TemporalType.TIME)
	@Column(name = "openTime")
	private Date openTime;
	
	@Temporal(TemporalType.TIME)
	@Column(name = "endTime")
	private Date endTime;

	@Temporal(TemporalType.TIME)
	@Column(name = "machineOfftime")
	private Date machineOfftime;

	@Column(name = "dayOfWeek")
	private Integer dayOfWeek;

	@Column(name = "isEndTimeCrossDay", nullable = false)
	private Integer isEndTimeCrossDay;

	@Column(name = "isMachineOfftimeCrossDay", nullable = false)
	private Integer isMachineOfftimeCrossDay;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getOpenTime() {
		return openTime;
	}

	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getIsEndTimeCrossDay() {
		return isEndTimeCrossDay;
	}

	public void setIsEndTimeCrossDay(Integer isEndTimeCrossDay) {
		this.isEndTimeCrossDay = isEndTimeCrossDay;
	}

	public Integer getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(Integer dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public Date getMachineOfftime() {
		return machineOfftime;
	}

	public void setMachineOfftime(Date machineOfftime) {
		this.machineOfftime = machineOfftime;
	}

	public Integer getIsMachineOfftimeCrossDay() {
		return isMachineOfftimeCrossDay;
	}

	public void setIsMachineOfftimeCrossDay(Integer isMachineOfftimeCrossDay) {
		this.isMachineOfftimeCrossDay = isMachineOfftimeCrossDay;
	}
}
