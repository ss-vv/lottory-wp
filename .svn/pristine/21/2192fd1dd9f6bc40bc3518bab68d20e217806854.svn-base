package com.unison.lottery.weibo.uc.persist;

import com.unison.lottery.weibo.common.nosql.BaseDao;
import com.unison.lottery.weibo.data.Relationship;

/**
 * @Description: 用户关系相关DAO
 * @author 江浩翔   
 * @date 2013-10-28 上午10:39:19 
 * @version V1.0
 */
public interface RelationshipDao extends BaseDao<Relationship>{
	/**
	 * 查找用户粉丝
	 * @param uid 用户id
	 * @return 用户粉丝的id数组
	 */
	String[] findFollowersByUid(Long uid);
	
	/**
	 * 查找用户关注
	 * @param uid 用户id
	 * @return 该用户关注的人的uid
	 */
	String[] findFollowingByUid(Long uid);
	
	/**
	 * 保存关注关系
	 * @param followerUid 关注者
	 * @param beFollowUid 被关注者（一个或多个）
	 */
	void saveFollower(Long followerUid, String... beFollowUids);
	
	/**
	 * 批量保存关注关系
	 * @param followerUid
	 * @param beFollowUid 被关注者（一个或多个）
	 */
	void deleteFollower(Long followerUid, String... beFollowUids);
	
	/**
	 * 判断微博id为weiboUserId的用户是否关注了微博id为followingId的用户
	 * @param weiboUserId
	 * @param followingId
	 * @return
	 */
	boolean isFollowing(String weiboUserId,String followingId);
}
