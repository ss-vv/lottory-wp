package com.xhcms.lottery.admin.web.action.promote;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.xhcms.commons.lang.Data;
import com.xhcms.lottery.admin.data.Admin;
import com.xhcms.lottery.admin.lang.AdminConstant;
import com.xhcms.lottery.admin.web.action.BaseAction;
import com.xhcms.lottery.commons.persist.service.PMRechargeRedeemedService;
import com.unison.lottery.pm.service.PromotionService;

public class AjaxSubmitAction extends BaseAction {

    private static final long serialVersionUID = 8668189206299243579L;
    
    @Autowired
    private PromotionService promotionServie;
    
    @Autowired
    private PMRechargeRedeemedService pMRechargeRedeemedService;
    
    private List<Long> id;
    
    private Data data = Data.success("");
    
    @Override
    public String execute(){
    	Admin a = (Admin) request.getSession().getAttribute(AdminConstant.USER);
        try {
        	promotionServie.sponsorGrants(id, a.getId(), a.getUsername());
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
        return SUCCESS;
    }
    
	public String recharge_submit() {
		Admin a = (Admin) request.getSession().getAttribute(AdminConstant.USER);
		pMRechargeRedeemedService.submitGrant(id, a.getId(), a.getUsername());

		return SUCCESS;
	}

	public List<Long> getId() {
		return id;
	}

	public void setId(List<Long> id) {
		this.id = id;
	}
	
	public Data getData() {
		return data;
	}
}
