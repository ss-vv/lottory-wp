package com.xhcms.lottery.commons.persist.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.commons.lang.Assert;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.commons.persist.dao.BetSchemeDao;
import com.xhcms.lottery.commons.persist.dao.BetSchemePresetDao;
import com.xhcms.lottery.commons.persist.dao.TicketDao;
import com.xhcms.lottery.commons.persist.dao.TicketPresetDao;
import com.xhcms.lottery.commons.persist.entity.BBMatchPO;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;
import com.xhcms.lottery.commons.persist.entity.BetSchemePresetPO;
import com.xhcms.lottery.commons.persist.entity.FBMatchPO;
import com.xhcms.lottery.commons.persist.service.GetPresetSchemeService;
import com.xhcms.lottery.commons.util.PresetSchemeTool;
import com.xhcms.lottery.lang.AppCode;
import com.xhcms.lottery.utils.POUtils;

/**
 * 获取预兑奖方案信息
 * @author Wang Lei
 *
 */
public class GetPresetSchemeServiceImpl implements GetPresetSchemeService {

    @Autowired
    private BetSchemePresetDao betSchemePresetDao;
    @Autowired
    private TicketDao ticketDao;
    @Autowired
    private TicketPresetDao ticketPresetDao;
    @Autowired
    private BetSchemeDao betSchemeDao;
    
	@Override
    @Transactional
	public BetSchemePO getRightSchemeById(Long schemeId){
		BetSchemePO sourceSpo = betSchemeDao.get(schemeId);
		Assert.notNull(sourceSpo, AppCode.SCHEME_NOT_EXIST);
		return getRightSchemePO(sourceSpo);
	}
	
	@Override
    @Transactional
	public  BetSchemePO getRightSchemeByPO(BetSchemePO sourcePO){
    	Assert.notNull(sourcePO, AppCode.SCHEME_NOT_EXIST);
    	return getRightSchemePO(sourcePO);
    }
	
	@Transactional
	private BetSchemePO getRightSchemePO(BetSchemePO sourcePO){
		BetSchemePO spo = new BetSchemePO();
    	if(PresetSchemeTool.isPresetScheme(sourcePO)){
    		BetSchemePresetPO presetPO = betSchemePresetDao.get(sourcePO.getId());
    		Assert.notNull(presetPO, AppCode.SCHEME_NOT_EXIST);
			BeanUtils.copyProperties(presetPO, spo);
			spo.setPublicTime(sourcePO.getPublicTime());
			spo.setShowScheme(sourcePO.getShowScheme());
			spo.setIsPublishShow(sourcePO.getIsPublishShow());
    	}else{
    		spo = sourcePO;
    	}
    	return spo;
	}
	
	@Transactional
	private BetScheme getRightSchemeDO(BetScheme sourceDO){
		BetScheme spo = new BetScheme();
		if(PresetSchemeTool.isPresetScheme(sourceDO)){
			BetSchemePresetPO presetPO = betSchemePresetDao.get(sourceDO.getId());
			Assert.notNull(presetPO, AppCode.SCHEME_NOT_EXIST);
			BeanUtils.copyProperties(presetPO, spo);
		}else{
			spo = sourceDO;
		}
		return spo;
	}

	@Override
	@Transactional
	public BetScheme getRightSchemeByDO(BetScheme betScheme) {
		Assert.notNull(betScheme, AppCode.SCHEME_NOT_EXIST);
		return getRightSchemeDO(betScheme);
	}

	@Override
	@Transactional
	public List<Ticket> findTicketsByBetSchemePO(BetSchemePO sourcePO){
		Assert.notNull(sourcePO, AppCode.SCHEME_NOT_EXIST);
		Long schemeId = sourcePO.getId();
		if(PresetSchemeTool.isPresetScheme(sourcePO)){
			return POUtils.copyBeans(ticketPresetDao.findByScheme(schemeId, -1), Ticket.class);
    	}else{
    		return POUtils.copyBeans(ticketDao.findByScheme(schemeId, -1), Ticket.class);
    	}
	}
	
	@Override
	@Transactional
	public FBMatchPO getRightScore(BetSchemePO spo, FBMatchPO matchPO){
		if(PresetSchemeTool.isPresetScheme(spo)){
			FBMatchPO match = new FBMatchPO();
			BeanUtils.copyProperties(matchPO, match);
			match.setHalfScore(matchPO.getHalfScorePreset());
			match.setScore(matchPO.getScorePreset());
			return match;
		}
		return matchPO;
	}
	
	@Override
	@Transactional
	public BBMatchPO getRightScore(BetSchemePO spo, BBMatchPO matchPO){
		if(PresetSchemeTool.isPresetScheme(spo)){
			BBMatchPO match = new BBMatchPO();
			BeanUtils.copyProperties(matchPO, match);
			match.setFinalScore(matchPO.getFinalScorePreset());
			return match;
		}
		return matchPO;
	}

	@Override
	@Transactional
	public List<BetSchemePO> findFollowSchemes(BetSchemePO spo) {
		if(PresetSchemeTool.isPresetScheme(spo)){
			return POUtils.copyBeans(betSchemePresetDao.findFollowSchemes(spo.getId()), BetSchemePO.class);
		}
		return betSchemeDao.findFollowSchemes(spo.getId());
	}
}
