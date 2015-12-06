package com.xhcms.lottery.account.web.action.alipay;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.activemq.util.ByteArrayInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.account.util.alipay.AlipayNotify;
import com.xhcms.lottery.account.web.AliPayContext;
import com.xhcms.lottery.account.web.action.BaseAction;
import com.xhcms.lottery.commons.persist.service.AccountService;

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
        try {	
        	//获取支付宝POST过来反馈信息
        	Map<String,String> params = new HashMap<String,String>();
        	Map requestParams = request.getParameterMap();
        	for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
        		String name = (String) iter.next();
        		String[] values = (String[]) requestParams.get(name);
        		String valueStr = "";
        		for (int i = 0; i < values.length; i++) {
        			valueStr = (i == values.length - 1) ? valueStr + values[i]
        					: valueStr + values[i] + ",";
        		}
        		//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
        		//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "UTF-8");
        		params.put(name, valueStr);
        	}
        	log.info("get return data,params={}",params);
        	String tradeNo = request.getParameter("trade_no");				//支付宝交易号
        	String tradeStatus = request.getParameter("trade_status");		//交易状态
        	String orderNo = request.getParameter("out_trade_no");	        //获取订单号
        	String userId = request.getParameter("extra_common_param");
        	String amount = request.getParameter("total_fee");
        	log.info("alipay,tradeNo={}",tradeNo);
        	log.info("alipay,tradeStatus={}",tradeStatus);
        	log.info("order id,orderNo={}",orderNo);
        	log.info("user id,userId={}",userId);
        	log.info("trade amount={}",amount);
        	AlipayNotify alipayNotify = new AlipayNotify();
        	String partner = aliPayContext.getPartner();
        	String alikey = aliPayContext.getKey();
            if (alipayNotify.verify(params, partner, alikey)) {
                if (tradeStatus.equals("TRADE_FINISHED")||tradeStatus.equals("TRADE_SUCCESS")) {
                	addActionMessage(getText("message.success"));
                	log.info("alipay return success!");
                }
                log.info("sign success!");
            } else {
                log.warn("Recharge is unsuccessful, orderId=" + orderNo + ", trxId=" + tradeNo);
            }
        } catch (Throwable t) {
            log.warn(t.getClass().getName() + " : " + t.getMessage());
        } 

        return SUCCESS;
    }

}
