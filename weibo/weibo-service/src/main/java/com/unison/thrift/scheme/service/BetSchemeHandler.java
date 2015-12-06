package com.unison.thrift.scheme.service;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unison.thrift.scheme.service.gen.BetSchemeData;
import com.unison.thrift.scheme.service.gen.BetSchemeHandlerGen.Iface;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;
import com.xhcms.lottery.commons.persist.service.BetSchemeBaseService;
import com.xhcms.lottery.lang.EntityType;

@Service
public class BetSchemeHandler implements Iface {

	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private BetSchemeBaseService betSchemeBaseService;

	@Override
	public BetSchemeData getSchemeById(long schemeId) throws TException {
		log.info("调用thrift接口，查询投注方案ID={}", schemeId);
		
		BetSchemePO po = betSchemeBaseService.getSchemePOById(schemeId);
		BetSchemeData data = new BetSchemeData();
		if (po != null) {
			data.setSchemeId(schemeId);
			data.setType(po.getType());
			data.setSponsorId(po.getSponsorId());
			data.setShowScheme(po.getShowScheme());
			data.setIsPublishShow(po.getIsPublishShow());
			data.setFollowedSchemeId(po.getFollowedSchemeId());
			data.setStatus(po.getStatus());
			data.setBonus(po.getAfterTaxBonus().doubleValue());
			data.setOfftime(po.getOfftime().getTime());
			data.setLotteryId(po.getLotteryId());
			data.setTotalAmount(po.getTotalAmount());
			data.setPurchasedAmount(po.getPurchasedAmount());
			data.setFloorAmount(po.getFloorAmount());
			if(po.getIsPublishShow() == EntityType.PUBLISH_SHOW || 
					po.getShowScheme() == EntityType.SHOW_SCHEME) {
				data.setAlreadyShow(true);
			}
			log.info("根据投注方案ID={},查询投注方案信息={}", schemeId, data);
		} else {
			log.info("根据投注方案ID={},查询投注方案信息为空.", schemeId);
		}
		return data;
	}

	@Override
	public boolean isCanSendShowScheme(long schemeId) throws TException {
		log.info("校验方案ID={},是否能够发起晒单微博.", schemeId);
		return betSchemeBaseService.isCanSendShowScheme(schemeId);
	}

	@Override
	public boolean updateBetSchemePublishShow(long schemeId) throws TException {
		log.info("更新方案ID={},为已发起晒单微博.", schemeId);
		return betSchemeBaseService.updateBetSchemePublishShow(schemeId);
	}
}