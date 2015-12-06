package com.unison.lottery.weibo.bbs.persist.service.impl;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.weibo.bbs.lang.Constants;
import com.unison.lottery.weibo.bbs.persist.dao.WeiboUserDao;
import com.unison.lottery.weibo.bbs.persist.dao.WeiboThreadDao;
import com.unison.lottery.weibo.bbs.persist.dao.UserDao;
import com.unison.lottery.weibo.bbs.persist.entity.UserPO;
import com.unison.lottery.weibo.bbs.persist.service.SyncForumThreadService;

public class SyncForumThreadServiceImpl implements SyncForumThreadService {
	
	private static final Logger logger = LoggerFactory
			.getLogger(SyncForumThreadServiceImpl.class);
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private WeiboUserDao lotteryUserDao;
	
	@Autowired
	private WeiboThreadDao threadDao;

	@Override
	@Transactional
	public void syncForumThread() {
		List<UserPO> userPOs = userDao.findEffectiveUsers(Constants.USER_VALID);
		int wids = 0;
		if(null != userPOs && !userPOs.isEmpty()) {
			logger.info("彩票用户数量:{}", userPOs.size());
			for(UserPO userPO : userPOs) {
				String weiboUserId = lotteryUserDao.getWeiboUserIdByLotteryUserId(userPO.getId());
				if(StringUtils.isNotBlank(weiboUserId)) {
					wids++;
					Set<String> threadIds = threadDao.getThreadIdByWeiboUserId(userPO.getId());
				}
			}
		}
	}

}
