package com.unison.lottery.weibo.web.action.pc.ajax.scheme;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.common.service.SchemeService;
import com.unison.lottery.weibo.web.action.BaseAction;
import com.xhcms.commons.lang.Data;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.lang.EntityType;
import com.xhcms.lottery.utils.CombineBetMatchUtil;

public class AjaxBetSchemeAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;

	Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
    private SchemeService schemeService;
	
	private Data data = Data.success(null);
	private long schemeId;
	private int schemeType;
	private String showScheme;
	
	@Override
	public String execute() {
		BetScheme betScheme = null;
		try {
			int displayMode = -1;
			if(StringUtils.isNotBlank(showScheme) && 
					showScheme.contains(EntityType.SHOW_SCHEME + "") &&
					schemeType == 0) {
				displayMode = EntityType.DISPLAY_SHOW;
			} else if(schemeType == 0) {
				displayMode = EntityType.DISPLAY_ALONE;
			} else if(schemeType == 1) {
				displayMode = EntityType.DISPLAY_GROUPBUY;
			} else if(schemeType == 2) {
				displayMode = EntityType.DISPLAY_ALONE;
			}
			betScheme = schemeService.viewSchemeCache(schemeId, getUserId(), displayMode);
			//将同一赛事不同玩法合并
			betScheme = CombineBetMatchUtil.combine(betScheme);
			data.setData(betScheme);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public Data getData() {
		return data;
	}

	public void setSchemeId(long schemeId) {
		this.schemeId = schemeId;
	}

	public void setSchemeType(int schemeType) {
		this.schemeType = schemeType;
	}

	public void setShowScheme(String showScheme) {
		this.showScheme = showScheme;
	}
}