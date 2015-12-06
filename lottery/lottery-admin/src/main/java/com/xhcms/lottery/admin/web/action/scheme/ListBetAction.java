package com.xhcms.lottery.admin.web.action.scheme;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.admin.persist.service.BetSchemeService;
import com.xhcms.lottery.admin.web.action.BaseListAction;

/**
 * 
 * <p>Title: 投注方案管理</p>
 * <p>Description: </p>
 * <p>Copyright：Copyright (c) 2011</p>
 * <p>Company：XingHui Spirit (Beijing) Technical Co.,Ltd.</p>
 * 
 * @author xulang
 * @version 1.0.0
 */
public class ListBetAction extends BaseListAction {
    private static final long serialVersionUID = -6405871733143923846L;
    
    @Autowired
    private BetSchemeService betSchemeService;

    private String lotteryId;
    
    private String playId;
    
    private String passType;
    
    private String sponsor;
    
    private Long schemeId;
    
    private int state = -1;
    
    @Override
    public String execute(){
        init();
        if(lotteryId == null){
            lotteryId = "JCZQ";
        }
        if(StringUtils.isBlank(playId)){
            playId = null;
        }
        if(StringUtils.isBlank(passType)){
            passType = null;
        }
        
        betSchemeService.listBetSchemeOrderByOfftimeAsc(paging, from, to, state, lotteryId, schemeId, sponsor, playId, passType);
        return SUCCESS;
    }

    public String getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(String lotteryId) {
        this.lotteryId = lotteryId;
    }

    public String getPlayId() {
        return playId;
    }

    public void setPlayId(String playId) {
        this.playId = playId;
    }

    public String getPassType() {
        return passType;
    }

    public void setPassType(String passType) {
        this.passType = passType;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public Long getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(Long schemeId) {
        this.schemeId = schemeId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

}
