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
import com.xhcms.lottery.commons.data.VoucherUser;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.lottery.commons.persist.service.VoucherUserService;

public class NotifyAction extends BaseAction {

	private static final long serialVersionUID = 6288246022979740696L;

	private static final Logger log = LoggerFactory.getLogger(NotifyAction.class);
    
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private AliPayContext aliPayContext;
    
    @Autowired
    private VoucherUserService voucherUserService;
    
    private InputStream inputStream;
    
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
        	log.info("get notify data,params={}",params);
        	String tradeNo = request.getParameter("trade_no");				//支付宝交易号
        	String tradeStatus = request.getParameter("trade_status");		//交易状态
        	String orderNo = request.getParameter("out_trade_no");	        //获取订单号
        	String userId = request.getParameter("extra_common_param");
        	String amount = request.getParameter("total_fee");
        	log.info("alipay ,tradeNo={}",tradeNo);
        	log.info("tradeStatus={}",tradeStatus);
        	log.info("order id,orderNo={}",orderNo);
        	log.info("user id,userId={}",userId);
        	log.info("trade,amount={}",amount);
        	AlipayNotify alipayNotify = new AlipayNotify();
        	String partner = aliPayContext.getPartner();
        	String alikey = aliPayContext.getKey();
            if (alipayNotify.verify(params, partner, alikey)) {
                if (tradeStatus.equals("TRADE_FINISHED")||tradeStatus.equals("TRADE_SUCCESS")) {
                	//判断该笔订单是否已经做过处理
                	if(!accountService.isDealWithTransactionResult(new Long(orderNo))){
                		
                		try{
                			Long uId = Long.parseLong(userId);
                			Long rId = Long.parseLong(orderNo);
                			BigDecimal ramount = new BigDecimal(amount);
                			VoucherUser vu = new VoucherUser();
                			try {
                				// 使用优惠劵,并获取优惠劵信息
                				vu = voucherUserService.useWebRechargeVoucher(rId, uId);
							} catch (Exception e) {
								e.printStackTrace();
								log.error("用户ID:"+uId+"  流水id:"+rId+" msg:{}",e);
							}
                			// 如果取得优惠劵信息说明使用成功！
							if (vu != null && vu.getId() != null) {
								// 充值到赠款
								accountService.rechargeSuccess(rId);
								accountService.grant(0, uId, ramount, vu.getGrantType().getName());
							} else {
								// 正常充值
								accountService.comfirmRecharge(0, uId, ramount, rId, tradeNo);
							}
                		}catch(Exception e) {
                			e.printStackTrace();
                			log.info("支付宝异步回调出错！");
                		}
                		
                		log.info("alipay notify success!");
                	}
                	inputStream = new ByteArrayInputStream("success".getBytes());
                	log.info("sign check success!");
                }
            } else {
            	inputStream = new ByteArrayInputStream("fail".getBytes());
                log.warn("Recharge is unsuccessful, orderId=" + orderNo + ", trxId=" + tradeNo);
            }
        } catch (Throwable t) {
            log.warn(t.getClass().getName() + " : " + t.getMessage());
        } 

        return SUCCESS;
    }

	public InputStream getInputStream() {
		return inputStream;
	}
}
