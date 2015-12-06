/**
 * 
 */
package com.xhcms.lottery.commons.persist.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @author Bean.Long
 * 自动参与跟单合买的明细
 */
@Entity
@Table(name = "lt_custom_made_detail")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CustomMadeDetailPO implements Serializable {
	private static final long serialVersionUID = 3916089164388957708L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "custom_made_id")
	private CustomMadePO customMade;
	
	@Column(name = "createtime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "scheme_id")
	private BetSchemePO betScheme;
	
	@Column(name = "bet_money")
	private int betMoney;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CustomMadePO getCustomMade() {
		return customMade;
	}

	public void setCustomMade(CustomMadePO customMade) {
		this.customMade = customMade;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public BetSchemePO getBetScheme() {
		return betScheme;
	}

	public void setBetScheme(BetSchemePO betScheme) {
		this.betScheme = betScheme;
	}

	public int getBetMoney() {
		return betMoney;
	}

	public void setBetMoney(int betMoney) {
		this.betMoney = betMoney;
	}
}
