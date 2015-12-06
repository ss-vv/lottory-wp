package com.xhcms.lottery.admin.web.action.match;

import org.springframework.beans.factory.annotation.Autowired;
import com.xhcms.lottery.admin.web.action.BaseListAction;
import com.xhcms.lottery.commons.persist.service.MatchColorService;
import com.xhcms.lottery.lang.Constants;

public class ListNoColorAction extends BaseListAction {

    private static final long serialVersionUID = 7745142796518100377L;

    @Autowired
    private MatchColorService matchColorService;
    private String lotteryId=Constants.JCZQ;
    
    @Override
    public String execute(){
        init();
        matchColorService.listNoColor(lotteryId, paging);
        return SUCCESS;
    }

    public String getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(String lotteryId) {
        this.lotteryId = lotteryId;
    }
}
