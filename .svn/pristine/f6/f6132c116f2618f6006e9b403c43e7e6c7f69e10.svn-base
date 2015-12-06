package com.xhcms.lottery.account.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.lottery.account.service.BetSchemeService;
import com.xhcms.lottery.account.service.RealSchemeCacheUpdate;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.lang.EntityType;
import com.xhcms.lottery.lang.SchemeDisplayMode;

public class RealSchemeCacheUpdateImpl implements RealSchemeCacheUpdate {

	@Autowired
    private BetSchemeService betSchemeService;
	
	@Transactional(readOnly=true)
	@Override
	public void betSuccessUpdateSchemeCache(BetScheme scheme, boolean isFollowScheme) {
		if(isFollowScheme) {
    		betSchemeService.updateRealSchemeCache(scheme.getId(), EntityType.DISPLAY_ALONE);
    		betSchemeService.updateRealSchemeCache(scheme.getFollowedSchemeId(), EntityType.DISPLAY_SHOW);
    	} else {
    		int displayMode = SchemeDisplayMode.getDisplayMode(scheme.getType(), 
    				scheme.getShowScheme(), scheme.getIsPublishShow());
    		betSchemeService.updateRealSchemeCache(scheme.getId(), displayMode);
    	}
	}

}
