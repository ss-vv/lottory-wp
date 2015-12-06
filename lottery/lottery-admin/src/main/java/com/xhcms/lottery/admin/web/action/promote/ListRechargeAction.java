package com.xhcms.lottery.admin.web.action.promote;

import org.springframework.beans.factory.annotation.Autowired;
import com.xhcms.lottery.admin.web.action.BaseListAction;
import com.xhcms.lottery.commons.persist.service.PMRechargeRedeemedService;

public class ListRechargeAction extends BaseListAction {

	private static final long serialVersionUID = -2408668028982039015L;
	@Autowired
    private PMRechargeRedeemedService pMRechargeRedeemedService;
	
	private String username;
	
	private String operatorName;

	private int state = -1;
    
    @Override
    public String execute(){
        init();
        paging.setCount(true);
        pMRechargeRedeemedService.findPMRechargeRedeemed(paging, from, to, state, username, operatorName);
        
        return SUCCESS;
    }
    
	public int getState() {
		return state;
	}


	public void setState(int state) {
		this.state = state;
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
    
}
