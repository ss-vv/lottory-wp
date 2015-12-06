package com.xhcms.lottery.commons.persist.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "lt_return_status")
public class ReturnStatusPO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "system_key", nullable = false,unique=true)
	private String systemKey;
	
	@Column(name = "status_code_for_client", nullable = false,unique=true)
	private String statusCodeForClient;
	
	@Column(name = "desc_for_client", nullable = false,unique=true)
	private String descForClient;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSystemKey() {
		return systemKey;
	}
	public void setSystemKey(String systemKey) {
		this.systemKey = systemKey;
	}
	public String getStatusCodeForClient() {
		return statusCodeForClient;
	}
	public void setStatusCodeForClient(String statusCodeForClient) {
		this.statusCodeForClient = statusCodeForClient;
	}
	public String getDescForClient() {
		return descForClient;
	}
	public void setDescForClient(String descForClient) {
		this.descForClient = descForClient;
	}

}
