package com.unison.lottery.api;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.util.Random; 

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.Test;

import com.unison.lottery.api.framework.utils.DecryptAndEncryptService;

public class XmlProtocolWithEncryptionJuintTest {

	private static final int CONNECTION_TIME_OUT = 6000000;
	private String strURL;
	private String orignalData;
	@Test
	public void test() {

//		orignalData="<methodRequest name=\"queryOnSaleLottery\" clientVersion=\"android-dav-1.0.5\" channel=\"/HJSocQnBOqzp3BrVH/tKn0g8NZhzz58Sz4CvE2PywKmxBbjEIksww==\" modelName=\"iPhone6,2\" deviceId=\"DC00D06A-76B3-45F6-AD90-A651E53945D1\" imei=\"DC00D06A-76B3-45F6-AD90-A651E53945D1\" packageName=\"com.davcai.lottery\" banner=\"0\" validId=\"5ac917c3-d8b2-475a-a711-1c07d45cc01c\"/>";
//		orignalData= "{\"name\" : \"queryUserInfo\", \"clientVersion\" : \"android-dav-1.0.1\", \"validId\" : \"ccda7310-184e-4ffe-8f03-8dfa5a500d62\", \"channel\" : \"/HJSocQnBOqzp3BrVH/tKqkEKKlwSL7ds7KhwhD7aBpbzS5vOf7wf0GWosm2 H738\", \"modelName\" : \"Evo 3D GSM\", \"deviceId\" : \"352647050610262\"}";
	//orignalData= "{\"name\" : \"querySysScheme\",\"clientVersion\" : \"android-dav-1.0.0\",\"validId\" : \"9b38d30d-d88b-4335-bd8d-d898be8213e4\", \"channel\" : \"aVCHdHRvYvUaX1zEFOXHrJY39YtuX+KOfT3a45nvlEgLjNpVjrNmMD4Uq8YL SY29\",\"modelName\" : \"MI 3W\",\"page\" : 1,\"type\" : \"1\",\"deviceId\" : \"864690021128114\", \"groupid\" : \"aVCHdHRvYvUaX1zEFOXHrKMYgoiTJ6Kz\", \"packageName\" : \"com.davcai.lottery\"}";
		// =============================================================================
//	orignalData="<methodRequest name=\"registe\" clientVersion=\"ios-dav-1.0.0\" channel=\"aVCHdHRvYvUaX1zEFOXHrKLscfLSNG5jtU5HGejLbhcgp6AFBwHRTg==\" deviceId=\"DC00D06A-76B3-45F6-AD90-A651E53945D1\" imei=\"DC00D06A-76B3-45F6-AD90-A651E53945D1\" mac=\"020000000000\" modelName=\"iPhone6,2\" packageName=\"com.davcai.lottery\" nickname=\"Believe9634\" uid=\"owO6LjmjY3kPnr4KNq94Mtf3kBeo\" platType=\"1\" imageUrl=\"http://wx.qlogo.cn/mmopen/VzQsdzsGScPCUibKPoMyIN5uF79YZnU5WdPlGlkeyxZSUFWW5VarqmeAXX5nVcrGeLwbotgnwZYW22LKbJZSTfw/0\" />";
//	orignalData="<methodRequest name=\"queryJCZQMatch\" clientVersion=\"android-dav-1.0.1\" validId=\"5c2b5f2b-9e5f-4b6b-a742-0b7beabce8d3\" channel=\"PBFLO5tPNy/49zu/0OGNVNt2AD7n3U8jdt9QSwXpFnFO5yDcX/VVMQ==\" modelName=\"LT26i\" deviceId=\"004402144247354\" playId=\"555_ZCFH_2\" leagueShortName=\"墨西哥超级联赛,巴西圣保罗州锦赛,德国乙级联赛,德国甲级联赛,意大利甲级联赛,日本乙级联赛,日本职业联赛,智利甲级联赛,法国乙级联赛,法国甲级联赛,澳大利亚超级联赛,美国职业大联盟,苏格兰超级联赛,苏格兰足总杯,英格兰冠军联赛,英格兰甲级联赛,英格兰超级联赛,英格兰足总杯,荷兰乙级联赛,荷兰甲级联赛,葡萄牙超级联赛,西班牙甲级联赛,阿根廷甲级联赛\" firstResult=\"0\"/>";
//	orignalData="<methodRequest name=\"queryJCLQMatch\" clientVersion=\"android-dav-1.0.0\"  channel=\"/HJSocQnBOqzp3BrVH/tKqkEKKlwSL7ds7KhwhD7aBpbzS5vOf7wf0GWosm2 H738\" modelName=\"MI 3W\" deviceId=\"864690021128114\" playId=\"08_LC_1\" leagueShortName=\"美职篮\" firstResult=\"0\"/>";
//	orignalData="<methodRequest name=\"queryCTZCMatch\" clientVersion=\"android-dav-1.0.0\" validId=\"4cf39f95-d980-48e3-8569-57d1dce2a978\" channel=\"/HJSocQnBOqzp3BrVH/tKqkEKKlwSL7ds7KhwhD7aBovFZB63PErknXTqC9u hC8Z\" modelName=\"Evo 3D GSM\" deviceId=\"352647050610262\" playId=\"27_ZC_JQ\" issueNumber=\"15033\"/>";
	//orignalData="<?xml version=\"1.0\" encoding=\"utf-8\"?><methodRequest name=\"lotteryResult\" clientVersion=\"android-dav-1.0.0\" channel=\"aVCHdHRvYvUaX1zEFOXHrEr8fC7TSATJ94ROb131/XySEs+zuHbHe6DLYvgz Mk+Q\" modelName=\"MI 3\" page=\"1\" deviceId=\"865411025013023\" lotteryId=\"JCLQ\" matchDate=\"2015-02-09\"/>";
//    orignalData="<methodRequest schemeId=\"711827\" modelName=\"LT26i\" name=\"schemeDetail\" displayMode=\"0\" validId=\"5ac917c3-d8b2-475a-a711-1c07d45cc01c\" clientVersion=\"android-dav-1.0.5\" channel=\"/HJSocQnBOqzp3BrVH/tKqkEKKlwSL7ds7KhwhD7aBpbzS5vOf7wf0GWosm2 H738\" deviceId=\"004402144247354\"/>";
//		orignalData="<methodRequest name=\"betScheme\" clientVersion=\"android-dav-1.0.3\" validId=\"70dc1d2e-38b9-47e1-8cd5-c297fd84a2bf\" channel=\"PBFLO5tPNy/49zu/0OGNVNt2AD7n3U8jdt9QSwXpFnFBYUTKcYBrnQ==\" modelName=\"Evo 3D GSM\" deviceId=\"352647050610262\" playId=\"555_ZCFH_2\" lotteryId=\"JCZQ\" multiple=\"1\" betNote=\"11\" passType=\"4@1,2@1,3@1\" isShow=\"1\" showType=\"0\" followedRatio=\"0\" betContent=\"201504304001-40013-false-brqspf,201504304002-40023-false-brqspf,201505014007-40073-false-brqspf,201505014008-40083-false-brqspf\" betType=\"0\" issueNumber=\"2015年04月30日\" buyAmount=\"\" floorAmount=\"\"/>";	
//		 orignalData=" <methodRequest name=\"betScheme\" clientVersion=\"android-dav-1.0.1\" validId=\"9d98b979-f8b4-47e9-904c-e02537b540a3\" channel=\"/HJSocQnBOqzp3BrVH/tKqkEKKlwSL7ds7KhwhD7aBpbzS5vOf7wf0GWosm2 H738\" modelName=\"MI 3\" deviceId=\"865411025013023\" playId=\"80_ZC_2\" lotteryId=\"JCZQ\" multiple=\"1\" betNote=\"2\" passType=\"2@1\" showType=\"0\" followedRatio=\"0\" betContent=\"201503087023-70233,201503087029-702930\" betType=\"1\" issueNumber=\"2015年03月08日\" buyAmount=\"1\" floorAmount=\"2\"/>";	
//		 orignalData="<methodRequest name=\"showAndFollow\" clientVersion=\"android-dav-1.0.4\" validId=\"\" channel=\"/HJSocQnBOqzp3BrVH/tKqkEKKlwSL7ds7KhwhD7aBpRYsrcWRlpqkINvITL pz1v\" modelName=\"MI 2\" type=\"1\" deviceId=\"867064019748696\" firstResult=\"8\" filter=\"ALL\"/>";	
//		 orignalData="<methodRequest modelName=\"LT26i\" clientVersion=\"android-dav-1.0.1\" channel=\"PBFLO5tPNy/49zu/0OGNVNt2AD7n3U8jdt9QSwXpFnFO5yDcX/VVMQ==\" deviceId=\"004402144247354\" name=\"queryImmediateIndexInfo\" time=\"2015-03-17\" banner=\"0\" matchType=\"1\"/>";
orignalData="<methodRequest name=\"betScheme\" clientVersion=\"android-dav-1.0.7\" validId=\"175f41f7-3378-4966-b2b2-7a58bea847fa\" channel=\"FRX7H5nsqusNFQS2b0BlemiVnyrMewr54VEfffDZoAnezgR7SqFnZw==\" modelName=\"GT-I9152\" deviceId=\"355795059626263\" playId=\"555_ZCFH_2\" lotteryId=\"JCZQ\" multiple=\"13\" betNote=\"48\" passType=\"5@6\" isShow=\"1\" showType=\"3\" followedRatio=\"5\" betContent=\"201507024001-40010-false-spf,201507034003-40030-false-spf,201507034003-40031-false-brqspf,201507034007-40070-false-spf,201507034007-40071-false-brqspf,201507034008-40080-false-spf,201507034008-40080-false-brqspf,201507034010-40103-false-spf\" betType=\"0\" issueNumber=\"2015年07月02日\" buyAmount=\"\" floorAmount=\"\"/>";
//		 orignalData="<methodRequest name=\"buyHistory\" clientVersion=\"android-dav-1.0.0\" validId=\"69607a87-2fe7-464a-a8cc-5f75a1fc692f\" channel=\"/HJSocQnBOqzp3BrVH/tKqkEKKlwSL7ds7KhwhD7aBovFZB63PErknXTqC9u hC8Z\" modelName=\"LT26i\" deviceId=\"004402144247354\" firstResult=\"0\" betType=\"0\"/>";
		 //orignalData="<methodRequest name=\"schemeTicket\" clientVersion=\"android-dav-1.0.0\" validId=\"e5b4852a-c10c-4032-a309-9e56d224b1c1\" channel=\"/HJSocQnBOqzp3BrVH/tKqkEKKlwSL7ds7KhwhD7aBovFZB63PErknXTqC9u hC8Z\" modelName=\"MI 3W\" deviceId=\"864690021128114\" schemeId=\"710769\" displayMode=\"0\"/>";
		//orignalData="<?xml version=\"1.0\" encoding=\"utf-8\"?><methodRequest modelName=\"GT-I9508\" phoneNumber=\"\" verifyCode=\"\" imageUrl=\"http://wx.qlogo.cn/mmopen/a1OWbzWNFpEOTPQ8NNxbkKbtW8ALuvOLtzPeEIHXqj9tJ7zNGcYnfGJ7QibNZ2pBzbk5tzeeCVdILjiagmtYTgVGb9iaPyGKxy1/0\" uid=\"owO6LjSACACAClEYbBFWlegzMplgbWBVan8\" name=\"registe\" nickname=\"HDppppppp\" channel=\"aVCHdHRvYvUaX1zEFOXHrJY39YtuX+KOfT3a45nvlEgLjNpVjrNmMD4Uq8YL SY29\" clientVersion=\"android-dav-1.0.0\" packagename=\"\" deviceId=\"357748051529934\" password=\"\" platType=\"1\"/>";
//		orignalData = "{\"name\":\"queryImmediateIndexDetails\",\"scdcdcdcd\":\"ppppppppp\",\"clientVersion\":\"android-dvc-1.0.0\",\"channel\":\"aVCHdHRvYvUaX1zEFOXHrJY39YtuX+KOfT3a45nvlEj3m61YW3CXDEoXPhAD BH+C\",\"deviceId\":\"test1\",\"matchType\":\"0\",\"matchId\":\"1091510\",\"corpId\":\"31\",\"oddsType\":\"1\",\"time\":\"2015-03-17\",\"packageName\":\"com.davcai.lottery\",\"modelName\":\"modelname\",\"imsi\":\"cssce_121_imsi\",\"imei\":\"cdsss_imei\",\"groupid\":\"aVCHdHRvYvUaX1zEFOXHrKMYgoiTJ6Kz\",\"page\":\"4\"}";
//		orignalData = "{\"type\":\"0\",\"subType\":\"1\",\"footballMatchMessage\":{\"state\":\"0\",\"guestTeamRed\":0,\"homeTeamRed\":0,\"homeTeamYellow\":0,\"guestTeamYellow\":0,\"homeTeamName\":\"主队A\",\"guestTeamName\":\"客队B\",\"homeTeamHalfScore\":0,\"guestTeamHalfScore\":0,\"matchChannelPrefix\":\"/public/match/\",\"shouldUnsubscribe\":false,\"matchId\":\"201501201008\",\"homeScore\":4,\"guestScore\":3}}";
//		orignalData = "{\"modelName\":\"GT-I9508\",\"subType\":\"\",\"leagueShortName\":\"\",\"imei\":\"\",\"imsi\":\"\",\"name\":\"queryImmediateIndexInfo\",\"matchType\":\"0\",\"matchId\":\"\",\"oddsType\":\"0\",\"corpId\":\"351\",\"channel\":\"/HJSocQnBOqzp3BrVH/tKqkEKKlwSL7ds7KhwhD7aBpFwDxYY+m57srAONUW fvX1\",\"clientVersion\":\"android-dav-1.0.2\",\"packageName\":\"com.davcai.lottery\",\"deviceId\":\"357748051529934\"}";
    orignalData = "{\"name\":\"sendRedEnvalope\",\"clientVersion\":\"android-dav-1.0.5\",\"validId\":\"7c69ff07-abcb-4cdc-82dd-bf93bfd3084d\",\"envalopeId\":\"48\",\"groupid\":\"/HJSocQnBOqzp3BrVH/tKhMW9qOxwpr9\",\"redEnvalopeAmount\":\"2000\",\"envalopeNum\":\"5\",\"channel\":\"PBFLO5tPNy/49zu/0OGNVNt2AD7n3U8jdt9QSwXpFnHf2IsK/Z6tBw==\", \"modelName\":\"MI 2\",\"deviceId\":\"860308026820916\",\"leagueShortName\":\"\",\"imsi\":\"\",\"imei\":\"\",\"packageName\":\"com.davcai.lottery\",\"subType\":\"0\",\"matchType\":\"1\"}";
	 orignalData = "{\"name\":\"grabRedEnvalope\",\"clientVersion\":\"android-dav-1.0.5\",\"validId\":\"baebdad7-a983-4946-97c5-77a3981bec22\",\"envalopeId\":\"295\",\"groupid\":\"/HJSocQnBOqzp3BrVH/tKhMW9qOxwpr9\",\"redEnvalopeAmount\":\"200\",\"envalopeNum\":\"2\",\"channel\":\"PBFLO5tPNy/49zu/0OGNVNt2AD7n3U8jdt9QSwXpFnHf2IsK/Z6tBw==\", \"modelName\":\"MI 2\",\"deviceId\":\"860308026820916\",\"leagueShortName\":\"\",\"imsi\":\"\",\"imei\":\"\",\"packageName\":\"com.davcai.lottery\",\"subType\":\"0\",\"matchType\":\"1\"}";
		System.out.println(orignalData);  
		//strURL = "http://localhost:8082/lottery- api/json/queryGroupMembers";
//		strURL = "http://localhost:80/lottery-api/xml/queryJCLQMatch";
//		strURL = "http://localhost:80/lottery-api/xml/queryJCZQMatch";
//		strURL = "http://localhost:80/lottery-api/xml/queryCTZCMatch";
//		strURL = "http://localhost:8080/lottery-api/xml/lotteryResult";
//		strURL = "http://localhost:80/lottery-api/xml/security/schemeDetail";
//		strURL = "http://localhost:80/lottery-api-test/xml/security/betScheme";

//		strURL = "http://localhost:80/lottery-api/xml/security/schemeDetail";
//		strURL = "http://localhost:80/lottery-api-test/xml/security/betScheme";
//		strURL = "http://localhost:80/lottery-api/xml/showAndFollow";
//		strURL = "http://localhost:80/lottery-api/xml/queryOnSaleLottery";
		strURL = "http://localhost:8080/lottery-api/xml/security/betScheme";
//		strURL = "http://localhost:80/lottery-api/xml/security/buyHistory";
		//strURL = "http://localhost:8080/lottery-api/xml/schemeTicket";
//		strURL = "http://localhost:80/lottery-api/xml/registe";
//		strURL = "http://localhost:8080/lottery-api/json/security/queryImmediateIndexDetails";
		strURL = "http://182.92.191.193:28080/lottery-api-test/json/security/sendRedEnvalope";
		strURL = "http://localhost:8080/lottery-api/json/security/sendRedEnvalope";
		strURL = "http://182.92.191.193:28080/lottery-api-test/json/security/grabRedEnvalope";
		strURL = "http://localhost:8080/lottery-api/json/security/grabRedEnvalope";
		//strURL = "http://localhost:8082/lottery-api/syncLiveScores";
//		strURL = "http://182.92.191.193:28080/lottery-api-test/json/queryImmediateIndexDetails";
//		strURL = "http://localhost:8080/lottery-api/json/queryScoreLiveInfo";
//		strURL = "http://182.92.191.78:28080/lott80ery-api/json/queryScoreLiveInfo";
//		strURL = "http://api.davcai.com/lottery-api/json/queryScoreLiveInfo";
//		strURL = "http://api-test.davcai.com/lottery-api-test/json/queryScoreLiveInfo";

		// Prepare HTTP post
		doOnePost();

	}


	private void doOnePost() {

		//System.out.println(orignalData);
		String encryptStr = DecryptAndEncryptService.encrypt(orignalData);
		//String encryptStr = orignalData;
		//System.out.println("encryptStr= " + encryptStr);
		//System.out.println(DecryptAndEncryptService.decrypt("V/iUhnlHLg6DfmlwzoLhwIGU1Wyq+dJq/Ni5eTRfo5TkoF/tVhgMXrZVIPPD zFhz5vf9RGFNrJU2fk8zt1N7RLIjEBMYT65e"));
		// strURL =
		// "http://218.200.230.149/miguring_server_test/s/xml/security/updateUserInfo";
		// strURL="http://218.200.230.149/miguring_server_test/s/xml/showChartDetail";
		// strURL =
		// "http://localhost:8080/clt_server1.8.1_branch/s/xml/showChartDetail";
		// Prepare HTTP post
		post(encryptStr);
	}

	private void post(String encryptStr) {
		HttpPost post = new HttpPost(strURL);
		// Request content will be retrieved directly
		// from the input stream
		try {
			StringEntity entity = new StringEntity(encryptStr, "utf-8");
			post.setEntity(entity);

			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters,
					CONNECTION_TIME_OUT);
			HttpConnectionParams.setSoTimeout(httpParameters,
					CONNECTION_TIME_OUT);

			// Get HTTP client
			HttpClient httpclient = new DefaultHttpClient(httpParameters);
			// Execute request

			HttpResponse response = httpclient.execute(post);
			// Display status code
			System.out.println("Response status code: "
					+ response.getStatusLine().getStatusCode());
			// Display response
			System.out.println("Response body: ");
			// response.getEntity().writeTo(System.out);
			String str = EntityUtils.toString(response.getEntity());
			System.out.println(str);
			str = DecryptAndEncryptService.decrypt(str);
			System.out.println(str);
		} catch (ClientProtocolException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			// Release current connection to the connection pool once you are
			// done
			post.abort();
		}
	}

	private String getMD5() {
		try {
			Random random = new Random();
			String str = "abcdefghijklmnopqldsfdsfsdfweewvrstuvwxyzQWERTYUIPLKJHGFDSAZXCVBNM0987654321";
			StringBuffer sb = new StringBuffer();
			// 获取一个随机字符串
			for (int i = 0; i < random.nextInt(10000); i++) {
				int j = random.nextInt(62);
				sb.append(str.charAt(j));
			}
			byte[] res = sb.toString().getBytes();
			MessageDigest md = MessageDigest.getInstance("MD5".toUpperCase());
			byte[] result = md.digest(res);
			for (int i = 0; i < result.length; i++) {
				md.update(result[i]);
			}
			byte[] hash = md.digest();

			StringBuffer d = new StringBuffer("");
			for (int i = 0; i < hash.length; i++) {
				int v = hash[i] & 0xFF;
				if (v < 16)
					d.append("0");
				d.append(Integer.toString(v, 16).toUpperCase() + "");
			}
			return d.toString();
		} catch (Exception e) {
			return null;
		}
	}
	// @Test
	// public void DeTest() {
	// String result =
	// "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";
	// result = DecryptAndEncryptService.decrypt(result, seed);
	// System.out.println(result);
	// }
	
	public static void main(String args[]){
		BigDecimal redEnvalopeAmountBigDecimal = new BigDecimal(100/100.0);
		BigDecimal account = new BigDecimal(1.0);
		System.out.println(account.compareTo(redEnvalopeAmountBigDecimal)==0);
	}

}
