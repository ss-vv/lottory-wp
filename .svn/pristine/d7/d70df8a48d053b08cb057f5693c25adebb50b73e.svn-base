package com.unison.lottery.itf;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class AnRuiOrderNotifyActionTest {

	@Test
	public void testAnRuiOrderNotifyAction() {
//		String interfaceUrl = "http://182.92.191.193:8080/rcv/anRuiOrderNotify";
//		String interfaceUrl = "http://127.0.0.1/lottery-interface-receiver/anrui/anrui_order_result.do";
		String interfaceUrl = "http://127.0.0.1/lottery-interface-receiver/anRuiOrderNotify";
		String finalParams = "802^35~1712829~1~63461:周五301(0):[主胜@1.05]/63462:周五302(0):[客胜1-5@3.65+主胜1-5@4.25+客胜6-10@3.75+主胜6-10@5.45+客胜11-15@6.50+主胜11-15@8.50+客胜16-20@10.00+主胜16-20@22.00+客胜21-25@19.00+主胜21-25@50.00+客胜26@29.00+主胜26@61.00]~20150206143842~20007842004026485821$35~1712826~1~63461:周五301(-9.50):[让分主负@1.70]/63462:周五302(0):[主胜@2.11]~20150206143842~20007026107845253206$35~1712827~1~63461:周五301(-9.50):[让分主负@1.70]/63462:周五302(0):[客胜1-5@3.65+主胜1-5@4.25+客胜6-10@3.75+主胜6-10@5.45+客胜11-15@6.50+主胜11-15@8.50+客胜16-20@10.00+主胜16-20@22.00+客胜21-25@19.00+主胜21-25@50.00+客胜26@29.00+主胜26@61.00]~20150206143842~20007613626551016662^FBDF788A6BB7E2196680A53E5DCDA7DB";
		if (StringUtils.isNotBlank(interfaceUrl)
				&& StringUtils.isNotBlank(finalParams)) {
			
			try {
//				finalParams=new String(finalParams.getBytes("utf-8"),"GB2312");
				HttpPost post = new HttpPost(interfaceUrl);
				List<NameValuePair> formParams = new ArrayList<NameValuePair>();
				formParams.add(new BasicNameValuePair("printresualt", finalParams));
				HttpEntity entity = new UrlEncodedFormEntity(formParams, "GB2312");
//				HttpEntity entity = new UrlEncodedFormEntity(formParams, "utf-8");
				post.setEntity(entity);
//				post.setHeader(name, value);
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpResponse httpResonse = httpClient.execute(post);
				String response = EntityUtils.toString(httpResonse.getEntity(),
						"GB2312");
				System.out.println(response);
				assertTrue(StringUtils.equals(response, "0"));
			} catch (ClientProtocolException e) {
			} catch (IOException e) {
			}
		}

	}
}