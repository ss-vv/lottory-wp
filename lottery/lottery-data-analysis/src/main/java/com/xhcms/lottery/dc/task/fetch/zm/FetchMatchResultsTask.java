package com.xhcms.lottery.dc.task.fetch.zm;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.mc.uni.client.QueryMatchResultsClient;
import com.unison.lottery.mc.uni.parser.QueryMatchResultsParserStatus;
import com.xhcms.lottery.dc.data.BDMatch;
import com.xhcms.lottery.dc.data.BDResult;
import com.xhcms.lottery.dc.persist.service.BJDCMatchService;
import com.xhcms.lottery.lang.LotteryIdForZM;

/**
 * 获取比赛结果。
 * 
 * @author Yang Bo
 */
public class FetchMatchResultsTask extends ZMFetchTask {

	private String type; 	// 可用值：jczq,jclq,bd
	private List<String> types;//北京单场 除sf外
	private String playId;  //玩法
	@Autowired
	private BJDCMatchService bjdcMatchService;
	
	@Override
	
	protected void execute() throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String issue = format.format(new Date());
		QueryMatchResultsParserStatus matchParserStatus = new QueryMatchResultsParserStatus(type);
		QueryMatchResultsClient client = (QueryMatchResultsClient) getZmClient();
		
		//client.postWithStatus(type, issue, matchParserStatus);
		//判断是否是北京单场
		if(typeIsValid(type)){
			//type 转换成玩法 传进去 北单统一转换为 91_BJDC_SPF  胜负转换为 91_BJDC_SF
			 List<String> issues=bjdcMatchService.getIssueNumber(playId);
			 for(int i=0;i<issues.size();i++){
				 
				 client.postWithStatus(type,issues.get(i), matchParserStatus); 
				
				// storeData(getStoreDataName(), matchParserStatus.getMatchResults());
			 }

			
		}else{
			
			
			client.postWithStatus(type,issue, matchParserStatus);
			//storeData(getStoreDataName(), matchParserStatus.getMatchResults());
		}

		storeData(getStoreDataName(), matchParserStatus.getMatchResults());
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BJDCMatchService getBjdcMatchService() {
		return bjdcMatchService;
	}

	public void setBjdcMatchService(BJDCMatchService bjdcMatchService) {
		this.bjdcMatchService = bjdcMatchService;
	}
	private boolean typeIsValid(String type){
		
		 String types[]=new String[]{"SPF","BF","SXDS","JQS","BQC","SF"};
		 for(String t:types){
			  if(t.equals(type)){
				  if("SF".equals(type)){
					  
					  changeToGeneral(type);
				  }else{
					  
					  changeToGeneral("SPF");  
				  }
				  return true;
			  }
			 
		 }
		 return false;
		
	}
	//转换为玩法
	private void changeToGeneral(String type){
		
		playId=LotteryIdForZM.getLcPlayTypeFromZmLotteryId(type).getPlayId();
	}
	
	

}
