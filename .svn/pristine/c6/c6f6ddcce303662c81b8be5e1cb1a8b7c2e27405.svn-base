package com.unison.lottery.weibo.uc.service;

import java.util.List;

import com.unison.lottery.weibo.common.data.FollowCommand;
import com.unison.lottery.weibo.data.PageRequest;
import com.unison.lottery.weibo.data.Relationship;
import com.unison.lottery.weibo.data.WeiboUser;

/**
 * @Description:用户关系 
 * @author 江浩翔   
 * @date 2013-10-28 上午10:44:10 
 * @version V1.0
 */
public interface RelationshipService {
	
	/**
	 * 获取关注我的用户
	 * @param uid
	 * @return
	 */
	Relationship findFollowersByUid(Long uid);
	/**
	 * 获取我关注的用户
	 * @param uid
	 * @return
	 */
	Relationship findFollowingByUid(Long uid);
	/**
	 * 获取关注我的用户（分页） 
	 * @param uid
	 * @return
	 */
	Relationship findFollowersByUid(Long uid,PageRequest pageRequest);
	/**
	 * 获取我关注的用户（分页） 
	 * @param uid
	 * @return
	 */
	Relationship findFollowingByUid(Long uid,PageRequest pageRequest);

	/**
	 * 保存关注关系
	 * @param followerUid 关注者
	 * @param beFollowUid 被关注者（一个或多个）
	 */
	void saveFollower(Long followerUid, String... beFollowUids);
	
	/**
	 * 删除关注关系
	 * @param followerUid
	 * @param beFollowUid 被关注者（一个或多个）
	 */
	void deleteFollower(Long followerUid, String... beFollowUids);
	
	/**
	 * 判断微博id为weiboUserId的用户是否关注了weiboUser 用户
	 * if 关注了 ，设置WeiboUser 的beFollowed 为true
	 * else 设置WeiboUser 的beFollowed 为false
	 * @param weiboUserId
	 * @param weiboUsers
	 * @return
	 */
	List<WeiboUser> isFollowing(String weiboUserId,List<WeiboUser> weiboUsers);
	WeiboUser isFollowing(String weiboUserId,WeiboUser weiboUser);
	
	/**
	 * 判断weiboUsers集合中的用户是否关注了weiboUserId这个用户
	 * if 关注了 ，设置 WeiboUser 的 beFollower 为true
	 * else 设置 WeiboUser 的 beFollower 为false
	 * @param weiboUserId
	 * @param weiboUsers
	 * @return
	 */
	List<WeiboUser> isFollower(String weiboUserId, List<WeiboUser> weiboUsers);
	
	WeiboUser isFollower(String weiboUserId, WeiboUser weiboUser);
	
	void processAsyncFollowCommand(FollowCommand followCommand);
	
	/**
	 * 我的粉丝ID集合
	 * @param uid
	 * @return
	 */
	List<Long> myFollowers(Long uid);
}
