package com.xhcms.lottery.commons.persist.service.impl;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.lottery.commons.data.CacheUserScore;
import com.xhcms.lottery.commons.data.ShareUserScore;
import com.xhcms.lottery.commons.data.UserScore;
import com.xhcms.lottery.commons.persist.dao.UserScoreDao;
import com.xhcms.lottery.commons.persist.entity.UserScorePO;
import com.xhcms.lottery.commons.persist.service.CacheUserScoreService;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.EntityType;
import com.xhcms.lottery.utils.ConvertUserScore;

/**
 * @author yonglizhu
 */
public class CacheUserScoreServiceImpl implements CacheUserScoreService {

	@Autowired
	private UserScoreDao userScoreDao;
	
	@Autowired
	private ShareUserScore shareUserScore;
	
	@Override
	@Transactional(readOnly = true)
	public UserScore findByUserIdAndLotteryId(long userId, String lotteryId) {
		if (StringUtils.isBlank(lotteryId)) {
			lotteryId = Constants.ZCZ;
		}
		UserScore userScore = new UserScore();
		Date date = new Date();
		String ul = userId+lotteryId;
		Map<String, CacheUserScore> usmap = shareUserScore.getUsmap();
		CacheUserScore cacheus = usmap.get(ul);
		if(null != cacheus) {
			long hour = (date.getTime()-cacheus.getCreatedTime().getTime())/1000/60/60;
			
			if(hour >= 1) {
				//从数据库取得并放入map
				cacheus = getCacheMap(userId, lotteryId);
			} 
		} else {
			//从数据库取得并放入map
			cacheus = getCacheMap(userId, lotteryId);
		}
		
		if(null != cacheus) {	
			if(cacheus.getShowScore() > 0) {
				//晒单战绩
				String showPic = ConvertUserScore.convertScore(cacheus.getShowScore(),
						EntityType.SHOW_SCORE);
				userScore.setShowPic(showPic);
			}
			if(cacheus.getGroupScore() > 0) {
				//合买战绩
				String groupPic = ConvertUserScore.convertScore(cacheus.getGroupScore(),
						EntityType.GROUP_SCORE);
				userScore.setGroupPic(groupPic);
			}
		}
		
		return userScore;
	}
	
	private CacheUserScore getCacheMap(long userId, String lotteryId) {
		Map<String, CacheUserScore> usmap = shareUserScore.getUsmap();
		Date date = new Date();
		CacheUserScore cacheus = new CacheUserScore();
		UserScorePO userScorePO = userScoreDao.getUserScoreByUserIdLottoryId(
				userId, lotteryId);
		if (userScorePO != null) {	
			//放入map中
			cacheus.setShowScore(userScorePO.getShowScore());
			cacheus.setGroupScore(userScorePO.getGroupScore());
			cacheus.setCreatedTime(date);
			usmap.put(userScorePO.getUserId()+userScorePO.getLotteryId(), cacheus);
		} else {
			cacheus.setCreatedTime(date);
			usmap.put(userId+lotteryId, cacheus);
		}
		
		shareUserScore.setUsmap(usmap);
		
		return cacheus;
	}
}