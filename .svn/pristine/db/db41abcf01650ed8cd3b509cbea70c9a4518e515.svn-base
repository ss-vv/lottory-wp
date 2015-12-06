package com.unison.lottery.weibo.web.action.analyse;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.davcai.lottery.constant.FbLeagueRankDataType;
import com.opensymphony.xwork2.ActionContext;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbLeagueScorePO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchBaseInfoPO;
import com.unison.lottery.weibo.web.action.BaseAction;
import com.unison.lottery.weibo.web.service.JCZQAnalyseService;
import com.xhcms.commons.lang.Data;

/**
 * 新版竞彩足球分析、赔率页面
 * @author baoxing.peng@davcai.com
 *
 */
public class JCZQAnalyseAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8263871309648525006L;
	
	private String daVCaiMatchId; //竞彩足球大v彩赛事id
	@Autowired
	private JCZQAnalyseService jCZQAnalyseService;
	
	private FbMatchBaseInfoPO matchBaseInfoPO;
	private Map<String, Object> leagueRankMap;
	private Data data = Data.success(null);
	private String homeTeamId;
	private String guestTeamId;
	private String teamId;
	private String leagueId;
	private String time;
	/**
	 * 排行榜类型{total_rank:总积分榜,zc_rank:主场积分榜,
	 * kc_rank:客场积分榜,half_total_rank:半场总积分榜,
	 * half_zc_rank:主场半场积分榜,half_kc_rank:客场半场积分榜}
	 */
	private String rankType;
	public String execute(){
		leagueRankMap = jCZQAnalyseService.findFbLeagueRankData(daVCaiMatchId);
		matchBaseInfoPO = (FbMatchBaseInfoPO) leagueRankMap.get(FbLeagueRankDataType.matchMess.toString());
		
		homeTeamId = (String) leagueRankMap.get("homeTeamId");
		guestTeamId = (String) leagueRankMap.get("guestTeamId");
		return SUCCESS;
	}
	
	public String twoSideAgainstHistory(){
		String qtMatchBaseModels = jCZQAnalyseService.findAgainstHistory_latest_20(homeTeamId,guestTeamId);
		makeJsonData(qtMatchBaseModels);
		return SUCCESS;
	}
	
	public String teamRecentRecord(){
		String qtMatchBaseModels = jCZQAnalyseService.findTeamRecentRecord_latest_20(teamId);
		makeJsonData(qtMatchBaseModels);
		return SUCCESS;
	}
	
	public String leagueScoreRank(){
		String leagueScoreData = jCZQAnalyseService.findLeagueScoreRankByLeagueId(leagueId,rankType);
		makeJsonData(leagueScoreData);
		return SUCCESS;
	}
	
	public String ajaxJczqMatchTeamPosition(){
		Map<String, Map> dataMap = jCZQAnalyseService.findJczqMatchTeamPosition(time);
		data.setData(dataMap);
		data.setSuccess(true);
		return SUCCESS;
	}
	/**
	 * @param leagueScoreData
	 */
	private void makeJsonData(String jsonData) {
		data.setSuccess(true);
		data.setData(jsonData);
	}
	
	public String getLeagueId() {
		return leagueId;
	}

	public void setLeagueId(String leagueId) {
		this.leagueId = leagueId;
	}
	
	public Map<String, Object> getLeagueRankMap() {
		return leagueRankMap;
	}
	
	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
	
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setLeagueRankMap(Map<String, Object> leagueRankMap) {
		this.leagueRankMap = leagueRankMap;
	}
	public Data getData() {
		return data;
	}
	public void setData(Data data) {
		this.data = data;
	}
	
	public String getRankType() {
		return rankType;
	}

	public void setRankType(String rankType) {
		this.rankType = rankType;
	}

	public String getHomeTeamId() {
		return homeTeamId;
	}

	public void setHomeTeamId(String homeTeamId) {
		this.homeTeamId = homeTeamId;
	}

	public String getGuestTeamId() {
		return guestTeamId;
	}

	public void setGuestTeamId(String guestTeamId) {
		this.guestTeamId = guestTeamId;
	}

	public String getDaVCaiMatchId() {
		return daVCaiMatchId;
	}
	
	public FbMatchBaseInfoPO getMatchBaseInfoPO() {
		return matchBaseInfoPO;
	}
	
	public void setMatchBaseInfoPO(FbMatchBaseInfoPO matchBaseInfoPO) {
		this.matchBaseInfoPO = matchBaseInfoPO;
	}

	public void setDaVCaiMatchId(String daVCaiMatchId) {
		this.daVCaiMatchId = daVCaiMatchId;
	}
	public JCZQAnalyseService getjCZQAnalyseService() {
		return jCZQAnalyseService;
	}
	public void setjCZQAnalyseService(JCZQAnalyseService jCZQAnalyseService) {
		this.jCZQAnalyseService = jCZQAnalyseService;
	}
	
	
	
	

}
