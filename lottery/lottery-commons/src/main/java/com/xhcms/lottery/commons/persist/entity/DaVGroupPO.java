package com.xhcms.lottery.commons.persist.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "lt_dav_group")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DaVGroupPO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String groupid; //大v群id
	
	@Column(nullable = false)
	private String clientVersion;//客户端的版本
	
	@Column(nullable = false)
	private String vid;//大v的id
	
	@Column(nullable = true, name="imageUrl")
	private String groupImageUrl;//大v群的头像

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	public String getClientVersion() {
		return clientVersion;
	}

	public void setClientVersion(String clientVersion) {
		this.clientVersion = clientVersion;
	}

	public String getVid() {
		return vid;
	}

	public void setVid(String vid) {
		this.vid = vid;
	}

	public String getGroupImageUrl() {
		return groupImageUrl;
	}

	public void setGroupImageUrl(String groupImageUrl) {
		this.groupImageUrl = groupImageUrl;
	}
	
	

}
