package com.xhcms.lottery.alipay;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.xhcms.lottery.account.web.action.alipay.AlipayUtils;

public class AlipayUtilsTest {
   
	@Test
	public void testAlipayCreateLinkString(){
		Map<String, String> params = new HashMap<String,String>();
		params.put("aa", "AA");
		params.put("bb", "BB");
		params.put("cc", "CC");
		params.put("dd", "DD");
		params.put("ee", "EE"); 
		System.out.println("test:" + AlipayUtils.createLinkString(params));
	}
	
	@Test
	public void testAlipayPFString(){
		Map<String, String> params = new HashMap<String,String>();
		params.put("dd", "DD");
		params.put("cc", "CC");
		params.put("ee", "EE"); 
		params.put("aa", "AA");
		params.put("bb", "BB");
		params.put("ff", ""); 
		params.put("sign", "ZZZAZAxaaxaxaxaxax"); 
		params.put("sign_type", "aaaaaaxxwwxwwdwwdwdwAZAxaaxaxaxaxax"); 
		System.out.println("test:" + AlipayUtils.paraFilter(params));
	}
	
	@Test
	public void mainTest() {
		String str = "http://localhost:8082/lottery-account/callback/clientAlipayNotify.do?buyer_id=2088102216940311&trade_no=2015010769556731&body=大V彩客户端充值&use_coupon=N&notify_time=\"2015-01-07 10:51:21\"&subject=大V彩客户端充值&sign_type=RSA&is_total_fee_adjust=N&notify_type=trade_status_sync&out_trade_no=010710510751578&gmt_payment=\"2015-01-07 10:51:20\"&trade_status=TRADE_SUCCESS&discount=0.00&sign=YSysJIE+mP8GFTsUBs14J73zpcxCqcNy0RVjYEutWyl1k5NGj0tr3U7fAAPIYSHd2QKRsNzZUe828KUTTL5uPZn6Bko0LYOP0GWhbWqFM5y0RHVejlmhVF2t+Lp4+MFaLvxhNEOroI0/0ka6lQYZ7F5DQCHWkJINjXtKP5ZjVJA=&gmt_create=\"2015-01-07 10:51:20\"&buyer_email=axiang669@163.com&price=0.01&total_fee=0.01&seller_id=2088611118242558&quantity=1&seller_email=fuqiushuma@163.com&notify_id=d1c3e3fc156416799dffc3d07f8312063q&payment_type=1";
	    System.out.println("test:"+ str.substring(160));
	}
}
