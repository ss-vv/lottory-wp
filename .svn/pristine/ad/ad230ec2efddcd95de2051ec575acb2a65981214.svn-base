package com.unison.lottery.api.pay.bo;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.api.model.User;
import com.unison.lottery.api.pay.alipay.AliPayContext;
import com.unison.lottery.api.pay.alipay.Rsa;
import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.response.model.QueryAlipayRSAKeyResponse;
import com.unison.lottery.api.protocol.status.IStatusRepository;
import com.unison.lottery.api.protocol.status.ReturnStatus;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.lottery.commons.persist.service.VoucherUserService;

public class QueryAlipayRSAKeyBOImpl implements QueryAlipayRSAKeyBO {

	@Autowired
	private IStatusRepository statusRepository;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private AliPayContext aliPayContext;
    @Autowired
    private VoucherUserService voucherUserService;
	
	private Logger logger=LoggerFactory.getLogger(getClass());
	
	@Override
	public QueryAlipayRSAKeyResponse getRSAResultString(User user,
			Map<String,Object> paramMap) {
		QueryAlipayRSAKeyResponse response =new QueryAlipayRSAKeyResponse();
		ReturnStatus succStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.QueryAlipayRSAKey.SUCC);
		ReturnStatus failStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.QueryAlipayRSAKey.FAIL);
		response.setReturnStatus(failStatus);
		BigDecimal rechargeAmount = (BigDecimal) paramMap.get(com.unison.lottery.api.protocol.common.Constants.RECHARE_AMOUNT);
		Object voucherUserId = paramMap.get(com.unison.lottery.api.protocol.common.Constants.VOUCHER_USER_ID);
		try{
			String resultString=null;
			Long userId = Long.parseLong(user.getId());
			
			// check voucher
			try {
				if(voucherUserId != null){
					Long voucherId = Long.parseLong(voucherUserId.toString());
					voucherUserService.validClientVoucherRV(voucherId, userId, rechargeAmount);
				}
			} catch (Exception e) {
				logger.error("use voucher error, userid={} , voucherUserId={} , error:{}",new String[]{userId.toString(),voucherUserId.toString()},e);
				response.setReturnStatus(failStatus);
				logger.info("returnStatus-----------" + response.getReturnStatus());
				return response;
			}
			
			String channelCode = "CLIENTALIPAY";
			Long rechargeId = accountService.clientAlipayForRecharge(userId, rechargeAmount, BigDecimal.ZERO, BigDecimal.ZERO, channelCode, user.getIp());
			logger.info("userId={}" + userId);
			logger.info("rechargeId={}" + rechargeId);
			
			// locking voucher
			try {
				if(voucherUserId != null){
					voucherUserService.lockingRechargeVoucher(Long.valueOf(rechargeId.toString()), userId, Long.parseLong(voucherUserId.toString()), rechargeAmount);
				}
			} catch (Exception e) {
				logger.error("locking voucher error, userid={} , voucherUserId={} , error:{}",new String[]{userId.toString(),voucherUserId.toString()},e);
				response.setReturnStatus(failStatus);
				logger.info("returnStatus-----------" + response.getReturnStatus());
				return response;
			}
			String content = getOrderInfo(rechargeId.toString(), rechargeAmount.toString());
			logger.info("content={}" + content);
			String sign = Rsa.sign(content, aliPayContext.getRsaPrivate());
			sign = URLEncoder.encode(sign, "utf-8");
			resultString = content + "&sign=\"" +sign + "\"&sign_type=\"RSA\"";
			logger.info("resultString={}",resultString);
			
			response.setResultString(resultString);
			response.setReturnStatus(succStatus);
		}
		catch(Exception e){
			e.printStackTrace();
			response.setReturnStatus(failStatus);
		}
		return response;
	}
	
	private String getOrderInfo(String orderNo, String amount) {
		String subject = "大V彩客户端充值";
		String body = "大V彩客户端充值";
		String strOrderInfo = "partner=" + "\"" + aliPayContext.getPartner() + "\"";
		strOrderInfo += "&";
		strOrderInfo += "seller_id=" + "\"" + aliPayContext.getSeller() + "\"";
		strOrderInfo += "&";
		strOrderInfo += "out_trade_no=" + "\"" + orderNo + "\"";
		strOrderInfo += "&";
		strOrderInfo += "subject=" + "\"" + subject
				+ "\"";
		strOrderInfo += "&";
		strOrderInfo += "body=" + "\"" + body + "\"";
		strOrderInfo += "&";
		strOrderInfo += "total_fee=" + "\""
				+ amount + "\"";
		strOrderInfo += "&";
		strOrderInfo += "notify_url=" + "\""
				+ aliPayContext.getNotifyUrl() + "\"";
		
		strOrderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		strOrderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		strOrderInfo += "&_input_charset=\"utf-8\"";

		return strOrderInfo;
	}

}
