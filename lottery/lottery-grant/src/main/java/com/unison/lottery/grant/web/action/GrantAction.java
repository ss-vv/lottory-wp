package com.unison.lottery.grant.web.action;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.activemq.util.ByteArrayInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.grant.util.RingNotify;
import com.unison.lottery.grant.web.RingContext;
import com.xhcms.lottery.commons.data.ColorRingGrant;
import com.xhcms.lottery.commons.persist.service.ColorRingService;

public class GrantAction extends BaseAction {
	private static final long serialVersionUID = -3304071859597509062L;
	
	private static final Logger log = LoggerFactory.getLogger(GrantAction.class);
	
	@Autowired
	private ColorRingService colorRingService;
	
	@Autowired
	private RingContext ringContext;
	
    private InputStream inputStream;
	
    @Override
    public String execute() {
    	//交易流水号
    	String tradeNo = request.getParameter("tradeNo");
    	//彩铃订购成功时间
    	String orderTime = request.getParameter("orderTime");
    	//手机号
    	String mobile = request.getParameter("mobile");
    	//赠送金额
    	String grantAmount = request.getParameter("grantAmount");
    	//签名
    	String sign = request.getParameter("sign");
    	String username = ringContext.getUsername();
    	String password = ringContext.getPassword();
    	
    	log.info("tradeNo={}", tradeNo);
    	log.info("orderTime={}", orderTime);
    	log.info("mobile={}", mobile);
    	log.info("grantAmount={}", grantAmount);
    	log.info("sign={}", sign);
    	log.info("username={}", username);
    	log.info("password={}", password);
    	
    	Map<String, String> params = new HashMap<String, String>();
    	params.put("tradeNo", tradeNo);
    	params.put("orderTime", orderTime);
    	params.put("mobile", mobile);
    	params.put("grantAmount", grantAmount);
    	params.put("username", username);
    	params.put("sign", sign);
    	
    	if (RingNotify.verify(params, password)) {
    		if (Integer.parseInt(grantAmount)>=5000 && Integer.parseInt(grantAmount) <= 0) {
    			inputStream = new ByteArrayInputStream("000002".getBytes());
    			log.info("Color ring order grant failure,The price is not legitimate");
    		} else {
    			//是否处理过
				if (!colorRingService.whetherTreatment(tradeNo)) {
					try{
		    			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		    			ColorRingGrant crGrant = new ColorRingGrant();
		    			crGrant.setTradeNo(tradeNo);
		    			try {
							crGrant.setOrderTime(sdf.parse(orderTime));
						} catch (ParseException e) {
							e.printStackTrace();
							inputStream = new ByteArrayInputStream("000004".getBytes());
							log.error("orderTime is error, orderTime={}", orderTime);
						}
		    			crGrant.setMobile(mobile);
		    			crGrant.setGrantAmount(Integer.parseInt(grantAmount));
		    			crGrant.setCreatedTime(new Date());
		    			crGrant.setStatus("0");
		    			colorRingService.addColorRingGrant(crGrant);
		    		} catch (Exception e) {
		    			e.printStackTrace();
		    			inputStream = new ByteArrayInputStream("000005".getBytes());
		    			log.error("彩铃订购赠送购彩金出错！");
		    		}
					inputStream = new ByteArrayInputStream("000000".getBytes());
					log.info("Color ring order grant treatment success!");
				} else {
					inputStream = new ByteArrayInputStream("000003".getBytes());
					log.info("Has already been processed!");
				}
	
    		}
    		
    	} else {
    		inputStream = new ByteArrayInputStream("000001".getBytes());
    		log.info("color ring order grant check failure!");
    	}
   	
    	return SUCCESS;
    }

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

}
