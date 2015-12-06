package com.xhcms.lottery.commons.persist.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xhcms.lottery.commons.data.BetMatch;
import com.xhcms.lottery.commons.data.bonus.JCLQBetOptGroup;
import com.xhcms.lottery.commons.data.bonus.JCZQBetOptGroup;
import com.xhcms.lottery.commons.persist.service.BonusCalculatorService;
import com.xhcms.lottery.commons.persist.service.OptionGroupByScoreService;
import com.xhcms.lottery.commons.util.BetMatchSplit;
import com.xhcms.lottery.commons.util.bonus.BetContent;
import com.xhcms.lottery.lang.LotteryId;

public class BonusCalculatorServiceImpl implements BonusCalculatorService {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	private static final String HIT = "_HIT_";
	
    /**
     * 竞彩投注选项按等价集合分组
     * 1.先按赛事ID分组
     * 2.每场赛事的每个选项都要拆分成BetMatch
     * 3.找出所有的等价集合
     */
	@Override
	public Map<Long, List<List<BetContent>>> groupOptionsByLottery(List<BetMatch> betMatchs, 
			String lotteryId, Map<Long, BigDecimal> matchScoreMap) {
		log.info("对投注内容进行等价集合分组，彩种ID={}, 用户投注的赛事内容:{}", lotteryId, betMatchs);
		
		Map<Long, List<List<BetContent>>> map = new HashMap<>();
		if(null!=betMatchs&&!betMatchs.isEmpty()&&StringUtils.isNotBlank(lotteryId)&&null!=matchScoreMap&&!matchScoreMap.isEmpty()){
			//with jclq deal
			if(LotteryId.JCLQ.name().equals(lotteryId)) {
				Map<Long, List<BetMatch>> matchGroup = matchGroup(betMatchs);
				Set<Long> matchIds = matchGroup.keySet();
				Iterator<Long> iter = matchIds.iterator();
				while(iter.hasNext()) {
					Long matchId = iter.next();
					List<BetMatch> betMatchGroupList = matchGroup.get(matchId);
					List<BetContent> splitBetMatchsWithJCLQ = splitBetMatchsWithJCLQ(betMatchGroupList);
					List<List<BetContent>> result = groupJCLQEquOption(splitBetMatchsWithJCLQ, matchScoreMap);
					if(result.size() > 0) {
						map.put(matchId, result);
					}
					log.debug("获取到竞彩篮球赛事ID={},等价集合分组数据={}", matchId, result);
				}
			} else if(LotteryId.JCZQ.name().equals(lotteryId)){
				Map<Long, List<BetMatch>> matchGroup = matchGroup(betMatchs);
				Set<Long> matchIds = matchGroup.keySet();
				Iterator<Long> iter = matchIds.iterator();
				while(iter.hasNext()) {
					Long matchId = iter.next();
					List<BetMatch> betMatchGroupList = matchGroup.get(matchId);
					List<BetContent> splitBetMatchsWithJCZQ = splitBetMatchsWithJCLQ(betMatchGroupList);
					List<List<BetContent>> result = groupJCZQEquOption(splitBetMatchsWithJCZQ, matchScoreMap);
					if(result.size() > 0) {
						map.put(matchId, result);
					}
					log.debug("获取到竞彩足球赛事ID={},等价集合分组数据={}", matchId, result);
				}
			}
		}
		log.info("生成的等价集合：{}", map);
		return map;
	}

	private List<List<BetContent>> groupJCZQEquOption(
			List<BetContent> bcList,
			Map<Long, BigDecimal> matchScoreMap) {
		Long matchId = bcList.get(0).getMatchId();
		BigDecimal score = matchScoreMap.get(matchId);
		
		log.info("竞彩足球赛事ID={}, 对应让球数={}", matchId, score);
		
		JCZQBetOptGroup equRule = new JCZQBetOptGroup();
		equRule = OptionGroupByScoreService.findHitResults(LotteryId.JCZQ.name(), score, equRule);		
		
		List<List<String>> equList = equRule.getList();
		
		return composeBcList(equList, bcList);
	}

	/**
	 * 对拆分好的BetMatch数组进行等价集合分组
	 * @param bcList
	 */
	private List<List<BetContent>> groupJCLQEquOption(List<BetContent> bcList,
			Map<Long, BigDecimal> matchScoreMap) {
		Long matchId = bcList.get(0).getMatchId();
		BigDecimal score = matchScoreMap.get(matchId);
		
		log.info("竞彩篮球赛事ID={}, 对应让分值={}", matchId, score);
		
		JCLQBetOptGroup equRule = new JCLQBetOptGroup();
		equRule = OptionGroupByScoreService.findHitResults(LotteryId.JCLQ.name(), score, equRule);		
		
		List<List<String>> equList = equRule.getList();
		
		return composeBcList(equList, bcList);
	}

	/**
	 * 按用户投注项列表，分出多个等价集合列表
	 * @param equList
	 * @param bcList
	 */
	private List<List<BetContent>> composeBcList(List<List<String>> equList,
			List<BetContent> bcList) {
		List<List<BetContent>> results = new ArrayList<>();
		List<List<String>> prepareList = new ArrayList<>();
		for(List<String> src : equList) {
			List<String> dest = new ArrayList<String>(src.size());
			for(int c=0; c<src.size(); c++) {
				dest.add(c, "");
			}
			Collections.copy(dest, src);
			prepareList.add(dest);
		}
		
		for(int i=0; i<bcList.size(); i++) {
			BetContent bc = bcList.get(i);
			String betOption = bc.getBetOption();
			for(int index=0; index<prepareList.size(); index++) {
				List<String> equResult = prepareList.get(index);
				int postion = equResult.indexOf(betOption);
				if(-1 != postion) {
					String val = prepareList.get(index).get(postion);
					prepareList.get(index).set(postion, val + HIT + i);
				}
			}
		}
		
		Set<Set<String>> uniqPreSet = new HashSet<>();
		//unique list
		for(int index=0; index<prepareList.size(); index++) {
			List<String> equResult = prepareList.get(index);
			Set<String> rs = new HashSet<String>();
			for(int j=0; j<equResult.size(); j++) {
				String option = equResult.get(j);
				if(option.indexOf(HIT) != -1) {
					rs.add(option);
				}
			}
			if(rs.size() > 0) {
				uniqPreSet.add(rs);
			}
		}
		
		Iterator<Set<String>> uniqSetIter = uniqPreSet.iterator();
		while(uniqSetIter.hasNext()) {
			Set<String> rs = uniqSetIter.next();
			boolean isHit = false;
			List<BetContent> bcl = new ArrayList<BetContent>();
			Iterator<String> rsIter = rs.iterator();
			while(rsIter.hasNext()) {
				String option = rsIter.next();
				if(option.indexOf(HIT) != -1) {
					isHit = true;
					int pos = Integer.parseInt(option.split(HIT)[1]);
					bcl.add(bcList.get(pos));
				}
			}
			if(isHit) {
				results.add(bcl);
			}
		}
		
		return results;
	}

	/**
	 * 按选项拆分成多个BetContent
	 * @param betMatchs
	 * @return
	 */
	private List<BetContent> splitBetMatchsWithJCLQ(List<BetMatch> betMatchs) {
		List<BetContent> bsList = new ArrayList<BetContent>();
		for(BetMatch bm : betMatchs) {
			List<BetContent> resultList = BetMatchSplit.split(bm);
			if(null != resultList && resultList.size() > 0) {
				bsList.addAll(resultList);
			}
		}
		log.debug("将用户投注的每一个选项转换为BetContent对象,得到的结果集:{}", bsList);
		return bsList;
	}
	
	/**
	 * 对赛事进行分组：竞彩篮球和竞彩足球通用
	 * @param betMatchs
	 * @return
	 */
	private Map<Long, List<BetMatch>> matchGroup(List<BetMatch> betMatchs) {
		Long tmpMatchId = 0L;
		Map<Long, List<BetMatch>> matchGroupMap = new HashMap<Long, List<BetMatch>>();
		for(BetMatch bm : betMatchs) {
			Long matchId = bm.getMatchId();
			if(null != matchId && matchId > 0) {
				if(0L == tmpMatchId || tmpMatchId.longValue() == matchId.longValue()) {
					if(matchGroupMap.get(matchId) == null) {
						matchGroupMap.put(matchId, new ArrayList<BetMatch>());
					}
					matchGroupMap.get(matchId).add(bm);
				} else {
					tmpMatchId = matchId;
				}
			}
		}
		log.debug("对投注内容按赛事ID分组，得到结果：{}", matchGroupMap);
		return matchGroupMap;
	}
}