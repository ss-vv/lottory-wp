package com.unison.lottery.weibo.msg.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.unison.lottery.weibo.common.nosql.impl.Keys;
import com.unison.lottery.weibo.data.PageRequest;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.WeiboMsgVO;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.msg.persist.dao.RecommendDao;
import com.unison.lottery.weibo.msg.service.MessageService;
import com.unison.lottery.weibo.msg.service.RecommendService;
import com.unison.lottery.weibo.uc.service.UserAccountService;

@Service
public class RecommendServiceImpl implements RecommendService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private RecommendDao recommendDao;

	@Autowired
	private MessageService messageService;
	
	@Autowired
	private UserAccountService userAccountService;
	
	@Override
	public PageResult<WeiboMsgVO> queryLotteryUserRecommendList(String lotteryId,
			PageRequest pageRequest) {
		String lotteryIdUid = Keys.lotteryIdUid(lotteryId);
		String weiboUserId = recommendDao.getString(lotteryIdUid);
		Long uid = 0L;
		if(StringUtils.isNotBlank(weiboUserId)) {
			uid = Long.parseLong(weiboUserId);
		}
		log.info("查询彩种:{}, 对应微博用户ID={}，推荐列表.", new Object[] {lotteryId,
				weiboUserId });
		PageResult<WeiboMsgVO> result = null;
		if (uid > 0) {
			result = messageService.findAllRecommendsPost(uid, pageRequest);
		} else {
			result = new PageResult<WeiboMsgVO>(pageRequest, new ArrayList<WeiboMsgVO>());
		}
		return result;
	}

	@Override
	public List<WeiboUser> findAnalyzeTalent() {
		LinkedHashSet<String> userIds = recommendDao.zrange(Keys.ANALYSIS_EXPERT, 0, -1);
		List<WeiboUser> users = new ArrayList<WeiboUser>();
		for (String id : userIds) {
			WeiboUser u = userAccountService.findWeiboUserByWeiboUid(id);
			if(null != u){
				users.add(u);
			}
		}
		return users;
	}
}
