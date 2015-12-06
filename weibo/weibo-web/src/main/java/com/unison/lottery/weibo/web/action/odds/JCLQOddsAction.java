package com.unison.lottery.weibo.web.action.odds;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.web.action.BaseAction;
import com.unison.lottery.weibo.web.service.JCLQAnalyseService;
import com.xhcms.commons.lang.Data;

/**
 * @author baoxing.peng@davcai.com
 *
 */
public class JCLQOddsAction extends BaseAction {
	private String corpIds;
	private Data data = Data.success(null); 
	@Autowired
	private JCLQAnalyseService jclqAnalyseService;
	
	private String time;
	
	public String bbOddsPushInit(){
		Map<String, Map<String, Map>> oddsData = jclqAnalyseService.findBbOddsPushInit(corpIds,time);
		data.setSuccess(true);
		data.setData(oddsData); 
		return SUCCESS;
	}
	public String getCorpIds() {
		return corpIds;
	}
	public void setCorpIds(String corpIds) {
		this.corpIds = corpIds;
	}
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public JCLQAnalyseService getJclqAnalyseService() {
		return jclqAnalyseService;
	}
	public void setJclqAnalyseService(JCLQAnalyseService jclqAnalyseService) {
		this.jclqAnalyseService = jclqAnalyseService;
	}
	public Data getData() {
		return data;
	}
	public void setData(Data data) {
		this.data = data;
	}
	
	
}
