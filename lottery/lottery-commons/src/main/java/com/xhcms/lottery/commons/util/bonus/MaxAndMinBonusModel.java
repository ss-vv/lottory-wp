package com.xhcms.lottery.commons.util.bonus;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class MaxAndMinBonusModel {
	private float maxBonus;
	private float minBonus;
	private List<double[]> detailExpress;//最大详情
	private double[] minDetailExpress;//最小详情
	private int note;
	private int buyAmount;
	
	public String toString(){
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
	
	public MaxAndMinBonusModel(){
		this.detailExpress = new ArrayList<double[]>();
	}
	
	public float getMaxBonus() {
		return maxBonus;
	}
	public void setMaxBonus(float maxBonus) {
		this.maxBonus = maxBonus;
	}
	public float getMinBonus() {
		return minBonus;
	}
	public void setMinBonus(float minBonus) {
		this.minBonus = minBonus;
	}
	public List<double[]> getDetailExpress() {
		return detailExpress;
	}
	public void setDetailExpress(List<double[]> detailExpress) {
		this.detailExpress = detailExpress;
	}

	public double[] getMinDetailExpress() {
		return minDetailExpress;
	}

	public void setMinDetailExpress(double[] minDetailExpress) {
		this.minDetailExpress = minDetailExpress;
	}

	public int getNote() {
		return note;
	}

	public void setNote(int note) {
		this.note = note;
	}

	public int getBuyAmount() {
		return buyAmount;
	}

	public void setBuyAmount(int buyAmount) {
		this.buyAmount = buyAmount;
	}
}
