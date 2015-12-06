package com.unison.lottery.weibo.web.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Tuple;

import com.unison.lottery.weibo.common.nosql.StatisticUserDataDao;
import com.unison.lottery.weibo.common.nosql.impl.Keys;
import com.unison.lottery.weibo.data.WeiboMsgVO;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.data.WeiboUserStatis;
import com.unison.lottery.weibo.msg.service.MessageService;
import com.unison.lottery.weibo.uc.service.RelationshipService;
import com.unison.lottery.weibo.uc.service.UserAccountService;
import com.unison.lottery.weibo.web.service.UserStatisService;
@Service
public class UserStatisServiceImpl implements UserStatisService {
	
	@Autowired
	private StatisticUserDataDao statisticUserDataDao;
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private RelationshipService relationshipService;
	@Override
	public WeiboUserStatis getWeiboUserStatisByWeiboUserId(String weiboUserId) {
		if(StringUtils.isBlank(weiboUserId)){
			return new WeiboUserStatis();
		}
		WeiboUser w = userAccountService.findWeiboUserByWeiboUid(weiboUserId);
		WeiboUserStatis weiboUserStatis = statisticUserDataDao.hashGet(Keys.getWeiboUserStatisKey(weiboUserId));
		if(null == weiboUserStatis){
			weiboUserStatis = new WeiboUserStatis();
		}
		BeanUtils.copyProperties(w, weiboUserStatis);
		weiboUserStatis.setFollowerCount(relationshipService.findFollowersByUid(weiboUserStatis.getWeiboUserId()).getFollowers().length);
		weiboUserStatis.setFollowingCount(relationshipService.findFollowingByUid(weiboUserStatis.getWeiboUserId()).getFollowings().length);
		weiboUserStatis.setWeiboCount(messageService.weiboCount(weiboUserStatis.getWeiboUserId() + "").intValue());
		if(weiboUserStatis.getRealWeibo7OpenCount() != 0){
			weiboUserStatis.setGuodanlv(weiboUserStatis.getRealWeibo7GainCount()*100/
					weiboUserStatis.getRealWeibo7OpenCount());
		} else {
			weiboUserStatis.setGuodanlv(0);
		}
		return weiboUserStatis; 
	}

	@Override
	public List<WeiboMsgVO> getLastWinWeibos() {
		LinkedHashSet<Tuple> weiboMsgId = statisticUserDataDao.zrevrangeWithScores(Keys.WeiboWinRankList, 0, -1);
		List<WeiboMsgVO> weiboMsgVOs = new ArrayList<WeiboMsgVO>();
		for (Tuple tuple : weiboMsgId) {
			WeiboMsgVO mVo = messageService.getWeiboVoById(Long.parseLong(tuple.getElement()));
			weiboMsgVOs.add(mVo);
		}
		return weiboMsgVOs;
	}
}
