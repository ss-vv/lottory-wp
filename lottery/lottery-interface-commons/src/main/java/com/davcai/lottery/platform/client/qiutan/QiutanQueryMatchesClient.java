package com.davcai.lottery.platform.client.qiutan;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.davcai.lottery.platform.client.ILotteryPlatformQueryMatcheOddsClient;
import com.davcai.lottery.platform.client.ILotteryPlatformQueryMatchesClient;
import com.davcai.lottery.platform.client.LotteryPlatformBaseResponse;
import com.davcai.lottery.platform.client.anruizhiying.constants.AnRuiZhiYingTransCode;
import com.davcai.lottery.platform.client.anruizhiying.model.AnRuiZhiYingJCMatchModel;
import com.davcai.lottery.platform.client.anruizhiying.model.AnRuiZhiYingJCMatchesResponse;
import com.davcai.lottery.platform.client.anruizhiying.model.AnRuiZhiYingResponse;
import com.davcai.lottery.platform.client.anruizhiying.parser.IAnRuiZhiYingRespParser;
import com.xhcms.lottery.commons.utils.MakeQiutanURLKeyUtil;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.utils.BeanUtilsTools;

/**
 * @author haoxiang.jiang@davcai.com
 * @date 2015年1月9日 下午6:19:45
 */
@Service
public class QiutanQueryMatchesClient extends QiutanBaseClient 
		implements ILotteryPlatformQueryMatchesClient,ILotteryPlatformQueryMatcheOddsClient{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private static String JCZQ_URL="http://apk.310win.com/default.aspx?client=%s&transcode=%s&version=%s&msg=%s&baseinfoversion=%s&key=%s";
	
	@SuppressWarnings("deprecation")
	private static String getPageUrl(String url,String matchCode,String kind) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		String matchid = "";
		if(StringUtils.isNotBlank(matchCode)){
			matchid = "\"matchid\":\""+matchCode+"\",";
		}
		String msg = "{"+matchid+"\"kind\":\""+kind+"\",\"typeid\":\"100\",\"datatype\":\"1\"}";
		String client = "1";
		String transcode = "105";
		String version = "3.0";
		String baseinfoversion = "1_3.0|G1H5I1J1K1L1M1N1O1P1Q1R1S1";
		String key = MakeQiutanURLKeyUtil.makeClientKey(msg, baseinfoversion, client, transcode, version);
		return String.format(url, client,transcode,version,
				URLEncoder.encode(msg),URLEncoder.encode(baseinfoversion),key);
	}
	
	public QiutanQueryMatchesClient(){
	}
	
	@Override
	public LotteryPlatformBaseResponse getOddsByLotteryId(String lotteryId) {
		return postByMatchType(lotteryId);
	}
	/**
	 * 查询在售比赛信息 
	 */
	@Override
	public LotteryPlatformBaseResponse postByMatchType(String matchType) {
		QiutanJCMatchInfo ret = new QiutanJCMatchInfo();
    	if (!typeIsValid(matchType)){
    		logger.error("球探竞彩足球赛程查询客户端不支持的赛事类型,type=" + matchType);
    		return ret;
    	}
    	QiutanJCMatchInfo q1 = new QiutanJCMatchInfo();
    	QiutanJCMatchInfo q2 = new QiutanJCMatchInfo();
		try {
			q1 = getQiutanJCMatchInfo(matchType,"1");
			q2 = getQiutanJCMatchInfo(matchType,"2");
			HashMap<String, QiutanJCMatch> map1 = new HashMap<String, QiutanJCMatch>(); 
			for (QiutanJCMatch match : q1.getMatches()) {
				map1.put(match.getId(), match);
			}
			for (QiutanJCMatch match : q2.getMatches()) {
				QiutanJCMatch match1 = map1.get(match.getId());
				if(null != match1){
					match.setHidetypeid(match1.getHidetypeid());//把不支持单关的玩法放到q2的match中
				}
			}
			return q2;
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			logger.error("",e);
			return q1;
		}
	}
	
	private QiutanJCMatchInfo getQiutanJCMatchInfo(String matchType,String kind) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		String url = "";
		if(LotteryId.JCZQ.name().equals(matchType)){
			url = JCZQ_URL;
			this.setUrl(getPageUrl(url, "", kind));
    	} else if(LotteryId.JCLQ.name().equals(matchType)){
    		this.setUrl(url);
    	}
		QiutanJCMatchInfo qiutanJCMatchInfo = new QiutanJCMatchInfo();
		LotteryPlatformBaseResponse resp = this.doGetDirect(null);
		QiutanJCMatchInfo q = (QiutanJCMatchInfo)resp;
		while(null != q.getMatches()  && q.getMatches().size() > 0){
			qiutanJCMatchInfo.getMatches().addAll(q.getMatches());
			QiutanJCMatch qiutanJCMatch = q.getMatches().get(q.getMatches().size()-1);
			this.setUrl(getPageUrl(url,qiutanJCMatch.getMatchid(), kind));
			resp = this.doGetDirect(null);
			q = (QiutanJCMatchInfo)resp;
		}
		return qiutanJCMatchInfo;
	}

	private boolean typeIsValid(String type) {
		String[] validTypes = new String[] {"JCLQ","JCZQ"};
		for (String theType : validTypes) {
			if (theType.equals(type)){
				return true;
			}
		}
		return false;
	}

	@Override
	protected LotteryPlatformBaseResponse parseResponse(String responseStr) {
		JSONObject jsonObject = JSONObject.fromObject(responseStr);
		JSONObject result = jsonObject.getJSONObject("result");
		QiutanJCMatchInfo qiutanJCMatchInfo = new QiutanJCMatchInfo();
		if(null != result){
			JSONArray schedulelist = result.getJSONArray("schedulelist");
			for (int i = 0; i < schedulelist.size(); i++) {
				JSONObject match = schedulelist.getJSONObject(i);
				QiutanJCMatch q = (QiutanJCMatch)JSONObject.toBean(match, QiutanJCMatch.class);
				qiutanJCMatchInfo.getMatches().add(q);
			}
		}
		return qiutanJCMatchInfo;
	}
}
