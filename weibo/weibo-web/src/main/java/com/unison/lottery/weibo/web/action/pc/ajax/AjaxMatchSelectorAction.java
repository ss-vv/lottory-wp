package com.unison.lottery.weibo.web.action.pc.ajax;

import java.util.Date;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.opensymphony.xwork2.Action;
import com.unison.lottery.weibo.web.service.MatchScheduleService;
import com.xhcms.commons.lang.Data;
import com.xhcms.lottery.commons.data.MatchSelector;
import com.xhcms.lottery.lang.Constants;

/**
 * 赛事筛选
 * 
 * @author lei.li@unison.net.cn
 * @version 1.0
 */
public class AjaxMatchSelectorAction implements Action {
	
	Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private MatchScheduleService matchScheduleService;
	
	private Data data = Data.success(null);
	
	private String playId;
	
	private Date playingTime;
	
	private String isShowStopSell;
	
	private String issueNum;
	
	private long begin;
	private long end;
	private MatchSelector selector;
	private Map<String, Object> results;

	@Override
	public String execute() {
		return SUCCESS;
	}
	
	public String fbMatchs() {
		if(StringUtils.isBlank(playId)) {
			playId = Constants.PLAY_80_ZC_2;
		}
		try {
			beforeMethod();
			results = matchScheduleService.findFBMatchs(selector);
			afterMethod();
		} catch (Exception e) {
			data = Data.failure(null);
			log.error("赛事过滤异常.", e);
		}
		return SUCCESS;
	}
	
	public String bbMatchs() {
		if(StringUtils.isBlank(playId)) {
			playId = Constants.PLAY_06_LC_2;
		}
		try {
			beforeMethod();
			results = matchScheduleService.findBBMatchs(selector);
			afterMethod();
		} catch (Exception e) {
			data = Data.failure(null);
			log.error("赛事过滤异常.", e);
		}
		return SUCCESS;
	}
	public String bjdcMatchs() {
		if(StringUtils.isBlank(playId)) {
			playId = Constants.PLAY_01_BD_SPF;
		}
		try {
			beforeMethod();
			results = matchScheduleService.findBJDCMatchs(selector);
			afterMethod();
		} catch (Exception e) {
			data = Data.failure(null);
			log.error("赛事过滤异常.", e);
		}
		return SUCCESS;
	}

	private void beforeMethod() {
		log.debug("\n赛事过滤:开赛时间={}, 玩法ID={}, 是否显示停售={}",
				new Object[]{playingTime, playId, isShowStopSell});
		begin = System.currentTimeMillis();
		selector = parseQueryParam();
	}
	
	private void afterMethod() {
		data.setData(results);
		end = System.currentTimeMillis();
		log.debug("\n整个请求竞彩足球赛程过滤查询耗时(毫秒)：{}", (end-begin));
	}
	
	private MatchSelector parseQueryParam() {
		MatchSelector selector = new MatchSelector();
		selector.setPlayId(playId);
		if(StringUtils.isBlank(isShowStopSell) || 
				Boolean.TRUE.toString().equals(isShowStopSell)) {
			selector.setShowStopSell(true);
		} else {
			selector.setShowStopSell(false);
		}
		if(playingTime == null) {
			playingTime = new Date();
		}
		selector.setIssueNum(issueNum);
		selector.setPlayingTime(playingTime);
		log.debug("\n赛程查询参数：{}", selector);
		return selector;
	}
	
	public void setPlayId(String playId) {
		this.playId = playId;
	}
	
	public void setPlayingTime(Date playingTime) {
		this.playingTime = playingTime;
	}
	
	public void setIsShowStopSell(String isShowStopSell) {
		this.isShowStopSell = isShowStopSell;
	}
	
	public Data getData() {
		return data;
	}

	public String getIssueNum() {
		return issueNum;
	}

	public void setIssueNum(String issueNum) {
		this.issueNum = issueNum;
	}
}