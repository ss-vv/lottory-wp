package com.xhcms.lottery.dc.platform.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.davcai.lottery.platform.client.ILotteryPlatformFetchIssueInfoClient;
import com.davcai.lottery.platform.client.LotteryPlatformBaseResponse;
import com.davcai.lottery.platform.client.qiutan.QiutanCTZCIssueInfo;
import com.xhcms.lottery.commons.data.CTFBMatch;
import com.xhcms.lottery.commons.data.IssueInfo;
import com.xhcms.lottery.dc.platform.parser.IssueInfoStoreParser;
import com.xhcms.lottery.dc.platform.parser.QiutanCTZCStoreParse;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.lang.PlayType;

public class FetchIssueInfoWithMultiPlatformTask extends BaseTaskWithMultiPlatform{
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	//暂时没有使用多平台特性，使用spring直接注入
	@Autowired
	private ILotteryPlatformFetchIssueInfoClient fetchIssueInfoClient;
	private List<String> playTypes;
	
	@Autowired
	private IssueInfoStoreParser issueInfoStoreParser;
	@Autowired
	private QiutanCTZCStoreParse qiutanCTZCStoreParse;
	@Override
	protected void execute() throws Exception {
		List<IssueInfo> list=null;
		for (String pt : playTypes) {
			logger.info("开始执行");
			LotteryPlatformBaseResponse response = fetchIssueInfoClient.postByPlayType(PlayType.valueOfLcPlayId(pt));
			if(response instanceof QiutanCTZCIssueInfo){
				list = issueInfoStoreParser.parseToIssueInfo(response, "",PlayType.valueOfLcPlayId(pt));//LotteryId.CTZC
				List<CTFBMatch> ctfbList=qiutanCTZCStoreParse.parseQiuTanCTZTToCTFBMatch(response, PlayType.valueOfLcPlayId(pt));
				//TODO 按照原有逻辑存list
				putIssueInfo(list);
				putMatchInfo(ctfbList);
			}
		}
		issueInfoStoreParser.checkIsExistsIssueInfo(list);
	}
	//期信息
	private void putIssueInfo(List<IssueInfo> list){
		this.storeData("query_issueinfo_result", list);
	}
	//赛程
	private void putMatchInfo(List<CTFBMatch> list){
		this.storeData("ct_zc_match", list);
	}
	public List<String> getPlayTypes() {
		return playTypes;
	}
	public void setPlayTypes(List<String> playTypes) {
		this.playTypes = playTypes;
	}
}
