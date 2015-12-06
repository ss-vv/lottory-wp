package com.xhcms.lottery.dc.feed.web.action.bet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.common.service.BetMatchesService;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.persist.entity.BetMatchRecPO;
import com.xhcms.lottery.commons.persist.entity.MixMatchPlay;
import com.xhcms.lottery.commons.persist.service.BetMatchRecService;
import com.xhcms.lottery.commons.persist.service.BetSchemeRecService;
import com.xhcms.lottery.commons.persist.service.MatchService;
import com.xhcms.lottery.dc.feed.web.action.BaseAction;
import com.xhcms.lottery.lang.LotteryId;

/**
 * 新版大V彩竞彩足球投注页面
 * @author haoxiang.jiang@davcai.com
 */
public class JCZQAction extends BaseAction {
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
	private BetMatchesService betMatchesService;
	@Autowired
	private BetSchemeRecService betSchemeRecService;
	private List<MixMatchPlay> jczqAllMatches;
	private String time;
	public String execute(){
		getSchemeBetMatchs(schemeId);
		getRecommend();
		return SUCCESS;
	}
	
	public String allMatches(){
		jczqAllMatches = betMatchesService.getMixMatchPlaysByDate(time, LotteryId.JCZQ.name());
		if(null != jczqAllMatches && jczqAllMatches.size() > 0){
			return SUCCESS;
		}
		time = getValidTime(time);
		if (StringUtils.isBlank(time)) {
			jczqAllMatches = matchService.listFBInLast2Days();
		} else {
			jczqAllMatches = matchService.listFBInLastDays(time);
		}
		betMatchesService.saveMixMatchPlaysCache(jczqAllMatches, time, LotteryId.JCZQ.name());
		return SUCCESS;
	}
	
	private String getValidTime(String time){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if(null == time){
				time = simpleDateFormat.format(new Date());
				return time;
			}
			simpleDateFormat.parse(time);
			return time;
		} catch (ParseException e) {
			time = simpleDateFormat.format(new Date());
			return time;
		}
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
	public List<MixMatchPlay> getJczqAllMatches() {
		return jczqAllMatches;
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

	public void setTime(String time) {
		this.time = time;
	}

	public String getTime() {
		return time;
	}
}
