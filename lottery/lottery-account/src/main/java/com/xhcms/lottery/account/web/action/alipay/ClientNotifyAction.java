package com.xhcms.lottery.account.web.action.alipay;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.apache.activemq.util.ByteArrayInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.account.util.alipay.Rsa;
import com.xhcms.lottery.account.web.AliPayContext;
import com.xhcms.lottery.account.web.action.BaseAction;
import com.xhcms.lottery.commons.data.Recharge;
import com.xhcms.lottery.commons.data.VoucherUser;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.lottery.commons.persist.service.VoucherUserService;

/**
 * 客户端支付宝回调
 * @author Wang Lei
 *
 */
public class ClientNotifyAction extends BaseAction {

	private static final long serialVersionUID = -3873741360729086686L;

	private static final Logger log = LoggerFactory.getLogger(ClientNotifyAction.class);
    
    @Autowired
    private AccountService accountService;
    @Autowired
    private VoucherUserService voucherUserService;
    @Autowired
    private AliPayContext aliPayContext;
    
    private InputStream inputStream;
    
    @Override
    public String execute() {
        try {
        	//获取支付宝POST过来反馈信息
        	Map<String,String> params = new HashMap<String,String>();
        	makeCallBackParams(params);
			String signContent = AlipayUtils.createLinkString(AlipayUtils.paraFilter(params));
			log.info("signContent: {}", signContent);
			String sign = params.get("sign");
			String signType = params.get("sign_type");
			String tradeNo = params.get("trade_no");
			String amount = params.get("total_fee");
			String tradeStatus = params.get("trade_status");
			Long rechageId = new Long(params.get("out_trade_no"));
        	
        	if (signType.equalsIgnoreCase("RSA") && Rsa.doCheck(signContent, sign,
					aliPayContext.getRsaPublic())) {
        		
        		log.info("验签成功！");
				 if (tradeStatus.equals("TRADE_FINISHED")||tradeStatus.equals("TRADE_SUCCESS")) {
					 log.info("客户端支付宝处理成功！");
		         	//判断该笔订单是否已经做过处理
		         	if(!accountService.isDealWithTransactionResult(rechageId)){
		         		log.info("客户端支付宝订单没有处理！");
		        		Recharge recharge = accountService.getRecharge(rechageId);
		        		Long userId = recharge.getUserId();
		        		// use voucher
		        		VoucherUser vu = new VoucherUser();
            			try {
            				// 使用优惠劵,并获取优惠劵信息
            				vu = voucherUserService.useClientRechargeVoucher(rechageId, userId);
						} catch (Exception e) {
							e.printStackTrace();
							log.error("用户ID:"+userId+"  流水id:"+rechageId+" msg:{}",e);
						}
            			// 如果取得优惠劵信息说明使用成功！
						if (vu != null && vu.getId() != null) {
							// 充值到赠款
							accountService.rechargeSuccess(rechageId);
							accountService.grant(0, userId, new BigDecimal(amount), vu.getGrantType().getName());
						} else {
							// 正常充值
							accountService.comfirmRecharge(0, userId, new BigDecimal(amount), rechageId, tradeNo);
						}
		         		
		         		log.info("client alipay notify success!");
		         	}
		         	inputStream = new ByteArrayInputStream("success".getBytes());
		         	log.info("sign check success!");
		         }
        		
			} else {
            	inputStream = new ByteArrayInputStream("fail".getBytes());
                log.warn("Client alipay recharge is unsuccessful, orderId=" + rechageId + ", trxId=" + tradeNo);
            }
        } catch (Throwable t) {
        	t.printStackTrace();
            log.warn(t.getClass().getName() + " : " + t);
        } 

        return SUCCESS;
    }
    /**
     * 解析支付宝异步返回的参数
     * @param params
     */
	private void makeCallBackParams(Map<String, String> params) {
		log.info("解析支付宝异步通知的参数开始...");
		
		Enumeration<String> pNames=request.getParameterNames();
		while(pNames.hasMoreElements()){    
			String name=(String)pNames.nextElement();   
			String value=request.getParameter(name);    
			params.put(name, value);
		}			
    	for(String key : params.keySet()){
    		log.info(key + ":" + params.get(key));
    	}
	}

	public InputStream getInputStream() {
		return inputStream;
	}
}
