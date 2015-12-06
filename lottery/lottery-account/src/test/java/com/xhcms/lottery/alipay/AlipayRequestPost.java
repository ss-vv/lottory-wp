package com.xhcms.lottery.alipay;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;

public class AlipayRequestPost {

	public static String strURL = "http://localhost:8082/lottery-account/callback/clientAlipayNotify.do";

	static String buyer_id = "2088102216940311";
	static String trade_no = "2015010769556731";
	static String body = "大V彩客户端充值";
	static String use_coupon = "N";
	static String notify_time = "2015-01-07 10:51:21";
	static String subject = "大V彩客户端充值";
	static String sign_type = "RSA";
	static String is_total_fee_adjust = "N";
	static String notify_type = "trade_status_sync";
	static String out_trade_no = "010710510751578";
	static String gmt_payment = "2015-01-07 10:51:20";
	static String trade_status = "TRADE_SUCCESS";
	static String discount = "0.00";
	static String sign = "YSysJIE+mP8GFTsUBs14J73zpcxCqcNy0RVjYEutWyl1k5NGj0tr3U7fAAPIYSHd2QKRsNzZUe828KUTTL5uPZn6Bko0LYOP0GWhbWqFM5y0RHVejlmhVF2t+Lp4+MFaLvxhNEOroI0/0ka6lQYZ7F5DQCHWkJINjXtKP5ZjVJA=";
	static String gmt_create = "2015-01-07 10:51:20";
	static String buyer_email = "axiang669@163.com";
	static String price = "0.01";
	static String total_fee = "0.01";
	static String seller_id = "2088611118242558";
	static String quantity = "1";
	static String seller_email = "fuqiushuma@163.com";
	static String notify_id = "d1c3e3fc156416799dffc3d07f8312063q";
	static String payment_type = "1";

	@Test
	public void doPost() {
		HttpPost post = new HttpPost(strURL);
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("buyer_id", buyer_id));
		nameValuePairs.add(new BasicNameValuePair("trade_no", trade_no));
		nameValuePairs.add(new BasicNameValuePair("body", body));
		nameValuePairs.add(new BasicNameValuePair("use_coupon", use_coupon));
		nameValuePairs.add(new BasicNameValuePair("notify_time", notify_time));
		nameValuePairs.add(new BasicNameValuePair("subject", subject));
		nameValuePairs.add(new BasicNameValuePair("sign_type", sign_type));
		nameValuePairs.add(new BasicNameValuePair("is_total_fee_adjust", is_total_fee_adjust));
		nameValuePairs.add(new BasicNameValuePair("notify_type", notify_type));
		nameValuePairs.add(new BasicNameValuePair("out_trade_no", out_trade_no));
		nameValuePairs.add(new BasicNameValuePair("gmt_payment", gmt_payment));
		nameValuePairs.add(new BasicNameValuePair("trade_status", trade_status));
		nameValuePairs.add(new BasicNameValuePair("discount", discount));
		nameValuePairs.add(new BasicNameValuePair("sign", sign));
		nameValuePairs.add(new BasicNameValuePair("gmt_create", gmt_create));
		nameValuePairs.add(new BasicNameValuePair("buyer_email", buyer_email));
		nameValuePairs.add(new BasicNameValuePair("price", price));
		nameValuePairs.add(new BasicNameValuePair("total_fee", total_fee));
		nameValuePairs.add(new BasicNameValuePair("seller_id", seller_id));
		nameValuePairs.add(new BasicNameValuePair("quantity", quantity));
		nameValuePairs.add(new BasicNameValuePair("seller_email", seller_email));
		nameValuePairs.add(new BasicNameValuePair("notify_id", notify_id));
		nameValuePairs.add(new BasicNameValuePair("payment_type", payment_type));
		try {
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		// Get HTTP client
		HttpClient httpclient = new DefaultHttpClient();
		// Execute request
		try {
			HttpResponse response = httpclient.execute(post);
			System.out.println("Response status code: " + response.getStatusLine().getStatusCode());
			System.out.println("Response body: ");
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			post.abort();
		}
	}
}