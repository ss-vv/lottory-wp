package com.xhcms.lottery.admin.web.action.promote;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.unison.lottery.pm.entity.WinGrantPO;
import com.unison.lottery.pm.service.PromotionService;
import com.xhcms.lottery.admin.web.action.BaseListAction;
import com.xhcms.lottery.commons.data.GrantType;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;
import com.xhcms.lottery.commons.persist.service.GrantTypeService;
import com.xhcms.lottery.lang.Constants;

public class ListJCZQAction extends BaseListAction {

    private static final long serialVersionUID = 8668189836299243579L;
    
    @Autowired
    private PromotionService promotionServie;
    @Autowired
    private GrantTypeService grantTypeService;
    
    private int state = -1;
    private long schemeId = 0;
    private long granttypeId = 0;
    private  List<GrantType> grantTypes;
    
    private WinGrantPO winGrant = new WinGrantPO();
    
    @Override
    public String execute(){
        paging = wrapPaging();
        winGrant.setScheme(new BetSchemePO());
        if(schemeId!=0){
        	winGrant.getScheme().setId(schemeId);
        }
        if(granttypeId!=0){
        	winGrant.setGranttypeId(granttypeId);
        }
        grantTypes = grantTypeService.JCZCGrantTypeList(Constants.JCZQ);
        promotionServie.listWinGrantPO(paging, from, to, state,winGrant,granttypeId==0?Constants.JCZQ:null);
        return SUCCESS;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

	public WinGrantPO getWinGrant() {
		return winGrant;
	}

	public void setWinGrant(WinGrantPO winGrant) {
		this.winGrant = winGrant;
	}

	public long getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(long schemeId) {
		this.schemeId = schemeId;
	}

	public List<GrantType> getGrantTypes() {
		return grantTypes;
	}

	public void setGrantTypes(List<GrantType> grantTypes) {
		this.grantTypes = grantTypes;
	}

	public long getGranttypeId() {
		return granttypeId;
	}

	public void setGranttypeId(long granttypeId) {
		this.granttypeId = granttypeId;
	}
	
}
