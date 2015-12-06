package com.xhcms.lottery.account.web.action.bet;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.xhcms.lottery.account.service.BetSchemeService;
import com.xhcms.lottery.account.web.action.BaseListAction;

/**
 * <p>Title: 查询我发起的合买记录 </p>
 * @author wang lei
 * @version 1.0.0
 */
public class ListMyLaunchGroupbuyAction extends BaseListAction {

	private static final long serialVersionUID = 2308842221093438441L;

	@Autowired
	private BetSchemeService betSchemeService;

	private String lotteryId;
	private long sponsorid;
	private String playId;
	private String passType;
	private int status;
	private Long schemeId;
	
	@Override
	public String execute() {
	    init();
	    if(StringUtils.isBlank(lotteryId)){
	        lotteryId = null;
	    }
	    sponsorid=getUser().getId();
	    betSchemeService.findMyLaunchGroupbuyList(paging, status, lotteryId, sponsorid, playId, passType, from, to);
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Long getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(Long schemeId) {
		this.schemeId = schemeId;
	}

	public long getSponsorid() {
		return sponsorid;
	}

	public void setSponsorid(long sponsorid) {
		this.sponsorid = sponsorid;
	}
    
}
