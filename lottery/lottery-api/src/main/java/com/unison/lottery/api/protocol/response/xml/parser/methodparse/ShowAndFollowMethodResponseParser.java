package com.unison.lottery.api.protocol.response.xml.parser.methodparse;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List; 

import javax.servlet.http.HttpServletRequest;

import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.common.model.Item;
import com.unison.lottery.api.protocol.common.model.Result;
import com.unison.lottery.api.protocol.response.model.IHaveReturnStatus;
import com.unison.lottery.api.protocol.response.model.Response;
import com.unison.lottery.api.protocol.response.model.ShowAndFollowResponse;
import com.unison.lottery.api.showAndFollow.model.BetShemeParams;

public class ShowAndFollowMethodResponseParser extends
		AbstractMethodResponseParser {

	@Override
	protected IHaveReturnStatus getResponseFromHttpRequest(
			HttpServletRequest httpRequest) {
		return (IHaveReturnStatus) httpRequest
				.getAttribute(VONames.SHOW_AND_FOLLOW_VO_NAME);
	}

	@Override
	protected void handle(Response resultResponse,
			Object responseFromHttpRequest) {
		ShowAndFollowResponse showAndFollowResponse = (ShowAndFollowResponse) responseFromHttpRequest;

		if (null != showAndFollowResponse
				&& showAndFollowResponse.getList() != null) {
			List<BetShemeParams> list = showAndFollowResponse.getList();
			resultResponse.result = new Result();
			resultResponse.result.item = new ArrayList<Item>();
			for (BetShemeParams betShemeParams : list) {
				Item resultItem = new Item();
				String playId = betShemeParams.getBetSchemePO().getPlayId();
				resultItem.playId = playId;
				resultItem.schemeId = betShemeParams.getBetSchemePO().getId();
				resultItem.lotteryId = betShemeParams.getBetSchemePO().getLotteryId();
				resultItem.sponsor = betShemeParams.getBetSchemePO().getSponsor();
				resultItem.mutiple = betShemeParams.getBetSchemePO().getMultiple();
				resultItem.totalAmount = betShemeParams.getBetSchemePO().getTotalAmount();
				resultItem.offtime = betShemeParams.getBetSchemePO().getOfftime();
				resultItem.followRatio = betShemeParams.getBetSchemePO().getFollowedRatio();
				resultItem.followCount = betShemeParams.getBetSchemePO().getFollowingCount();
				resultItem.maxReturnRatio = betShemeParams.getBetSchemePO().getMaxBonus().divide(new BigDecimal(betShemeParams.getBetSchemePO().getTotalAmount()), 0,
						BigDecimal.ROUND_HALF_UP).intValue();
				resultItem.maxBonus = betShemeParams.getBetSchemePO().getMaxBonus();
				resultItem.floorAmount = betShemeParams.getBetSchemePO().getFloorAmount()*1.0 / betShemeParams.getBetSchemePO().getTotalAmount();
				double progressDouble = (betShemeParams.getBetSchemePO().getPurchasedAmount()*1.0)/betShemeParams.getBetSchemePO().getTotalAmount();
				BigDecimal progress=new BigDecimal(progressDouble).setScale(2, RoundingMode.DOWN);
				resultItem.progress = progress.doubleValue();
				resultItem.surplus = betShemeParams.getBetSchemePO().getTotalAmount() - betShemeParams.getBetSchemePO().getPurchasedAmount();
				resultItem.pushMoney = betShemeParams.getBetSchemePO().getShareRatio();
				resultItem.betSchemeStatus = String.valueOf(betShemeParams.getBetSchemePO().getStatus());
				resultItem.militaryExploits = betShemeParams.getMilitaryExploits();
				resultItem.nickName = betShemeParams.getNickName();
				resultItem.imageUrl = betShemeParams.getImageUrl();
				resultItem.type = betShemeParams.getType();
				
				resultResponse.result.item.add(resultItem);
			}

		}

		
	}

	@Override
	protected String getDefaultReturnStatusKeyName() {
		return SystemStatusKeyNames.ShowAndFollow.FAIL;
	}

}
