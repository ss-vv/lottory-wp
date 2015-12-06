package com.unison.lottery.weibo.web.action.analyse;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.davcai.lottery.constant.BbLeagueRankDataType;
import com.davcai.lottery.constant.FbLeagueRankDataType;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.BasketBallMatchPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchBaseInfoPO;
import com.unison.lottery.weibo.web.action.BaseAction;
import com.unison.lottery.weibo.web.service.JCLQAnalyseService;
import com.xhcms.commons.lang.Data;

/**
 * 竞彩篮球分析、赔率
 * @author baoxing.peng@davcai.com
 *
 * @since 2015年3月2日下午5:03:47
 */
public class JCLQAnalyseAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8032720147129394863L;
	
	private String daVCaiMatchId; //竞彩足球大v彩赛事id
	@Autowired
	private JCLQAnalyseService jCLQAnalyseService;
	private Data data = Data.success(null);
	private String homeTeamId;
	private String guestTeamId;
	private Map<String, Object> leagueRankMap;
	private BasketBallMatchPO matchBaseInfoPO;
	private String leagueId;
	private String subLeagueId;
	private String rankType;
	public String twoSideAgainstHistory(){
		//双方对阵历史
		String qtMatchBaseModels = jCLQAnalyseService.findAgainstHistory_latest_20(homeTeamId,guestTeamId);
		makeJsonData(qtMatchBaseModels);
		return SUCCESS;
	}
	
	public String execute() throws Exception{
		leagueRankMap = jCLQAnalyseService.findMatchMessageById(daVCaiMatchId);
		matchBaseInfoPO = (BasketBallMatchPO) leagueRankMap.get(BbLeagueRankDataType.matchMess.toString());
		
		homeTeamId = (String) leagueRankMap.get("homeTeamId");
		guestTeamId = (String) leagueRankMap.get("guestTeamId");
		
		return SUCCESS;
	}
	
	public String leagueScoreRank(){
		String leagueScoreData = jCLQAnalyseService.findLeagueScoreRankByLeagueId(leagueId,rankType,subLeagueId);
		makeJsonData(leagueScoreData);
		return SUCCESS;
	}
	private void makeJsonData(String qtMatchBaseModels) {
		data.setData(qtMatchBaseModels);
		data.setSuccess(true);
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
	public String getHomeTeamId() {
		return homeTeamId;
	}
	public void setHomeTeamId(String homeTeamId) {
		this.homeTeamId = homeTeamId;
	}
	public String getGuestTeamId() {
		return guestTeamId;
	}
	
	public Map<String, Object> getLeagueRankMap() {
		return leagueRankMap;
	}

	public void setLeagueRankMap(Map<String, Object> leagueRankMap) {
		this.leagueRankMap = leagueRankMap;
	}

	public void setGuestTeamId(String guestTeamId) {
		this.guestTeamId = guestTeamId;
	}
	public JCLQAnalyseService getjCLQAnalyseService() {
		return jCLQAnalyseService;
	}
	public void setjCLQAnalyseService(JCLQAnalyseService jCLQAnalyseService) {
		this.jCLQAnalyseService = jCLQAnalyseService;
	}

	public BasketBallMatchPO getMatchBaseInfoPO() {
		return matchBaseInfoPO;
	}
	
	public String getLeagueId() {
		return leagueId;
	}

	public void setLeagueId(String leagueId) {
		this.leagueId = leagueId;
	}

	public String getSubLeagueId() {
		return subLeagueId;
	}

	public void setSubLeagueId(String subLeagueId) {
		this.subLeagueId = subLeagueId;
	}

	public String getRankType() {
		return rankType;
	}

	public void setRankType(String rankType) {
		this.rankType = rankType;
	}

	public void setMatchBaseInfoPO(BasketBallMatchPO matchBaseInfoPO) {
		this.matchBaseInfoPO = matchBaseInfoPO;
	}
	
}
