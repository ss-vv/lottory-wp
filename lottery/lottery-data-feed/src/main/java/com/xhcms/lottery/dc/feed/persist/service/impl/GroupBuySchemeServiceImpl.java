/**
 * 
 */
package com.xhcms.lottery.dc.feed.persist.service.impl;

import java.util.ArrayList;
import java.util.Date;
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
import com.xhcms.lottery.commons.data.Play;
import com.xhcms.lottery.commons.persist.dao.BetSchemeDao;
import com.xhcms.lottery.commons.persist.entity.BetSchemeWithPlayPO;
import com.xhcms.lottery.commons.persist.service.GetPresetSchemeService;
import com.xhcms.lottery.dc.feed.persist.service.GroupBuySchemeService;
import com.xhcms.lottery.lang.EntityStatus;

/**
 * @author Bean.Long
 *
 */
@Transactional
public class GroupBuySchemeServiceImpl implements GroupBuySchemeService {
	@Autowired
	private GetPresetSchemeService getPresetSchemeService;
	@Autowired
	private BetSchemeDao betSchemeDao;
	
	private List<String> orderByFields = new ArrayList<String>();
	
	public GroupBuySchemeServiceImpl() {
		orderByFields.add("totalProcessPer");
		orderByFields.add("offtime");
		orderByFields.add("returnRate");
		orderByFields.add("totalAmount");
		orderByFields.add("shareRatio");
	}
	
	@Override
	@Transactional(readOnly = true)
	public void pagingRecommendGroupBuyShcemes(String username, Integer commission,
			String play, String orderBy, boolean asc, Date startTime, 
			Date endTime, Paging paging) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BetSchemeWithPlayPO.class);

		if(StringUtils.isNotEmpty(username)) {
			criteria.add(Restrictions.ilike("sponsor", username));
		}
		
		//佣金
		if(commission != null && commission != -1) {
			if(commission == 2) {
				criteria.add(Restrictions.gt("shareRatio", 0));
			} else if(commission == 1){
				criteria.add(Restrictions.eq("shareRatio", 0));
			} else {
				//all
			}
		}
		
		if(StringUtils.isNotEmpty(play)) {
			criteria.add(Restrictions.eq("playId", play));
		}
		
		//推荐方案
		
		criteria.add(Restrictions.eq("recommendation", 1));
		criteria.add(Restrictions.eq("type", EntityStatus.BETSCHEME_TYPE_GROUPBUY));
		
		//佣金和截止时间排序
		//EntityStatus.SCHEME_ON_SALE;
		criteria.add(Restrictions.ge("createdTime", startTime));
		criteria.add(Restrictions.le("createdTime", endTime));
		
		addOrderFields(criteria, orderBy, asc);
		
		List<?> schemePOs = betSchemeDao.findBetSchemeByDetachCriteria(criteria, paging);
		
		List<BetScheme> result = new ArrayList<BetScheme>();
		for(Object po : schemePOs) {
			result.add(copyBean((BetSchemeWithPlayPO)po));
		}
		paging.setResults(result);
	}

	@Override
	@Transactional(readOnly = true)
	public void pagingGroupBuyShcemesByLottery(String lottery, String username,
			Integer commission, String play, String orderBy, Boolean asc, Date startTime, 
			Date endTime, Paging paging) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(BetSchemeWithPlayPO.class);
		
		if(StringUtils.isNotEmpty(username)) {
			criteria.add(Restrictions.ilike("sponsor", username));
		}
		
		if(StringUtils.isNotEmpty(lottery)) {
			criteria.add(Restrictions.eq("lotteryId", lottery));
		}

		//佣金
		if(commission != null && commission != -1) {
			if(commission == 2) {
				criteria.add(Restrictions.gt("shareRatio", 0));
			} else if(commission == 1){
				criteria.add(Restrictions.eq("shareRatio", 0));
			} else {
				//all
			}
		}
		
		if(StringUtils.isNotEmpty(play)) {
			criteria.add(Restrictions.eq("playId", play));
		}
		
		//推荐方案
		criteria.add(Restrictions.eq("type", EntityStatus.BETSCHEME_TYPE_GROUPBUY));
		criteria.add(Restrictions.ge("createdTime", startTime));
		criteria.add(Restrictions.le("createdTime", endTime));
		
		//criteria.add(Restrictions.eq("saleStatus", 0));

		//佣金和截止时间排序
		addOrderFields(criteria, orderBy, asc);
		
		List<?> schemePOs = betSchemeDao.findBetSchemeByDetachCriteria(criteria, paging);
		
		List<BetScheme> result = new ArrayList<BetScheme>();
		for(Object po : schemePOs) {
			result.add(copyBean((BetSchemeWithPlayPO)po));
		}
		
		paging.setResults(result);
	}
	
	private void addOrderFields(DetachedCriteria criteria, String orderBy, boolean asc) {
		criteria.addOrder(Order.asc("saleStatus"));
		criteria.addOrder(Order.desc("recommendation"));
		criteria.addOrder(Order.desc("partnerCount"));
		if(orderBy != null && orderByFields.contains(orderBy)) {
			if(asc) {
				criteria.addOrder(Order.asc(orderBy));
			} else {
				criteria.addOrder(Order.desc(orderBy));
			}
		}
		
		for(String orderField : orderByFields) {
			if(!orderField.equalsIgnoreCase(orderBy)) {
				criteria.addOrder(Order.desc(orderField));
			}
		}
	}
	
	private BetScheme copyBean(BetSchemeWithPlayPO po) {
		BetScheme betScheme = new BetScheme();
		BeanUtils.copyProperties(po, betScheme, new String[]{"play"});
		Play play = new Play();
		BeanUtils.copyProperties(po.getPlay(), play, new String[]{"passTypes"});
		getPresetSchemeService.getRightSchemeByDO(betScheme).setPlay(play);
		return betScheme;
	}
}
