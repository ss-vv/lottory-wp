package com.xhcms.lottery.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.xhcms.lottery.commons.data.repeat.RepeatBetContent;
import com.xhcms.lottery.commons.persist.dao.RepeatBetContentDao;
import com.xhcms.lottery.commons.persist.entity.RepeatBetContentPO;
import com.xhcms.lottery.service.RepeatBetContentService;

public class RepeatBetContentServiceImpl implements RepeatBetContentService {

	@Autowired
	private RepeatBetContentDao betContentDao;
	
	@Override
	@Transactional
	public void saveRepeatBetContent(List<RepeatBetContent> betContentList) {
		List<RepeatBetContentPO> list = new ArrayList<RepeatBetContentPO>();
		for(RepeatBetContent betCont : betContentList) {
			RepeatBetContentPO betContentPO = new RepeatBetContentPO();
			BeanUtils.copyProperties(betCont, betContentPO);
			list.add(betContentPO);
		}
		betContentDao.saveBetContentBatch(list);
	}
	
	@Override
	@Transactional
	public List<RepeatBetContent> queryBetContentOfPlanId(long planId) {
		List<RepeatBetContentPO> list = betContentDao.queryBetContentOfPlanId(planId);
		List<RepeatBetContent> betContentList = new ArrayList<RepeatBetContent>();
		if(null != list && list.size() > 0) {
			for(RepeatBetContentPO betContentPO : list) {
				RepeatBetContent betContent = new RepeatBetContent();
				BeanUtils.copyProperties(betContentPO, betContent);
				betContentList.add(betContent);
			}
		}
		return betContentList;
	}

	@Transactional
	@Override
	public List<String> queryBetListOfPlanId(long planId) {
		return betContentDao.queryBetListOfPlanId(planId);
	}
}
