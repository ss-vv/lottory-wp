package com.unison.lottery.weibo.web.action.odds;

import java.util.List;
import java.util.Map; 

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchAsiaOuOddsInfoPO;
import com.unison.lottery.weibo.web.action.BaseAction;
import com.unison.lottery.weibo.web.service.JCZQAnalyseService;
import com.xhcms.commons.lang.Data;

/**
 * 新版竞彩足球赔率页
 * @author baoxing.peng@davcai.com
 *
 * @since 2015年2月5日下午5:24:13
 */
public class JCZQOddsAction extends BaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5440661697640338681L;

	private String daVCaiMatchId; //竞彩足球大v彩赛事id
	@Autowired
	private JCZQAnalyseService jCZQAnalyseService;
	
	private Long matchId;
	private Integer corpId;
	
	private Data data = Data.success(null);
	private String corpIds;

	private String time;
	public String execute(){
		String jsonData = jCZQAnalyseService.findFbMatchEuroOddsById(daVCaiMatchId);
		ActionContext.getContext().getValueStack().set("euroOdds", jsonData);
		return SUCCESS;
	}
	
	public String asianOddsList(){
		List<Map<String, Object>> map = jCZQAnalyseService.findFbMatchAsianOddsById(daVCaiMatchId);
		if(!map.isEmpty()){
			//初盘
			ActionContext.getContext().getValueStack().set("init_asianOdds", map.get(0));
			//即时盘
			ActionContext.getContext().getValueStack().set("jishi_asianOdds", map.get(1));
			//比赛信息
			ActionContext.getContext().getValueStack().set("fb_match", map.get(2));
		}
		return SUCCESS;
	}
	
	public String oneCompanyAsianOdds(){
		List<FbMatchAsiaOuOddsInfoPO> fbMatchAsiaOuOddsInfoPOs = jCZQAnalyseService.findFbAsianOddsOneCompany(matchId,corpId);
		ActionContext.getContext().getValueStack().set("asianOdds",fbMatchAsiaOuOddsInfoPOs);
		return SUCCESS;
	}
	
	public String fbOddsPushInit(){
		Map<String, Map<String, Map>> oddsData = jCZQAnalyseService.findFbOddsPushInit(corpIds,time);
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

	public String getDaVCaiMatchId() {
		return daVCaiMatchId;
	}
	public void setDaVCaiMatchId(String daVCaiMatchId) {
		this.daVCaiMatchId = daVCaiMatchId;
	}
	public Data getData() {
		return data;
	}
	public void setData(Data data) {
		this.data = data;
	}

	public Long getMatchId() {
		return matchId;
	}

	public void setMatchId(Long matchId) {
		this.matchId = matchId;
	}

	public Integer getCorpId() {
		return corpId;
	}

	public void setCorpId(Integer corpId) {
		this.corpId = corpId;
	}
	
}
