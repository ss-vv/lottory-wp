package com.xhcms.lottery.commons.persist.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.persist.dao.BetSchemeDao;
import com.xhcms.lottery.commons.persist.dao.GrantDao;
import com.xhcms.lottery.commons.persist.dao.PMPromotionDao;
import com.xhcms.lottery.commons.persist.dao.ShowSchemeDao;
import com.xhcms.lottery.commons.persist.dao.UserScoreDao;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;
import com.xhcms.lottery.commons.persist.entity.GrantPO;
import com.xhcms.lottery.commons.persist.entity.PromotionPO;
import com.xhcms.lottery.commons.persist.entity.UserScorePO;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.lottery.commons.persist.service.GetPresetSchemeService;
import com.xhcms.lottery.commons.persist.service.ShowFollowQueryCondition;
import com.xhcms.lottery.commons.persist.service.ShowSchemeService;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.EntityType;
import com.xhcms.lottery.utils.NumberUtils;


/**
 * 晒单服务
 * 
 * @author zhuyongli
 */
public class ShowSchemeServiceImpl implements ShowSchemeService {

	@Autowired
	private ShowSchemeDao showSchemeDao;
	@Autowired
	private GetPresetSchemeService getPresetSchemeService;
	@Autowired
	private PMPromotionDao pMPromotionDao;
	@Autowired
	private UserScoreDao userScoreDao;
    @Autowired
    private BetSchemeDao betSchemeDao;
	@Autowired
	private AccountService accountService;
	@Autowired
	private GrantDao grantDao;

	/**
	 * 查找在售的晒单方案。 排序规则: (全部推荐彩种) 人气、截止时间、投注金额、战绩; <br/>
	 * 排序: (足球、篮球) 是否推荐、人气、截止时间、投注金额、战绩;
	 */
	@Override
	@Transactional(readOnly = true)
	public void findOnSaleShowingSchemes(Paging paging, boolean isRecommend,
			String lotteryId, String username, String playId, int followRatio,
			String orderBy, boolean asc) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BetSchemePO.class);
		criteria.add(Restrictions.gt("offtime", new Date()))
			.add(Restrictions.eq("showScheme", EntityType.SHOW_SCHEME));
		//推荐方案
		if(isRecommend) {
			criteria.add(Restrictions.eq("recommendation", 1));
		}
		//彩种
		if(StringUtils.isNotBlank(lotteryId)) {
			criteria.add(Restrictions.eq("lotteryId", lotteryId));
		}
		//发起人名称
		if (StringUtils.isNotBlank(username)) {
			criteria.add(Restrictions.like("sponsor", "%" + username + "%"));
		}
		//玩法
		if (StringUtils.isNotBlank(playId)) {
			criteria.add(Restrictions.eq("playId", playId));
		}
		//佣金提成
		if(followRatio == 1) {
			criteria.add(Restrictions.eq("followedRatio", 0));
		} else if(followRatio == 2) {
			criteria.add(Restrictions.gt("followedRatio", 0));
		} else {
			//all
		}
		// 过滤出票失败和撤单方案
		criteria.add(Restrictions.ne("status", EntityStatus.TICKET_BUY_FAIL));
		criteria.add(Restrictions.ne("status", EntityStatus.TICKET_SCHEME_CANCEL));

		compseOrder(criteria, lotteryId, orderBy, asc);
		List<BetSchemePO> schemes = showSchemeDao
				.findOnSaleShowingSchemes(paging, criteria);
		
		List<BetScheme> result = new ArrayList<BetScheme>();
		for(BetSchemePO po : schemes) {
			BetScheme betScheme = new BetScheme();
			BeanUtils.copyProperties(getPresetSchemeService.getRightSchemeByPO(po), betScheme);
			result.add(betScheme);
		}
		
		paging.setResults(result);
	}
	
	private void compseOrder(DetachedCriteria criteria,String lotteryId, String orderBy, boolean asc) {
		if (StringUtils.isBlank(orderBy)) {
			if (StringUtils.isNotBlank(lotteryId)) {
				criteria.addOrder(Order.desc("recommendation"));
			}
			criteria.addOrder(Order.desc("followingCount"))
					.addOrder(Order.asc("offtime"))
					.addOrder(Order.desc("totalAmount"));
		} else {
			if (asc) {
				criteria.addOrder(Order.asc(orderBy));
			} else {
				criteria.addOrder(Order.desc(orderBy));
			}
		}
	}
	
	/**
	 * 查询某个人的晒单方案
	 * 
	 * @param userId
	 * @return
	 */
	@Override
	@Transactional(readOnly = true)
	public void findShowSchemesByCondition(Paging paging,
			ShowFollowQueryCondition condition) {
		List<BetSchemePO> schemes = showSchemeDao
				.findShowSchemes(condition);

		List<BetScheme> result = new ArrayList<BetScheme>();
		for(Object po : schemes) {
			BetScheme betScheme = new BetScheme();
			BeanUtils.copyProperties(po, betScheme);			
			
			result.add(betScheme);
		}
		
		paging.setResults(result);
	}

	/**
	 * 查询某个人的跟单方案
	 * 
	 * @param userId
	 * @return
	 */
	@Override
	@Transactional(readOnly = true)
	public void findFollowSchemesByCondition(Paging paging,
			ShowFollowQueryCondition condition) {		
		List<BetSchemePO> schemes = showSchemeDao
				.findFollowSchemes(condition);

		List<BetScheme> result = new ArrayList<BetScheme>();
		for(Object po : schemes) {
			BetScheme betScheme = new BetScheme();
			BeanUtils.copyProperties(po, betScheme);
			
			if(condition.getStatus() > 0) {
				List followData = showSchemeDao.findFollowByUserId(betScheme.getId(), condition.getUserId());
				if (followData != null && followData.size() > 0) {
					for (int j = 0; j < followData.size(); j++) {
						Object[] data = (Object[]) followData.get(j);
						if (data[0] != null) {
							betScheme.setFollowTotalAmount(((Long) data[0])
									.intValue());
						}
						if (data[1] != null) {
							betScheme.setFollowTotalBonus((BigDecimal) data[1]);
						}
					}
				}
			}
			
			result.add(betScheme);
		}
		
		paging.setResults(result);
	}

	@Override
	@Transactional
	public void pagingRecommendFollowShcemes(Date startTime, Date endTime, Paging paging) {
		List<BetSchemePO> schemes = showSchemeDao.queryShowSchemes(Constants.RECOMMEND, startTime, endTime, paging);
		
		List<BetScheme> results = new ArrayList<BetScheme>();
		for(BetSchemePO po : schemes) {
			BetScheme betScheme = new BetScheme();
			BeanUtils.copyProperties(po, betScheme);
			results.add(betScheme);
		}
		
		paging.setResults(results);
	}
	
	@Override
	public void awardedShowFollowGrant(Integer operator, BetSchemePO betSchemePo) {
		PromotionPO promotionPO = pMPromotionDao
				.get(Constants.SHOW_FOLLOW_PROMOTION_ID);
		if(checkPromotionTime(promotionPO)) {
			if(null != betSchemePo) {
				showFollowAward(operator, betSchemePo, promotionPO);
			}
		}
	}
	
	@Override
	public void notAwardShowFollowGrant(Integer operator, HashMap<Long, BetSchemePO> sMap) {
		PromotionPO promotionPO = pMPromotionDao
				.get(Constants.SHOW_FOLLOW_PROMOTION_ID);
		if(checkPromotionTime(promotionPO)) {
			if(null != sMap && !sMap.isEmpty()){
				for(BetSchemePO betSchemePO : sMap.values()){
					if (shouldsfAward(betSchemePO)
							&& shouldbetSchemeGrant(betSchemePO.getId(), promotionPO)) {
						showFollowAward(operator, betSchemePO, promotionPO);
					}
				}
			}
		}
	}
	
	private boolean checkPromotionTime(PromotionPO promotionPO) {
		Date date = new Date();
		return null != promotionPO
				&& date.compareTo(promotionPO.getStartTime()) >= 0
				&& date.compareTo(promotionPO.getEndTime()) <= 0;
	}
	
	private boolean shouldsfAward(BetSchemePO betSchemePO) {
		return betSchemePO.getStatus() == EntityStatus.TICKET_NOT_WIN
				&& betSchemePO.getType() == EntityType.BETTING_ALONE
				&& betSchemePO.getShowScheme() == EntityType.SHOW_SCHEME;
	}
	
	private boolean shouldbetSchemeGrant(Long schemeId, PromotionPO promotionPO) {
		GrantPO grantPO = null;
		if(null != promotionPO) {
			grantPO = grantDao.find(schemeId, promotionPO.getGrantTypeId());
		}
		if(null != promotionPO && null == grantPO) {
			return true;
		} else {
			return false;
		}
	}
	
	
	@Transactional
	private void showFollowAward(Integer operator, BetSchemePO betSchemePo, PromotionPO promotionPO) {
		BigDecimal percent = getGrantPercent(betSchemePo.getSponsorId());
		if(percent.compareTo(BigDecimal.ZERO) == 1) {
			int betAmount = 0;
			List<BetSchemePO> followSchemes = betSchemeDao.findFollowSchemes(betSchemePo.getId());
			if(betSchemePo.getAfterTaxBonus().compareTo(new BigDecimal(betSchemePo.getTicketNote()*2)) == 1) {
				betAmount = betSchemePo.getTicketNote()*2 + getFollowAmount(followSchemes);
				grant(operator, betAmount, percent, betSchemePo, promotionPO);
			} else {
				betAmount = betSchemePo.getTicketNote() * 2;
				grant(operator, betAmount, percent, betSchemePo, promotionPO);
				
				if(null != followSchemes && !followSchemes.isEmpty()) {
					for(BetSchemePO follow : followSchemes) {
						betAmount = follow.getTicketNote() * 2;
						grant(operator, betAmount, percent, follow, promotionPO);
					}
				}
				
			}
		}

	}
	
	private BigDecimal getGrantPercent(Long userId) {
		UserScorePO userScorePO = userScoreDao.getUserScoreByUserIdLottoryId(
				userId, Constants.ZCZ);

		int grantNum = 3;
		if(null != userScorePO) {
			long showScore = userScorePO.getShowScore();
			if(showScore >= 100) {
				grantNum = 6;
			} else if(showScore >= 10) {
				grantNum = 5;
			} else if(showScore > 0) {
				grantNum = 4;
			} else {
				grantNum = 3;
			}
		}
		
		return NumberUtils.percent(grantNum);
	}
	
	private int getFollowAmount(List<BetSchemePO> followSchemes) {
		int amount = 0;
		if(null != followSchemes && !followSchemes.isEmpty()) {
			for(BetSchemePO fscheme : followSchemes) {
				amount = amount + fscheme.getTicketNote()*2;
			}
		}
		return amount;
	}
	
	private void grant(Integer operator, Integer betAmount, BigDecimal percent,
			BetSchemePO betSchemePo, PromotionPO promotionPO) {
		BigDecimal grantAmount = new BigDecimal(betAmount).multiply(percent)
				.setScale(2, RoundingMode.DOWN);
		Long userId = betSchemePo.getSponsorId();

		Date now = new Date();
		GrantPO grant = new GrantPO();
		grant.setUserId(userId);
		grant.setOrderId(betSchemePo.getId());
		grant.setAmount(grantAmount);
		grant.setCreatedTime(now);
		grant.setOperatorId(operator);
		grant.setAuditId(operator);
		grant.setAuditTime(now);
		grant.setNote(promotionPO.getName());
		grant.setStatus(EntityStatus.GRANT_AUDIT);
		grant.setGrantTypeId(promotionPO.getGrantTypeId());
		grantDao.save(grant);

		accountService.grant(operator, userId, grantAmount,
				promotionPO.getName());
	}
	
}
