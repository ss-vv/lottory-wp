package com.xhcms.lottery.commons.persist.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.persist.dao.RecommendUserDao;
import com.xhcms.lottery.commons.persist.dao.UserDao;
import com.xhcms.lottery.commons.persist.entity.RecommendUserPO;
import com.xhcms.lottery.commons.persist.entity.UserPO;
import com.xhcms.lottery.commons.persist.service.FollowService;

public class FollowServiceImpl implements FollowService {

	@Autowired
	private RecommendUserDao recUserDao;
	@Autowired
	private UserDao userDao;
	
	@Override
	@Transactional(readOnly=true)
	public List<RecommendUserPO> listRecUser(Paging paging) {
		return recUserDao.findAllWithPaging(paging);
	}

	@Override
	@Transactional
	public void recommendUser(long id, List<String> lotteries, long creatorId) {
		UserPO user = userDao.get(id);
		for(String lottery:lotteries){
			RecommendUserPO recommend = recUserDao.findByUserIdAndLotteryId(id, lottery);
			if(recommend != null) {
				recommend.setUsername(user.getUsername());
				recommend.setCreatorId(creatorId);
				recommend.setCreateTime(new Date());
				recUserDao.update(recommend);
			} else {
				recommend = new RecommendUserPO();
				recommend.setUserId(id);
				recommend.setUsername(user.getUsername());
				recommend.setLotteryId(lottery);
				recommend.setCreatorId(creatorId);
				recommend.setCreateTime(new Date());
				recUserDao.save(recommend);
			}
		}
	}

	@Override
	@Transactional
	public void cancelRecommendUser(long recId) {
		recUserDao.deleteById(recId);
	}

	@Override
	@Transactional
	public RecommendUserPO findRecommendUser(long userId, String lotteryId){
		return recUserDao.findByUserIdAndLotteryId(userId, lotteryId);
	}
}
