/**
 * 
 */
package com.xhcms.lottery.commons.persist.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @author Bean.Long
 *
 */
@Entity
@Table(name="lt_custom_made_candidate")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class CustomMadeAvaiableSchemePO implements Serializable {
	private static final long serialVersionUID = -1420749587825941956L;

	@Id
	private Long id;
	
	@Column(name="createtime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	
	@Column
	private int status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
