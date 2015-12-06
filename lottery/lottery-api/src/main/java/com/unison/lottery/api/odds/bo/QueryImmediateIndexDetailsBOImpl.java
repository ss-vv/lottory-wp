package com.unison.lottery.api.odds.bo;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.davcai.lottery.service.QueryImmediateIndexInfoService;
import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.response.model.HaveReturnStatusResponse;
import com.unison.lottery.api.protocol.response.model.QueryImmediateIndexDetailsResponse;
import com.unison.lottery.api.protocol.response.model.QueryImmediateIndexInfoResponse;
import com.unison.lottery.api.protocol.status.IStatusRepository;
import com.unison.lottery.api.protocol.status.ReturnStatus;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchAsiaOuOddsInfoPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchOpOddsInfoPO;

/**
 *
 * @author baoxing.peng
 * @since 2015年3月26日上午9:44:34
 */
public class QueryImmediateIndexDetailsBOImpl implements
		QueryImmediateIndexDetailsBO {

	@Autowired
	private QueryImmediateIndexInfoService queryImmediateIndexInfoService;
	@Autowired
	private IStatusRepository status;
	
	@Override
	public HaveReturnStatusResponse queryImmediateIndexDetails(Long matchId,
			String corpId, String oddsType,String matchType) {
		QueryImmediateIndexDetailsResponse queryImmediateIndexInfoResponse = new QueryImmediateIndexDetailsResponse();
		ReturnStatus succStatus = status
				.getSystemStatusBySystemKey(SystemStatusKeyNames.QueryImmediateIndexDetails.QUERY_INDEX_DETAILS_SUCC);
		ReturnStatus failStatus = status
				.getSystemStatusBySystemKey(SystemStatusKeyNames.QueryImmediateIndexDetails.QUERY_INDEX_DETAILS_FAIL);

		queryImmediateIndexInfoResponse.setReturnStatus(failStatus);
		if(StringUtils.isNotBlank(corpId)&&StringUtils.isNotBlank(oddsType)
				&&matchId!=null&&StringUtils.isNotBlank(matchType)){
			
			if(StringUtils.equals(matchType, "0")){ //足球
				if(StringUtils.equals("0", oddsType)){ //足球欧赔
					Map<String, Object> oddsMap = queryImmediateIndexInfoService.findFbMatchOpOdds(matchId,corpId);
					if(oddsMap!=null){
						FbMatchOpOddsInfoPO opOddsInfoPO = (FbMatchOpOddsInfoPO)oddsMap.get("opOdds");
						Date matchTime = (Date) oddsMap.get("matchTime");
						if(opOddsInfoPO!=null){
							String[] oddsData = opOddsInfoPO.getEuroOdds().split("!");
							String timestamp = opOddsInfoPO.getChangeTime();
							queryImmediateIndexInfoResponse.setOddsData("");
							String opOdds = "";
							for(String odds:oddsData){
								opOdds += StringUtils.substring(odds,0,odds.lastIndexOf(","))+"!";
							}
							queryImmediateIndexInfoResponse.setOddsData(StringUtils.removeEnd(opOdds, "!"));
							makeChangeTime(queryImmediateIndexInfoResponse,
									opOddsInfoPO, matchTime);
						}
					}
				}else{ //亚赔或大小
					Map<String, String> asiaOuOddsInfoPOs = queryImmediateIndexInfoService.findFbMatchAsianOrOuOdds(matchId, corpId,oddsType);
					transDataToResponse(queryImmediateIndexInfoResponse,
							asiaOuOddsInfoPOs);
				}
			}else{
				if(StringUtils.equals("0", oddsType)){ //篮球欧赔
					Map<String, String> asiaOuOddsInfoPOs = queryImmediateIndexInfoService.findBbEuroOddsByMatchIdAndCorpId(matchId, corpId);
					transDataToResponse(queryImmediateIndexInfoResponse,
							asiaOuOddsInfoPOs);
				}else {
					Map<String, String> asiaOuOddsInfoPOs = queryImmediateIndexInfoService.findBbAsianOrOuOddsByMatchIdAndCorpId(matchId, corpId,oddsType);
					transDataToResponse(queryImmediateIndexInfoResponse,
							asiaOuOddsInfoPOs);
				}
			}
			queryImmediateIndexInfoResponse.setReturnStatus(succStatus);
		}else{
			failStatus.setDescForClient("参数格式不正确");
		}
		return queryImmediateIndexInfoResponse;
	}

	private void makeChangeTime(
			QueryImmediateIndexDetailsResponse queryImmediateIndexInfoResponse,
			FbMatchOpOddsInfoPO opOddsInfoPO, Date matchTime) {
		String times[] = opOddsInfoPO.getChangeTime().split(",");
		String changeTime = "";
		int i=0;
		int length = times.length;
		String[] pattern = {"yyyyMMddHHmmss"};
		try {
			for(String time:times){
				i++;
				if(StringUtils.isNotBlank(time)){
					changeTime+=queryImmediateIndexInfoService.calculateTimeToMatchTime(DateUtils.parseDate(time, pattern), matchTime, i, length)+",";
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			changeTime+=",";
			e.printStackTrace();
		}
		queryImmediateIndexInfoResponse.setTimestamp(StringUtils.removeEnd(changeTime, ","));
	}

	private void transDataToResponse(
			QueryImmediateIndexDetailsResponse queryImmediateIndexInfoResponse,
			Map<String, String> asiaOuOddsInfoPOs) {
		if(asiaOuOddsInfoPOs!=null){
			queryImmediateIndexInfoResponse.setOddsData(asiaOuOddsInfoPOs.get("oddsData"));
			queryImmediateIndexInfoResponse.setTimestamp(asiaOuOddsInfoPOs.get("timestamp"));
		}
	}

}
