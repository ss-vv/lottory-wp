package com.unison.lottery.api.protocol.response.xml.parser.methodparse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.unison.lottery.api.lang.LotteryUtil;
import com.unison.lottery.api.protocol.common.Constants;
import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.common.model.Item;
import com.unison.lottery.api.protocol.common.model.Result;
import com.unison.lottery.api.protocol.common.model.ResultList;
import com.unison.lottery.api.protocol.response.model.IHaveReturnStatus;
import com.unison.lottery.api.protocol.response.model.Response;
import com.unison.lottery.api.protocol.response.model.SchemeTicketResponse;
import com.unison.lottery.api.schemeDetail.data.CTSchemeDetailData;
import com.xhcms.lottery.commons.data.BetMatch;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.PlayMatch;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.utils.ResultTool;

public class SchemeTicketMethodResponseParser extends
		AbstractMethodResponseParser {

	@Override
	protected IHaveReturnStatus getResponseFromHttpRequest(
			HttpServletRequest httpRequest) {
		return (IHaveReturnStatus) httpRequest.getAttribute(VONames.SCHEME_TICKET_VO_NAME);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void handle(Response resultResponse,
			Object responseFromHttpRequest) {
 		SchemeTicketResponse schemeTicketResponse=(SchemeTicketResponse) responseFromHttpRequest;
		
		if(null!=schemeTicketResponse&&schemeTicketResponse.getResultMap()!=null){
			Map<String,Object> resultMap = schemeTicketResponse.getResultMap();
			List<Ticket> tickets = (List<Ticket>)resultMap.get(Constants.TICKET);
			BetScheme scheme = (BetScheme)resultMap.get(Constants.SCHEME);
			Object splietMatchs = resultMap.get(Constants.SPLIT_MATCH_DATA);
			Object matchs = resultMap.get(Constants.MATCH_DATA);
			List<CTSchemeDetailData> matchList = new ArrayList<CTSchemeDetailData>();
			if(null != matchs) {
				matchList = (List<CTSchemeDetailData>) matchs;
			}
			
			resultResponse.resultList = new ResultList();
			resultResponse.resultList.result = new ArrayList<Result>();
			Map<String, List<CTSchemeDetailData>> splitTicketMatchs = 
					new HashMap<String, List<CTSchemeDetailData>>();
			if(null != splietMatchs) {
				splitTicketMatchs = (Map<String, List<CTSchemeDetailData>>) splietMatchs;
			}
			
			String lotteryId = scheme.getLotteryId();
			Map<String,BetMatch> matches = null;
			if(LotteryUtil.isUsedParseMatch(lotteryId)) {
				matches = (Map<String,BetMatch>)resultMap.get(Constants.MATCHES);
			}
			
			String totalAmount = null;
			//如果拆完票之后票数量大于10则进行处理
			boolean isMoreTicket = false;//表示在客户端显示时是否票数过多；
			Result rs = null;
			if(null != tickets && tickets.size() > 10) {
				isMoreTicket = true;
				totalAmount = String.valueOf(resultMap.get(Constants.TOTAL_AMOUNT));
				rs = new Result();
			}
			
			if(null != tickets && tickets.size() > 0) {
				for (Ticket ticket : tickets) {
					Result result = null;
					if(!isMoreTicket) {
						result = new Result();
					} else {
						result = rs;
					}
					result.item = new ArrayList<Item>();
					result.ticketNumber = ticket.getNumber();
					if(LotteryUtil.isUsedParsePassType(lotteryId)) {
						if(ticket.getPassTypeId().charAt(0)=='1'){
							result.passType = "单关";
						}else{
							result.passType = ticket.getPassTypeId().replace("@", "串");
						}
					}
					result.multiple = ticket.getMultiple();
					result.money = ticket.getMoney();
					result.afterTaxBonus = ticket.getAfterTaxBonus();
					result.status = ticket.getStatus();
					String playId = ticket.getPlayId();
					
					List<PlayMatch> playMatchs = ticket.getMatches();
					
					if(com.xhcms.lottery.lang.Constants.CQSS.equals(lotteryId) ||
							com.xhcms.lottery.lang.Constants.JX11.equals(lotteryId) ||
							com.xhcms.lottery.lang.Constants.SSQ.equals(lotteryId)) {
						Item betContentItem = new Item();
						betContentItem.betContent = ticket.getActualCode();
						betContentItem.cnCode = ticket.getIssue();
						result.item.add(betContentItem);
					} else if(com.xhcms.lottery.lang.Constants.JCLQ.equals(lotteryId) ||
							com.xhcms.lottery.lang.Constants.JCZQ.equals(lotteryId)) {
						if(isMoreTicket) {
							Object o = resultMap.get(Constants.JC_BET_MATCH);
							if(null != o) {
								playMatchs = (List<PlayMatch>) o;
							}
						}
						if(null != playMatchs && playMatchs.size() > 0) {
							for (PlayMatch playMatch : playMatchs) {
								Item playMatchItem = new Item();
								playMatchItem.cnCode = playMatch.getCnCode();
								BetMatch betMatch = matches.get(playMatch.getCode());
								//投注内容
								if (com.xhcms.lottery.lang.Constants.PLAY_05_ZC_2.equals(playId)) {
									//如果是混合，玩法Id为“混合玩法”中选择的玩法Id
									String betCode = playMatch.getBetCode();
									playMatchItem.betContent = ResultTool.cn(betMatch.getPlayId(), betCode.substring(0, betCode.indexOf(":")), "");
								} else if (com.xhcms.lottery.lang.Constants.PLAY_10_LC_2.equals(playId)){
									String betCode = playMatch.getBetCode();
									playMatchItem.betContent = ResultTool.cn(betMatch.getPlayId(), betCode.substring(0, betCode.indexOf(":")), "");
								} else {
									//非混合玩法直接使用ticket中的playId
									playMatchItem.betContent = ResultTool.cn(playId,playMatch.getBetCode(), "");
								}
								
								if(((PlayMatch)betMatch).getResult() != null){
									playMatchItem.result = ResultTool.cn(playId, ((PlayMatch)betMatch).getResult(), null);
								}
								playMatchItem.resultOdd = playMatch.getResultOdd();
								playMatchItem.concedePoints = playMatch.getConcedePoints();
								result.item.add(playMatchItem);
							}
						}
					} else if(com.xhcms.lottery.lang.Constants.CTZC.equals(lotteryId)) {
						Long ticketId = ticket.getId();
						List<CTSchemeDetailData> list = splitTicketMatchs.get(String.valueOf(ticketId));
						if(isMoreTicket) {
							list = matchList;
						}
						if(null != list && list.size() > 0) {
							for(CTSchemeDetailData ctMatchData : list) {
								Item betContentItem = new Item();
								if(StringUtils.isNotBlank(ctMatchData.getBetContent())) {
									betContentItem.betContent = ctMatchData.getBetContent().trim();
								}
								if(StringUtils.isNotBlank(ctMatchData.getMatchResult())) {
									betContentItem.result = ctMatchData.getMatchResult().trim();
								}
								betContentItem.cnCode = ctMatchData.getMatchName();
								result.item.add(betContentItem);
							}
						}
					}
					if(!isMoreTicket) {
						resultResponse.resultList.result.add(result);
					} else {
						result.money = Integer.parseInt(totalAmount);
						break;
					}
				}
			}
			if(isMoreTicket) {
				resultResponse.resultList.result.add(rs);
			}
		}
	}

	@Override
	protected String getDefaultReturnStatusKeyName() {
		return SystemStatusKeyNames.SchemeTicket.FAIL;
	}
}