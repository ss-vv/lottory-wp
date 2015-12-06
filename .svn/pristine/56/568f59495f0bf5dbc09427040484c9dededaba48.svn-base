package com.unison.lottery.api.voucher.servlet;

import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import com.unison.lottery.api.framework.servlet.AbstractProcessServlet;
import com.unison.lottery.api.model.User;
import com.unison.lottery.api.protocol.common.MethodNames;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.response.model.HaveReturnStatusResponse;
import com.unison.lottery.api.voucher.bo.VoucherBO;

/**
 * 使用优惠劵
 * @author Wang Lei
 *
 */
@WebServlet("/xml/security/useVoucher")
public class UseVoucherBOServlet extends AbstractProcessServlet{

	private static final long serialVersionUID = 7940389029775336333L;

	public UseVoucherBOServlet(){
		super();
	}
	
	@Override
	protected String getMethodName() {
		return MethodNames.USE_VOUCHER;
	}

	@Override
	protected String getResponseVOName() {
		return VONames.USE_VOUCHER_RESPONSE_VO_NAME;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected HaveReturnStatusResponse process(HttpServletRequest request) {
		User user=(User) request.getAttribute(VONames.USER_AFTER_LOGIN_VO_NAME);
		Map<String,Object>  paramMap = (Map<String,Object>)request.getAttribute(VONames.USE_VOUCHER_REQUEST_VO_NAME);
		VoucherBO voucherBO=(VoucherBO) ctx.getBean("voucherBO");
		return voucherBO.useVoucher(paramMap, user.getId());
	}
}