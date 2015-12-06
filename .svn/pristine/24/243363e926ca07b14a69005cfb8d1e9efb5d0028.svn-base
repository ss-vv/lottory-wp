package com.unison.lottery.pm.data;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * 足彩奖上奖排行榜结果
 * @author Wang Lei
 *
 */
public class PromotionWinResult implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2453126927242724001L;

	private String username;//用户登录名
	
	private BigDecimal totalGrant = new BigDecimal(0);//总赠款
	
	private BigDecimal totalAfterTaxBonus = new BigDecimal(0);//总税后奖金
	
	private Long uid;//用户id
	
	public PromotionWinResult(){
		
	}
	public PromotionWinResult(String username, BigDecimal totalGrant,
			BigDecimal totalAfterTaxBonus,Long uid) {
		super();
		this.username = username;
		this.totalGrant = totalGrant;
		this.totalAfterTaxBonus = totalAfterTaxBonus;
		this.uid = uid;
	}
	
	public PromotionWinResult(String username,BigDecimal totalAfterTaxBonus,Long uid) {
		super();
		this.username = username;
		this.totalAfterTaxBonus = totalAfterTaxBonus;
		this.uid = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public BigDecimal getTotalGrant() {
		return totalGrant.setScale(0, BigDecimal.ROUND_HALF_UP);
	}

	public void setTotalGrant(BigDecimal totalGrant) {
		this.totalGrant = totalGrant;
	}

	public BigDecimal getTotalAfterTaxBonus() {
		return totalAfterTaxBonus.setScale(0, BigDecimal.ROUND_HALF_UP);
	}

	public void setTotalAfterTaxBonus(BigDecimal totalAfterTaxBonus) {
		this.totalAfterTaxBonus = totalAfterTaxBonus;
	}
	
	public String toString(){
		return ReflectionToStringBuilder.toString(this);
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
}
