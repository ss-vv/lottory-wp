package com.xhcms.lottery.dc.persist.persister;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.dc.core.Persister;
import com.xhcms.lottery.dc.data.ZCOdds;
import com.xhcms.lottery.dc.persist.service.FBMatchService;
import com.xhcms.lottery.dc.persist.service.MatchSupportPlayService;

/**
 * 
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright：Copyright (c) 2011</p>
 * <p>Company：XingHui Spirit (Beijing) Technical Co.,Ltd.</p>
 * 
 * @author xulang
 * @version 1.0.0
 */
public class AnRuiZCOddsPersisterImpl implements Persister<ZCOdds> {
	@Autowired
	private MatchSupportPlayService supportPlayService;
	@Autowired
	private FBMatchService fbMatchService;
	
	@Override
	public void persist(List<ZCOdds> data) {
		if(null != data && data.size() > 0) {
			//安瑞不需要过滤玩法是单关的赔率数据
//			if(data.get(0).getPlayId().endsWith("ZC_1")) {
//				List<ZCOdds> result = supportPlayService.filterZCOdds(data);
//				data = result;
//			}
			data = supportPlayService.filterZCZeroOdds(data);
			fbMatchService.batchUpdateOdds(data);
		}
	}

	public FBMatchService getFbMatchService() {
		return fbMatchService;
	}

	public void setFbMatchService(FBMatchService fbMatchService) {
		this.fbMatchService = fbMatchService;
	}
	
}
