package com.unison.lottery.wap.action.user;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Map;

import org.apache.activemq.util.ByteArrayInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.wap.utils.alipay.security.RSASignature;
import com.unison.lottery.wap.utils.alipay.util.Notify;
import com.unison.lottery.wap.utils.alipay.util.XMapUtil;
import com.xhcms.lottery.commons.data.Recharge;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.ucenter.action.BaseAction;

public class NotifyAction extends BaseAction {

	private static final long serialVersionUID = -2563096642396403194L;

	private static final Logger log = LoggerFactory.getLogger(NotifyAction.class);
    
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private AliPayContext aliPayContext;
    
    private InputStream inputStream;
    
    @Override
    public String execute() {
    	//获取支付宝异步反馈信息
    	Map<String, String[]> map = request.getParameterMap();
    	log.info("notify start -----------");
        //获得通知签名
        String sign = (String) ((Object[]) map.get("sign"))[0];
        log.info("sign={}" + sign);
        //获得待验签名的数据
        String verifyData = getVerifyData(map);
        log.info("notify verifyData={}" + verifyData);
        String notifyData = getNotifyData(map);
        //解析xml到Notify
        XMapUtil.register(Notify.class);
        InputStream inStream = new ByteArrayInputStream(notifyData.getBytes());
        Notify notify = new Notify();
        try {
			notify = (Notify)XMapUtil.load(inStream);
		} catch (Exception e1) {
			log.info("解析xml出错！");
			e1.printStackTrace();
		}

		String tradeNo = notify.getTradeNo();				//支付宝交易号
		String tradeStatus = notify.getTradeStatus();		//交易状态
		String orderNo = notify.getOutTradeNo();	        //获取订单号
		//取得用户id
		Recharge recharge = accountService.getRecharge(new Long(orderNo));
		String userId = String.valueOf(recharge.getUserId());
		String amount = notify.getTotalFee();
		log.info("alipay ,tradeNo={}",tradeNo);
		log.info("alipay,tradeStatus={}",tradeStatus);
		log.info("order id,orderNo={}",orderNo);
		log.info("user id,userId={}",userId);
		log.info("trade,amount={}",amount);
        boolean verified = false;
        //使用支付宝公钥验签名
        try {
            verified=RSASignature.doCheck(verifyData, sign, aliPayContext.getRsaAlipayPublic(), "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //验证签名通过
        if (verified) {
        	if (tradeStatus.equals("TRADE_FINISHED")||tradeStatus.equals("TRADE_SUCCESS")) {
	        	//根据交易状态处理业务逻辑
	        	//判断该笔订单是否已经做过处理
	        	if(!accountService.isDealWithTransactionResult(new Long(orderNo))){
	        		accountService.comfirmRecharge(0, Long.parseLong(userId), new BigDecimal(amount), Long.parseLong(orderNo), tradeNo);
	        		log.info("wap alipay notify success!");
	        	}
	        	//当交易状态成功，处理业务逻辑成功。回写success
	        	inputStream = new ByteArrayInputStream("success".getBytes());
	        	log.info("sign check success!");
        	}
        } else {
        	inputStream = new ByteArrayInputStream("fail".getBytes());
            log.warn("wap charge is unsuccessful, orderId=" + orderNo + ", trxId=" + tradeNo);
        }
        
        return SUCCESS;
    }
    
    /**
     * 获得验签名的数据
     * @param map
     * @return
     * @throws Exception 
     */
	private String getVerifyData(Map<String, String[]> map) {
      String service = (String) ((Object[]) map.get("service"))[0];
        String v = (String) ((Object[]) map.get("v"))[0];
        String sec_id = (String) ((Object[]) map.get("sec_id"))[0];
        String notify_data = (String) ((Object[]) map.get("notify_data"))[0];
        try {
            //对返回的notify_data数据用商户私钥解密
            notify_data=RSASignature.decrypt(notify_data, aliPayContext.getRsaPrivate());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "service=" + service + "&v=" + v + "&sec_id=" + sec_id + "&notify_data="
               + notify_data;
    }
	
	/**
	 * 取得返回的参数数据
	 * @param map
	 * @return
	 */
	private String getNotifyData(Map<String, String[]> map) {
	        String notify_data = (String) ((Object[]) map.get("notify_data"))[0];
	        try {
	            //对返回的notify_data数据用商户私钥解密
	            notify_data=RSASignature.decrypt(notify_data, aliPayContext.getRsaPrivate());
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return notify_data;
	}

	public InputStream getInputStream() {
		return inputStream;
	}
}
