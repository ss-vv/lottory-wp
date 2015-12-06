package com.xhcms.lottery.account.web.action.bonusWeek;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.lang.Data;
import com.xhcms.lottery.account.web.action.BaseAction;
import com.xhcms.lottery.commons.persist.service.PMWeekBonusService;



/**
 * 用户申请领奖	
 */
public class AjaxExpiryAction extends BaseAction {

	private static final long serialVersionUID = -5190025512878521488L;
	
	Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private PMWeekBonusService pMWeekBonusService;
	public Long recordId;
	private Data data = Data.success("");

	@Override
	public String execute() {
		try {
			log.info("用户ID={},申请领取加奖奖金,奖金记录ID={}", new Object[]{getUser(), recordId});
			pMWeekBonusService.prize(getUserId(), recordId);
		} catch (Exception e) {
			log.error("用户ID={},领取加奖奖金异常:", e);
		}
		return SUCCESS;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public Long getRecordId() {
		return recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}
}
