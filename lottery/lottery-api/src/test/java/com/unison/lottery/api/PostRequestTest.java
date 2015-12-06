package com.unison.lottery.api;

import java.io.File;
import java.io.IOException;
import junit.framework.Assert;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import com.unison.lottery.api.protocol.common.Constants;

public class PostRequestTest {

	private String url;

	private String path;

	private static String seed;

	private static String contentType;

	private int code;

	@BeforeClass
	public static void setUpBeforeClass() {
		seed = "12345678123456781234567812345678";
		contentType = "text/plain; charset='utf-8'";
	}

	@After
	public void tearDown() {
		File input = new File(path);
		FileEntity entity = new FileEntity(input, contentType);
		HttpPost post = new HttpPost(url);
		post.setEntity(entity);
		post.setHeader(Constants.SEED_PARAMETER_NAME, seed);
		HttpClient httpclient = new DefaultHttpClient();
		try {
			HttpResponse response = httpclient.execute(post);
			code = response.getStatusLine().getStatusCode();
			System.out.println("********");
			System.out.println("Response status code: " + code);
			System.out.println("Response body: ");
			response.getEntity().writeTo(System.out);
			System.out.println("\n********\n");

			if (200 != code) {
				Assert.fail();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			post.abort();
		}
	}
	
	// 查询跟单用户
	@Test
	@Ignore
	public void selectFollowTest() {
		url = "http://10.96.200.135/lottery-api/xml/security/selectFollow";
		path = "E:/workspace/unison/lottery-api/src/test/resources/lotteryInfo/selectFollow_encryption.txt";
	}
	
	//投注
	@Test
	@Ignore
	public void betSchemeTest() {
		url = "http://10.96.200.135/lottery-api/xml/security/betScheme";
		path = "E:/workspace/unison/hh_zc_api/davcai/lottery/lottery-api/src/test/resources/betScheme.txt";
	}
	
	//方案详情
	@Test
	@Ignore
	public void schemeDetailTest() {
		//url = "http://118.145.17.132:28080/lottery-api/xml/security/schemeDetail";
		url = "http://10.96.200.135/lottery-api/xml/security/schemeDetail";
		path = "E:/workspace/unison/hh_zc_api/davcai/lottery/lottery-api/src/test/resources/schemeDetail.txt";
	}
	
	//在售彩票
	@Test
	@Ignore
	public void queryOnSaleLotteryTest() {
		url = "http://10.96.200.135/lottery-api/xml/queryOnSaleLottery";
		url = "http://182.92.191.193:28080/lottery-api/xml/queryOnSaleLottery";
		path = "E:/workspace/unison/hh_zc_api/davcai/lottery/lottery-api/src/test/resources/queryOnSaleLottery.txt";
	}
	
	//查询可投注的传统足彩赛事
	@Test
	@Ignore
	public void queryCTZCMatchTest() {
		url = "http://10.96.200.135/lottery-api/xml/queryCTZCMatch";
		path = "E:/workspace/unison/hh_zc_api/davcai/lottery/lottery-api/src/test/resources/queryCTZCMatch.txt";
	}
	
	//开奖
	@Test
	@Ignore
	public void bonusResultTest() {
		url = "http://10.96.200.135/lottery-api/xml/bonusResult";
		path = "E:/workspace/unison/hh_zc_api/davcai/lottery/lottery-api/src/test/resources/lotteryInfo/lotteryInfo_encryption.txt";
	}
	
	// 彩种开奖信息详情
	@Test
	@Ignore
	public void lotteryResultTest() {
		url = "http://10.96.200.135/lottery-api/xml/lotteryResult";
		path = "E:/workspace/unison/hh_zc_api/davcai/lottery/lottery-api/src/test/resources/lotteryInfo/lotteryResult_encryption.txt";
	}
	
	//出票明细
	@Test
	@Ignore
	public void schemeTicketTest() {
		url = "http://10.96.200.135/lottery-api/xml/security/schemeTicket";
		path = "E:/workspace/unison/hh_zc_api/davcai/lottery/lottery-api/src/test/resources/schemeTicket.txt";
	}
	
	@Test
	public void queryJCZQMatchTest() {
//		url = "http://58.83.235.133:28080/lottery-api/xml/queryJCZQMatch";
		url = "http://182.92.191.193:28080/lottery-api/xml/queryJCZQMatch";
		path = "E:\\weibo\\weibo\\lottery\\lottery-api\\src\\test\\resources\\queryJCZQMatch.txt";
	}
}