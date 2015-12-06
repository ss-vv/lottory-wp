package com.xhcms.lottery.commons.data.bonus;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 中奖明细
 * 
 * @author Yang Bo
 *
 */
public class BonusDetail {

	private List<String> passTypes = new LinkedList<String>();

	/** 假设命中场次详情 */
	private List<SupposeHit> supposeHits = new LinkedList<SupposeHit>();

	public List<SupposeHit> getSupposeHits() {
		return supposeHits;
	}

	public void setSupposeHits(List<SupposeHit> supposeHits) {
		this.supposeHits = supposeHits;
	}

	public List<String> getPassTypes() {
		return passTypes;
	}

	public void setPassTypes(List<String> passTypes) {
		this.passTypes = passTypes;
	}
	
	public String toString(){
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
