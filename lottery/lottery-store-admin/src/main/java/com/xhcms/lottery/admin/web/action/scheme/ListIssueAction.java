package com.xhcms.lottery.admin.web.action.scheme;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.admin.persist.service.BetSchemeService;
import com.xhcms.lottery.admin.web.action.BaseAction;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.HFBetContent;
import com.xhcms.lottery.commons.data.ctfb.CTBetContent;
import com.xhcms.lottery.commons.persist.service.CTBetService;
import com.xhcms.lottery.commons.persist.service.JX11BetService;
import com.xhcms.lottery.lang.Constants;

public class ListIssueAction extends BaseAction {

    private static final long serialVersionUID = 128142404801446862L;

    @Autowired
    private BetSchemeService betSchemeService;
    
    @Autowired
    private JX11BetService jx11BetService;
    
    @Autowired
    private CTBetService cTBetService;
    
    private Long id;
    private String playId;
    private String lotteryId;
    private List<HFBetContent> hfBetContents;
    private List<CTBetContent> cTBetContents;

    @Override
    public String execute() {
    	BetScheme betScheme = betSchemeService.getScheme(id);
    	lotteryId = betScheme.getLotteryId();
    	if(Constants.JX11.equals(lotteryId)) {
    		hfBetContents = jx11BetService.findHfBetContent(id);
    	} else if(Constants.CTZC.equals(lotteryId)) {
    		cTBetContents = cTBetService.findCtBetContent(id);
    	}
        return SUCCESS;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getPlayId() {
        return playId;
    }

    public void setPlayId(String playId) {
        this.playId = playId;
    }

	public String getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}

	public List<HFBetContent> getHfBetContents() {
		return hfBetContents;
	}
	
	public List<CTBetContent> getCTBetContents() {
		return cTBetContents;
	}

}
