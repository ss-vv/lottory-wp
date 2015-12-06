package com.xhcms.lottery.commons.persist.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 赠款类型
 * 
 * @author Wang Lei
 */
@Entity
@Table(name = "lt_granttype")
public class GrantTypePO implements Serializable{
	private static final long serialVersionUID = 789179792563848444L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name; // 类型名称

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
