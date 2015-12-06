package com.xhcms.lottery.dc.task;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xhcms.commons.job.Job;
import com.xhcms.lottery.commons.data.SsqInfo;
import com.xhcms.lottery.dc.core.DataStore;

public class SsqIssueInfoTask extends Job{

	private Logger logger=LoggerFactory.getLogger(getClass());
	// 沿用星汇的异步保存数据机制。
	private DataStore dataStore;
	private String lotteryId;
	private String storeDataName;
	private static ScriptEngine engine;
	static {
		ScriptEngineManager factory = new ScriptEngineManager(); 
		engine = factory.getEngineByName ("JavaScript"); 
	}
	@Override
	protected void execute() throws Exception {
		logger.info("开始查询双色球期信息...");
		if (StringUtils.isBlank(storeDataName)){
			throw new IllegalStateException("storeDataName不能为空!");
		}
		HttpClient client = new DefaultHttpClient();
		String url = "http://www.zhcw.com/kaijiang/kjData/2012/zhcw_ssq_index_last30.js";
		try {
			HttpGet httpGet = new HttpGet(url);
			HttpResponse response = client.execute(httpGet);
			HttpEntity respEntity = response.getEntity();
			String charset = "utf-8";
			String respText = EntityUtils.toString(respEntity, charset);
			logger.info(respText);
			engine.eval(respText);
			String issueNos_1 = engine.get("issueNos_1").toString();
			String[] iss = issueNos_1.split(",");
			List<SsqInfo> ssqInfos = new ArrayList<SsqInfo>();
			for (int i = 0; i < iss.length; i++) {
				StringBuffer s  = new StringBuffer();
				s.append("var kj = kjData_1[" + iss[i] +"];");
				s.append("var zj = zjData_1[" + iss[i] +"];");
				s.append("var qNum = zj.QNum;");
				s.append("var benqi = zj.tzMoney.toLocaleString().replace(\".00\",\"\");");
				s.append("var ydj =  zj.oneJ.toLocaleString().replace(\".00\",\"\");");
				s.append("var leiji = zj.jcMoney.toLocaleString().replace(\".00\",\"\");");
				engine.eval(s.toString());
				String qNum = engine.get("qNum").toString();
				String benqi = engine.get("benqi").toString();
				String leiji =engine.get("leiji").toString();
				String ydj =engine.get("ydj").toString();
				SsqInfo ssqInfo = new SsqInfo();
				ssqInfo.setIssueNumber(iss[i]);
				ssqInfo.setLotteryId("SSQ");
				ssqInfo.setJackpot(BigDecimal.valueOf(Double.valueOf(leiji)));
				ssqInfo.setTotalSales(BigDecimal.valueOf(Double.valueOf(benqi)));
				ssqInfo.setYdjBouns(BigDecimal.valueOf(Double.valueOf(ydj)));
				ssqInfo.setUsedBallNum(Integer.valueOf(qNum));
				ssqInfos.add(ssqInfo);
			}
			this.storeData(ssqInfos);
		} catch (Exception e) {
			logger.error("不能抓取页面地址：{}", url, e);
		} finally{
			client.getConnectionManager().shutdown();
		}
	}
	
	private void storeData(List<SsqInfo> ssqInfos) {
		this.dataStore.put(storeDataName, ssqInfos);
	}
	
	public String getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}

	public String getStoreDataName() {
		return storeDataName;
	}

	public void setStoreDataName(String storeDataName) {
		this.storeDataName = storeDataName;
	}

	public DataStore getDataStore() {
		return dataStore;
	}

	public void setDataStore(DataStore dataStore) {
		this.dataStore = dataStore;
	}
}
