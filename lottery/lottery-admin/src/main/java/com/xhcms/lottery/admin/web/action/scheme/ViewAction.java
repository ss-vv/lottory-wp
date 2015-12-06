package com.xhcms.lottery.admin.web.action.scheme;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.admin.persist.service.BetSchemeService;
import com.xhcms.lottery.admin.web.action.BaseAction;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.lang.Constants;

public class ViewAction extends BaseAction {

    private static final long serialVersionUID = 128142404801446862L;
    
    @Autowired
    private BetSchemeService betSchemeService;
    
    private BetScheme s;
    private Long id;
    private String lotteryId;
    
    @Override
    public String execute(){
        s = betSchemeService.getScheme(id);
        lotteryId = s.getLotteryId();
		if (null != lotteryId
				&& (Constants.JCZQ.equals(lotteryId) || Constants.JCLQ.equals(lotteryId))) {
			s.setPassTypeIds(s.getPassTypeIds().replace(',', ' ')
					.replaceAll("@", "串"));
		}
        return SUCCESS;
    }
    
    public String view(){
        s = betSchemeService.getPresetScheme(id);
        lotteryId = s.getLotteryId();
		if (null != lotteryId
				&& (Constants.JCZQ.equals(lotteryId) || Constants.JCLQ.equals(lotteryId))) {
			s.setPassTypeIds(s.getPassTypeIds().replace(',', ' ')
					.replaceAll("@", "串"));
		}
        return SUCCESS;
    }

    public BetScheme getS() {
        return s;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
}
