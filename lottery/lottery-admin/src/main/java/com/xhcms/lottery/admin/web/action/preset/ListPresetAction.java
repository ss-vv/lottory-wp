package com.xhcms.lottery.admin.web.action.preset;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.admin.web.action.BaseListAction;
import com.xhcms.lottery.commons.data.BetSchemePreset;
import com.xhcms.lottery.commons.persist.service.PresetAwardService;
import com.xhcms.lottery.lang.EntityStatus;

/**
 * 查询并预览可预兑奖方案
 * @author Wang Lei
 *
 */
public class ListPresetAction extends BaseListAction {
	private static final long serialVersionUID = 5235943196152554334L;
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private PresetAwardService presetAwardService;
	private String sponsor;
	private Long schemeId;
	private String lotteryId;
	private BetSchemePreset betSchemePreset;
	private List<BetSchemePreset> betSchemePresets;
	
	/**
	 * 预览未派奖的预派奖方案
	 */
	@Override
	public String execute() {
		try {
			init();
			query(EntityStatus.TICKET_NOT_AWARD);
		} catch (Exception e) {
			logger.error("查询未派奖方案出错！{}",e);
		}
		
	    return SUCCESS;
	}
	
	/**
	 * 已派奖
	 * @return
	 */
	public String awarded() {
	    return query(EntityStatus.TICKET_AWARDED);
	}
	
	/**
	 * 未中奖
	 * @return
	 */
	public String notWin() {
	    return query(EntityStatus.TICKET_NOT_WIN);
	}
	
	private String query(int status){
	    init();
	    if(StringUtils.isBlank(lotteryId)){
	        lotteryId = null;
	    }
	    if(null == betSchemePreset){
	    	betSchemePreset = new BetSchemePreset();
	    }
	    if(StringUtils.isNotBlank(lotteryId)){
	    	betSchemePreset.setLotteryId(lotteryId);
	    }
	    if(StringUtils.isNotBlank(sponsor)){
	    	betSchemePreset.setSponsor(sponsor);
	    }
	    if(null != schemeId){
	    	betSchemePreset.setId(schemeId);
	    }
	    betSchemePreset.setStatus(status);
	    presetAwardService.list(betSchemePreset, from , to, paging);
        return SUCCESS;
	}

    public String getSponsor() {
		return sponsor;
	}

	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}

	public String getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(String lotteryId) {
        this.lotteryId = lotteryId;
    }

	public Long getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(Long schemeId) {
		this.schemeId = schemeId;
	}


	public BetSchemePreset getBetSchemePreset() {
		return betSchemePreset;
	}


	public void setBetSchemePreset(BetSchemePreset betSchemePreset) {
		this.betSchemePreset = betSchemePreset;
	}


	public List<BetSchemePreset> getBetSchemePresets() {
		return betSchemePresets;
	}


	public void setBetSchemePresets(List<BetSchemePreset> betSchemePresets) {
		this.betSchemePresets = betSchemePresets;
	}
	
}
