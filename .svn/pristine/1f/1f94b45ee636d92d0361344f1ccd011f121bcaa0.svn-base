package com.xhcms.lottery.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.Play;
import com.xhcms.lottery.commons.data.UserScore;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;
import com.xhcms.lottery.commons.persist.entity.BetSchemeWithAssocPO;
import com.xhcms.lottery.commons.persist.entity.UserScorePO;

public class POUtils {
	private static Logger logger = LoggerFactory.getLogger(POUtils.class);

	public static <DO,PO> List<DO> copyBeans(List<PO> list, Class<DO> dataObjectClass) {
		if(list==null)
			return null;
		List<DO> rets = new ArrayList<DO>(list.size());
		DO b = null;
		for (PO po : list) {
			try {
				b = dataObjectClass.newInstance();
			} catch (Exception e) {
				logger.error("不能创建DataObject class的实例！class is: {}", dataObjectClass, e);
			} 
			BeanUtils.copyProperties(po, b);
			rets.add(b);
		}
	
		return rets;
	}

	@SuppressWarnings("unchecked")
	public static BetScheme copyBean(BetSchemeWithAssocPO schemePO) {
		String [] ignoredProperties = new String[]{"userScores", "play","followSchemes"};
		
		BetScheme schemeData = new BetScheme();
		BeanUtils.copyProperties(schemePO, schemeData, ignoredProperties);
		
		if (schemePO.getUserScores()!=null && schemePO.getUserScores().size()>0){
			List<UserScore> userScoreList = new ArrayList<UserScore>(schemePO.getUserScores().size());
			for (UserScorePO userScorePO : schemePO.getUserScores()){
				UserScore userScoreDO = new UserScore();
				BeanUtils.copyProperties(userScorePO, userScoreDO);
				userScoreList.add(userScoreDO);
			}
			schemeData.setUserScores(userScoreList);
		}else{
			schemeData.setUserScores(Collections.EMPTY_LIST);
		}
		
		if (schemePO.getPlay()!=null){
			Play play = new Play();
			BeanUtils.copyProperties(schemePO.getPlay(), play, new String[]{"passTypes"});
			schemeData.setPlay(play);
		}
		
		if (schemePO.getFollowSchemes()!=null){
			List<BetScheme> followSchemeList = new ArrayList<BetScheme>(schemePO.getFollowSchemes().size());
			for(BetSchemePO followSchemePO :schemePO.getFollowSchemes()){
				BetScheme followScheme = new BetScheme();
				BeanUtils.copyProperties(followSchemePO, followScheme);
				followSchemeList.add(followScheme);					
			}
			schemeData.setFollowSchemes(followSchemeList);
		}
		
		return schemeData;
	}
	
	public static List<BetScheme> copyBeans(List<BetSchemeWithAssocPO> pos) {
		List<BetScheme> result = new ArrayList<BetScheme>();
		for(BetSchemeWithAssocPO po : pos) {
			result.add(copyBean(po));
		}
		return result;
	}
}
