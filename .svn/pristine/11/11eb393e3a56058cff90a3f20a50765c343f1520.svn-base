package com.xhcms.lottery.commons.util.bonus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.commons.data.BetMatch;
import com.xhcms.lottery.commons.persist.service.BonusCalculatorService;

public class MaxAndMinBonusUtil {
	
	private static Logger logger = LoggerFactory.getLogger(MaxAndMinBonusUtil.class);
	
	@Autowired
	private BonusCalculatorService bonusCalculatorService;
	
	private static final int ONE_NOTE_AMOUNT=2;
	
	/**
	 * 
	 * @param passtype 
	 * @return 
	 */
	public MaxAndMinBonusModel maxAndMinBonusForJC(List<BetMatch> betMatchList,String lotteryId, Map<Long, BigDecimal> matchScoreMap, String passtype){
		
		Map<Long, List<List<BetContent>>> map = bonusCalculatorService.groupOptionsByLottery(betMatchList, lotteryId, matchScoreMap);
		return maxAndMinBonus(map,passtype);
	}
	
	public MaxAndMinBonusModel maxAndMinBonus(Map<Long, List<List<BetContent>>> map, String passtype){
		MaxAndMinBonusContext maxAndMinBonusContext = makeContext(map,passtype);
		computeMinAndMaxOddsBetContentList(maxAndMinBonusContext);
		prepareCombination(maxAndMinBonusContext);
		return calculateMaxAndMinBonus(maxAndMinBonusContext);
	}

	private MaxAndMinBonusContext makeContext(
			Map<Long, List<List<BetContent>>> map, String passType) {
		if(null!=map&&!map.isEmpty()&&StringUtils.isNotBlank(passType)){
			MaxAndMinBonusContext context=new MaxAndMinBonusContext();
			List<MaxAndMinBonusContextOfMatch> matchContexts=new ArrayList<MaxAndMinBonusContextOfMatch>();
			context.setMatchContexts(matchContexts);
			for(Entry<Long, List<List<BetContent>>> entrySet:map.entrySet()){
				MaxAndMinBonusContextOfMatch matchContext=new MaxAndMinBonusContextOfMatch();
				matchContext.setMatchId(entrySet.getKey());
				matchContext.setCoexistenceSet(entrySet.getValue());
				if(null!=entrySet.getValue()&&!entrySet.getValue().isEmpty()){
					List<BetContent> betContentList=entrySet.getValue().get(0);
					if(null!=betContentList&&!betContentList.isEmpty()){
						boolean seed=betContentList.get(0).isSeed();
						matchContext.setSeed(seed);
					}
					
				}
				
				matchContexts.add(matchContext);
			}
			passType=StringUtils.removeStart(passType, ",");
			passType=StringUtils.removeEnd(passType, ",");
			context.setPassType(passType);
			return context;
		}
		return null;
	}

	/**
	 * 计算每场比赛的最大赔率值投注项集合
	 * @param maxAndMinBonusContext
	 */
	public  void computeMaxOddsBetContentList(
			MaxAndMinBonusContext maxAndMinBonusContext) {
		if (null != maxAndMinBonusContext
				&& null != maxAndMinBonusContext.getMatchContexts()
				&& !maxAndMinBonusContext.getMatchContexts().isEmpty()) {
			List<MaxAndMinBonusContextOfMatch> matchContexts = maxAndMinBonusContext.getMatchContexts();
			for(MaxAndMinBonusContextOfMatch matchContext:matchContexts){
				List<BetContent> maxOddsBetContentList=findMaxOddsBetContent(matchContext);
				matchContext.setMaxOddsBetContentList(maxOddsBetContentList);
			}
		}
	}
	
	/**
	 * 计算最大和最小赔率值投注项集合
	 * @param maxAndMinBonusContext
	 */
	public  void computeMinAndMaxOddsBetContentList(MaxAndMinBonusContext maxAndMinBonusContext){
		computeMinOddsBetContentList(maxAndMinBonusContext);
		computeMaxOddsBetContentList(maxAndMinBonusContext);
	}
	
    public  void computeMinOddsBetContentList(MaxAndMinBonusContext maxAndMinBonusContext){
	   if(maxAndMinBonusContext!=null 
			   && maxAndMinBonusContext.getMatchContexts()!=null 
			   && maxAndMinBonusContext.getMatchContexts().size()>0){
			List<MaxAndMinBonusContextOfMatch> matchContexts = maxAndMinBonusContext.getMatchContexts(); 
			for(MaxAndMinBonusContextOfMatch matchContext:matchContexts){
				List<BetContent> minOddsBetContentList=findMinOddsBetContent(matchContext);
				matchContext.setMinOddsBetContentList(minOddsBetContentList);
			}
	   }
   }
	private  List<BetContent> findMaxOddsBetContent(
			MaxAndMinBonusContextOfMatch matchContext) {
		List<BetContent> result=null;
		if (null != matchContext && null != matchContext.getCoexistenceSet()
				&& !matchContext.getCoexistenceSet().isEmpty()) {
			List<List<BetContent>> coexistenceSet = matchContext.getCoexistenceSet();
			BigDecimal maxTotalOdds=BigDecimal.ZERO;
			BigDecimal totalOdds=null;
			for(List<BetContent> coexistenceSetItem:coexistenceSet){
				totalOdds=computeTotalOdds(coexistenceSetItem);
				
				if(null!=totalOdds&&totalOdds.compareTo(maxTotalOdds)>0){
					maxTotalOdds=totalOdds;
					result=coexistenceSetItem;
				}
			}
		}
		return result;
	}
	private  List<BetContent> findMinOddsBetContent(
			MaxAndMinBonusContextOfMatch matchContext){
		List<BetContent> result=null;
		if(matchContext != null && matchContext.getCoexistenceSet() != null
				&& matchContext.getCoexistenceSet().size()>0){
			result=new ArrayList<BetContent>();
			List<List<BetContent>> coexistenceSet = matchContext.getCoexistenceSet();
			BetContent globalBetContent=null;
			for(List<BetContent> coexistenceSetItem:coexistenceSet){
				if(coexistenceSetItem!=null && coexistenceSetItem.size()>0){
					BetContent tempBetContent=null;
					for(BetContent betContent:coexistenceSetItem){
						if(tempBetContent == null){
							tempBetContent=betContent;
						}else{
							if(tempBetContent.getOdd().compareTo(betContent.getOdd())>0){
								tempBetContent=betContent;
							}
						}
					}
					if(globalBetContent == null){
						globalBetContent=tempBetContent;
					}else{
						if(globalBetContent.getOdd().compareTo(tempBetContent.getOdd())>0){
							globalBetContent=tempBetContent;
						}
					}
				}
			}
			result.add(globalBetContent);
		}
		return result;
	}
	private  BigDecimal computeTotalOdds(
			List<BetContent> coexistenceSetItem) {
		if(null!=coexistenceSetItem&&!coexistenceSetItem.isEmpty()){
			BigDecimal totalOdds=BigDecimal.ZERO;
			for(BetContent betContent:coexistenceSetItem){
				totalOdds=totalOdds.add(betContent.getOdd());
			}
			return totalOdds;
		}
		return null;
	}

	public  MaxAndMinBonusModel calculateMaxAndMinBonus(MaxAndMinBonusContext context){
		MaxAndMinBonusModel model = new MaxAndMinBonusModel();
		MaxAndMinBonusModel model1 = new MaxAndMinBonusModel();
		model=calculateMaxBonus(context);
		model1=calculateMinBonus(context);
		model.setMinBonus(model1.getMinBonus());
		model.setMinDetailExpress(model1.getMinDetailExpress());
		return model;
	}
	
	public  MaxAndMinBonusModel calculateMaxBonus(MaxAndMinBonusContext context){
		MaxAndMinBonusModel model = new MaxAndMinBonusModel();
		String [] passTypes = context.getPassType().split(",");
		List<BetContent> betContents = context.getMaxOddsBetContentListOfAll();
		List<Integer[]> excludeIndex = context.getExcludeIndexOfMaxOdds();
		List<Integer[]> includeIndex = context.getSeedIndexOfMaxOdds();
		float maxBonus=0;
		for(int j = 0; j < passTypes.length; j++){//循环计算每个过关方式
    		String[] mn = passTypes[j].split("@");//串关数组,eg:[3,1]表示3@1
    		int ms = betContents.size();//所选选项数
            if(mn.length != 2){
            	logger.error("无效的过关方式！{}", passTypes[j]);
                return new MaxAndMinBonusModel();
            }
            if(Integer.valueOf(mn[0]) > ms){
            	logger.error("无效的过关方式！选项：{}个，过关方式：{}",ms,passTypes[j]);
                return new MaxAndMinBonusModel();
            }
            MaxAndMinBonusModel maxmodel = CombinationUtil.calculateBetData(mn,ms,betContents,excludeIndex,includeIndex,"max");
            maxBonus += maxmodel.getMaxBonus()*ONE_NOTE_AMOUNT;
            model.getDetailExpress().addAll(maxmodel.getDetailExpress());
    	}
		model.setMaxBonus(maxBonus);
		return model;
	}
	
	public  MaxAndMinBonusModel calculateMinBonus(MaxAndMinBonusContext context){
		MaxAndMinBonusModel model = new MaxAndMinBonusModel();
		String [] passTypes = context.getPassType().split(",");
		List<BetContent> betContents = context.getMinOddsBetContentListOfAll();
		List<Integer[]> excludeIndex = context.getExcludeIndexOfMinOdds();
		List<Integer[]> includeIndex = context.getSeedIndexOfMinOdds();
		float minBonus=0;
		for(int j = 0; j < passTypes.length; j++){//循环计算每个过关方式
    		String[] mn = passTypes[j].split("@");//串关数组,eg:[3,1]表示3@1
    		int ms = betContents.size();//所选选项数
            if(mn.length != 2){
            	logger.error("无效的过关方式！{}", passTypes[j]);
                return new MaxAndMinBonusModel();
            }
            if(Integer.valueOf(mn[0]) > ms){
            	logger.error("无效的过关方式！选项：{}个，过关方式：{}",ms,passTypes[j]);
                return new MaxAndMinBonusModel();
            }
            MaxAndMinBonusModel minmodel = CombinationUtil.calculateBetData(mn,ms,betContents,excludeIndex,includeIndex,"min");
            float v = minmodel.getMinBonus()*ONE_NOTE_AMOUNT;
            if(minBonus == 0 || v < minBonus){
            	minBonus = v;
            	model.setMinDetailExpress(minmodel.getMinDetailExpress());
            }
    	}
		model.setMinBonus(minBonus);
		return model;
	}
	/**
	 * 为之后的组合操作准备需要的参数
	 * @param maxAndMinBonusContext
	 */
	public  void prepareCombination(
			MaxAndMinBonusContext maxAndMinBonusContext) {
		if(null!=maxAndMinBonusContext&&null!=maxAndMinBonusContext.getMatchContexts()&&!maxAndMinBonusContext.getMatchContexts().isEmpty()){
			List<BetContent> maxOddsBetContentListOfAll=new ArrayList<BetContent>();
			List<BetContent> minOddsBetContentListOfAll=new ArrayList<BetContent>();
			List<Integer[]> excludeIndexOfMaxOdds=new ArrayList<Integer[]>();
			List<Integer[]> excludeIndexOfMinOdds=new ArrayList<Integer[]>();
			List<Integer[]> seedIndexOfMaxOdds=new ArrayList<Integer[]>();
			List<Integer[]> seedIndexOfMinOdds=new ArrayList<Integer[]>();
			for(MaxAndMinBonusContextOfMatch matchContext:maxAndMinBonusContext.getMatchContexts()){
				List<BetContent> maxOddsBetContentListForOneMatch = matchContext.getMaxOddsBetContentList();
				if(null!=maxOddsBetContentListForOneMatch&&!maxOddsBetContentListForOneMatch.isEmpty()){
					int size = maxOddsBetContentListForOneMatch.size();
					int sizeOfMaxOddsBetContentListOfAll=maxOddsBetContentListOfAll.size();
					
					 makeExcludeIndexOfMaxOdds(
							excludeIndexOfMaxOdds, size,
							sizeOfMaxOddsBetContentListOfAll);
					
					makeSeedIndexOfMaxOdds(seedIndexOfMaxOdds, matchContext,
							size, sizeOfMaxOddsBetContentListOfAll);
					
					maxOddsBetContentListOfAll.addAll(maxOddsBetContentListForOneMatch);
					
				}
				
				List<BetContent> minOddsBetContentListForOneMatch=matchContext.getMinOddsBetContentList();
				if(null!=minOddsBetContentListForOneMatch&&!minOddsBetContentListForOneMatch.isEmpty()){
					int size = minOddsBetContentListForOneMatch.size();
					int sizeOfMinOddsBetContentListOfAll=minOddsBetContentListOfAll.size();
					makeSeedIndexOfMinOdds(seedIndexOfMinOdds, matchContext,
							size, sizeOfMinOddsBetContentListOfAll);
					minOddsBetContentListOfAll.addAll(minOddsBetContentListForOneMatch);
				}
			}
			maxAndMinBonusContext.setExcludeIndexOfMaxOdds(excludeIndexOfMaxOdds);
			maxAndMinBonusContext.setExcludeIndexOfMinOdds(excludeIndexOfMinOdds);
			maxAndMinBonusContext.setMaxOddsBetContentListOfAll(maxOddsBetContentListOfAll);
			maxAndMinBonusContext.setMinOddsBetContentListOfAll(minOddsBetContentListOfAll);
			maxAndMinBonusContext.setSeedIndexOfMaxOdds(seedIndexOfMaxOdds);
			maxAndMinBonusContext.setSeedIndexOfMinOdds(seedIndexOfMinOdds);
		}
		
	}

	private  void makeSeedIndexOfMinOdds(
			List<Integer[]> seedIndexOfMinOdds,
			MaxAndMinBonusContextOfMatch matchContext, int size,
			int sizeOfMinOddsBetContentListOfAll) {
		if(matchContext.isSeed()){
			Integer[] seedIndexOneMatch=new Integer[size];
			for (int i=0;i<size;i++){
				seedIndexOneMatch[i]=sizeOfMinOddsBetContentListOfAll+i;
			}
			seedIndexOfMinOdds.add(seedIndexOneMatch);
		}
	}

	private  void makeSeedIndexOfMaxOdds(
			List<Integer[]> seedIndexOfMaxOdds,
			MaxAndMinBonusContextOfMatch matchContext, int size,
			int sizeOfMaxOddsBetContentListOfAll) {
		makeSeedIndexOfMinOdds(seedIndexOfMaxOdds, matchContext, size,
				sizeOfMaxOddsBetContentListOfAll);
	}

	private  Integer[] makeExcludeIndexOfMaxOdds(
			List<Integer[]> excludeIndexOfMaxOdds, int size,
			int sizeOfMaxOddsBetContentListOfAll) {
		Integer[] excludeIndexOneMatch=new Integer[size];
		for (int i=0;i<size;i++){
			excludeIndexOneMatch[i]=sizeOfMaxOddsBetContentListOfAll+i;
		}
		excludeIndexOfMaxOdds.add(excludeIndexOneMatch);
		return excludeIndexOneMatch;
	}

}
