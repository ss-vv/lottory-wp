package com.xhcms.lottery.commons.persist.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.xhcms.lottery.commons.data.BJDCMatchsViewResult;
import com.xhcms.lottery.commons.data.IssueInfo;
import com.xhcms.lottery.commons.persist.dao.BJDCMatchPlayDao;
import com.xhcms.lottery.commons.persist.dao.MatchColorDao;
import com.xhcms.lottery.commons.persist.entity.BJDCMatchPlayPO;
import com.xhcms.lottery.commons.persist.entity.MatchPlay;
import com.xhcms.lottery.commons.persist.service.BJDCMatchService;
import com.xhcms.lottery.commons.persist.service.IssueService;
import com.xhcms.lottery.commons.util.BJDCPlayIdConvert;
import com.xhcms.lottery.lang.EntityStatus;

public class BJDCMatchServiceImpl implements BJDCMatchService {

	final static Logger logger = LoggerFactory
			.getLogger(BJDCMatchService.class);

	@Autowired
	private BJDCMatchPlayDao bjdcMatchPlayDao;
	
	@Autowired
	IssueService issueService;
	
	@Autowired
    private MatchColorDao matchColorDao;

	@Transactional(readOnly=true)
	@Override
	public List<MatchPlay> listBDOnSale(String playId, String issueNumber) {
		List<Object[]> data = bjdcMatchPlayDao.findByStatus(issueNumber,
				playId, EntityStatus.MATCH_ON_SALE);
		
		return convertBDMatchs(data);
	}
	
	private List<MatchPlay> convertBDMatchs(List<Object[]> data) {
		if(null == data) {
			return null;
		}
		List<MatchPlay> results = new ArrayList<MatchPlay>(data.size());
		Map<String,String> leagueMap = new HashMap<String, String>();
		
		for (Object[] d : data) {
			MatchPlay mp = new MatchPlay();
			BJDCMatchPlayPO matchPlay = (BJDCMatchPlayPO) d[0];

			BeanUtils.copyProperties(d[1], mp);
			if(null != matchPlay.getConcedePoints()) {
				mp.setDefaultScore(matchPlay.getConcedePoints().floatValue());
			}
			mp.setPlayId(matchPlay.getPlayId());
			mp.setOptions(matchPlay.getOptions());
			mp.setOdds(matchPlay.getOdds());
			leagueMap.put(mp.getLeagueName(), mp.getLeagueName());
			
			results.add(mp);
		}
		
		Map<String, String> colors = new HashMap<String, String>();
        if(null != leagueMap && leagueMap.size() > 0) {
        	colors = matchColorDao.listColorsByLeagueNames(leagueMap.keySet());
        }
        for(MatchPlay mp : results){
        	mp.setColor(colors.get(mp.getLeagueName()));
        }
		
		return results;
	}

	@Transactional(readOnly=true)
	@Override
	public List<MatchPlay> listBDMatchs(String playId, String issueNumber) {
		List<Object[]> data = bjdcMatchPlayDao.findBy(issueNumber, playId);
		
		return convertBDMatchs(data);
	}
	
	@Transactional(readOnly=true)
	@Override
	public BJDCMatchsViewResult findMatchs(String issueNumber, String lotteryId, String playId) {
		String queryPlayId = BJDCPlayIdConvert.convertQueryPlayId(playId);
		BJDCMatchsViewResult viewResult = new BJDCMatchsViewResult();
		
		if(StringUtils.isBlank(issueNumber)) {
			issueNumber = issueService.findOnsale(lotteryId, queryPlayId);
			if (StringUtils.isNotBlank(issueNumber)) {
				//查询在售期北单赛程数据
				logger.debug("query bjdc playType={} match list.", new Object[] {playId });
				List<MatchPlay> result = listBDOnSale(playId, issueNumber);
				viewResult.setMatchs(result);
				viewResult.setOnSale(true);
			}
		} else {
			String onSaleIssueNumber = issueService.findOnsale(lotteryId, queryPlayId);
			List<MatchPlay> result = listBDMatchs(playId, issueNumber);
			viewResult.setMatchs(result);
			viewResult.setOnSale(issueNumber.equals(onSaleIssueNumber));
		}
		viewResult.setCurrIssue(issueNumber);
		
		Calendar beginStopTime=Calendar.getInstance();
        beginStopTime.add(Calendar.MONTH, -1);
		List<IssueInfo> issues = issueService.findByPlayId(queryPlayId, 8);
		viewResult.setIssues(issues);
		
		return viewResult;
	}
}