package com.xhcms.lottery.commons.persist.entity;

import java.io.Serializable;
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
@Table(name = "lt_bet_client_publish")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PublishBetSchemePO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "lt_bet_id", nullable = false)
	private long betSchemeId;

	@Column(name = "lt_user_id", nullable = false)
	private long userId;

	@Column(name = "groupid", nullable = false)
	private String groupid;
	
	@Column(name = "lt_match_id", nullable = false)
	private long matchId;
	
	@Column(name = "nickname", nullable = true)
	private String nickname;
	
	@Column(name = "imageUrl", nullable = true)
	private String imageUrl;
	
	@Column(name = "createDate", nullable = false)
	private Date createDate;
    
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public long getMatchId() {
		return matchId;
	}

	public void setMatchId(long matchId) {
		this.matchId = matchId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getBetSchemeId() {
		return betSchemeId;
	}

	public void setBetSchemeId(long betSchemeId) {
		this.betSchemeId = betSchemeId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	
	
	
}
