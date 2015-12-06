package com.unison.lottery.api.pay.bo;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.api.model.User;
import com.unison.lottery.api.pay.yeepay.DigestUtil;
import com.unison.lottery.api.pay.yeepay.HttpUtils;
import com.unison.lottery.api.pay.yeepay.NonBankcardPaymentResult;
import com.unison.lottery.api.pay.yeepay.YeePayContext;
import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.response.model.RechargeCardResponse;
import com.unison.lottery.api.protocol.status.IStatusRepository;
import com.unison.lottery.api.protocol.status.ReturnStatus;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.lottery.commons.persist.service.VoucherUserService;


public class RechargeCardBOImpl implements RechargeCardBO {
	
	@Autowired
	private IStatusRepository statusRepository;
	
	@Autowired
	private AccountService accountService;
	@Autowired
    private VoucherUserService voucherUserService;
	@Autowired
	private YeePayContext yeePayContext;
	
	private static final String p0_Cmd = "ChargeCardDirect";// 请求命令名称
	private static final String p4_verifyAmt = "false";
	private static final String p5_Pid = "大V彩客户端手机卡充值";
	// 使用应答机制
	private static final String pr_NeedResponse = "1";
		
	private Logger logger=LoggerFactory.getLogger(getClass());

	@Override
	public RechargeCardResponse recharge(User user, BigDecimal rechargeAmount,
			String rechargeCardType, String rechargeCardNumber,
			String rechargeCardPass, String voucherUserId) {
		RechargeCardResponse response=new RechargeCardResponse();
		ReturnStatus succStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.RechargeCard.SUCC);
		ReturnStatus failStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.RechargeCard.FAIL);
		ReturnStatus signfailed=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.RechargeCard.SIGNFAILED);
		ReturnStatus processed=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.RechargeCard.PROCESSED);
		ReturnStatus tomuch=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.RechargeCard.TOMUCH);
		ReturnStatus repeti=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.RechargeCard.REPETI);
		ReturnStatus amounte=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.RechargeCard.AMOUNTE);
		ReturnStatus paymentn=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.RechargeCard.PAYMENTN);
		ReturnStatus notob=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.RechargeCard.NOTOB);
		ReturnStatus carde=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.RechargeCard.CARDE);
		ReturnStatus passem=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.RechargeCard.PASSEM);
		
		try{
			Long userId = Long.parseLong(user.getId());
			
			// check voucher
			try {
				if(StringUtils.isNotBlank(voucherUserId)){
					voucherUserService.validClientVoucherRV(Long.parseLong(voucherUserId), userId, rechargeAmount);
				}
			} catch (Exception e) {
				logger.error("use voucher error, userid={} , voucherUserId={} , error:{}",new String[]{userId.toString(),voucherUserId},e);
				response.setReturnStatus(failStatus);
				logger.info("returnStatus-----------" + response.getReturnStatus());
				return response;
			}
			StringBuffer rechargeId = new StringBuffer();
			String respCode = getYeePayRespCode(rechargeId,userId, rechargeAmount, rechargeCardType, user.getIp(), rechargeCardNumber, rechargeCardPass);
			logger.info("respCode={}", respCode);
			
			// locking voucher
			try {
				if(StringUtils.isNotBlank(voucherUserId)){
					voucherUserService.lockingRechargeVoucher(Long.valueOf(rechargeId.toString()), userId, Long.parseLong(voucherUserId), rechargeAmount);
				}
			} catch (Exception e) {
				logger.error("locking voucher error, userid={} , voucherUserId={} , error:{}",new String[]{userId.toString(),voucherUserId},e);
				response.setReturnStatus(failStatus);
				logger.info("returnStatus-----------" + response.getReturnStatus());
				return response;
			}
			if(null != respCode && respCode.equals("1")) {
				logger.info("respCode success!");
				response.setReturnStatus(succStatus);
			} else if(respCode.equals("-1")) {
				response.setReturnStatus(signfailed);
			} else if(respCode.equals("2")) {
				response.setReturnStatus(processed);
			}else if(respCode.equals("5")) {
				response.setReturnStatus(tomuch);
			}else if(respCode.equals("11")) {
				response.setReturnStatus(repeti);
			}else if(respCode.equals("66")) {
				response.setReturnStatus(amounte);
			}else if(respCode.equals("95")) {
				response.setReturnStatus(paymentn);
			}else if(respCode.equals("112")) {
				response.setReturnStatus(notob);
			}else if(respCode.equals("8001")) {
				response.setReturnStatus(carde);
			}else if(respCode.equals("8002")) {
				response.setReturnStatus(passem);
			} else {
				logger.info("respCode fail!");
				response.setReturnStatus(failStatus);
			}		
			
		}
		catch(Exception e){
			e.printStackTrace();
			logger.info("respCode catch fail!");
			response.setReturnStatus(failStatus);
		}
		logger.info("returnStatus-----------" + response.getReturnStatus());
		return response;
	}
	
	private String getYeePayRespCode(StringBuffer rechargeId , Long userId, BigDecimal amount, String cardType, String ip, String pa8_cardNo, String pa9_cardPwd) {
		String respCode = null;
		//易宝支付手续费是5%
		BigDecimal cardfee = amount.multiply(new BigDecimal(5)).divide(
				new BigDecimal(100));

        String p2_Order = String.valueOf(accountService.clientYeepayCardForRecharge(userId, amount, cardfee, cardfee, cardType,
                ip));
        rechargeId.append(p2_Order);
        String p3_Amt = amount.toString();
        //商户扩展信息，原样返回
        String pa_MP = String.valueOf(userId);
        //卡面金额
        String pa7_cardAmt = p3_Amt;
        //商户编号
        String p1_MerId = yeePayContext.getMemberId();
        //商户接收支付成功数据的地址
        String p8_Url = yeePayContext.getCallback();
       
		// 生成hmac，保证交易信息不被篡改,关于hmac详见《易宝支付非银行卡支付专业版接口文档 v3.0》
		String hmac = DigestUtil.getHmac(new String[] { p0_Cmd, 
												 p1_MerId,
												 p2_Order,
												 p3_Amt,
												 p4_verifyAmt,
												 p5_Pid,
												 p8_Url,
												 pa_MP,
												 pa7_cardAmt,
												 pa8_cardNo,
												 pa9_cardPwd,
												 cardType,
												 pr_NeedResponse}, yeePayContext.getKeyValue());
		logger.info("p2_Order={}" + p2_Order);
		logger.info("p3_Amt={}" + p3_Amt);
		logger.info("pa8_cardNo={}" + pa8_cardNo);
		logger.info("pa9_cardPwd={}" + pa9_cardPwd);
		logger.info("cardType={}" + cardType);
		logger.info("hmac={}" + hmac);
		// 封装请求参数，参数说明详见《易宝支付非银行卡支付专业版接口文档 v3.0》
		Map<String, String> reqParams = new HashMap<String, String>();
		reqParams.put("p0_Cmd", p0_Cmd);
		reqParams.put("p1_MerId", p1_MerId);
		reqParams.put("p2_Order", p2_Order);
		reqParams.put("p3_Amt", p3_Amt);
		reqParams.put("p4_verifyAmt", p4_verifyAmt);
		reqParams.put("p5_Pid", p5_Pid);
		reqParams.put("p8_Url", p8_Url);
		reqParams.put("pa_MP", pa_MP);
		reqParams.put("pa7_cardAmt", pa7_cardAmt);
		reqParams.put("pa8_cardNo", pa8_cardNo);
		reqParams.put("pa9_cardPwd", pa9_cardPwd);
		reqParams.put("pd_FrpId", cardType);
		reqParams.put("pr_NeedResponse", pr_NeedResponse);
		reqParams.put("hmac", hmac);
		
		List responseStr = null;
		try {
			// 发起支付请求
			logger.info("Begin http communications,request params[" + reqParams
					+ "]");
			responseStr = HttpUtils.URLPost(yeePayContext.getUrl(), reqParams);
			logger.info("End http communications.responseStr:" + responseStr);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		if (responseStr.size() == 0) {
			logger.error("no response.");
			throw new RuntimeException("no response.");
		}
		
		// 创建非银行卡专业版消费请求结果
		NonBankcardPaymentResult rs = new NonBankcardPaymentResult();
		// 解析易宝支付返回的消费请求结果,关于返回结果数据详见《易宝支付非银行卡支付专业版接口文档 v3.0》
		for (int t = 0; t < responseStr.size(); t++) {
			String currentResult = (String) responseStr.get(t);
			logger.info("responseStr[" + t + "]:" + currentResult);
			if (currentResult == null || currentResult.equals("")) {
				continue;
			}
			int i = currentResult.indexOf("=");
			if (i >= 0) {
				String sKey = currentResult.substring(0, i);
				String sValue = currentResult.substring(i + 1);
				if (sKey.equals("r0_Cmd")) {
					rs.setR0_Cmd(sValue);
				} else if (sKey.equals("r1_Code")) {
					rs.setR1_Code(sValue);
				} else if (sKey.equals("r6_Order")) {
					rs.setR6_Order(sValue);
				} else if (sKey.equals("rq_ReturnMsg")) {
					rs.setRq_ReturnMsg(sValue);
				} else if (sKey.equals("hmac")) {
					rs.setHmac(sValue);
				} else {
					logger.error("throw exception:" + currentResult);
					throw new RuntimeException(currentResult);
				}
			} else {
				logger.error("throw exception:" + currentResult);
				throw new RuntimeException(currentResult);
			}
		}
		
		//请求返回结果代码
		respCode = rs.getR1_Code();
		
		if (rs.getR1_Code().equals("1")) {
			String newHmac = DigestUtil.getHmac(new String[] { rs.getR0_Cmd(),
					rs.getR1_Code(),rs.getR6_Order(),
					rs.getRq_ReturnMsg() }, yeePayContext.getKeyValue());
			// hmac不一致则抛出异常
			if (newHmac.equals(rs.getHmac())) {
				logger.info("yeepay client mobile card recharge response success!!");
			} else {
				logger.error("交易签名被篡改");
				throw new RuntimeException("交易签名被篡改");
			}
		} else {
			logger.error("errorCode:" + rs.getR1_Code() + ";errorMessage:"
					+ rs.getRq_ReturnMsg());
		}
		
		return respCode;
	}

}
