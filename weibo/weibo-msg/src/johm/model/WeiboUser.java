package com.unison.lottery.weibo.msg.model;

import java.util.Date;

import redis.clients.johm.Attribute;
import redis.clients.johm.Id;
import redis.clients.johm.Indexed;
import redis.clients.johm.Model;
import redis.clients.johm.utils.ObjectHelper;

import com.xhcms.lottery.commons.data.User;

/**
 * 大V彩微博账户扩展信息。
 * @author Yang Bo
 */
@Model
public class WeiboUser extends User {
	private static final long serialVersionUID = -2838420031293400275L;

	/** 大V彩微博账户Id (redis中的id) */
	@Id
	private Long weiboUid;
	
	@Attribute
	@Indexed
	/** 昵称*/
	private String nickName;
	
	@Attribute
	/** 头像地址*/
	private String headImageURL;
	
	@Attribute
	/** 个人简介 */
	private String individualResume;

	@Attribute
	/** 擅长彩种*/
	private String familiarLottery;
	
	@Attribute
	/** 唯一标识新浪微博用户的uid*/
	private String sinaWeiboUid;
	
	@Attribute
	/** 访问新浪微博用户信息的令牌*/
	private String sinaWeiboToken;
	
	@Attribute
	/** 唯一标识腾讯微博用户的uid*/
	private String qqWeiboUid;
	
	@Attribute
	/** 访问腾讯微博用户信息的令牌*/
	private String qqWeiboToken;

	@Attribute
	/** 创建时间*/
	private Date weiboUserCreateTime;
		
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getHeadImageURL() {
		return headImageURL;
	}

	public void setHeadImageURL(String headImageURL) {
		this.headImageURL = headImageURL;
	}

	public String getIndividualResume() {
		return individualResume;
	}

	public void setIndividualResume(String individualResume) {
		this.individualResume = individualResume;
	}

	public String getFamiliarLottery() {
		return familiarLottery;
	}

	public void setFamiliarLottery(String familiarLottery) {
		this.familiarLottery = familiarLottery;
	}

	public String getSinaWeiboUid() {
		return sinaWeiboUid;
	}

	public void setSinaWeiboUid(String sinaWeiboUid) {
		this.sinaWeiboUid = sinaWeiboUid;
	}

	public String getSinaWeiboToken() {
		return sinaWeiboToken;
	}

	public void setSinaWeiboToken(String sinaWeiboToken) {
		this.sinaWeiboToken = sinaWeiboToken;
	}

	public String getQqWeiboUid() {
		return qqWeiboUid;
	}

	public void setQqWeiboUid(String qqWeiboUid) {
		this.qqWeiboUid = qqWeiboUid;
	}

	public String getQqWeiboToken() {
		return qqWeiboToken;
	}

	public void setQqWeiboToken(String qqWeiboToken) {
		this.qqWeiboToken = qqWeiboToken;
	}

	public Date getWeiboUserCreateTime() {
		return weiboUserCreateTime;
	}

	public void setWeiboUserCreateTime(Date weiboUserCreateTime) {
		this.weiboUserCreateTime = weiboUserCreateTime;
	}

	public Long getWeiboUid() {
		return weiboUid;
	}

	public void setWeiboUid(Long weiboUid) {
		this.weiboUid = weiboUid;
	}


    @Override
    public int hashCode() {
    	Object[] thisFields = new Object[]{
    			weiboUid,
    			familiarLottery,
    			headImageURL,
    			individualResume,
    			nickName,
    			qqWeiboToken,
    			qqWeiboUid,
    			sinaWeiboToken,
    			sinaWeiboUid,
    			weiboUserCreateTime
    	};
    	return ObjectHelper.hashCode(thisFields);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        WeiboUser other = (WeiboUser) obj;

    	Object[] thisFields = new Object[]{
    			weiboUid,
    			familiarLottery,
    			headImageURL,
    			individualResume,
    			nickName,
    			qqWeiboToken,
    			qqWeiboUid,
    			sinaWeiboToken,
    			sinaWeiboUid,
    			weiboUserCreateTime
    	};
    	
    	Object[] otherFields = new Object[]{
    			other.weiboUid,
    			other.familiarLottery,
    			other.headImageURL,
    			other.individualResume,
    			other.nickName,
    			other.qqWeiboToken,
    			other.qqWeiboUid,
    			other.sinaWeiboToken,
    			other.sinaWeiboUid,
    			other.weiboUserCreateTime
    	};
    	return ObjectHelper.equals(thisFields, otherFields, this, obj);
    }
}
