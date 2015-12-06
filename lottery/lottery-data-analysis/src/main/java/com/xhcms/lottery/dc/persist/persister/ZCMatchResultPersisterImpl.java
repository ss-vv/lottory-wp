package com.xhcms.lottery.dc.persist.persister;

import java.util.List;

import com.xhcms.lottery.dc.core.Persister;
import com.xhcms.lottery.dc.data.ZCResult;
import com.xhcms.lottery.dc.persist.service.FBMatchService;

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
public class ZCMatchResultPersisterImpl implements Persister<ZCResult> {
	private FBMatchService fbMatchService;
	
	public void setFbMatchService(FBMatchService fbMatchService) {
		this.fbMatchService = fbMatchService;
	}
	
	@Override
	public void persist(List<ZCResult> data) {
		fbMatchService.batchUpdateMatchResult(data);
	}

}
