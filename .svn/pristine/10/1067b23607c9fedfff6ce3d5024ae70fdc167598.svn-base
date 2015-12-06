package com.xhcms.lottery.commons.persist.dao;

import java.util.Date;
import java.util.List;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.persist.entity.BetPartnerPO;

public interface BetPartnerDao extends Dao<BetPartnerPO>{

    /**
     * 获取投注方案合买人
     * @param schemeId
     * @param userId
     * @return
     */
    BetPartnerPO get(Long schemeId, Long userId);
    
    BetPartnerPO findById(long betRecordId); 
    
	List<BetPartnerPO> findBySchemeId(long schemeId);
	
	BetPartnerPO findGroupSponsorRecord(long schemeId, long sponsorId);

    List<BetPartnerPO> find(String lotteryId, Long userId, Date startDate, Date endDate, int status, Paging paging);
    List<BetPartnerPO> find(String lotteryId, Long userId, Date startDate, Date endDate, int status, int firstResult,int maxResult, int type,int showScheme); 
    List<BetPartnerPO> find(String lotteryId, Long userId, Date startDate, Date endDate, int status, Paging paging,int type,int showScheme);

    Object[] sumBonus(Long schemeId, Long userId);
    
    List<BetPartnerPO> find(Long userId,String lotteryId,int duration,int status, Paging paging,int type,int showScheme);
    
    List<BetPartnerPO> find(Long userId,String[] lotteryId,int duration,int status, Paging paging,int type,int showScheme);

	List<BetPartnerPO> findPartnersWithPager(Long betSchemeID, int startPosition);
}
