package com.xhcms.lottery.commons.persist.entity;
import java.io.Serializable;
/**
 * 大V彩 id
 */
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "lt_goldscheme")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GoldSchemePO implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "goldSchemeId", nullable = false)
	private String goldSchemeId;
	
	@Column(name = "createtime", nullable = false)
	private Date createtime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	public String getGoldSchemeId() {
		return goldSchemeId;
	}

	public void setGoldSchemeId(String goldSchemeId) {
		this.goldSchemeId = goldSchemeId;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	
	
	
}
