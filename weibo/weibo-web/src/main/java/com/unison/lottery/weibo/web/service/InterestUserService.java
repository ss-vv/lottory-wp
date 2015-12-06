package com.unison.lottery.weibo.web.service;

import java.util.List;
import java.util.Set;

import com.unison.lottery.weibo.data.WeiboUser;

public interface InterestUserService {
	/**
	 * 通过共同关注方式获取感兴趣的人
	 * @param weiboUserId  登录用户的微博UID
	 * @param excludeUids 需要排除的用户集合
	 * @param size 返回多少个用户 默认1个 
	 * @return
	 */
	List<WeiboUser> findUserByTogetherFollow(Long weiboUserId,Set<String> excludeUids,int size);
}
