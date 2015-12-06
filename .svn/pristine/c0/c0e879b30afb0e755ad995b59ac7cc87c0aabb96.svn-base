package com.xhcms.lottery.commons.persist.service;

import java.util.List;

import com.xhcms.lottery.commons.data.PublishBetScheme;
import com.xhcms.lottery.commons.persist.entity.BetMatchPO;

public interface PublishBetSchemeService { 

	boolean savePublishScheme(PublishBetScheme publishBetScheme, List<BetMatchPO> matchPOs);
	
	PublishBetScheme getPublishBetSchemeByBetSchemeId(long betSchemeId);
	
	List<PublishBetScheme> getPublishBetSchemesBySchemeId(long betSchemeId);
	
	List<PublishBetScheme> getPublishBetSchemeByUserId(long userId);
	
	List<PublishBetScheme> getPublishBetSchemeByGroupId(String groupid);

	List<PublishBetScheme> findByMatchId(String matchId);
	
	List<PublishBetScheme> getPublishBetSchemeByUserIdWithPages(long userId, String pages,int maxResult ,String groupid);
	
	List<PublishBetScheme> getPubSchemeIsOthersWithPages(long userId, long daVId, String pages, int maxResult,String groupid);

	boolean isExistPublishBetScheme(PublishBetScheme publishBetScheme);

}
