package com.unison.lottery.weibo.dataservice.commons.crawler.post;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.hibernate.cfg.annotations.Nullability;

import com.unison.lottery.weibo.dataservice.commons.crawler.constants.Qt_fb_match_oddsType;
import com.unison.lottery.weibo.dataservice.commons.crawler.constants.ReceiveAndSendDataType;
import com.unison.lottery.weibo.dataservice.commons.crawler.model.HttpResult;
import com.unison.lottery.weibo.dataservice.commons.crawler.util.HttpUtil;
import com.unison.lottery.weibo.dataservice.commons.crawler.util.HttpUtilImpl;
import com.unison.lottery.weibo.dataservice.commons.util.SystemPropertiesUtil;
import com.unison.lottery.weibo.mq.CrawlerFbZqCompanyOddsHandle;

/**
 * 发送数据
 * @author baoxing.peng
 * @since 2014年12月30日下午2:43:07
 */
public class PostDataServiceImpl implements PostDataService {
	
	private String sendUrl;  //向服务器发送数据
	private String receiveUrl;//接收服务器的响应数据
	private HttpUtil httpUtil;
	
	public PostDataServiceImpl() {
		httpUtil = new HttpUtilImpl();
		initServerUrl();
	}
	private void initServerUrl() {
		// TODO Auto-generated method stub
		final String baseUrl = SystemPropertiesUtil.getPropsValue("server_base_url");
		sendUrl = baseUrl+"receive.do";
		receiveUrl = baseUrl + "send.do";
		
	}
	@Override
	public void sendZqJishiBifenDataToServer(String encryptedData) throws IOException {
		Map<String, String> params = makeSendParams(encryptedData,ReceiveAndSendDataType.FbJingcaiJishiBifen);
		
		httpUtil.httpPost(sendUrl, params,null);
	}

	private Map<String, String> makeSendParams(String encryptedData,
			ReceiveAndSendDataType dataType) {
		Map<String, String> extend = new HashMap<String, String>();
		if (encryptedData != null) {
			extend.put("jsonObject", encryptedData);
		}
		if (dataType != null) {
			extend.put("dataType", dataType.toString());
		}
		return extend;
	}
	@Override
	public void sendBbJingcaiJishiBifenDataToServer(String encodeData) throws IOException {
		Map<String, String> params = makeSendParams(encodeData, ReceiveAndSendDataType.BbJingcaiJishiBifen);
		httpUtil.httpPost(sendUrl, params, null);
	}
	@Override
	public void sendZqChangeOddsToServer(String encryptedData,
			Qt_fb_match_oddsType oddsType) throws IOException {
		Map<String, String> params = makeSendParams(encryptedData, ReceiveAndSendDataType.ZqOddsChange);
		params.put("oddsType", oddsType.toString());
		httpUtil.httpPost(sendUrl, params, null);
	}
	@Override
	public String receiveAllJingcaiZqMatchInLive() throws IOException {
		Map<String, String> params = makeSendParams(null, ReceiveAndSendDataType.ZqJingcaiMatchInLive);
		return makeHttpResponse(params);
	}
	@Override
	public void sendZqJishiEventToServer(String encodeData) throws IOException {
		Map<String, String> params = makeSendParams(encodeData, ReceiveAndSendDataType.ZqJingcaiJishiEvent);
		httpUtil.httpPost(sendUrl, params, null);
	}
	@Override
	public void sendFbLeagueToServer(String encodeData) throws IOException {
		Map<String, String> params = makeSendParams(encodeData, ReceiveAndSendDataType.ZqLeagueInfo);
		httpUtil.httpPost(sendUrl,params,null);
	}
	@Override
	public String receiveAllSeasonMessageSubLeague(Integer source, int leagueType) throws IOException {
		Map<String, String> params = makeSendParams(null, ReceiveAndSendDataType.fbSeasonMessSubLeague);
		params.put("source", String.valueOf(source));
		params.put("leagueType", String.valueOf(leagueType));
		return makeHttpResponse(params);
	}
	@Override
	public void sendFbSubLeagueToServer(Integer leagueType, String encodeData) throws IOException {
		Map<String, String> params = makeSendParams(encodeData, ReceiveAndSendDataType.fbSubLeague);
		params.put("leagueType", String.valueOf(leagueType));
		
		httpUtil.httpPost(sendUrl, params, null);
	}
	@Override
	public String receiveAllSeasonMessageNotCrawler(Integer source) throws IOException {
		// TODO Auto-generated method stub
		Map<String, String> params = makeSendParams(null, ReceiveAndSendDataType.queryAllSeasonMessNotCrawler);
		params.put("source", source.toString());
		return makeHttpResponse(params);
	}
	@Override
	public void sendFbMatchBaseMessToServer(int round, String encode, Integer seasonId) throws IOException {
		Map<String, String> params = makeSendParams(encode, ReceiveAndSendDataType.saveFbMatchList);
		params.put("round", String.valueOf(round));
		params.put("seasonId", String.valueOf(seasonId));
		httpUtil.httpPost(sendUrl, params, null);
	}
	@Override
	public String receiveAllCupGroupMessToCraw(Integer source, Integer cupType) throws IOException {
		Map<String, String> params = makeSendParams(null, ReceiveAndSendDataType.queryFbCupGroupMess);
		params.put("source", String.valueOf(source));
		params.put("cupType", String.valueOf(cupType));
		return makeHttpResponse(params);
	}
	@Override
	public String receiveNowSeasonNameByLeagueId(String leagueId) throws IOException {
		Map<String, String> params = makeSendParams(null, ReceiveAndSendDataType.queryLeagueNowSeason);
		params.put("leagueId", String.valueOf(leagueId));
		return makeHttpResponse(params);
	}
	@Override
	public void sendCupMatchToServer(String decodeData, Integer seasonId) throws IOException {
		Map<String, String> params = makeSendParams(decodeData, ReceiveAndSendDataType.saveFbCupMatchList);
		params.put("seasonId",String.valueOf(seasonId));
		httpUtil.httpPost(sendUrl, params, null);
	}
	@Override
	public String receiveAllZqMatchNotHaveLineup() throws IOException {
		Map<String, String> params = makeSendParams(null, ReceiveAndSendDataType.queryZqMatchNotHaveLineup);
		return makeHttpResponse(params);
	}
	/**
	 * @param params
	 * @return
	 * @throws IOException
	 */
	private String makeHttpResponse(Map<String, String> params)
			throws IOException {
		HttpResult httpResult = httpUtil.httpPost(receiveUrl, params, null);
		if(httpResult!=null&&httpResult.isSucc()){
			return httpResult.getResponseStr();
		}
		return null;
	}
	@Override
	public void sendMatchLineupDataToServer(String decodeData) throws IOException {
		Map<String, String> params = makeSendParams(decodeData, ReceiveAndSendDataType.saveFbMatchLineup);
		httpUtil.httpPost(sendUrl, params, null);
	}
	@Override
	public String receiveAllSeasonNotHaveRule(Integer source, int leagueType) throws IOException {
		Map<String, String> params = makeSendParams(null, ReceiveAndSendDataType.queryFbLeagueSeasonNotHaveRule);
		params.put("leagueType", String.valueOf(leagueType));
		return makeHttpResponse(params);
	}
	@Override
	public void sendLeagueScoreDataToServer(String scoreRuleJson,
			String seasonJson) throws IOException {
		Map<String, String> params = makeSendParams(scoreRuleJson, ReceiveAndSendDataType.saveFbLeagueScore);
		params.put("season", seasonJson);
		httpUtil.httpPost(sendUrl, params,null);
	}
	@Override
	public String receiveAllCupSeasonNotCrawler(int leagueType,
			int isSubLeague, Integer source) throws IOException {
		Map<String, String> params = makeSendParams(null, ReceiveAndSendDataType.queryAllCupSeasonNotCrawler);
		params.put("isSubLeague", String.valueOf(isSubLeague));
		params.put("source", String.valueOf(source));
		params.put("leagueType", String.valueOf(leagueType));
		HttpResult result = httpUtil.httpPost(receiveUrl, params, null);
		if(result!=null&&result.isSucc()){
			return result.getResponseStr();
		}
		return null;
	}
	@Override
	public void sendCupScoreDataToserver(String encodeData, String sesonEncode) throws IOException {
		Map<String, String> params = makeSendParams(encodeData, ReceiveAndSendDataType.saveCupScoreData);
		params.put("season", sesonEncode);
		
		httpUtil.httpPost(sendUrl, params, null);
	}
	@Override
	public void sendFbMatchBaseMessToServer(String encode) throws IOException {
		Map<String, String>  params = makeSendParams(encode,ReceiveAndSendDataType.updateFbAllJishiMatchMess);
		httpUtil.httpPost(sendUrl, params, null);
	}
	@Override
	public void sendLqJishiMatchBaseMessToServer(String encode) throws IOException {
		Map<String, String> params = makeSendParams(encode, ReceiveAndSendDataType.updateLqAllJishiMatchMess);
		httpUtil.httpPost(sendUrl, params, null);
	}
	@Override
	public void sendBbLeagueToServer(String encode) throws IOException {
		Map<String, String> params = makeSendParams(encode, ReceiveAndSendDataType.saveBbLeagueSeason);
		httpUtil.httpPost(sendUrl, params, null);
	}
	@Override
	public String receiveLqSubLeague(int leagueType, int isSubLeague, int source) throws IOException {
		Map<String, String> params = makeSendParams(null, ReceiveAndSendDataType.queryLqSubLeagueNotCrawler);
		params.put("leagueType", String.valueOf(leagueType));
		params.put("isSubLeague", String.valueOf(isSubLeague));
		params.put("source", String.valueOf(source));
		HttpResult result = httpUtil.httpPost(receiveUrl, params, null);
		if(result!=null&&result.isSucc()){
			return result.getResponseStr();
		}
		return null;
	}
	@Override
	public void sendLqHistoryMatchToServer(String ballMatchEncodeData,
			String seasonEncodeData) throws IOException {
		Map<String, String> params = makeSendParams(ballMatchEncodeData, ReceiveAndSendDataType.saveLqLeagueMatchMessage);
		params.put("season",seasonEncodeData);
		httpUtil.httpPost(sendUrl, params, null);
	}
	@Override
	public String receiveLqLeagueNotSub(Integer leagueType, Integer source) throws IOException {
		Map<String, String> params = makeSendParams(null, ReceiveAndSendDataType.queryLqLeagueNotSubByType);
		params.put("leagueType", String.valueOf(leagueType));
		params.put("source", String.valueOf(source));
		HttpResult result = httpUtil.httpPost(receiveUrl, params, null);
		if(result!=null&&result.isSucc()){
			return result.getResponseStr();
		}
		return null;
	}
	@Override
	public void sendLqCupGroupToServer(String jsonData, Integer cupType) throws IOException {
		Map<String, String> params = makeSendParams(jsonData, ReceiveAndSendDataType.saveLqCupGroup);
		params.put("leagueType", String.valueOf(cupType));
		httpUtil.httpPost(sendUrl, params, null);
		
	}
	@Override
	public void sendLqSubLeagueToServer(String jsonData) throws IOException {
		Map<String, String> params = makeSendParams(jsonData, ReceiveAndSendDataType.saveLqSubLeague);
		httpUtil.httpPost(sendUrl, params, null);
	}
	@Override
	public void sendCupMatchToServer(String encodeLeaugeScoreAndMatch,
			String encodeSeason) throws IOException {
		Map<String, String> params = makeSendParams(encodeLeaugeScoreAndMatch, ReceiveAndSendDataType.saveLqCupMatchAndCupScore);
		params.put("season", encodeSeason);
		httpUtil.httpPost(sendUrl, params, null);
	}
	@Override
	public String receiveAllBasketMathcInlive() throws IOException {
		Map<String, String> params = makeSendParams(null, ReceiveAndSendDataType.queryAllJingcaiBasketMathcInlive);
		HttpResult result = httpUtil.httpPost(receiveUrl, params, null);
		if(result!=null&&result.isSucc()){
			return result.getResponseStr();
		}
		return null;
	}
	@Override
	public void sendBasketMatchPlayerStatisticDataToServer(String encodeString) throws IOException {
		// TODO Auto-generated method stub
		Map<String, String> params = makeSendParams(encodeString, ReceiveAndSendDataType.saveLqMatchPlayerStatisticData);
		httpUtil.httpPost(sendUrl, params,null);
	}
	@Override
	public void sendMatchTeamStatisticDataToServer(String encodeString) throws IOException {
		Map<String, String> params = makeSendParams(encodeString, ReceiveAndSendDataType.saveLqMatchTeamStatistic);
		httpUtil.httpPost(sendUrl, params,null);
	}
	@Override
	public String gotAllJingcaiLqMatchNotStart() throws IOException {
		Map<String, String> params = makeSendParams(null, ReceiveAndSendDataType.queryAllJingcaiLqMatchNotStart);
		HttpResult httpResult = httpUtil.httpPost(receiveUrl, params,null);
		if(httpResult!=null&&httpResult.isSucc()){
			return httpResult.getResponseStr();
		}
		return null;
		
	}
	@Override
	public void sendLqJishiOddsToServer(String matchBaseData, String oddsData,
			Qt_fb_match_oddsType oddsType) throws IOException {
		Map<String, String> params = makeSendParams(oddsData, ReceiveAndSendDataType.saveLqJishiOdds);
		params.put("oddsType", oddsType.toString());
		params.put("lqMatchBase", matchBaseData);
		httpUtil.httpPost(sendUrl, params, null);
	}
	@Override
	public String receiveAllZqMatchInMatchNotHaveLiveUrl() throws IOException {
		Map<String, String> params = makeSendParams(null, ReceiveAndSendDataType.queryAllZqMatchInMatchNotHaveLiveUrl);
		HttpResult httpResult = httpUtil.httpPost(receiveUrl, params, null);
		if(httpResult!=null&&httpResult.isSucc()){
			return httpResult.getResponseStr();
		}
		return null;
	}
	@Override
	public void sendZqLiveUrlToServer(String encodeData, long id) throws IOException {
		Map<String, String> params = makeSendParams(encodeData, ReceiveAndSendDataType.saveZqTelevisonLiveUrl);
		params.put("id", String.valueOf(id));
		httpUtil.httpPost(sendUrl, params, null);
	}
	@Override
	public String receiveJingcaiZqMatchNotStart() throws IOException {
		Map<String, String> params = makeSendParams(null, ReceiveAndSendDataType.queryJingcaiZqMatchNotStart);
		HttpResult httpResult = httpUtil.httpPost(receiveUrl, params, null);
		if(httpResult!=null&&httpResult.isSucc()){
			return httpResult.getResponseStr();
		}
		return null;
	}
	@Override
	public void sendFbMatchOpOneCompanyOddsToServer(String fbMatchData,
			CrawlerFbZqCompanyOddsHandle zqCompanyOddsHandle) throws IOException {
		Map<String, String> params = makeSendParams(fbMatchData, ReceiveAndSendDataType.saveFbMatchOpOneCompany);
		params.put("bsId",zqCompanyOddsHandle.getBsId().toString());
		params.put("companyId", zqCompanyOddsHandle.getCompanyId());
		
		httpUtil.httpPost(sendUrl, params, null);
	}
	
}
