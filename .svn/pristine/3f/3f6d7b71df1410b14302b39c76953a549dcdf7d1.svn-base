package com.xhcms.lottery.account.web.action.custommade;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.account.web.action.BaseAction;
import com.xhcms.lottery.commons.persist.service.CustomMadeService;

/**
 * @author yonglizhu
 *
 */
public class CustomMadeCancelAction extends BaseAction {

	private static final long serialVersionUID = 6709196796932125213L;
	
	private Long cmid;
	@Autowired
	private CustomMadeService customMadeService;
	
	@Override
	public String execute() throws Exception {
		if(cmid == null) {
			return ERROR;
		}
		customMadeService.deleteCustomMade(cmid);
		return SUCCESS;
	}

	public Long getCmid() {
		return cmid;
	}

	public void setCmid(Long cmid) {
		this.cmid = cmid;
	}	
}
