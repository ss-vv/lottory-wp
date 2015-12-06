/**
 * 
 */
package com.xhcms.lottery.dc.persist.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.Bet;
import com.xhcms.lottery.commons.data.BetResult;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.CustomMade;
import com.xhcms.lottery.commons.data.CustomMadeAvaiableScheme;
import com.xhcms.lottery.commons.data.User;
import com.xhcms.lottery.commons.persist.dao.BetSchemeDao;
import com.xhcms.lottery.commons.persist.dao.CustomMadeAvaiableSchemeDao;
import com.xhcms.lottery.commons.persist.dao.CustomMadeDao;
import com.xhcms.lottery.commons.persist.dao.CustomMadeDetailDao;
import com.xhcms.lottery.commons.persist.entity.CustomMadeAvaiableSchemePO;
import com.xhcms.lottery.commons.persist.entity.CustomMadeDetailPO;
import com.xhcms.lottery.commons.persist.entity.CustomMadePO;
import com.xhcms.lottery.commons.persist.service.BetSchemeBaseService;
import com.xhcms.lottery.dc.persist.service.CustomMadeService;
import com.xhcms.lottery.lang.EntityStatus;

/**
 * @author Bean.Long
 * 
 */
public class CustomMadeServiceImpl implements CustomMadeService {
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory
			.getLogger(CustomMadeServiceImpl.class);

	@Autowired
	private CustomMadeDao customMadeDao;
	@Autowired
	private CustomMadeDetailDao customMadeDailyDao;
	@Autowired
	private CustomMadeAvaiableSchemeDao customMadeAvaiableSchemeDao;
	@Autowired
	private BetSchemeBaseService betSchemeBaseService;

	@Autowired
	private BetSchemeDao betSchemeDao;

	@Override
	@Transactional
	public void betFollow(CustomMade customMade, BetScheme betScheme, Bet bet) {
	
		int	result = betSchemeBaseService.betFollow(
					customMade.getUserId(), betScheme,
					bet.getTickets());
			// 增加自动跟单明细
		if(result == 0) {
			addCustomMadeDetail(customMade, betScheme.getId(),
					betScheme.getBuyAmount());
		}
	}
	
	@Transactional
	public void purchase(CustomMade cm, Long origalShemeId, int buyMoney) {
		BetResult br = betSchemeBaseService.purchase(cm.getUserId(),
					origalShemeId, buyMoney);
		int purchaseStatus = br.getAppCode();
			
		if(purchaseStatus == 0 || purchaseStatus == -1) {
				addCustomMadeDetail(cm, origalShemeId, buyMoney);
		}
	}
	
	@Transactional(readOnly=true)
	public boolean checkCustomMadeDaily(Long userId, Long followedUserId, int maxMoney,
			int currentMoney, int maxFollowCount) {
		Date currentTime = Calendar.getInstance().getTime();
		Date startTime = DateUtils.truncate(currentTime, Calendar.DATE);
		Date endTime = DateUtils.addDays(startTime, 1);

		int count = customMadeDailyDao.countFollowCount(userId,  followedUserId, startTime,
				endTime);
		if (count >= maxFollowCount) {
			return false;
		}

		int totalMoney = customMadeDailyDao.sumMoneny(userId, followedUserId, startTime,
				endTime);
		if ((totalMoney + currentMoney) > maxMoney)
			return false;

		return true;
	}
	
	@Transactional(readOnly=true)
	public boolean checkCustomMadeCount(Long userId, Long followedUserId, int maxFollowCount) {
		Date currentTime = Calendar.getInstance().getTime();
		Date startTime = DateUtils.truncate(currentTime, Calendar.DATE);
		Date endTime = DateUtils.addDays(startTime, 1);

		int count = customMadeDailyDao.countFollowCount(userId, followedUserId, startTime,
				endTime);
		if (count >= maxFollowCount)
			return false;

		return true;
	}
	
	@Transactional(readOnly=true)
	public int checkCustomMadeSum(Long userId, Long followedUserId, int maxMoney,
			int currentMoney) {
		Date currentTime = Calendar.getInstance().getTime();
		Date startTime = DateUtils.truncate(currentTime, Calendar.DATE);
		Date endTime = DateUtils.addDays(startTime, 1);
		
		int totalMoney = customMadeDailyDao.sumMoneny(userId, followedUserId, startTime,
				endTime);
		
		if(maxMoney - totalMoney >= currentMoney) {
			return currentMoney;
		} else if((maxMoney - totalMoney) > 0 && (maxMoney - totalMoney) < currentMoney) {
			return maxMoney - totalMoney;
		} else {
			return 0;
		}
	}
	
	@Transactional
	public void addCustomMadeDetail(CustomMade customMade, Long schemeId,
			int betMoney) {
		CustomMadeDetailPO dailyPO = new CustomMadeDetailPO();
		CustomMadePO customMadePO = customMadeDao.get(customMade.getId());
		//dailyPO.setId(0L);
		dailyPO.setCreateTime(Calendar.getInstance().getTime());
		dailyPO.setCustomMade(customMadePO);
		dailyPO.setBetScheme(betSchemeDao.get(schemeId));
		dailyPO.setBetMoney(betMoney);
		
		customMadeDailyDao.save(dailyPO);
	}

	@Override
	@Transactional(readOnly = true)
	public List<CustomMadeAvaiableScheme> listCustomMadeAvaiableSchemes() {
		List<CustomMadeAvaiableSchemePO> availablePOs = customMadeAvaiableSchemeDao
				.listCustomMadeAvaiableSchemes(EntityStatus.CUSTOMMADE_STATUS_NO);
		
		List<CustomMadeAvaiableScheme> result = new ArrayList<CustomMadeAvaiableScheme>();
		
		for(CustomMadeAvaiableSchemePO po : availablePOs) {
			CustomMadeAvaiableScheme data = new CustomMadeAvaiableScheme();
			BeanUtils.copyProperties(po, data);
			result.add(data);
		}
		
		return result;
	}
	
	@Transactional
	@Override
	public void updateCustomMadeStatus(Long id, int status) {
		CustomMadeAvaiableSchemePO po = customMadeAvaiableSchemeDao.get(id);
		po.setStatus(status);
	}
	
	@Transactional
	public List<CustomMade> listCustomMades (long sponsorId, Paging paging) {
		List<CustomMadePO> customMadePOs = customMadeDao
				.findFollowMeCustomMades(sponsorId, paging);
		
		List<CustomMade> result = new ArrayList<CustomMade>();
		
		for(CustomMadePO po : customMadePOs) {
			result.add(copyBean(po));
		}
		
		return result;
	}
	
	@Transactional
	@Override
	public List<CustomMade> listAllCustomMades (long sponsorId) {
		List<CustomMadePO> customMadePOs = customMadeDao
				.topFollowMeCustomMades(sponsorId, -1);
		
		List<CustomMade> result = new ArrayList<CustomMade>();
		
		for(CustomMadePO po : customMadePOs) {
			result.add(copyBean(po));
		}
		
		return result;
	}
	
	private CustomMade copyBean(CustomMadePO po) {
		if(po == null)
			return null;
		
		CustomMade cm = new CustomMade();
		BeanUtils.copyProperties(po, cm, new String[]{"followedUser"});
		
		if(po.getFollowedUser() != null) {
			User user = new User();
			BeanUtils.copyProperties(po.getFollowedUser(), user);
			cm.setFollowedUser(user);
		}

		return cm;
	}
}
