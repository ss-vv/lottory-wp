package com.xhcms.lottery.commons.persist.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 活动详情
 * @author Wang lei
 */
@Entity
@Table(name = "pm_promotion_detail")
public class PromotionDetailPO implements Serializable{

	private static final long serialVersionUID = -6402487065096056439L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "promotion_id")
	private Long promotionId;
	
	/**包含过关类型列表*/
	@Column(name = "pass_type_ids")
	private String passTypeIds;
	
	/**排除过关类型列表*/
	@Column(name = "pass_type_ids_logic")
	private String passTypeIdsLogic;
	
	/** 最小值 */
	@Column(name = "min_value")
	private BigDecimal minValue;
	
	/** 最大值 */
	@Column(name = "max_value")
	private BigDecimal maxValue;
	
	/** 赠款金额 */
	private BigDecimal grant;
	
	/** 赠款百分比 */
	private Integer percent;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(Long promotionId) {
		this.promotionId = promotionId;
	}

	public String getPassTypeIds() {
		return passTypeIds;
	}

	public void setPassTypeIds(String passTypeIds) {
		this.passTypeIds = passTypeIds;
	}

	public String getPassTypeIdsLogic() {
		return passTypeIdsLogic;
	}

	public void setPassTypeIdsLogic(String passTypeIdsLogic) {
		this.passTypeIdsLogic = passTypeIdsLogic;
	}

	public BigDecimal getMinValue() {
		return minValue;
	}

	public void setMinValue(BigDecimal minValue) {
		this.minValue = minValue;
	}

	public BigDecimal getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(BigDecimal maxValue) {
		this.maxValue = maxValue;
	}

	public BigDecimal getGrant() {
		return grant;
	}

	public void setGrant(BigDecimal grant) {
		this.grant = grant;
	}

	public Integer getPercent() {
		return percent;
	}

	public void setPercent(Integer percent) {
		this.percent = percent;
	}
	
}
