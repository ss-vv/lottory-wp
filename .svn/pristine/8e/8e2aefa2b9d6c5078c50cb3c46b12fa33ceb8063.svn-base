package com.unison.lottery.pm.web.action;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.unison.lottery.pm.data.PromotionWinResult;
import com.unison.lottery.pm.lang.WinGrant;
import com.unison.lottery.pm.service.PromotionService;
import com.xhcms.commons.lang.Data;

/**
 * 足彩奖上奖TOP10榜单
 * @author Wang Lei
 *
 */
public class AjaxFootballListAction extends BaseAction{
	
	private static final long serialVersionUID = 1733210540172612534L;
	
	@Autowired
	private PromotionService promotionService;
	
	private Data data;

	@Override
    public String execute() throws Exception {
		List<PromotionWinResult> results = promotionService.findPromotionWinTOP(10,WinGrant.promotion.jczc_2012_6);
		data = Data.success(results);
        return SUCCESS;
	}
	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

}
