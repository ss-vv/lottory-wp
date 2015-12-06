package com.unison.thrift.scheme.service;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.unison.thrift.scheme.service.gen.BetSchemeData;
import com.unison.thrift.scheme.service.gen.RecoSchemeHandlerGen.Iface;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.persist.service.BetSchemeRecService;

@Service
public class RecoSchemeHandler implements Iface {

	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private BetSchemeRecService betSchemeRecService;

	@Override
	public BetSchemeData viewScheme(long schemeId) throws TException {
		log.info("调用thrift接口，查询推荐方案ID={}", schemeId);
		BetScheme scheme = betSchemeRecService.viewRecScheme(schemeId);
		BetSchemeData data = new BetSchemeData();
		if (scheme != null) {
			data.setSchemeId(schemeId);
			data.setType(scheme.getType());
			data.setSponsorId(scheme.getSponsorId());
			data.setStatus(scheme.getStatus());
			data.setOfftime(0);
			data.setLotteryId(scheme.getLotteryId());
			
			log.info("根据推荐方案ID={},查询推荐方案信息={}", schemeId, data);
		} else {
			log.info("根据推荐方案ID={},查询推荐方案信息为空.", schemeId);
		}
		return data;
	}
}