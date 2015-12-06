package com.unison.lottery.weibo.data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.unison.lottery.weibo.common.nosql.impl.Keys;
import com.unison.lottery.weibo.common.redis.ObjectId;
import com.unison.lottery.weibo.common.redis.ObjectKey;
import com.xhcms.lottery.commons.data.User;
import com.xhcms.lottery.lang.Constants;

/**
 * @Description: 大V彩微博账户扩展信息
 * @author 江浩翔
 * @date 2013-10-16 下午1:56:43
 * @version V1.0
 */
@ObjectKey(key=Keys.USER, nextIdKey=Keys.GLOBAL_NEXT_USER_ID)
public class WeiboUser extends User {
	private static final long serialVersionUID = -2838420031493400275L;

	@ObjectId
	/** 微博账户表Id (redis中的id) */
	private Long weiboUserId;

	/** 个人简介 */
	private String individualResume;

	/** 擅长彩种 */
	private String familiarLottery;

	/** 创建时间 */
	private Date weiboUserCreateTime;

	/** 标识该用户是否被某个登录用户关注 */
	private boolean beFollowed;

	/** 标识该用户是否是某个登录用户的粉丝 */
	private boolean beFollower;
	
	private boolean isSelf;
	
	/** 共同关注人数 */
	private int togetherFollowNum;
	
	private int followingCount;
	
	private int followerCount;
	
	private int weiboCount;
	
	private String certificationType;
	
	private String activeUser;//活跃用户使用标示
	/** 可用余额*/
	private BigDecimal freeMoney; 
	
	public BigDecimal getFreeMoney() {
		return freeMoney;
	}

	public void setFreeMoney(BigDecimal freeMoney) {
		this.freeMoney = freeMoney;
	}

	public Long getWeiboUserId() {
		return weiboUserId;
	}

	public void setWeiboUserId(Long weiboUserId) {
		this.weiboUserId = weiboUserId;
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

	public Date getWeiboUserCreateTime() {
		return weiboUserCreateTime;
	}

	public void setWeiboUserCreateTime(Date weiboUserCreateTime) {
		this.weiboUserCreateTime = weiboUserCreateTime;
	}

	public boolean isBeFollowed() {
		return beFollowed;
	}

	public void setBeFollowed(boolean beFollowed) {
		this.beFollowed = beFollowed;
	}

	public boolean isBeFollower() {
		return beFollower;
	}

	public void setBeFollower(boolean beFollower) {
		this.beFollower = beFollower;
	}

	public int getTogetherFollowNum() {
		return togetherFollowNum;
	}

	public void setTogetherFollowNum(int togetherFollowNum) {
		this.togetherFollowNum = togetherFollowNum;
	}

	public int getFollowingCount() {
		return followingCount;
	}

	public void setFollowingCount(int followingCount) {
		this.followingCount = followingCount;
	}

	public int getFollowerCount() {
		return followerCount;
	}

	public void setFollowerCount(int followerCount) {
		this.followerCount = followerCount;
	}
	
	public int getWeiboCount() {
		return weiboCount;
	}

	public void setWeiboCount(int weiboCount) {
		this.weiboCount = weiboCount;
	}
	
	public boolean isSelf() {
		return isSelf;
	}

	public void setSelf(boolean isSelf) {
		this.isSelf = isSelf;
	}

	public String getCertificationType() {
		return certificationType;
	}

	public void setCertificationType(String certificationType) {
		this.certificationType = certificationType;
	}
	
	public String getActiveUser() {
		return activeUser;
	}

	public void setActiveUser(String activeUser) {
		this.activeUser = activeUser;
	}

	public Map<String, String> toRedisHashValue() {
		Map<String, String> map = new HashMap<String, String>();

		/** 大V彩账户lt_user中的id */
		if (null != this.getId()) {
			map.put("id", this.getId().toString());
		}

		/** 用户名 */
		if (null != this.getUsername()) {
			map.put("username", this.getUsername());
		}

		/** redis中的id */
		if (null != weiboUserId) {
			map.put("weiboUserId", this.weiboUserId.toString());
		}
		if (null != getNickName()) {
			map.put("nickName", getNickName());
		}
		if (null != getHeadImageURL()) {
			map.put("headImageURL", getHeadImageURL());
		} else {//设置默认头像地址
			map.put("headImageURL", Constants.DEFAULT_HEAD_IMG);
		}

		if (null != individualResume) {
			map.put("individualResume", this.individualResume);
		}

		if (null != familiarLottery) {
			map.put("familiarLottery", this.familiarLottery);
		}

		if (null != getSinaWeiboUid()) {
			map.put("sinaWeiboUid", getSinaWeiboUid());
		}

		if (null != getSinaWeiboToken()) {
			map.put("sinaWeiboToken", getSinaWeiboToken());
		}

		if (null != getQqConnectUid()) {
			map.put("qqConnectUid", getQqConnectUid());
		}
		
		if (null != getQqConnectToken()) {
			map.put("qqConnectToken", getQqConnectToken());
		}

		if (null != weiboUserCreateTime) {
			map.put("weiboUserCreateTime",
					"" + this.weiboUserCreateTime.getTime());
		}
		if (null != certificationType) {
			map.put("certificationType",
					this.certificationType);
		}
		if (null != getWeixinUid()) {
			map.put("weixinUid",getWeixinUid());
		}
		if (null != getWeixinPCUid()) {
			map.put("weixinPCUid",getWeixinPCUid());
		}
		if (null != getWeixinUnionId()) {
			map.put("weixinUnionId",getWeixinUnionId());
		}
		if (null != getWeixinToken()) {
			map.put("weixinToken",getWeixinToken());
		}
		if (null != getAlipayUid()) {
			map.put("alipayUid",getAlipayUid());
		}
		if (null != getAlipayToken()) {
			map.put("alipayToken",getAlipayToken());
		}
		return map;
	}
}
