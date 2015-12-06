package com.xhcms.lottery.commons.persist.dao;

import java.util.List;

import com.xhcms.lottery.commons.persist.entity.PublishBetSchemePO;

public interface PublishBetSchemeDao { 

	String savePublishScheme(PublishBetSchemePO publishBetSchemePO);
	
	PublishBetSchemePO getPublishBetSchemePOByBetSchemeId(long betSchemeId);
	
	List<PublishBetSchemePO> getPublishBetSchemePOByUserId(long userId);
	
	List<PublishBetSchemePO> getPublishBetSchemePOByGroupId(String groupid);

	List<PublishBetSchemePO> getPublishBetSchemePOByMatchId(long matchId);
	
	List<PublishBetSchemePO> getPublishBetSchemePOsBySchemeId(long SchemeId);
	
	List<PublishBetSchemePO> getPublishBetSchemePOByUserIdAndPages(long userId, String pages,int maxResult, String groupid);
	
	List<PublishBetSchemePO> getPubSchemeIsOthersWithPages(long userId, long daVId,String pages, int maxResult, String groupid);

	List<PublishBetSchemePO> getPublishBetSchemePOByOtherParams(long userId, String groupid, long betSchemeId);

	PublishBetSchemePO getPublishSchemeByMathId(long matchId,long schemeId, String groupid);

	PublishBetSchemePO findById(Long id);
}
