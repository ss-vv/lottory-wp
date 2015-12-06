package com.unison.lottery.wap.action.user;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.wap.utils.alipay.security.RSASignature;
import com.unison.lottery.wap.utils.alipay.util.ParameterUtil;
import com.xhcms.ucenter.action.BaseAction;
/**
 * 
 * @author yonglizhu
 *
 */
public class ReturnAction extends BaseAction {

    private static final Logger log = LoggerFactory.getLogger(ReturnAction.class);
   
    @Autowired
    private AliPayContext aliPayContext;
    
    @Override
    public String execute() {
        //获得支付宝同步通知签名
        String sign = request.getParameter("sign");
        String result = request.getParameter("result");
        String requestToken = request.getParameter("request_token");
        String outTradeNo = request.getParameter("out_trade_no");
        String tradeNo = request.getParameter("trade_no");
        log.info("return start ----------");
        log.info("sign={}" + sign);
        log.info("result={}" + result);
        log.info("requestToken={}" + requestToken);
        log.info("orderNo={}" + outTradeNo);
        log.info("tradeNo={}" + tradeNo);
        Map<String,String> resMap  = new HashMap<String,String>();
        resMap.put("result", result);
        resMap.put("request_token", requestToken);
        resMap.put("out_trade_no", outTradeNo);
        resMap.put("trade_no", tradeNo);
        String verifyData = ParameterUtil.getSignData(resMap);
        boolean verified = false;

        //使用支付宝公钥验签名
        try {
            verified = RSASignature.doCheck(verifyData, sign, aliPayContext.getRsaAlipayPublic(), "");
	        if (!verified || !result.equals("success")) {
	        	log.info("failed sign!!");
	        } else {
	        	addActionMessage(getText("charge.success"));
	        	log.info("success!!");
	        }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return SUCCESS;
    }
}
