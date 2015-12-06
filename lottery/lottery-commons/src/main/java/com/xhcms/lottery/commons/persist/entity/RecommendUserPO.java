package com.xhcms.lottery.commons.persist.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 自动晒单推荐用户。
 * @author Yang Bo
 *
 */

@Entity
@Table(name = "lt_recommend_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RecommendUserPO implements Serializable {

	private static final long serialVersionUID = -704521143148075837L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="user_id", nullable=false)
    private Long userId;
    
    @Column(name="username", nullable=false)
    private String username;
    
    @Column(name="lottery_id", nullable=false)
    private String lotteryId;
    
    @Column(name="creator_id", nullable=false)
    private Long creatorId;

    @Column(name="create_time", nullable=false)
    private Date createTime;

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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
    
	public String toString(){
		return ReflectionToStringBuilder.toString(this);
	}
}
