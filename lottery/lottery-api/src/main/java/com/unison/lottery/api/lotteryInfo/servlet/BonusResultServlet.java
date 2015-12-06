package com.unison.lottery.api.lotteryInfo.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import com.unison.lottery.api.framework.servlet.AbstractProcessServlet;
import com.unison.lottery.api.lotteryInfo.bo.LotteryInfoBO;
import com.unison.lottery.api.model.User;
import com.unison.lottery.api.protocol.common.MethodNames;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.response.model.BonusResultResponse;
import com.unison.lottery.api.protocol.response.model.HaveReturnStatusResponse;

/**
 * @desc
 * @createTime 2012-11-30
 * @author lei.li@unison.net.cn
 * @version 1.0
 */
@WebServlet("/xml/bonusResult")
public class BonusResultServlet extends AbstractProcessServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected String getMethodName() {
		return MethodNames.BONUSRESULT;
	}

	@Override
	protected String getResponseVOName() {
		return VONames.BONUSRESULT_RESPONSE_VO_NAME;
	}

	@Override
	protected HaveReturnStatusResponse process(HttpServletRequest request) {
		LotteryInfoBO infoBO = (LotteryInfoBO) ctx.getBean("lotteryInfoBO");
		User user = (User) request.getAttribute(VONames.USER_VO_NAME);
		
		BonusResultResponse bonusResultResponse = infoBO.queryLotteryInfo(user);
		return bonusResultResponse;
	}
}