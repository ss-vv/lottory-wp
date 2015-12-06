package com.xhcms.lottery.dc.feed.web.action.bet;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.persist.entity.BetMatchRecPO;
import com.xhcms.lottery.commons.persist.entity.MixMatchPlay;
import com.xhcms.lottery.commons.persist.service.BetMatchRecService;
import com.xhcms.lottery.commons.persist.service.BetSchemeRecService;
import com.xhcms.lottery.commons.persist.service.MatchService;
import com.xhcms.lottery.dc.feed.web.action.BaseAction;

/**
 * 新版大V彩竞彩篮球投注页面
 * @author haoxiang.jiang@davcai.com
 */
public class JCLQAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private String schemeId;
	private BetMatchRecPO bmrpo;
	private String passTypeIds; 
	private int multiple;
	private List<BetMatchRecPO> betMatchList;
	@Autowired
	private MatchService matchService;
	@Autowired
	private BetMatchRecService matchRecService;
	@Autowired
	private BetSchemeRecService betSchemeRecService;
	private List<MixMatchPlay> jclqAllMatches;
	private String time;
	
	public String execute(){
		getSchemeBetMatchs(schemeId);
		getRecommend();
		return SUCCESS;
	}
	private void getSchemeBetMatchs(String schemeId) {
		if (StringUtils.isNotBlank(schemeId)) {
			try {
				Long id = Long.valueOf(schemeId);
				betMatchList = matchRecService.findBySchemeId(id);
				// 方案串关
				BetScheme scheme = betSchemeRecService.viewRecScheme(id);
				passTypeIds = scheme.getPassTypeIds();
				multiple = scheme.getMultiple();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	private void getRecommend(){
		if(bmrpo!=null &&bmrpo.getMatchId()!=null){
			betMatchList=new ArrayList<BetMatchRecPO>();
			betMatchList.add(bmrpo);
		}
	}
	public String allMatches(){
		jclqAllMatches = matchService.listBBInLastDays(time);
		return SUCCESS;
	}

	public List<MixMatchPlay> getJclqAllMatches() {
		return jclqAllMatches;
	}
	public void setSchemeId(String schemeId) {
		this.schemeId = schemeId;
	}
	public void setBmrpo(BetMatchRecPO bmrpo) {
		this.bmrpo = bmrpo;
	}
	public String getPassTypeIds() {
		return passTypeIds;
	}
	public int getMultiple() {
		return multiple;
	}
	public List<BetMatchRecPO> getBetMatchList() {
		return betMatchList;
	}
	public String getTime() {
		return time; 
	}
	public void setTime(String time) {
		this.time = time;
	}
}
