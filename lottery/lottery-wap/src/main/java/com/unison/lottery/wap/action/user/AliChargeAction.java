package com.unison.lottery.wap.action.user;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.wap.utils.alipay.base.ResponseResult;
import com.unison.lottery.wap.utils.alipay.security.RSASignature;
import com.unison.lottery.wap.utils.alipay.util.ParameterUtil;
import com.unison.lottery.wap.utils.alipay.util.StringUtil;
import com.unison.lottery.wap.utils.alipay.util.XMapUtil;
import com.unison.lottery.wap.utils.alipay.vo.DirectTradeCreateRes;
import com.unison.lottery.wap.utils.alipay.vo.ErrorCode;
import com.xhcms.lottery.commons.data.Account;
import com.xhcms.lottery.commons.data.User;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.ucenter.action.BaseAction;
import com.xhcms.ucenter.lang.Constant;

public class AliChargeAction extends BaseAction implements ServletRequestAware, ServletResponseAware {

	private static final long serialVersionUID = -8156580873870831910L;
	
	private static final Logger log = LoggerFactory.getLogger(AliChargeAction.class);
	
	
	private static final String SEC_ID = "0001";
	
	private static final String subject = "大V彩wap充值";
	
	private static final String REQ_URL = "https://wappaygw.alipay.com/service/rest.htm";
	
	private BigDecimal amount;
	
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private AliPayContext aliPayContext;
    
    private String redirectURL;
    
    private User user;
    
    private Account account;

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String alicharge() {
		user = (User) request.getSession().getAttribute(Constant.USER_KEY);
		if (user != null) {
			account = accountService.getAccount(user.getId());
		} else {
			return LOGIN;
		}
		return SUCCESS;
	}

	public String aliaffirm() throws UnsupportedEncodingException {
		User user = getSelf();
		if(user == null) {
			return LOGIN;
		}
		log.info("userId={}" + user.getId());
		Map<String, String> reqParams = prepareTradeRequestParamsMap(request);
		//签名类型 
		String signAlgo = SEC_ID;
		//签名
		String sign = sign(reqParams,signAlgo,aliPayContext.getRsaPrivate());
		log.info("sign={}" + sign);
		reqParams.put("sign", sign);
		
		ResponseResult resResult = new ResponseResult();
		String businessResult = "";
		try {
			resResult = send(reqParams,REQ_URL,signAlgo);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if (resResult.isSuccess()) {
			log.info("success");
			businessResult = resResult.getBusinessResult();
		} else {
			return null;
		}
		DirectTradeCreateRes directTradeCreateRes = null;
		XMapUtil.register(DirectTradeCreateRes.class);
		try {
			directTradeCreateRes = (DirectTradeCreateRes) XMapUtil
					.load(new ByteArrayInputStream(businessResult
							.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e) {
		} catch (Exception e) {
		}
		// 开放平台返回的内容中取出request_token
		String requestToken = directTradeCreateRes.getRequestToken();
		log.info("Token={}" + requestToken);
		Map<String, String> authParams = prepareAuthParamsMap(request,
				requestToken);
		//对调用授权请求数据签名
		String authSign = sign(authParams,signAlgo, aliPayContext.getRsaPrivate());
		authParams.put("sign", authSign);
		try {
			redirectURL = getRedirectUrl(authParams,REQ_URL);
			log.info("authAndExecute URL={}" + redirectURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (StringUtil.isNotBlank(redirectURL)) {
			return SUCCESS;
		}
		
		return SUCCESS;
	}
	
	/**
	 * 准备alipay.wap.trade.create.direct服务的参数
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private Map<String, String> prepareTradeRequestParamsMap(
			HttpServletRequest request) throws UnsupportedEncodingException {
		Map<String, String> requestParams = new HashMap<String, String>();
		request.setCharacterEncoding("utf-8");
        // 外部交易号 ,保证唯一性
        String outTradeNo = getOutTradeNo();
        String reqId = UUID.randomUUID().toString();
		// 卖家帐号
		String sellerAccountName =aliPayContext.getSeller();
		
		// 接收支付宝发送的异步通知的url
		String notifyUrl = aliPayContext.getNotifyUrl();
		//接受支付宝发送的同步通知的url
		String callbackUrl = aliPayContext.getCallbackUrl();
		//未完成支付，用户点击链接返回商户url
		String merchantUrl = aliPayContext.getMerchantUrl();
		// req_data的内容

		String reqData = "<direct_trade_create_req>" + "<subject>" + subject
				+ "</subject><out_trade_no>" + outTradeNo
				+ "</out_trade_no><total_fee>" + amount
				+ "</total_fee><seller_account_name>" + sellerAccountName
				+ "</seller_account_name><notify_url>" + notifyUrl
				+ "</notify_url><call_back_url>"+callbackUrl+"</call_back_url><merchant_url>" + merchantUrl+ "</merchant_url>";
		        reqData = reqData + "</direct_trade_create_req>";
		requestParams.put("req_data", reqData);
		requestParams.put("req_id", reqId);
		requestParams.putAll(prepareCommonParams(request));
		return requestParams;
	}

	private String getOutTradeNo() {
		String channelCode = "WAPALIPAY";
		//获取用户的IP地址，作为防钓鱼的一种方法
		String clientIp = request.getHeader("x-forwarded-for");
		if ((clientIp == null) || (clientIp.length() == 0)
				|| ("unknown".equalsIgnoreCase(clientIp))) {
			clientIp = request.getHeader("Proxy-Client-IP");
		}
		if ((clientIp == null) || (clientIp.length() == 0)
				|| ("unknown".equalsIgnoreCase(clientIp))) {
			clientIp = request.getHeader("WL-Proxy-Client-IP");
		}
		if ((clientIp == null) || (clientIp.length() == 0)
				|| ("unknown".equalsIgnoreCase(clientIp))) {
			clientIp = request.getRemoteAddr();
		}
		Long userId = new Long(getSelf().getId());
		Long reId = accountService.wapAlipayForRecharge(userId, amount, BigDecimal.ZERO, BigDecimal.ZERO, channelCode, clientIp);
		
		return String.valueOf(reId);
	}
	
	/**
	 * 准备alipay.wap.auth.authAndExecute服务的参数
	 * 
	 * @param request
	 * @param requestToken
	 * @return
	 */
	private Map<String, String> prepareAuthParamsMap(
			HttpServletRequest request, String requestToken) {
		Map<String, String> requestParams = new HashMap<String, String>();
		String reqData = "<auth_and_execute_req><request_token>" + requestToken
				+ "</request_token></auth_and_execute_req>";
		requestParams.put("req_data", reqData);
		requestParams.putAll(prepareCommonParams(request));
        //支付成功跳转链接

		requestParams.put("call_back_url", aliPayContext.getCallbackUrl());
		requestParams.put("service", "alipay.wap.auth.authAndExecute");
		return requestParams;
	}

	/**
	 * 准备通用参数
	 * 
	 * @param request
	 * @return
	 */
	private Map<String, String> prepareCommonParams(HttpServletRequest request) {
		Map<String, String> commonParams = new HashMap<String, String>();
		commonParams.put("service", "alipay.wap.trade.create.direct");
		commonParams.put("sec_id", SEC_ID);
		commonParams.put("partner", aliPayContext.getPartner());
		commonParams.put("call_back_url", aliPayContext.getCallbackUrl());
		commonParams.put("format", "xml");
		commonParams.put("v", "2.0");
		return commonParams;
	}

	/**
	 * 对参数进行签名
	 * 
	 * @param reqParams
	 * @return
	 */
	private String sign(Map<String, String> reqParams,String signAlgo,String key) {

		String signData = ParameterUtil.getSignData(reqParams);
		log.info("Unsigned Data={}" + signData);
		String sign = "";
		try {
			sign = RSASignature.sign(signData, key,"");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return sign;
	}

	/**
	 * 调用alipay.wap.auth.authAndExecute服务的时候需要跳转到支付宝的页面，组装跳转url
	 * 
	 * @param reqParams
	 * @return
	 * @throws Exception
	 */
	private String getRedirectUrl(Map<String, String> reqParams,String reqUrl)
			throws Exception {
		String redirectUrl = reqUrl + "?";
		redirectUrl = redirectUrl + ParameterUtil.mapToUrl(reqParams);
		return redirectUrl;
	}

	/**
	 * 调用支付宝开放平台的服务
	 * 
	 * @param reqParams
	 *            请求参数
	 * @return
	 * @throws Exception
	 */
	private ResponseResult send(Map<String, String> reqParams,String reqUrl,String secId) throws Exception {
		String response = "";
		String invokeUrl = reqUrl  + "?";
		URL serverUrl = new URL(invokeUrl);
		HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection();

		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		conn.connect();
		String params = ParameterUtil.mapToUrl(reqParams);
		log.info("Request Token={}" + URLDecoder.decode(params, "utf-8"));
		conn.getOutputStream().write(params.getBytes());

		InputStream is = conn.getInputStream();

		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		StringBuffer buffer = new StringBuffer();
		String line = "";
		while ((line = in.readLine()) != null) {
			buffer.append(line);
		}
		response = URLDecoder.decode(buffer.toString(), "utf-8");
		conn.disconnect();
		return praseResult(response,secId);
	}

	/**
	 * 解析支付宝返回的结果
	 * 
	 * @param response
	 * @return
	 * @throws Exception
	 */
	private ResponseResult praseResult(String response,String secId) throws Exception {
		// 调用成功
		HashMap<String, String> resMap = new HashMap<String, String>();
		String v = ParameterUtil.getParameter(response, "v");
		String service = ParameterUtil.getParameter(response, "service");
		String partner = ParameterUtil.getParameter(response, "partner");
		String sign = ParameterUtil.getParameter(response, "sign");
		String reqId = ParameterUtil.getParameter(response, "req_id");
		resMap.put("v", v);
		resMap.put("service", service);
		resMap.put("partner", partner);
		resMap.put("sec_id", secId);
		resMap.put("req_id", reqId);
		String businessResult = "";
		ResponseResult result = new ResponseResult();
		if (response.contains("<err>")) {
			result.setSuccess(false);
			businessResult = ParameterUtil.getParameter(response, "res_error");

			// 转换错误信息
			XMapUtil.register(ErrorCode.class);
			ErrorCode errorCode = (ErrorCode) XMapUtil
					.load(new ByteArrayInputStream(businessResult
							.getBytes("UTF-8")));
			result.setErrorMessage(errorCode);

			resMap.put("res_error", ParameterUtil.getParameter(response,
					"res_error"));
		} else {
		    businessResult = ParameterUtil.getParameter(response, "res_data");
		    System.out.println("businessResult:"+businessResult);
            result.setSuccess(true);
            //对返回的res_data数据先用商户私钥解密
            String resData= RSASignature.decrypt(businessResult, aliPayContext.getRsaPrivate());
            System.out.println("resData:"+resData);
            result.setBusinessResult(resData);
            resMap.put("res_data", resData);
		}
		//获取待签名数据
		String verifyData = ParameterUtil.getSignData(resMap);
		System.out.println("verifyData:"+verifyData);
		//对待签名数据使用支付宝公钥验签名
		boolean verified = RSASignature.doCheck(verifyData, sign, aliPayContext.getRsaAlipayPublic(),"");
				
		if (!verified) {
			throw new Exception("验证签名失败");
		}
		return result;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getRedirectURL() {
		return redirectURL;
	}

	public void setRedirectURL(String redirectURL) {
		this.redirectURL = redirectURL;
	}

}
