package com.unison.lottery.weibo.dataservice.scraper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.unison.lottery.weibo.dataservice.commons.model.MatchHistory;
import com.unison.lottery.weibo.dataservice.parse.JsUtils;

@Service
public class MatchInfoScraperImpl implements MatchInfoScraper {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	
	@Override
	public MatchHistory scrapeMatchInfo(String matchId) {
		String url = String.format("http://www.310win.com/analysis/%s.htm", matchId);
		HttpClient client = new DefaultHttpClient();
		try {
			HttpGet httpGet = new HttpGet(url);
			HttpResponse response = client.execute(httpGet);
			HttpEntity respEntity = response.getEntity();
			String charset = "utf-8";
			String respText = EntityUtils.toString(respEntity, charset);
			return parseMatchHistory(respText);
		} catch (Exception e) {
			logger.error("不能抓取页面地址：{}", url, e);
		} finally{
			client.getConnectionManager().shutdown();
		}
		return null;
	}

	private MatchHistory parseMatchHistory(String respText) {
		MatchHistory matchHistory = new MatchHistory();
		
		// 截取"<script>"段
		String script = parseScript(respText);
		
		// 解析 matchId
		matchHistory.setMatchId(parseMatchId(script));
		
		// 解析 sclassID
		matchHistory.setSclassID(parseSclassId(script));
		
		// 解析 homeTeamId
		matchHistory.setHomeTeamId(parseHomeTeamId(script));
		
		// 解析 guestTeamId
		matchHistory.setGuestTeamId(parseGuestTeamId(script));
		
		// 解析 homeTeamName
		matchHistory.setHomeTeamName(parseHomeTeamName(script));
		
		// 解析 guestTeamName
		matchHistory.setGuestTeamName(parseGuestTeamName(script));
		
		// 解析对赛往绩
		matchHistory.setAgainstHistory(parseAgainstHistory(script));
		
		// 解析主队近期战绩
		matchHistory.setHostNearScore(parseHostNearScore(script));

		// 解析客队近期战绩
		matchHistory.setGuestNearScore(parseGuestNearScore(script));
		
		// 解析欧赔
		matchHistory.setOpOdds(parseOpOdds(script));
		
		// 解析亚赔
		matchHistory.setYpOdds(parseYpOdds(script));
		
		// 解析竞彩赔率
		matchHistory.setJcOdds(parseJcOdds(script));
		
		return matchHistory;
	}
		private Object[][] parseJcOdds(String script) {
		return parseDoubleArray(script, "jcOddsData");
	}

	private Object[][] parseYpOdds(String script) {
		return parseDoubleArray(script, "ypData");
	}

	private Object[][] parseOpOdds(String script) {
		return parseDoubleArray(script, "opData");
	}

	// 客队近期战绩
	private Object[][] parseGuestNearScore(String script) {
		return parseDoubleArray(script, "a_data");
	}

	// 主队近期战绩
	private Object[][] parseHostNearScore(String script) {
		return parseDoubleArray(script, "h_data");
	}

	// 对赛往绩，v_data 数组
	private Object[][] parseAgainstHistory(String script) {
		return parseDoubleArray(script, "v_data");
	}

	private Object[][] parseDoubleArray(String script, String variableName) {
		Pattern pt = Pattern.compile("var\\s+"+variableName+"=.*?;");
		Matcher matcher = pt.matcher(script);
		if (matcher.find()){
			String arrayScript = matcher.group();
			ScriptEngineManager mgr = new ScriptEngineManager(); 
			ScriptEngine engine = mgr.getEngineByName("JavaScript");
			try {
			     engine.eval(arrayScript);
			     Bindings bindings = engine.getBindings(ScriptContext.ENGINE_SCOPE);
			     return JsUtils.nativeArrayTo2DimArray(bindings.get(variableName));
			} catch (ScriptException e) {
			     logger.error("不能执行JS！\n{}", script);
			}
		}else{
			logger.error("不能解析变量：{}, script:\n{}", variableName, script);
		}
		return null;
	}
	
	private String parseGuestTeamName(String script) {
		Pattern pt = Pattern.compile("guestteam.*?=.*?\"(.*?)\".*?;");
		return regFind(pt, script, "不能解析出 guestTeamName!");
	}

	private String parseHomeTeamName(String script) {
		Pattern pt = Pattern.compile("hometeam.*?=.*?\"(.*?)\".*?;");
		return regFind(pt, script, "不能解析出 homeTeamName!");
	}

	private String parseGuestTeamId(String script) {
		Pattern pt = Pattern.compile("h2h_away.*?=.*?(\\d+).*?;");
		return regFind(pt, script, "不能解析出 guestTeamId!");
	}

	private String parseHomeTeamId(String script) {
		Pattern pt = Pattern.compile("h2h_home.*?=.*?(\\d+).*?;");
		return regFind(pt, script, "不能解析出 homeTeamId!");
	}

	private String parseSclassId(String script) {
		Pattern pt = Pattern.compile("sclassID.*?=.*?(\\d+).*?;");
		
		return regFind(pt, script, "不能解析出 sclassID!");
	}

	private String parseScript(String respText) {
		Pattern pt = Pattern.compile(
				"<script language=\"javascript\">(.*?h_data.*?)</script>", 
				Pattern.MULTILINE|Pattern.DOTALL);
		
		return regFind(pt, respText, "页面中没能找到比赛历史script!");
	}

	private String parseMatchId(String script){
		Pattern pt = Pattern.compile("scheduleID.*?=.*?(\\d+).*?;");
		return regFind(pt, script, "不能解析出 matchId(scheduleId)!");
	}
	
	private String regFind(Pattern pt, String script, String errorMsg){
		Matcher matcher = pt.matcher(script);
		if (matcher.find()){
			return matcher.group(1);
		}else{
			logger.info("{}\n{}", errorMsg, script);
			return "";
		}
	}
}
