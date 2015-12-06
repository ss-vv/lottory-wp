package com.unison.lottery.weibo.data;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Top5Recommend implements Serializable{

	private static final long serialVersionUID = -8936615086823651L;
	
    private long id;
	
	private long userId;
	
	private int countOfRecommend;
	
	private int countOfHit;
	
	private float ratio;
	
	private String topType;
	
	private String dimension;
	
	private int sequenceNumber;
	
	private double bonus;
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
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

	public Integer getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(Integer sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}



	public double getBonus() {
		return bonus;
	}



	public void setBonus(double bonus) {
		this.bonus = bonus;
	}
	

}
