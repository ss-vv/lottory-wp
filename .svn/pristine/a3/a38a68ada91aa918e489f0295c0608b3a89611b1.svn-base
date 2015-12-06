package com.unison.lottery.weibo.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.weibo.common.nosql.StatisticUserDataDao;
import com.unison.lottery.weibo.common.nosql.impl.Keys;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.data.WeiboUserStatis;
import com.unison.lottery.weibo.msg.service.MessageService;
import com.unison.lottery.weibo.service.StatisticUserDataService;
import com.unison.lottery.weibo.uc.service.UserAccountService;
import com.unison.lottery.weibo.util.OfficialId;
import com.xhcms.lottery.commons.persist.dao.BetSchemeDao;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.EntityType;

@Service
public class StatisticUserDataServiceImpl implements StatisticUserDataService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private StatisticUserDataDao statisticUserDataDao;
	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private BetSchemeDao betSchemeDao;
	
	@Autowired
	private MessageService messageService;
	
	private Date get7From(){
		Calendar cal = Calendar.getInstance();
		int nowDate = cal.get(Calendar.DATE);
		
		int fromDate = nowDate - 7; 
		cal.set(Calendar.DATE, fromDate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 1);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	private Date get7To(){
		Calendar cal = Calendar.getInstance();
		int nowDate = cal.get(Calendar.DATE);
		
		int toDate = nowDate - 1;
		cal.set(Calendar.DATE, toDate);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	private WeiboUserStatis dealWeiboUserStatis(List<BetSchemePO> betSchemePOs){
		WeiboUserStatis weiboUserStatis = new WeiboUserStatis();
		int realCount = 0;
		int openCount = 0;
		int gainCount = 0;
		BigDecimal totalBonus = new BigDecimal(0);
		for (BetSchemePO betSchemePO : betSchemePOs) {
			if(betSchemePO.getStatus() == EntityStatus.TICKET_NOT_WIN){
				realCount ++;
				openCount ++;
			} else if (betSchemePO.getStatus() == EntityStatus.TICKET_BUY_SUCCESS){
				realCount ++;
			} else if(betSchemePO.getStatus() == EntityStatus.TICKET_NOT_AWARD || 
					betSchemePO.getStatus() == EntityStatus.TICKET_AWARDED){
				realCount ++;
				openCount ++;
				if(betSchemePO.getAfterTaxBonus().intValue() > betSchemePO.getTotalAmount()){
					gainCount ++;
				}
				totalBonus = totalBonus.add(betSchemePO.getAfterTaxBonus());
			}
		}
		weiboUserStatis.setRealWeibo7Count(realCount);
		weiboUserStatis.setRealWeibo7GainCount(gainCount);
		weiboUserStatis.setRealWeibo7OpenCount(openCount);
		weiboUserStatis.setUpdateDate(new Date());
		weiboUserStatis.setTotalBonus(totalBonus.doubleValue());
		return weiboUserStatis;
	}
	
	private WeiboUserStatis statisticUserWeiboCount(WeiboUserStatis weiboUserStatis){
		int i = messageService.weiboCount(weiboUserStatis.getWeiboUserId()+"").intValue();
		weiboUserStatis.setWeiboCount(i);
		return weiboUserStatis;
	}
	
	@Override
	@Transactional
	public void statisticRealWeiboData() {
		Date from = get7From();
		Date to = get7To();
		logger.info("start statistic RealWeiboData from '{}' to '{}'",from,to);
		LinkedHashSet<String> userIds = statisticUserDataDao.zrange(Keys.WEIBO_USERS, 0, -1);
		for (String id : userIds) {
			WeiboUser user = userAccountService.findWeiboUserByWeiboUid(id);
			if(StringUtils.isNotBlank(user.getNickName())){
				Long userId = user.getId();
				List<BetSchemePO> betSchemePOs = betSchemeDao.findBySponsorAndShowScheme(userId, true, from, to);
				logger.info("user ：{}, scheme Count={}",user.getNickName(),betSchemePOs.size());
				WeiboUserStatis weiboUserStatis = dealWeiboUserStatis(betSchemePOs);
				weiboUserStatis.setWeiboUserId(Long.parseLong(id));
				weiboUserStatis.setNickName(user.getNickName());
				weiboUserStatis.setId(user.getId());
				weiboUserStatis = statisticUserWeiboCount(weiboUserStatis);//统计微博数量
				statisticUserDataDao.hashAdd(Keys.getWeiboUserStatisKey(user.getWeiboUserId()+""),weiboUserStatis);
			}
		}
	}
	
	@Override
	@Transactional
	public void statisticRecWeiboData() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void statisticBonusTop10() {
		List<WeiboUserStatis> weiboUserStatisList = getAllWeiboUserStatis();
		Collections.sort(weiboUserStatisList, new Last7TotalBonusComparator());
		statisticUserDataDao.delete(Keys.Last7TotalBonusTop10);
		Map<Long, String> idsMap = new HashMap<Long, String>();
		
		for (WeiboUserStatis weiboUserStatis : weiboUserStatisList) {
			if( idsMap.size() > 10){
				break;
			} else {
				if(null == idsMap.get(weiboUserStatis.getId()) &&
						StringUtils.isNotBlank(weiboUserStatis.getNickName())){
					idsMap.put(weiboUserStatis.getId(), weiboUserStatis.getNickName());
					statisticUserDataDao.zadd(weiboUserStatis.getTotalBonus(), Keys.Last7TotalBonusTop10, weiboUserStatis.getWeiboUserId()+"");
					logger.info("总中奖金额：weiboUserId={},总中奖金额={}",
							weiboUserStatis.getWeiboUserId(),
							weiboUserStatis.getTotalBonus());
				}
			}
		}
	}
	
	@Override
	public void statisticGuoDanLvTop10() {
		List<WeiboUserStatis> weiboUserStatisList = getAllWeiboUserStatis();
		Collections.sort(weiboUserStatisList, new Last7GuoDanLvComparator());
		statisticUserDataDao.delete(Keys.Last7GuoDanLvTop10);
		Map<Long, String> idsMap = new HashMap<Long, String>();
		
		for (WeiboUserStatis weiboUserStatis : weiboUserStatisList) {
			if( idsMap.size() > 10){
				break;
			} else {
				if(null == idsMap.get(weiboUserStatis.getId()) &&
						StringUtils.isNotBlank(weiboUserStatis.getNickName())){
					idsMap.put(weiboUserStatis.getId(), weiboUserStatis.getNickName());
					if(weiboUserStatis.getRealWeibo7GainCount() != 0){
						statisticUserDataDao.zadd(
								weiboUserStatis.getRealWeibo7GainCount()*100/weiboUserStatis.getRealWeibo7OpenCount(), 
								Keys.Last7GuoDanLvTop10, 
								weiboUserStatis.getWeiboUserId()+"");
						logger.info("过单率排行：weiboUserId={},过单率={}%",
									weiboUserStatis.getWeiboUserId(),
									weiboUserStatis.getRealWeibo7GainCount()*100/weiboUserStatis.getRealWeibo7OpenCount());
					} else {
						statisticUserDataDao.zadd(0, 
								Keys.Last7GuoDanLvTop10, 
								weiboUserStatis.getWeiboUserId()+"");
						logger.info("过单率排行：weiboUserId={},过单率={}%",
								weiboUserStatis.getWeiboUserId(),0);
					}
				}
			}
		}
	}
	
	@Override
	public void statisticActiveUser() {
		List<WeiboUserStatis> weiboUserStatisList = getAllWeiboUserStatis();
		Collections.sort(weiboUserStatisList, new WeiboCountComparator());
		for (WeiboUserStatis weiboUserStatis : weiboUserStatisList) {
			statisticUserDataDao.zadd(weiboUserStatis.getWeiboCount(),Keys.WeiboCountRankList,weiboUserStatis.getWeiboUserId()+"");
		}
		for (Long id : OfficialId.getAll()) {//将推荐帐号放在活跃用户最前面
			statisticUserDataDao.zadd(new Date().getTime(),Keys.WeiboCountRankList,id.longValue()+"");
		}
	}
	
	private List<WeiboUserStatis> getAllWeiboUserStatis(){
		LinkedHashSet<String> userIds = statisticUserDataDao.zrange(Keys.WEIBO_USERS, 0, -1);
		
		List<WeiboUserStatis> weiboUserStatisList = new ArrayList<WeiboUserStatis>();
		for (String id : userIds) {
			try{
				WeiboUserStatis w = statisticUserDataDao.hashGet(Keys.getWeiboUserStatisKey(id));
				w.setWeiboUserId(Long.parseLong(id));
				weiboUserStatisList.add(w);
			} catch (Exception e) {
				logger.error("",e);
			}
		}
		return weiboUserStatisList;
	}
	@Override
	@Transactional
	public void statisticAllUserLast7DaysWin() {
		Date from = get7From();
		int publishShow = EntityType.PUBLISH_SHOW;
		int type = EntityType.BET_TYPE_ALONE;
		int status = EntityStatus.TICKET_AWARDED;
		//已经按照税后奖金排序的方案列表
		List<BetSchemePO> betSchemePOs = betSchemeDao
				.findScheme(publishShow,type,from,status,200);
		Map<String, BetSchemePO> schemeMap = new HashMap<String,BetSchemePO>();
		for (BetSchemePO betSchemePO : betSchemePOs) {
			if(null == schemeMap.get(betSchemePO.getSponsor())){
				schemeMap.put(betSchemePO.getSponsor(),
						betSchemePO);
			}
			if(schemeMap.size() >= 20){
				break;
			}
		}
		for (String key : schemeMap.keySet()) {
			BetSchemePO b = schemeMap.get(key);
			long postId = messageService.getWeiboIdByShowSchemeId(b.getId());
			if(postId > 0){
				statisticUserDataDao.zadd(
						b.getAfterTaxBonus().doubleValue(),
						Keys.WeiboWinRankList, postId+"");
			}
		}
	}
}

class Last7TotalBonusComparator implements Comparator<WeiboUserStatis>{
	@Override
	public int compare(WeiboUserStatis o1, WeiboUserStatis o2) {
		double i = o1.getTotalBonus() - o2.getTotalBonus();
		return  -(i > 0.0 ? 1 : i == 0 ? 0 :-1);
	}
}
class WeiboCountComparator implements Comparator<WeiboUserStatis>{
	@Override
	public int compare(WeiboUserStatis o1, WeiboUserStatis o2) {
		int i = o1.getWeiboCount() - o2.getWeiboCount();
		return  -i;
	}
}
class Last7GuoDanLvComparator implements Comparator<WeiboUserStatis>{
	@Override
	public int compare(WeiboUserStatis o1, WeiboUserStatis o2) {
		int i1 = 0;
		int i2 = 0;
		if(o1.getRealWeibo7OpenCount() == 0){
			i1=0;
		} else {
			i1 = o1.getRealWeibo7GainCount()*100/o1.getRealWeibo7OpenCount();
		}
		
		if(o2.getRealWeibo7OpenCount() == 0){
			i2=0;
		} else {
			i2 = o2.getRealWeibo7GainCount()*100/o2.getRealWeibo7OpenCount();
		}
		int i = (i1 - i2);
		return -i;
	}
}