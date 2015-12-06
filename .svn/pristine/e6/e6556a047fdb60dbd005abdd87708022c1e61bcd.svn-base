package com.davcai.data.statistic.task;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.davcai.data.statistic.mapper.Top5RecommendStatisticMapper;
import com.davcai.data.statistic.model.Top5Recommend;
import com.davcai.data.statistic.model.Top5RecommendMiddle;
import com.davcai.data.statistic.task.model.BBMatch;
import com.davcai.data.statistic.task.model.BJDCMatch;
import com.davcai.data.statistic.task.model.FBMatch;
import com.davcai.data.statistic.task.model.PlayOption;
import com.unison.lottery.weibo.common.nosql.HotAndRecommendMatchDao;
import com.unison.lottery.weibo.common.nosql.Top5RecommendDao;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.HotAndRecommendMatch;
import com.xhcms.lottery.commons.data.PlayMatch;
import com.xhcms.lottery.commons.persist.entity.BBMatchPO;
import com.xhcms.lottery.commons.persist.entity.BJDCMatchPO;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;
import com.xhcms.lottery.commons.persist.entity.FBMatchPO;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.utils.DateUtils;
import com.xhcms.lottery.utils.ResultTool;


public class Top5RecommendStatisticTaskImpl implements
		Top5RecommendStatisticTask {

	private static final int _4 = 4;


	@Autowired
	private Top5RecommendStatisticMapper mapper;
	
	
	private Map<String,PlayOption>  playOptionMap=new HashMap<String,PlayOption>();//玩法对应的所有选项


	private Logger logger=LoggerFactory.getLogger(getClass());
	
	@Autowired
	private Top5RecommendDao top5RecommendDao;

	@Autowired
	private HotAndRecommendMatchDao hotAndRecommendMatchDao;
	

	@Override
	@Transactional
	public void run(Date from, Date to) {
		run4Top5Recommend(from, to);//推荐排行榜
		run4Top5Follow(from, to);//跟单排行榜
		run4Top5Show(from,to);//晒单排行榜
		run4HotMatch();//最红赛事排行榜
		

	}

	private void run4HotMatch() {
		List<HotAndRecommendMatch> hotAndRecommendMatchs=findTop5GuessMatch();
		saveHotMatch(hotAndRecommendMatchs);
		addRedis(hotAndRecommendMatchs);
	}

	private void addRedis(List<HotAndRecommendMatch> hotAndRecommendMatchs) {
		
		hotAndRecommendMatchDao.addList(hotAndRecommendMatchs);
		
	}

	private void saveHotMatch(List<HotAndRecommendMatch> hotAndRecommendMatchs) {
		mapper.deleteHotAndRecommendMatch();
		if(null!=hotAndRecommendMatchs&&!hotAndRecommendMatchs.isEmpty()){
			for(HotAndRecommendMatch match:hotAndRecommendMatchs){
				mapper.saveHotAndRecommendMatch(match);
			}
		}
		
	}

	private List<HotAndRecommendMatch> findTop5GuessMatch() {
		List<com.davcai.data.statistic.model.BetScheme> betSchemeList=mapper.findYesterdayScheme(
			      DateUtils.yesterdaybeginTime(),DateUtils.yesterdayEndTime());
		List<HotAndRecommendMatch> top5RecommendMatch=findPlayMatchWithBetScheme(betSchemeList);
		findMatchInfo(top5RecommendMatch);
		return top5RecommendMatch;
	}

	private void findMatchInfo(List<HotAndRecommendMatch> matches) {
		for(HotAndRecommendMatch hm:matches){
			if(LotteryId.JCZQ.name().equals(hm.getLottery())){
				FBMatch fbpo=mapper.findFBMatch(hm.getMatchId());
				if(fbpo!=null){
					hm.setHostTeamName(fbpo.getHomeTeamName());
					hm.setGuestTeamName(fbpo.getGuestTeamName());
					hm.setLeagueName(fbpo.getLeagueName());
				}
			}else if(LotteryId.JCLQ.name().equals(hm.getLottery())){
				BBMatch bbpo=mapper.findBBMatch(hm.getMatchId());
				if(bbpo!=null){
					hm.setHostTeamName(bbpo.getHomeTeamName());
					hm.setGuestTeamName(bbpo.getGuestTeamName());
					hm.setLeagueName(bbpo.getLeagueName());
				}
				
			}else if(LotteryId.BJDC.name().equals(hm.getLottery())){
				BJDCMatch bjdcpo=mapper.findBJDCMatch(hm.getMatchId());
				if(bjdcpo!=null){
					hm.setHostTeamName(bjdcpo.getHomeTeamName());
					hm.setGuestTeamName(bjdcpo.getGuestTeamName());
					hm.setLeagueName(bjdcpo.getLeagueName());
				}
			}
			if(hm.getMatchId()!=null){
				List<HotAndRecommendMatch> position=mapper.findTeamPosition(hm.getMatchId());
				if(position!=null && position.size()>0){
					HotAndRecommendMatch o=position.get(0);
					hm.setHostTeamPosition(o.getHostTeamPosition());
					hm.setGuestTeamPostion(o.getGuestTeamPostion());
				}
			}
		}
		
	}

	private List<HotAndRecommendMatch> findPlayMatchWithBetScheme(
			List<com.davcai.data.statistic.model.BetScheme> betSchemeList) {
		Map<Long,HotAndRecommendMatch> hrMatch=new HashMap<Long,HotAndRecommendMatch>();
		for(com.davcai.data.statistic.model.BetScheme bet:betSchemeList){
			
			List<PlayMatch> list=findMatchInfoWithScheme(bet);
			computeGuessCountOfMatch(bet,list,hrMatch);
		}
		List<HotAndRecommendMatch> list=converMapToList(hrMatch);
		List<HotAndRecommendMatch> top5Match=new ArrayList<HotAndRecommendMatch>();
		if(list==null || list.size()==0){
			return top5Match;
		}
		
		if(list.size()>0 && list.size()<=5){
			
			return list;
		}else{
			
			for(int i=0;i<5;i++){
				top5Match.add(list.get(i));
			}
		}
	    return top5Match;	
	}

	private List<HotAndRecommendMatch> converMapToList(
			Map<Long, HotAndRecommendMatch> map) {
		List<HotAndRecommendMatch> hrd=new ArrayList<HotAndRecommendMatch>();
		if(map!=null&& map.size()>0){
			Set<Long> keys=map.keySet();
			Iterator<Long> it=keys.iterator();
			while(it.hasNext()){
			    Long matchId=it.next();
			    hrd.add(map.get(matchId));
			}
		}
		return hrd;
	}

	private void computeGuessCountOfMatch(com.davcai.data.statistic.model.BetScheme bet, List<PlayMatch> match,
			Map<Long, HotAndRecommendMatch> hrMatch) {
		if(match!=null && !match.isEmpty()){
			for(int i=0;i<match.size();i++){
				BetScheme b=new BetScheme();
				b.setPlayId(bet.getPlayId());
				PlayMatch pm=match.get(i);
				boolean flag=ResultTool.isMatchWin(match.get(i), b);
				if(flag){
					Long matchId=pm.getMatchId();
					HotAndRecommendMatch hr=hrMatch.get(matchId);
					if(hr!=null){
						int count=Integer.parseInt(hr.getRecommendCount());
						hr.setRecommendCount((count+1)+"");
					}else{
						hr=new HotAndRecommendMatch();
						hr.setMatchId(pm.getMatchId());
						hr.setLottery(bet.getLotteryId());
	                    hr.setRecommendCount(1+"");
	                    hrMatch.put(matchId, hr);
					}
				}
			}
		}
		
	}

	private List<PlayMatch> findMatchInfoWithScheme(com.davcai.data.statistic.model.BetScheme bet) {
		List<PlayMatch> playMatchs=new ArrayList<PlayMatch>();
		String lottery =bet.getLotteryId();
		List<PlayMatch> matchInfo=new ArrayList<PlayMatch>();
		if(LotteryId.JCZQ.name().equals(lottery)){
			matchInfo=mapper.findFootballMatchInfoBySchemeId(bet.getId(),bet.getPlayId());
		}else if(LotteryId.JCLQ.name().equals(lottery)){
			matchInfo=mapper.findBasketballMatchInfoBySchemeId(bet.getId(),bet.getPlayId());
		}else if(LotteryId.BJDC.name().equals(lottery)){
			matchInfo=mapper.findBJDCMatchInfoBySchemeId(bet.getId(),bet.getPlayId());
		}
//		for(PlayMatch o:matchInfo){
//			PlayMatch match=new PlayMatch();
//			match.setMatchId(Long.parseLong(o[0.toString()));
//			match.setBetCode(o[1]!=null?o[1].toString():"");
//			match.setPlayId(o[2]!=null?o[2].toString():"");
//			match.setResult(o[3]!=null?o[3].toString():"");
//			playMatchs.add(match);
//		}
		playMatchs.addAll(matchInfo);
		return playMatchs;
	}

	private void run4Top5Show(Date from, Date to) {
		// 准备7天晒单助人奖金排行榜数据
		List<Top5Recommend> sevenDayShowSchemeHelpTop5List=mapper.find7dayShowSchemeHelpTop5List(from,to);
		//准备50场晒单助人奖金排行榜数据
		List<Top5Recommend> fiftyShowSchemeHelpTop5List=mapper.find50ShowSchemeHelpTop5List(to);
		//准备7天晒单胜率奖金排行榜数据
		List<Top5Recommend> sevenDayShowSchemeWinRatioTop5List=mapper.find7dayShowSchemeWinRatioTop5List(from,to);
		//准备50场晒单胜率奖金排行榜数据
		List<Top5Recommend> fiftyShowSchemeWinRatioTop5List=mapper.find50ShowSchemeWinRatioTop5List(to);
		//生成最终结果表
		makeFinalResultTable4Show(sevenDayShowSchemeHelpTop5List,fiftyShowSchemeHelpTop5List,sevenDayShowSchemeWinRatioTop5List,fiftyShowSchemeWinRatioTop5List);
		//放入redis缓存
		addToRedis(sevenDayShowSchemeHelpTop5List);
		addToRedis(fiftyShowSchemeHelpTop5List);
		addToRedis(sevenDayShowSchemeWinRatioTop5List);
		addToRedis(fiftyShowSchemeWinRatioTop5List);
		
	}

	private void makeFinalResultTable4Show(
			List<Top5Recommend> sevenDayShowSchemeHelpTop5List,
			List<Top5Recommend> fiftyShowSchemeHelpTop5List,
			List<Top5Recommend> sevenDayShowSchemeWinRatioTop5List,
			List<Top5Recommend> fiftyShowSchemeWinRatioTop5List) {
		saveTop5Recommend(sevenDayShowSchemeHelpTop5List, TopType.SHOW_SCHEME_HELP, Dimension.SEVEN_DAY);
		saveTop5Recommend(fiftyShowSchemeHelpTop5List, TopType.SHOW_SCHEME_HELP, Dimension.FIFTY);
		saveTop5Recommend(sevenDayShowSchemeWinRatioTop5List, TopType.SHOW_SCHEME_WIN_RATIO, Dimension.SEVEN_DAY);
		saveTop5Recommend(fiftyShowSchemeWinRatioTop5List, TopType.SHOW_SCHEME_WIN_RATIO, Dimension.FIFTY);
	}

	private void run4Top5Follow(Date from, Date to) {
		// 准备7天奖金数据
		List<Top5Recommend> sevenDayBonusTop5List=mapper.find7dayFollowBonusTop5List(from,to);
		
		// 准备50场奖金数据
		List<Top5Recommend> fiftyBonusTop5List=mapper.find50FollowBonusTop5List(to);
		
		// 准备7天胜率数据
		List<Top5Recommend> sevenDayShenglvTop5List=mapper.find7dayFollowWinRatioTop5List(from,to);
		
		// 准备跟单50场胜率数据
		List<Top5Recommend> fiftyShenglvTop5List=mapper.find50FollowWinRatioTop5List(to);
		
		//生成最终结果表
		makeFinalResultTable4Follow(sevenDayBonusTop5List,fiftyBonusTop5List,sevenDayShenglvTop5List,fiftyShenglvTop5List);
		//放入redis缓存
		addToRedis(sevenDayBonusTop5List);
		addToRedis(fiftyBonusTop5List);
		addToRedis(sevenDayShenglvTop5List);
		addToRedis(fiftyShenglvTop5List);
		
	}

	private void makeFinalResultTable4Follow(
			List<Top5Recommend> sevenDayBonusTop5List, List<Top5Recommend> fiftyBonusTop5List, List<Top5Recommend> sevenDayShenglvTop5List, List<Top5Recommend> fiftyShenglvTop5List) {
		saveTop5Recommend(sevenDayBonusTop5List,TopType.FOLLOW_BONUS,Dimension.SEVEN_DAY);
		
		saveTop5Recommend(fiftyBonusTop5List,TopType.FOLLOW_BONUS,Dimension.FIFTY);
		
		saveTop5Recommend(sevenDayShenglvTop5List,TopType.FOLLOW_WIN_RATIO,Dimension.SEVEN_DAY);
		
		saveTop5Recommend(fiftyShenglvTop5List,TopType.FOLLOW_WIN_RATIO,Dimension.FIFTY);
		
	}

	private void saveTop5Recommend(List<Top5Recommend> sevenDayBonusTop5List,String topType,String dimension) {
		mapper.deleteTop5ByTopTypeAndDimension(topType,dimension);
		if(null!=sevenDayBonusTop5List&&!sevenDayBonusTop5List.isEmpty()){
			
			int i=1;
			for(Top5Recommend top5Recommend:sevenDayBonusTop5List){
				top5Recommend.setTopType(topType);
				top5Recommend.setDimension(dimension);
				top5Recommend.setSequenceNumber(i);
				mapper.saveTop5Recommend(top5Recommend);
				i++;
			}
		}
	}

	private void run4Top5Recommend(Date from, Date to) {
		// 准备足球数据
		List<Top5RecommendMiddle> footballDatas=mapper.loadFootballData();
		//准备篮球数据
		List<Top5RecommendMiddle> basketballDatas=mapper.loadBasketballData();
		//准备北单数据
		List<Top5RecommendMiddle> beidanDatas=mapper.loadBeidanData();
		//形成中间表，去掉对同一彩种同一玩法同一比赛“全包”的项，并且计算每一场是否赢以及盈利率
		makeMiddleTable(footballDatas,basketballDatas,beidanDatas);
		//为计算50场胜率形成排序的中间表
		makeOrderedMiddleTable();
		//7天胜率前5
		List<Top5Recommend> sevenDayWinRatioTop5List=mapper.find7dayWinRatioTop5List(from,to);
		//50场胜率前5
		List<Top5Recommend> fiftyWinRatioTop5List=mapper.find50WinRatioTop5List();
//		//7天盈利率前5
//		List<Top5Recommend> sevenDayRevenueRatioTop5List=mapper.find7dayRevenueRatioTop5List(from,to);
//		//50场盈利率前5
//		List<Top5Recommend> fiftyRevenueRatioTop5List=mapper.find50RevenueRatioTop5List();
		//生成最终结果表
		makeFinalResultTable(sevenDayWinRatioTop5List,fiftyWinRatioTop5List);
		//放入redis缓存
		addToRedis(sevenDayWinRatioTop5List,fiftyWinRatioTop5List);
	}

	private void makeOrderedMiddleTable() {
		mapper.deleteOrderedMiddle();
		mapper.makeOrderedMiddle();
		
	}

	private void addToRedis(List<Top5Recommend> sevenDayWinRatioTop5List,
			List<Top5Recommend> fiftyWinRatioTop5List) {
		addToRedis(sevenDayWinRatioTop5List);
		addToRedis(fiftyWinRatioTop5List);
		
	}

	private void addToRedis(List<Top5Recommend> top5List) {
		List<Top5Recommend> list=new ArrayList<Top5Recommend>();
		list.addAll(top5List);
		List<com.unison.lottery.weibo.data.Top5Recommend> redisTop5Recommends=new ArrayList<com.unison.lottery.weibo.data.Top5Recommend>();
		
		for(Top5Recommend top5Recommend:list){
			com.unison.lottery.weibo.data.Top5Recommend redisTop5Recommend=new com.unison.lottery.weibo.data.Top5Recommend();
			try {
				BeanUtils.copyProperties(redisTop5Recommend, top5Recommend);
				redisTop5Recommends.add(redisTop5Recommend);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			
		}
		top5RecommendDao.addList(redisTop5Recommends);
	}
	

	private void makeFinalResultTable(
			List<Top5Recommend> sevenDayWinRatioTop5List,
			List<Top5Recommend> fiftyWinRatioTop5List) {
		saveTop5Recommend(sevenDayWinRatioTop5List, TopType.WIN_RATIO, Dimension.SEVEN_DAY);
		saveTop5Recommend(fiftyWinRatioTop5List, TopType.WIN_RATIO, Dimension.FIFTY);
		saveTop5Recommend(null, TopType.REVENUE_RATIO, Dimension.SEVEN_DAY);
		saveTop5Recommend(null, TopType.REVENUE_RATIO, Dimension.FIFTY);
		
	}

	private void makeMiddleTable(List<Top5RecommendMiddle> footballDatas,
			List<Top5RecommendMiddle> basketballDatas,
			List<Top5RecommendMiddle> beidanDatas) {
		List<Top5RecommendMiddle> allToHandleList=new ArrayList<Top5RecommendMiddle>();
		if(null!=footballDatas&&!footballDatas.isEmpty()){
			allToHandleList.addAll(footballDatas);
		}
		if(null!=basketballDatas&&!basketballDatas.isEmpty()){
			allToHandleList.addAll(basketballDatas);
		}
		if(null!=beidanDatas&&!beidanDatas.isEmpty()){
			allToHandleList.addAll(beidanDatas);
		}
		if(!allToHandleList.isEmpty()){
			Map<String,List<Top5RecommendMiddle>> allToHandleMap=new HashMap<String,List<Top5RecommendMiddle>>();
			for(Top5RecommendMiddle top5RecommendMiddle:allToHandleList){
				if(null!=top5RecommendMiddle){
					String key=makeKey(top5RecommendMiddle);
					if(StringUtils.isNotBlank(key)){
						if(allToHandleMap.containsKey(key)){
							if(null!=allToHandleMap.get(key)){
								allToHandleMap.get(key).add(top5RecommendMiddle);
							}
						}
						else{
							List<Top5RecommendMiddle> list=new ArrayList<Top5RecommendMiddle>();
							list.add(top5RecommendMiddle);
							allToHandleMap.put(key, list);
						}
					}
				}
				
			}
			Iterator<Entry<String, List<Top5RecommendMiddle>>> it = allToHandleMap.entrySet().iterator();
			while(it.hasNext()){
				Entry<String, List<Top5RecommendMiddle>> entry=it.next();
				List<Top5RecommendMiddle> list=entry.getValue();
				if(shouldFilte(list)){
					it.remove();
				}
			}
			if(!allToHandleMap.isEmpty()){
				mapper.deleteTop5RecommendMiddle();
				for(List<Top5RecommendMiddle> top5RecommendMiddleList:allToHandleMap.values()){
					checkIfWinAndComputeRevenue(top5RecommendMiddleList);
					saveToTop5RecommendMiddle(top5RecommendMiddleList);
				}
			}
			
		}
		
		
	}

	private void saveToTop5RecommendMiddle(
			List<Top5RecommendMiddle> top5RecommendMiddleList) {
		if(null!=top5RecommendMiddleList&&!top5RecommendMiddleList.isEmpty()){
			for(Top5RecommendMiddle top5RecommendMiddle:top5RecommendMiddleList){
				mapper.saveTop5RecommendMiddle(top5RecommendMiddle);
			}
		}
		
	}

	private void checkIfWinAndComputeRevenue(
			List<Top5RecommendMiddle> top5RecommendMiddleList) {
		if(null!=top5RecommendMiddleList&&!top5RecommendMiddleList.isEmpty()){
			for(Top5RecommendMiddle top5RecommendMiddle:top5RecommendMiddleList){
				if (null != top5RecommendMiddle
						&& StringUtils
								.isNotBlank(top5RecommendMiddle.getCode())
						&& top5RecommendMiddle.getCode().length() > _4
						&& StringUtils.isNotBlank(top5RecommendMiddle
								.getWinOption())) {
					PlayMatch matchContainsResult=new PlayMatch();
					matchContainsResult.setBetCode(top5RecommendMiddle.getCode().substring(_4));
					matchContainsResult.setResult(top5RecommendMiddle.getWinOption());
					BetScheme scheme=new BetScheme();
					scheme.setPlayId(top5RecommendMiddle.getPlayId());
					if(ResultTool.isMatchWin(matchContainsResult, scheme)){
						top5RecommendMiddle.setIsWin(1);
					}
//					try{
//						computeRevenue(top5RecommendMiddle);
//					}catch(Exception e){
//						e.printStackTrace();
//					}
					
				}
				
				
			}
		}
	}

	private void computeRevenue(Top5RecommendMiddle top5RecommendMiddle) {

		float rightOdds = computeRightOdds(top5RecommendMiddle);
		int countOfWrong = computeCountOfWrong(top5RecommendMiddle);
		if (rightOdds > 0 && countOfWrong >= 0) {
			if (top5RecommendMiddle.getIsWin() == 0) {// 比赛没有赢，盈利率为-选择错误项数
				top5RecommendMiddle.setRevenueRatio(-countOfWrong);
			} else if (top5RecommendMiddle.getIsWin() == 1) {// 比赛赢了，计算盈利率
				// 盈利率计算公式：[求和（每场推荐正确的选择赔率-1) - 求和（推荐错误的选择次数）]。
				// 比如：竞足的不让球玩法，曼联vs阿森纳，胜平负赔率（1.3;3.5;2.5）推荐：“胜负”，赛果为2：0；切尔西vs曼城，胜平负赔率（1.2；3.0；2.0）推荐”平负”，赛果2：2。
				// 则盈利率为：[（1.3-1）+（3.0-1）] -[1+1]=30%
				top5RecommendMiddle.setRevenueRatio(rightOdds - 1
						- countOfWrong);
			}

		}

	}

	private int computeCountOfWrong(Top5RecommendMiddle top5RecommendMiddle) {
		int countOfWrong=-1;
		String playId=top5RecommendMiddle.getPlayId();
		PlayOption playOption = playOptionMap.get(playId);
		if(null!=playOption){
			if(playOption.getMinCountOfChar()==1){
				String betCode=top5RecommendMiddle.getCode().substring(_4);
				if(betCode.contains(top5RecommendMiddle.getWinOption())){//如果投注选项中包括赛果项，则为投注选项数-1
					countOfWrong=betCode.length()-1;
				}
				else{//如果投注选项中不包括赛果项，则为投注选项数
					countOfWrong=betCode.length();
				}
			}
		}
		return countOfWrong;
	}

	private float computeRightOdds(Top5RecommendMiddle top5RecommendMiddle) {
		float rightOdds=0;
		Map<String,Float> option_odds=new HashMap<String,Float>();
		if(StringUtils.isNotBlank(top5RecommendMiddle.getOptions())){
			String[] splitsOfOption = top5RecommendMiddle.getOptions().split(",");
			String[] splitsOfOdds = top5RecommendMiddle.getOdds().split(",");
			if(null!=splitsOfOption&&null!=splitsOfOdds&&splitsOfOption.length==splitsOfOdds.length&&splitsOfOption.length>0){
				int i=0;
				int size=splitsOfOption.length;
				for(i=0;i<size;i++){
					option_odds.put(splitsOfOption[i], Float.valueOf(splitsOfOdds[i]));
				}
			}
		}
		
		if(StringUtils.isBlank(top5RecommendMiddle.getFinalOdds())){//非北单
			rightOdds=option_odds.get(top5RecommendMiddle.getWinOption());
		}
		else{
			rightOdds=Float.valueOf(top5RecommendMiddle.getFinalOdds());
		}
		return rightOdds;
	}

	private boolean shouldFilte(List<Top5RecommendMiddle> list) {
		boolean result=false;
		if(null!=list&&!list.isEmpty()){
			String playId=list.get(0).getPlayId();
			PlayOption playOption=playOptionMap.get(playId);
			if(null!=playOption){
				//对于每一种playId，都会有固定的选项，首先将每一个固定的选项做一个二进制的编码，比如01_ZC_1有三种选项分别是0、1、3，
				//则先将这三种选项编码为二进制的001、010和100，每种选项出现一次，则该选项对应的编码会参与一次按位或运算，如果最终
				//按位或的结果是111，则表示“全包”，需要过滤
				int optionAppearCode=0b0;
				for(Top5RecommendMiddle top5RecommendMiddle:list){
					if(StringUtils.isNotBlank(top5RecommendMiddle.getOptions())&&top5RecommendMiddle.getOptions().length()>4){
						optionAppearCode=optionAppearCode|playOption.computeOptionAppear(top5RecommendMiddle.getCode().substring(_4));
					}
				}
				if(playOption.isAllApearCode(optionAppearCode)){
					logger.debug("检测到全包的比赛:{}，需要过滤",list);
					result=true;
				}
			}
			
			
		}
		return result;
	}


	private String makeKey(Top5RecommendMiddle top5RecommendMiddle) {
		
		return ""+top5RecommendMiddle.getUserId()+"_"+top5RecommendMiddle.getLotteryId()+"_"+top5RecommendMiddle.getPlayId()+"_"+top5RecommendMiddle.getMatchId();
	}

	public Map<String,PlayOption> getPlayOptionMap() {
		return playOptionMap;
	}

	public void setPlayOptionMap(Map<String,PlayOption> playOptionMap) {
		this.playOptionMap = playOptionMap;
	}

	

}
