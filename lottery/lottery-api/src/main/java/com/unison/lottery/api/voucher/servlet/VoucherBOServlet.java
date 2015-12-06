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
 * 查询可用的优惠劵
 * @author Wang Lei
 *
 */
@WebServlet("/xml/security/queryVouchers")
public class VoucherBOServlet extends AbstractProcessServlet{
	private static final long serialVersionUID = -5418730790002283469L;

	public VoucherBOServlet(){
		super();
	}
	
	@Override
	protected String getMethodName() {
		return MethodNames.QUERY_VOUCHERS;
	}

	@Override
	protected String getResponseVOName() {
		return VONames.QUERY_VOUCHERS_RESPONSE_VO_NAME;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected HaveReturnStatusResponse process(HttpServletRequest request) {
		User user=(User) request.getAttribute(VONames.USER_AFTER_LOGIN_VO_NAME);
		Map<String,Object>  paramMap = (Map<String,Object>)request.getAttribute(VONames.QUERY_VOUCHERS_RESPONSE_VO_NAME);
		VoucherBO voucherBO=(VoucherBO) ctx.getBean("voucherBO");
		return voucherBO.queryVouchers(paramMap, user.getId());
	}
}