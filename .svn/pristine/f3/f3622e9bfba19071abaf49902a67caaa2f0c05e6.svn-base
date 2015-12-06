package com.xhcms.lottery.dc.task;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.unison.lottery.mc.uni.ZMClient;
import com.unison.lottery.mc.uni.client.QueryIssueClient;
import com.unison.lottery.mc.uni.parser.QueryIssueResponseParserStatus;
import com.xhcms.commons.job.Job;
import com.xhcms.lottery.commons.data.IssueInfo;
import com.xhcms.lottery.commons.persist.entity.IssueInfoPO;
import com.xhcms.lottery.commons.persist.service.IssueService;
import com.xhcms.lottery.dc.core.DataStore;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.lang.LotteryIdForZM;

public class QueryIssueinfoTask extends Job{
	
	// 沿用星汇的异步保存数据机制。
	private DataStore dataStore;
	// 接口访问客户端
	private ZMClient zmClient;

	private String storeDataName;
	
	@Autowired
	private IssueService issueService;
	
	private Logger logger=LoggerFactory.getLogger(getClass());
	
	//待查询的彩种id列表
	private List<String> lotteryIds;
	int maxIssueNumber = 0;

	@Override
	protected void execute() throws Exception {
		if (StringUtils.isBlank(storeDataName)){
			throw new IllegalStateException("storeDataName不能为空!");
		}
		logger.debug("开始调用期查询接口...");
		QueryIssueClient client = (QueryIssueClient) getZmClient();
		List<String> oldIssueNumbers = new ArrayList<String>();
		
		
		if(null!=lotteryIds&&!lotteryIds.isEmpty()){
			for(String lotteryId:lotteryIds){
				oldIssueNumbers = new ArrayList<String>();
				findOldCTZCIssueInfos(lotteryId, oldIssueNumbers);
				findOldWFIssueInfo(lotteryId, oldIssueNumbers);
				//北京单场
				findOldBJDCIssueInfos(lotteryId, oldIssueNumbers);
				//北单胜负
				findOldBDSFIssueInfos(lotteryId, oldIssueNumbers);
				queryIssueinfos(client, oldIssueNumbers, lotteryId);
			}
		}
	}

	/**
	 * 查询当前期与已过期的期信息
	 * @param client
	 * @param oldIssueNumbers
	 * @param lotteryId
	 */
	private void queryIssueinfos(QueryIssueClient client,List<String> oldIssueNumbers,String lotteryId){
		/**查询当前期信息*/ 
		handleWithOneLotteryId(client,"", lotteryId);
		/**查询旧期信息*/
		for(String oldIssueNumber:oldIssueNumbers){
			handleWithOneLotteryId(client,oldIssueNumber, lotteryId);
		}
		/**查询预售期*/
		//北京单场 暂时不查预售期
		if(!("SPF".equals(lotteryId)||"SF".equals(lotteryId))){
			if(maxIssueNumber>0){
				for(int i=0;i<2;i++){
					handleWithOneLotteryId(client,String.valueOf(++maxIssueNumber), lotteryId);
				}
			}
			
		}
		
	}
	
	/**
	 * 查询传统足彩过期期信息
	 * @param lotteryId
	 * @param oldIssueNumbers
	 */
	private void findOldCTZCIssueInfos(String lotteryId,List<String> oldIssueNumbers){
		if(StringUtils.isBlank(lotteryId)){
			return;
		}
		if(LotteryIdForZM.isCTZCLotteryId(lotteryId)){
			maxIssueNumber = 0;
			String playId = LotteryIdForZM.getLcPlayTypeFromZmLotteryId(lotteryId).getPlayId();
			List<IssueInfoPO> issueInfoPOs = issueService.findOldIssuesWithStatusNotEQAWARD(playId);
			for(IssueInfoPO issuepo:issueInfoPOs){
				String issueNumbet = issuepo.getIssueNumber();
				oldIssueNumbers.add(issueNumbet);
				maxIssueNumber = Integer.parseInt(issueNumbet)<maxIssueNumber?maxIssueNumber:Integer.parseInt(issueNumbet);
			}
		}
	}
	/*
	 * 北京单场
	 */
	private void findOldBJDCIssueInfos(String lotteryId,List<String> oldIssueNumbers){
		if(StringUtils.isBlank(lotteryId)){
			return;
		}
		if(LotteryIdForZM.isBJDCLotteryId(lotteryId)){
			maxIssueNumber = 0;
			String playId = LotteryIdForZM.getLcPlayTypeFromZmLotteryId(lotteryId).getPlayId();
			List<IssueInfoPO> issueInfoPOs = issueService.findOldIssuesWithStatusNotEQAWARD(playId);
			for(IssueInfoPO issuepo:issueInfoPOs){
				String issueNumbet = issuepo.getIssueNumber();
				oldIssueNumbers.add(issueNumbet);
				maxIssueNumber = Integer.parseInt(issueNumbet)<maxIssueNumber?maxIssueNumber:Integer.parseInt(issueNumbet);
			}
		}
	}
	/**
	 * 北单胜负
	 * @param lotteryId
	 * @param oldIssueNumbers
	 */
	private void findOldBDSFIssueInfos(String lotteryId,List<String> oldIssueNumbers){
		if(StringUtils.isBlank(lotteryId)){
			return;
		}
		if(LotteryIdForZM.isBDSFLotteryId(lotteryId)){
			maxIssueNumber = 0;
			String playId = LotteryIdForZM.getLcPlayTypeFromZmLotteryId(lotteryId).getPlayId();
			List<IssueInfoPO> issueInfoPOs = issueService.findOldIssuesWithStatusNotEQAWARD(playId);
			for(IssueInfoPO issuepo:issueInfoPOs){
				String issueNumbet = issuepo.getIssueNumber();
				oldIssueNumbers.add(issueNumbet);
				maxIssueNumber = Integer.parseInt(issueNumbet)<maxIssueNumber?maxIssueNumber:Integer.parseInt(issueNumbet);
			}
		}
	}
	
	/**
	 * 查询福彩过期期信息
	 * @param lotteryId
	 * @param oldIssueNumbers
	 */
	private void findOldWFIssueInfo(String lotteryId,List<String> oldIssueNumbers){
		if(StringUtils.isBlank(lotteryId)){
			return;
		}
		String zmLotteryId = null;
		if(LotteryIdForZM.isFC3DLotteryId(lotteryId)) {
			zmLotteryId = lotteryId;
			lotteryId = LotteryId.FC3D.name();
		}
		if(LotteryIdForZM.isSSQLotteryId(lotteryId) || LotteryIdForZM.isFC3DLotteryId(zmLotteryId)) {
			List<IssueInfoPO> issueInfoPOs = issueService.findOldIssuesByLottery(lotteryId);
			for(IssueInfoPO issuepo:issueInfoPOs){
				String issueNumbet = issuepo.getIssueNumber();
				oldIssueNumbers.add(issueNumbet);
			}
		}
	}

	private void handleWithOneLotteryId(QueryIssueClient client,
			String issueNumber, String lotteryId) {
		logger.debug("开始查询彩种id为{}的期信息...",lotteryId);
		QueryIssueResponseParserStatus queryIssueResponseParserStatus = new QueryIssueResponseParserStatus();
		if(client.postWithStatus(lotteryId,issueNumber,queryIssueResponseParserStatus)){
			if(null!=queryIssueResponseParserStatus
					&&null!=queryIssueResponseParserStatus.getIssueinfos()
					&&!queryIssueResponseParserStatus.getIssueinfos().isEmpty()){
					storeData(queryIssueResponseParserStatus.getIssueinfos());
			}
		}
	}

	private void storeData(List<IssueInfo> issueinfos) {
		this.dataStore.put(storeDataName, issueinfos);
	}

	public ZMClient getZmClient() {
		return zmClient;
	}

	public void setZmClient(ZMClient zmClient) {
		this.zmClient = zmClient;
	}

	public DataStore getDataStore() {
		return dataStore;
	}

	public void setDataStore(DataStore dataStore) {
		this.dataStore = dataStore;
	}

	public String getStoreDataName() {
		return storeDataName;
	}

	public void setStoreDataName(String storeDataName) {
		this.storeDataName = storeDataName;
	}

	public List<String> getLotteryIds() {
		return lotteryIds;
	}

	public void setLotteryIds(List<String> lotteryIds) {
		this.lotteryIds = lotteryIds;
	}

}
