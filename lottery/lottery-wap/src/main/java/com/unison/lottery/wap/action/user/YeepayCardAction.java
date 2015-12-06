package com.unison.lottery.wap.action.user;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.wap.utils.yeepay.DigestUtil;
import com.unison.lottery.wap.utils.yeepay.HttpUtils;
import com.unison.lottery.wap.utils.yeepay.NonBankcardPaymentResult;
import com.xhcms.lottery.commons.data.Account;
import com.xhcms.lottery.commons.data.User;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.ucenter.action.BaseAction;
/**
 * <p>Title: 易宝手机卡充值</p>
 * 
 * @author yonglizhu
 * 
 */
public class YeepayCardAction extends BaseAction implements ServletRequestAware, ServletResponseAware {

	private static final long serialVersionUID = -5881695594716518799L;
	
    private static final Logger log = LoggerFactory.getLogger(YeepayCardAction.class);
    
    @Autowired
	private YeePayContext yeePayContext;
		
	private Account account;
	
    @Autowired
    private AccountService accountService;
	
	private static String p0_Cmd = "ChargeCardDirect"; 		// 请求命令名称
	private static String p4_verifyAmt = "false";
	private String p5_Pid = "大V彩WAP手机卡充值";

	// 使用应答机制
	private static String pr_NeedResponse = "1";
		
	private BigDecimal amount;
	
	private BigDecimal cardfee;
	
	private BigDecimal cardAmount;
	
	private String pa8_cardNo;
	
	private String pa9_cardPwd;
	
	private String pd_FrpId;
	
	private String respCode;
	
	private String respOrderNo;
	
	public String yeepayCharge() {
		User user = getSelf();
		if (user != null) {
			account = accountService.getAccount(user.getId());
			//易宝支付手续费是5%
			cardfee = amount.multiply(new BigDecimal(5)).divide(
					new BigDecimal(100));
			cardAmount = amount.multiply(new BigDecimal(95)).divide(
					new BigDecimal(100));
		} else {
			return LOGIN;
		}
		return SUCCESS;
	}
	
	public String yeepayAffirm() {
		User user = getSelf();
		if(user == null) {
			return LOGIN;
		}
		log.info("userId={}" + user.getId());
        String p2_Order = String.valueOf(accountService.yeepayCardForRecharge(user.getId(), amount, cardfee, cardfee, pd_FrpId,
                request.getRemoteAddr()));

        String p3_Amt = amount.toString();
        //商户扩展信息，原样返回
        String pa_MP = String.valueOf(user.getId());
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
												 pd_FrpId,
												 pr_NeedResponse}, yeePayContext.getKeyValue());
		log.info("p2_Order={}" + p2_Order);
		log.info("p3_Amt={}" + p3_Amt);
		log.info("hmac={}" + hmac);
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
		reqParams.put("pd_FrpId", pd_FrpId);
		reqParams.put("pr_NeedResponse", pr_NeedResponse);
		reqParams.put("hmac", hmac);
		
		List responseStr = null;
		try {
			// 发起支付请求
			log.info("Begin http communications,request params[" + reqParams
					+ "]");
			responseStr = HttpUtils.URLPost(yeePayContext.getUrl(), reqParams);
			log.info("End http communications.responseStr:" + responseStr);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		if (responseStr.size() == 0) {
			log.error("no response.");
			throw new RuntimeException("no response.");
		}
		
		// 创建非银行卡专业版消费请求结果
		NonBankcardPaymentResult rs = new NonBankcardPaymentResult();
		// 解析易宝支付返回的消费请求结果,关于返回结果数据详见《易宝支付非银行卡支付专业版接口文档 v3.0》
		for (int t = 0; t < responseStr.size(); t++) {
			String currentResult = (String) responseStr.get(t);
			log.info("responseStr[" + t + "]:" + currentResult);
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
					log.error("throw exception:" + currentResult);
					throw new RuntimeException(currentResult);
				}
			} else {
				log.error("throw exception:" + currentResult);
				throw new RuntimeException(currentResult);
			}
		}
   
		if (rs.getR1_Code().equals("1")) {
			String newHmac = DigestUtil.getHmac(new String[] { rs.getR0_Cmd(),
					rs.getR1_Code(),rs.getR6_Order(),
					rs.getRq_ReturnMsg() }, yeePayContext.getKeyValue());
			// hmac不一致则抛出异常
			if (newHmac.equals(rs.getHmac())) {
				respCode = rs.getR1_Code();
				respOrderNo = rs.getR6_Order();
	        	log.info("success!!");
			} else {
				log.error("交易签名被篡改");
				throw new RuntimeException("交易签名被篡改");
			}
		} else {
			log.error("errorCode:" + rs.getR1_Code() + ";errorMessage:"
					+ rs.getRq_ReturnMsg());
		}
	
		return SUCCESS;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getPa8_cardNo() {
		return pa8_cardNo;
	}

	public void setPa8_cardNo(String pa8_cardNo) {
		this.pa8_cardNo = pa8_cardNo;
	}

	public String getPa9_cardPwd() {
		return pa9_cardPwd;
	}

	public void setPa9_cardPwd(String pa9_cardPwd) {
		this.pa9_cardPwd = pa9_cardPwd;
	}

	public String getPd_FrpId() {
		return pd_FrpId;
	}

	public void setPd_FrpId(String pd_FrpId) {
		this.pd_FrpId = pd_FrpId;
	}

	public BigDecimal getCardfee() {
		return cardfee;
	}

	public void setCardfee(BigDecimal cardfee) {
		this.cardfee = cardfee;
	}

	public BigDecimal getCardAmount() {
		return cardAmount;
	}

	public void setCardAmount(BigDecimal cardAmount) {
		this.cardAmount = cardAmount;
	}

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getRespOrderNo() {
		return respOrderNo;
	}

	public void setRespOrderNo(String respOrderNo) {
		this.respOrderNo = respOrderNo;
	}
}
