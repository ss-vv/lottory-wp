package com.xhcms.lottery.commons.persist.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
@Entity
@Table(name="top5_recommend")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Top5RecommendPO implements Serializable{

	private static final long serialVersionUID = 5503538870337067935L;
	@Id
	private Long id;
	
	@Column(name="userId")
	private Long userId;
	
	@Column(name="countOfRecommend")
	private Integer countOfRecommend;
	
	@Column(name="countOfHit")
	private Integer countOfHit;
	
	@Column(name="ratio")
	private Double ratio;
	
	@Column(name="topType")
	private String topType;
	
	@Column(name="dimension")
	private String dimension;
	
	@Column(name="sequenceNumber")
	private Integer sequenceNumber;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getCountOfRecommend() {
		return countOfRecommend;
	}
	public void setCountOfRecommend(Integer countOfRecommend) {
		this.countOfRecommend = countOfRecommend;
	}
	public Integer getCountOfHit() {
		return countOfHit;
	}
	public void setCountOfHit(Integer countOfHit) {
		this.countOfHit = countOfHit;
	}
	public Double getRatio() {
		return ratio;
	}
	public void setRatio(Double ratio) {
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
	public Integer getSequenceNumber() {
		return sequenceNumber;
	}
	public void setSequenceNumber(Integer sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	

}
