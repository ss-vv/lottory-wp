package com.davcai.data.statistic.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
/**
 * 各种top5排行榜数据
 * @author 95
 *
 */
public class Top5Recommend {
	
	private long userId;
	private int countOfRecommend;
	private int countOfHit;
	private float ratio;
	private String topType;
	private String dimension;
	private int sequenceNumber;
	private long id;
	private double bonus;
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public int getCountOfRecommend() {
		return countOfRecommend;
	}
	public void setCountOfRecommend(int countOfRecommend) {
		this.countOfRecommend = countOfRecommend;
	}
	public int getCountOfHit() {
		return countOfHit;
	}
	public void setCountOfHit(int countOfHit) {
		this.countOfHit = countOfHit;
	}
	public float getRatio() {
		return ratio;
	}
	public void setRatio(float ratio) {
		this.ratio = ratio;
	}
	public String getTopType() {
		return topType;
	}
	public void setTopType(String topType) {
		this.topType = topType;
	}
	public String getDimension() {
		return dimension;
	}
	public void setDimension(String dimension) {
		this.dimension = dimension;
	}
	public int getSequenceNumber() {
		return sequenceNumber;
	}
	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public double getBonus() {
		return bonus;
	}
	public void setBonus(double bonus) {
		this.bonus = bonus;
	}
}
