package com.unison.lottery.weibo.data;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.unison.lottery.weibo.common.redis.Reference;
import com.xhcms.lottery.commons.data.BetScheme;

/**
 * 微博消息对象
 * @author Wang Lei
 *
 */
public class WeiboMsgVO extends WeiboMsg implements Serializable {
	private static final long serialVersionUID = -1359790909288653063L;

	/** 源微博 */
	private WeiboMsg sourceWeiboMsg;
	
	@Reference(by="postId")
	private WeiboMsgVO sourceWeibo;

	/** 微博作者用户 */
	@Reference(by="ownerId")
	private WeiboUser user;
	
	/** 源微博用户 */
	private WeiboUser sourceUser;
	
	/** 格式化后的创建时间 */
	private String createTimeFmt;
	
	/** 格式化后的源微博创建时间 */
	private String sourceCreateTimeFmt;
	
	/** 是否采纳过 */
	private boolean isLike = false;
	
	/** 是否收藏过 */
	private boolean isFavourited = false;
	
	private List<WeiboTag> tags;
	private List<WeiboUser> likeUsers;
	private List<RealFollower> realFollowers;
	private String[] myFollowingsIds;//我关注的所有人的id集合
	private BetScheme betScheme;
	private List<JoinGroupBuyUser> userRecords;//合买用户列表
	
	private BetScheme sourceBetScheme;//源微博方案信息
	private List<RealFollower> sourceRealFollowers;//源微博方案信息跟单用户
	private String sourceType;//源微博类型
	
	public WeiboMsg getSourceWeiboMsg() {
		return sourceWeiboMsg;
	}

	public String getCreateTimeFmt() {
		return createTimeFmt;
	}

	public void setCreateTimeFmt(String createTimeFmt) {
		this.createTimeFmt = createTimeFmt;
	}

	public String getSourceCreateTimeFmt() {
		return sourceCreateTimeFmt;
	}

	public void setSourceCreateTimeFmt(String sourceCreateTimeFmt) {
		this.sourceCreateTimeFmt = sourceCreateTimeFmt;
	}

	public void setSourceWeiboMsg(WeiboMsg sourceWeiboMsg) {
		this.sourceWeiboMsg = sourceWeiboMsg;
	}

	public WeiboUser getUser() {
		return user;
	}

	public void setUser(WeiboUser user) {
		this.user = user;
	}

	public WeiboUser getSourceUser() {
		return sourceUser;
	}

	public void setSourceUser(WeiboUser sourceUser) {
		this.sourceUser = sourceUser;
	}

	public boolean isLike() {
		return isLike;
	}

	public void setLike(boolean isLike) {
		this.isLike = isLike;
	}

	public boolean isFavourited() {
		return isFavourited;
	}

	public void setFavourited(boolean isFavourited) {
		this.isFavourited = isFavourited;
	}

	public WeiboMsgVO getSourceWeibo() {
		return sourceWeibo;
	}

	public void setSourceWeibo(WeiboMsgVO sourceWeibo) {
		this.sourceWeibo = sourceWeibo;
	}

	
	public List<WeiboTag> getTags() {
		return tags;
	}

	
	public void setTags(List<WeiboTag> tags) {
		this.tags = tags;
	}

	public List<WeiboUser> getLikeUsers() {
		return likeUsers;
	}

	public void setLikeUsers(List<WeiboUser> likeUsers) {
		this.likeUsers = likeUsers;
	}

	public List<RealFollower> getRealFollowers() {
		return realFollowers;
	}

	public void setRealFollowers(List<RealFollower> realFollowers) {
		this.realFollowers = realFollowers;
	}

	public String[] getMyFollowingsIds() {
		return myFollowingsIds;
	}

	public void setMyFollowingsIds(String[] myFollowingsIds) {
		this.myFollowingsIds = myFollowingsIds;
	}

	public BetScheme getBetScheme() {
		return betScheme;
	}

	public void setBetScheme(BetScheme betScheme) {
		this.betScheme = betScheme;
	}

	public BetScheme getSourceBetScheme() {
		return sourceBetScheme;
	}

	public void setSourceBetScheme(BetScheme sourceBetScheme) {
		this.sourceBetScheme = sourceBetScheme;
	}

	public List<RealFollower> getSourceRealFollowers() {
		return sourceRealFollowers;
	}

	public void setSourceRealFollowers(List<RealFollower> sourceRealFollowers) {
		this.sourceRealFollowers = sourceRealFollowers;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public List<JoinGroupBuyUser> getUserRecords() {
		return userRecords;
	}

	public void setUserRecords(List<JoinGroupBuyUser> userRecords) {
		this.userRecords = userRecords;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
