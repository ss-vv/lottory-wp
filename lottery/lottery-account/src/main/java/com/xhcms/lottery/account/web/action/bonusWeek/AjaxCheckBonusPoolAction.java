package com.xhcms.lottery.account.web.action.bonusWeek;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.lang.Data;
import com.xhcms.lottery.account.web.action.BaseAction;
import com.xhcms.lottery.commons.persist.service.PMWeekBonusService;
/**
 * 查询奖池
 * @author Wang Lei
 *
 */
public class AjaxCheckBonusPoolAction extends BaseAction {

	private static final long serialVersionUID = 5504373445983991375L;
	@Autowired
	private PMWeekBonusService pMWeekBonusService;
    private Data data = Data.success("");
    
	@Override
	public String execute() {
		try {
			data.setData(pMWeekBonusService.getBonusPool());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}
}
