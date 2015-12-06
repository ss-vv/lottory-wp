package com.unison.lottery.weibo.web.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.uc.service.RelationshipService;
import com.unison.lottery.weibo.uc.service.UserAccountService;
import com.unison.lottery.weibo.web.service.InterestUserService;

@Service
public class InterestUserServiceImpl implements InterestUserService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	UserAccountService userAccountService;
	@Autowired
	RelationshipService relationshipService;

	@Override
	public List<WeiboUser> findUserByTogetherFollow(Long weiboUserId,
			Set<String> excludeUids, int size) {
		if (size == 0) {
			size = 1;
		}
		if (null == excludeUids) {
			excludeUids = new HashSet<String>();
		}
		long start = new Date().getTime();
		// 我关注的人的uid数组
		String[] followings = relationshipService.findFollowingByUid(
				weiboUserId).getFollowings();
		List<WeiboUser> interestUsers = new ArrayList<WeiboUser>();
		if (followings.length > 0) {
			Map<String, String> excludeUidMap = makeExcludeUidMap(followings,
					excludeUids);
			List<Map.Entry<String, Integer>> interestList = sort(makeInterestUserMap(followings));
			// 返回前size个用户
			size = size > interestList.size() ? interestList.size() : size;
			for (int i = 0; i < size
					|| (interestUsers.size() < size && i < interestList.size()); i++) {
				String uid = interestList.get(i).getKey();
				if (null == excludeUidMap.get(uid)) {
					WeiboUser weiboUser = userAccountService
							.findWeiboUserByWeiboUid(uid);
					weiboUser.setTogetherFollowNum(interestList.get(i)
							.getValue());
					interestUsers.add(weiboUser);
				}
			}
		}
		// 测试数据-------开始
/*		if (interestUsers.size() < 1) {
			WeiboUser weiboUser1 = userAccountService
					.findWeiboUserByWeiboUid("" + 12);
			WeiboUser weiboUser2 = userAccountService
					.findWeiboUserByWeiboUid("" + 13);
			WeiboUser weiboUser3 = userAccountService
					.findWeiboUserByWeiboUid("" + 14);
			WeiboUser weiboUser4 = userAccountService
					.findWeiboUserByWeiboUid("" + 15);
			if (null != weiboUser1)
				interestUsers.add(weiboUser1);
			if (null != weiboUser2)
				interestUsers.add(weiboUser2);
			if (null != weiboUser3)
				interestUsers.add(weiboUser3);
			if (null != weiboUser4)
				interestUsers.add(weiboUser4);
			for (int i = 1; i <= 11; i++) {
				WeiboUser w = userAccountService
						.findWeiboUserByWeiboUid("" + i);
				if (null != w) {
					interestUsers.add(w);
				}
			}
		}*/
		// 测试数据-------结束
		logger.info("计算我感兴趣的人，花费时间：{} ms", new Date().getTime() - start);
		return interestUsers;
	}

	/** 将根据map的value进行排序，得到list */
	private List<Map.Entry<String, Integer>> sort(
			Map<String, Integer> interestMap) {
		List<Map.Entry<String, Integer>> interestList = new ArrayList<Map.Entry<String, Integer>>(
				interestMap.entrySet());
		Collections.sort(interestList,
				new Comparator<Map.Entry<String, Integer>>() {
					public int compare(Map.Entry<String, Integer> o1,
							Map.Entry<String, Integer> o2) {
						return (o2.getValue() - o1.getValue());
					}
				});
		return interestList;
	}

	/** 我感兴趣的人的，记录：id-共同关注人数 */
	private Map<String, Integer> makeInterestUserMap(String[] followings) {
		Map<String, Integer> interestMap = new HashMap<String, Integer>();
		for (int i = 0; i < followings.length; i++) {
			if (StringUtils.isBlank(followings[i])) {
				continue;
			}
			try {
				String[] follows = relationshipService.findFollowersByUid(
						Long.parseLong(followings[i])).getFollowers();
				for (int j = 0; j < follows.length; j++) {
					// 计算感兴趣的人集合中 ，各id出现的频率
					if (null == interestMap.get(follows[j])) {
						interestMap.put(follows[j], 1);
					} else {
						int tmp = ((Integer) interestMap.get(follows[j]))
								.intValue() + 1;
						interestMap.put(follows[j], tmp);
					}
				}
			} catch (NumberFormatException e) {
				logger.error("",e);
			}
		}
		return interestMap;
	}

	private Map<String, String> makeExcludeUidMap(String[] followings,
			Set<String> excludeUids) {
		// 将我已经关注的用户添加进排除集合
		for (int i = 0; i < followings.length; i++) {
			excludeUids.add(followings[i]);
		}
		Map<String, String> excludeUidMap = new HashMap<String, String>();
		for (String id : excludeUids) {
			excludeUidMap.put(id, id);
		}
		return excludeUidMap;
	}
}
