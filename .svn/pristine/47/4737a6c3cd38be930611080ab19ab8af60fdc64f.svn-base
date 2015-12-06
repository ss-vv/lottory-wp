package com.unison.lottery.api;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List; 

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test; 

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.unison.lottery.api.callBack.model.FootballMatchMessage;
import com.unison.lottery.api.callBack.model.LiveScores;
import com.unison.lottery.api.callBack.util.HttpRequestUtil;
import com.unison.lottery.api.login.hx.comm.Constants;
import com.unison.lottery.api.login.hx.httpclient.apidemo.EasemobIMUsers;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.request.json.model.JsonMethodRequest;
import com.unison.lottery.api.vGroup.data.GroupSecretkey;
import com.xhcms.commons.util.Text;
import com.xhcms.lottery.utils.DES;

public class TsetJson {
  
	
	@Test
	public void toJson() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		LiveScores liveScores = new LiveScores();
		FootballMatchMessage footballMatchMessage = new FootballMatchMessage();
		footballMatchMessage.setMatchId("20151290930");
		footballMatchMessage.setGuestScore(3);
		footballMatchMessage.setHomeScore(4);
		footballMatchMessage.setHomeTeamName("主队A");
		footballMatchMessage.setGuestTeamName("客队B");
		liveScores.type = "0";
		liveScores.subType = "0";
		liveScores.footballMatchMessage = footballMatchMessage;
		String jsonString = mapper.writeValueAsString(liveScores);
		
		System.out.println("jsonString :  " + jsonString);
		
	}
	
	@Test
	public void toObject() throws Exception {
		String orignalData = "{\"name\":\"queryGroupInfo\",\"clientVersion\":\"android-dvc-1.0.0\",\"channel\":\"aVCHdHRvYvUaX1zEFOXHrJY39YtuX+KOfT3a45nvlEj3m61YW3CXDEoXPhAD BH+C\",\"deviceId\":\"test1\",\"packageName\":\"com.davcai.lottery\",\"modelName\":\"modelname\",\"imsi\":\"cssce_121_imsi\",\"imei\":\"cdsss_imei\",\"groupid\":\"1420796433197488\"}";
		System.out.println(orignalData);
		ObjectMapper mapper = new ObjectMapper();
		JsonMethodRequest user = mapper.readValue(orignalData, JsonMethodRequest.class);
		System.out.println("user"+ user.getChannel());
		
	}
   
	@Test
	public void testJoin(){
		String[] strs = new String[20];
		StringUtils.join(strs, "xxxx");
		System.out.println("strs: " + strs[0]);
		
	}
	@Test
	public void testResouce(){
		 String nameString = Text.MD5Encode("天秤座厉鬼");
	     String pasString = Text.MD5Encode("天秤座厉鬼" + VONames.HX_USE_STRINE);
	     System.out.println("name: " + nameString);
	     System.out.println("password: " + pasString);
	}
	
	@Test
	public void TestChannel() throws Exception{
		String channel = "1420796433197488+14060+android-dav-1.0.0";
		String  str = DES.encryptDES(channel, GroupSecretkey.secretKey, "utf-8");
		System.out.println("str:"+str);
	}
	@Test
	public void TestStr() throws Exception{
		String cc13265 = Text.MD5Encode("13265"+"davcaihuanxinid#davcai");
		String ccP13265 = Text.MD5Encode("13265" + "HX_nameAndHX_password");
		String cc24 = Text.MD5Encode("24"+"davcaihuanxinid#davcai");
		String ccP24 = Text.MD5Encode("24" + "HX_nameAndHX_password");
		String cc14601 = Text.MD5Encode("14601"+"davcaihuanxinid#davcai");
		String ccP14601 = Text.MD5Encode("14601" + "HX_nameAndHX_password");
		String cc13928 = Text.MD5Encode("13928"+"davcaihuanxinid#davcai");
		String ccP13928 = Text.MD5Encode("13928" + "HX_nameAndHX_password");
		String cc74 = Text.MD5Encode("74"+"davcaihuanxinid#davcai");
		String ccP74 = Text.MD5Encode("74" + "HX_nameAndHX_password");
		String cc14390 = Text.MD5Encode("14390"+"davcaihuanxinid#davcai");
		String ccP14390 = Text.MD5Encode("14390" + "HX_nameAndHX_password");
		String cc75 = Text.MD5Encode("75"+"davcaihuanxinid#davcai");
		String ccP75 = Text.MD5Encode("75" + "HX_nameAndHX_password");
		String cc14060 = Text.MD5Encode("14060"+"davcaihuanxinid#davcai");
		String ccP14060 = Text.MD5Encode("14060" + "HX_nameAndHX_password");
		String cc3815 = Text.MD5Encode("3815"+"davcaihuanxinid#davcai");
		String ccP3815 = Text.MD5Encode("3815" + "HX_nameAndHX_password");
		System.out.println(cc13265 + ": " + ccP13265);
		System.out.println(cc24 + ": " + ccP24);
		System.out.println(cc14601 + ": " + ccP14601);
		System.out.println(cc13928 + ": " + ccP13928);
		System.out.println(cc74 + ": " + ccP74);
		System.out.println(cc14390 + ": " + ccP14390);
		System.out.println(cc75 + ": " + ccP75);
		System.out.println(cc14060 + ": " + ccP14060);
		System.out.println(cc3815 + ": " + ccP3815);
	}
	
	@Test
	public void testSend5HttpGet() {
		String url = "http://182.92.191.193:28080/lottery-api/syncSendAllGroupMsg";
		String param = "type=5&host=AC&guest=BC&url=aaaaaaaaaaa&info=8989998ccd";
		System.out.println("url=" + url + param);
		System.out.println(HttpRequestUtil.sendGet(url, param));
	}
	@Test
	public void testSend2HttpGet() {
		String url = "http://182.92.191.193:28080/lottery-api/syncSendAllGroupMsg";
		String param = "type=2&content=AC";
		System.out.println("url=" + url + param);
		System.out.println(HttpRequestUtil.sendGet(url, param));
	}
	@Test
	public void testSend1HttpGet() {
		String url = "http://localhost:8080/lottery-api/syncSendAllGroupMsg";
		String param = "type=1&content=AC&activityId=21&activityUrl=http://cdcdcdcdcc&endTime=1&imageUrl=dscscsdc";
		System.out.println("url=" + url + param);
		System.out.println(HttpRequestUtil.sendGet(url, param));
	}
	@Test
	public void testSend3HttpGet() {
		String url = "http://localhost:8080/lottery-api/syncWinningScores";
		String param = "type=3&schemeId=711053";
		System.out.println("url=" + url + param);
		System.out.println(HttpRequestUtil.sendGet(url, param));
	}
	
	@Test
	public void testAddUser(){
		List<String> uList = new ArrayList<String>();
		uList.add("XXXX");
		uList.add("xxxx111");
		if(uList.contains("XXXX")){
			System.out.println("true");
		}else{
			System.out.println("false");
		}
	}
	@Test
	public void testUpDateGroupName(){
		ObjectNode datanode = JsonNodeFactory.instance.objectNode();
        datanode.put("username", Text.MD5Encode("3815" + Constants.APPKEY));
        datanode.put("password", Text.MD5Encode("3815" + VONames.HX_USE_STRINE));
        datanode.put("nickname", "v5德胜");
        ObjectNode createNewIMUserSingleNode = EasemobIMUsers.createNewIMUserSingle(datanode);
		System.out.println("test: " + createNewIMUserSingleNode.toString());
	}
	
	@Test
	public void testBigDecmal(){
		BigDecimal bigDecimal = new BigDecimal(1.392111221212);
		System.out.print("bigDecimal:"+bigDecimal+ " , "+bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP));
	}
	@Test
	public void testDate(){
		long xx = 1427454918707l;
		long xx2 = 1427452200000l;
		
		System.out.print("qq"+(xx-xx2)/(60*1000));
	}
	@Test
	public void test1(){
		String xx2 = "我么,sfe是的,是";
		String xx = "我么";
		String[] a = xx.split(",");
		String[] a2 = xx2.split(",");
		System.out.print(a[0]+" ; " + a2[0]+" " + a2[1]);
	}
	@Test
	public void test111(){
		ObjectNode result=null;
		try{
			 result = EasemobIMUsers.getShieldUsersByusername("c8ac34f56a173964c5fdf9e43f64560b", "1428486188141356", false);
		}catch(Throwable e){
			e.printStackTrace();
		}
		System.out.println(result);
	}
}

