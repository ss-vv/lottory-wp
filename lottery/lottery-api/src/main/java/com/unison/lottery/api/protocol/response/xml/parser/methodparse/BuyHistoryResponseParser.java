package com.unison.lottery.api.protocol.response.xml.parser.methodparse;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.common.model.Item;
import com.unison.lottery.api.protocol.common.model.Result;
import com.unison.lottery.api.protocol.response.model.BuyHistoryResponse;
import com.unison.lottery.api.protocol.response.model.IHaveReturnStatus;
import com.unison.lottery.api.protocol.response.model.Response;
import com.unison.lottery.api.protocol.response.util.ResultResponseUtil;
import com.xhcms.lottery.commons.data.Account;
import com.xhcms.lottery.commons.data.BetRecord;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.EntityType;
import com.xhcms.lottery.lang.PlayType;

public class BuyHistoryResponseParser extends
		AbstractMethodResponseParser {
	@Override
	protected IHaveReturnStatus getResponseFromHttpRequest(
			HttpServletRequest httpRequest) {
		return (IHaveReturnStatus) httpRequest.getAttribute(VONames.BUY_HISTORY_RESPONSE_VO_NAME);
	}

	@Override
	protected void handle(Response resultResponse,
			Object responseFromHttpRequest) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		BuyHistoryResponse buyHistoryResponse=(BuyHistoryResponse) responseFromHttpRequest;
		if(null!=buyHistoryResponse&&null!=buyHistoryResponse.getReturnStatus()){
			resultResponse.result=new Result();
			resultResponse.result.item = new ArrayList<Item>();
			for (BetRecord b : buyHistoryResponse.getResults()) {
				String playId = b.getPlayId();
				String hhZQPlayId = PlayType.JCZQ_HH.getShortPlayStr();
				String hhLQPlayId = PlayType.JCLQ_HH.getShortPlayStr();
				if(playId.startsWith(hhLQPlayId) || playId.startsWith(hhZQPlayId)) {
					
				}
				
				Item resultItem = new Item();
				Long schemeId = b.getId();
				if(schemeId!=null){
					resultItem.schemeId = schemeId;
				}
				String lotteryId = b.getLotteryId();
				if(StringUtils.isNotBlank(lotteryId)){
					resultItem.lotteryId = lotteryId;
				}
				Long initiator = b.getSponsorId();
				if(initiator!=null){
					resultItem.initiator = initiator;
				}
				String sponsor = b.getSponsor();
				if(StringUtils.isNotBlank(sponsor)){
					resultItem.sponsor  = sponsor;
				}
				BigDecimal betAmount = new BigDecimal(b.getBetAmount());
				if(betAmount!=null){
					resultItem.betAmount = betAmount;
				}
				
				if(StringUtils.isNotBlank(playId)){
					resultItem.playId = playId;
				}
				String status = String.valueOf(b.getStatus());
				if(StringUtils.isNotBlank(status)){
					resultItem.betSchemeStatus = status;
				}
				String issueNumber = b.getIssue();
				if(StringUtils.isNotBlank(issueNumber)){
					resultItem.issueNumber = issueNumber;
				}
				String time =  sdf.format(b.getCreatedTime());
				if(StringUtils.isNotBlank(time)){
					resultItem.time = time;
				}
				int betType =  b.getType();
				int displayMode =  0;
				/** 代购 */
				if(Constants.TYPE_BUY == betType && Constants.NOT_SHOW_SCHEME == b.getShowScheme()){
					displayMode = EntityType.DISPLAY_ALONE;
					/**  晒单 */
				}else if(Constants.TYPE_BUY == betType && Constants.SHOW_SCHEME == b.getShowScheme()){
					displayMode = EntityType.DISPLAY_SHOW;
					/** 合买 */
				}else if(Constants.TYPE_GROUP  == betType ){
					displayMode = EntityType.DISPLAY_GROUPBUY;
					/** 跟单 */
				}else if(Constants.TYPE_FOLLOW == betType && Constants.NOT_SHOW_SCHEME == b.getShowScheme()){
					displayMode = EntityType.DISPLAY_ALONE;
				}
				resultItem.betType = betType ;
				resultItem.displayMode = displayMode;
				resultResponse.result.item.add(resultItem);
				
				Account account=buyHistoryResponse.getAccount();
				ResultResponseUtil.bindAccountInfoToResultResponse(resultResponse, account);
			}
		}
	}

	@Override
	protected String getDefaultReturnStatusKeyName() {
		return SystemStatusKeyNames.BuyHistory.SUCC;
	}
}
