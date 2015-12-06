package com.davcai.lottery.platform.client.qiutan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.velocity.app.event.ReferenceInsertionEventHandler.referenceInsertExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.davcai.lottery.platform.client.ILotteryPlatformFetchIssueInfoClient;
import com.davcai.lottery.platform.client.LotteryPlatformBaseResponse;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.lang.LotteryIdForZM;
import com.xhcms.lottery.lang.PlayType;

/**
 * 球探传统足彩期信息
 * @author haoxiang.jiang@davcai.com
 * @date 2015年4月13日 下午5:40:01
 */
@Service
public class QiutanCTZCIssueInfoClient extends QiutanBaseClient implements ILotteryPlatformFetchIssueInfoClient{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private static Map<String, String> playTypeUrlMap;
	static {
		playTypeUrlMap = new HashMap<String, String>();
		playTypeUrlMap.put("24_ZC_14", "http://apk.310win.com/default.aspx?client=1&transcode=105&version=3.0&msg=%7b%22typeid%22:%221%22,%22datatype%22:%221%22%7d&baseinfoversion=1_3.0%7cG1H5I1J1K1L1M1N1O1P1Q1R1S1&key=299fc80d260d971ad6c9fab6cef8c8ae");
		playTypeUrlMap.put("25_ZC_R9", "http://apk.310win.com/default.aspx?client=1&transcode=105&version=3.0&msg=%7b%22typeid%22:%222%22,%22datatype%22:%221%22%7d&baseinfoversion=1_3.0%7cG1H5I1J1K1L1M1N1O1P1Q1R1S1&key=918bcb2b60eb1dbdaaf73998863b8e8d");
		playTypeUrlMap.put("26_ZC_BQ", "http://apk.310win.com/default.aspx?client=1&transcode=105&version=3.0&msg=%7b%22typeid%22:%223%22,%22datatype%22:%221%22%7d&baseinfoversion=1_3.0%7cG1H5I1J1K1L1M1N1O1P1Q1R1S1&key=887ee6954c0250a99c89d8eab3f5142d");
		playTypeUrlMap.put("27_ZC_JQ", "http://apk.310win.com/default.aspx?client=1&transcode=105&version=3.0&msg=%7b%22typeid%22:%224%22,%22datatype%22:%221%22%7d&baseinfoversion=1_3.0%7cG1H5I1J1K1L1M1N1O1P1Q1R1S1&key=c29195744d80192904a90522e75128d8");
	}
	
	/**
	 * 根据玩法判断请求链接，发送请求，获取响应，转换成LotteryPlatformBaseResponse返回
	 */
	@Override
	public LotteryPlatformBaseResponse postByPlayType(PlayType playType) {
		QiutanCTZCIssueInfo qiutanIssueInfo = new QiutanCTZCIssueInfo();
		LotteryId l = playType.getLotteryId();
		String ln = l.name();
		if(LotteryId.CTZC.name().equals(playType.getLotteryId().name())){//是传统足彩
			this.setUrl(playTypeUrlMap.get(playType.getPlayId()));//设置url
			return doGetDirect(null);
		} else {
			logger.info("参数错误！playType必须是传统足彩玩法，实际playType={}",playType.getPlayName());
			return qiutanIssueInfo;
		}
	}
	
	@Override
	protected LotteryPlatformBaseResponse parseResponse(String responseStr) {
		JSONObject jsonObject = JSONObject.fromObject(responseStr);
		JSONObject result = jsonObject.getJSONObject("result");
		QiutanCTZCIssueInfo qiutanCTZCIssueInfo = new QiutanCTZCIssueInfo();
		if(null != result){
			JSONArray issuelist = result.getJSONArray("issuelist");
			if(null != issuelist && issuelist.size() > 0){
				JSONObject issueinfo = (JSONObject)issuelist.get(0);
				qiutanCTZCIssueInfo.setIssueid(issueinfo.getString("issueid"));
				qiutanCTZCIssueInfo.setIssuenum(issueinfo.getString("issuenum"));
				qiutanCTZCIssueInfo.setM_stoptime(issueinfo.getString("m_stoptime"));
				JSONObject laskKaiJiangInfo = result.getJSONObject("lastkaijianginfo");
				if(null!=laskKaiJiangInfo){
					qiutanCTZCIssueInfo.setPre_issuenum(laskKaiJiangInfo.getString("issuenum"));
					qiutanCTZCIssueInfo.setPre_bonusinfo(laskKaiJiangInfo.getString("result"));
				}
				List<QiutanCTZCMatch> matchList = new ArrayList<QiutanCTZCMatch>();
				JSONArray schedulelist = result.getJSONArray("schedulelist");
				for (int i = 0; i < schedulelist.size(); i++) {
					JSONObject matchinfo = (JSONObject)schedulelist.get(i);
					QiutanCTZCMatch qiutanCTZCMatch = new QiutanCTZCMatch();
					qiutanCTZCMatch.setColor(matchinfo.getString("color"));
					qiutanCTZCMatch.setGuestteam(matchinfo.getString("guestteam"));
					qiutanCTZCMatch.setGuestteamf(matchinfo.getString("guestteamf"));
					qiutanCTZCMatch.setHometeam(matchinfo.getString("hometeam"));
					qiutanCTZCMatch.setHometeamf(matchinfo.getString("hometeamf"));
					qiutanCTZCMatch.setMatchid(matchinfo.getString("matchid"));
					qiutanCTZCMatch.setMatchtime(matchinfo.getString("matchtime"));
					qiutanCTZCMatch.setScheduleid(matchinfo.getString("scheduleid"));
					qiutanCTZCMatch.setSclass(matchinfo.getString("sclass"));
					matchList.add(qiutanCTZCMatch);
				}
				qiutanCTZCIssueInfo.setCtzcMatchs(matchList);
			}
		}
		return qiutanCTZCIssueInfo;
	}
}
