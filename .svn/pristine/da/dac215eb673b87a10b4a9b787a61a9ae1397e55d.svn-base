package com.xhcms.lottery.admin.web.action.scheme;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.admin.persist.service.BetSchemeService;
import com.xhcms.lottery.admin.web.action.BaseAction;
import com.xhcms.lottery.commons.data.BetPartner;
import com.xhcms.lottery.commons.data.BetScheme;

public class ListPartnerAction extends BaseAction {

    private static final long serialVersionUID = 128142404801446862L;

    @Autowired
    private BetSchemeService betSchemeService;

    private List<BetPartner> partners;
    private Long id;
    private String playId;
    private String lotteryId;

    @Override
    public String execute() {
    	BetScheme betScheme = betSchemeService.getScheme(id);
    	lotteryId = betScheme.getLotteryId();
        partners = betSchemeService.listPartner(id);
        return SUCCESS;
    }

    public List<BetPartner> getPartners() {
        return partners;
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
}
