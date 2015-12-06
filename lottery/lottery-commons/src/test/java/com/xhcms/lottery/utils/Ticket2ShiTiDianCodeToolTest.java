package com.xhcms.lottery.utils;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import com.xhcms.lottery.commons.data.Ticket;

public class Ticket2ShiTiDianCodeToolTest {

	@Test
	public void convertJCLQ_SF(){
		//复式
		Ticket needConverttiTicket = new Ticket();
		needConverttiTicket.setCode("530112-53022-53041");
		needConverttiTicket.setPassTypeId("3@1");
		needConverttiTicket.setMultiple(5);
		needConverttiTicket.setPlayId("07_LC_2");
		String ret = Ticket2ShiTiDianCodeTool.convert(needConverttiTicket);
		Assert.assertEquals("62222,竞彩足球胜平负,T,(62)(5301>12)(5302>2)(5304>1),3串1,5,2", ret);
		//单式
		needConverttiTicket.setCode("53061-53092");
		needConverttiTicket.setPassTypeId("1@1");
		needConverttiTicket.setMultiple(2);
		needConverttiTicket.setPlayId("07_LC_1");
		ret = Ticket2ShiTiDianCodeTool.convert(needConverttiTicket);
		Assert.assertEquals("62222,竞彩足球胜平负,T,(62)(5306>1)(5309>2),单关,2,2", ret);
	}
	
	@Test
	public void convertJCLQ_RFSF(){
		//复式
		Ticket needConverttiTicket = new Ticket();
		needConverttiTicket.setCode("530112-53022-53042");
		needConverttiTicket.setPassTypeId("3@3");
		needConverttiTicket.setMultiple(3);
		needConverttiTicket.setPlayId("06_LC_2");
		String ret = Ticket2ShiTiDianCodeTool.convert(needConverttiTicket);
		Assert.assertEquals("61111,竞彩足球胜平负,T,(61)(5301>12)(5302>2)(5304>2),3串3,3,2", ret);
		//单式
		needConverttiTicket.setCode("430121-430221");
		needConverttiTicket.setPassTypeId("1@1");
		needConverttiTicket.setMultiple(3);
		needConverttiTicket.setPlayId("06_LC_1");
		ret = Ticket2ShiTiDianCodeTool.convert(needConverttiTicket);
		Assert.assertEquals("61111,竞彩足球胜平负,T,(61)(4301>21)(4302>21),单关,3,2", ret);
	}
	
	@Test
	public void convertJCLQ_DXF(){
		//复式
		Ticket needConverttiTicket = new Ticket();
		needConverttiTicket.setCode("43012-43022-43032-43041-43052");
		needConverttiTicket.setPassTypeId("5@1");
		needConverttiTicket.setMultiple(3);
		needConverttiTicket.setPlayId("09_LC_2");
		String ret = Ticket2ShiTiDianCodeTool.convert(needConverttiTicket);
		Assert.assertEquals("64444,竞彩足球胜平负,T,(64)(4301>2)(4302>2)(4303>2)(4304>1)(4305>2),5串1,3,2", ret);
		//单式
		needConverttiTicket.setCode("330412");
		needConverttiTicket.setPassTypeId("1@1");
		needConverttiTicket.setMultiple(3);
		needConverttiTicket.setPlayId("09_LC_1");
		ret = Ticket2ShiTiDianCodeTool.convert(needConverttiTicket);
		Assert.assertEquals("64444,竞彩足球胜平负,T,(64)(3304>12),单关,3,2", ret);
	}
	
	@Test
	public void testJCLQ_SFC(){
		//复式
		Ticket needConverttiTicket = new Ticket();
		needConverttiTicket.setCode("530212-530504-530602-530701");
		needConverttiTicket.setPassTypeId("4@1");
		needConverttiTicket.setMultiple(2);
		needConverttiTicket.setPlayId("08_LC_2");
		String ret = Ticket2ShiTiDianCodeTool.convert(needConverttiTicket);
		Assert.assertEquals("63333,竞彩足球胜平负,T,(63)(5302>12)(5305>04)(5306>02)(5307>01),4串1,2,2", ret);
		
		needConverttiTicket.setCode("2302130111030515-2303140212040616");
		needConverttiTicket.setPassTypeId("2@1");
		needConverttiTicket.setMultiple(5);
		needConverttiTicket.setPlayId("08_LC_2");
		ret = Ticket2ShiTiDianCodeTool.convert(needConverttiTicket);
		Assert.assertEquals("63333,竞彩足球胜平负,T,(63)(2302>130111030515)(2303>140212040616),2串1,5,2", ret);
		
		//单式
		needConverttiTicket.setCode("4310110112021303140415051606");
		needConverttiTicket.setPassTypeId("1@1");
		needConverttiTicket.setMultiple(2);
		needConverttiTicket.setPlayId("08_LC_1");
		ret = Ticket2ShiTiDianCodeTool.convert(needConverttiTicket);
		Assert.assertEquals("63333,竞彩足球胜平负,T,(63)(4310>110112021303140415051606),单关,2,2", ret);
	}
	
	@Test
	public void testJCLQ_HH(){
		Ticket needConverttiTicket = new Ticket();
		needConverttiTicket.setCode("330121:SF-3302130112040516:FC");
		needConverttiTicket.setPassTypeId("2@1");
		needConverttiTicket.setMultiple(2);
		needConverttiTicket.setPlayId("10_LC_2");
		String ret = Ticket2ShiTiDianCodeTool.convert(needConverttiTicket);
		Assert.assertEquals("69999,竞彩足球胜平负,T,(69)(3301>62-21)(3302>63-130112040516),2串1,2,2", ret);
		
		needConverttiTicket.setCode("230111:FC-23022:DXF-230313:FC-23042:RFSF");
		needConverttiTicket.setPassTypeId("4@1");
		needConverttiTicket.setMultiple(2);
		needConverttiTicket.setPlayId("10_LC_2");
		ret = Ticket2ShiTiDianCodeTool.convert(needConverttiTicket);
		Assert.assertEquals("69999,竞彩足球胜平负,T,(69)(2301>63-11)(2302>64-2)(2303>63-13)(2304>61-2),4串1,2,2", ret);
	}
	
	//14场胜负
	@Test
	public void testCTZC_14_DS(){
		Ticket needConverttiTicket = new Ticket();
		needConverttiTicket.setCode("0,1,3,1,3,0,1,1,0,3,0,3,3,1");
		needConverttiTicket.setPassTypeId("DS");
		needConverttiTicket.setPlayId("24_ZC_14");
		needConverttiTicket.setMultiple(1);
		needConverttiTicket.setMoney(2);
		String ret = Ticket2ShiTiDianCodeTool.convert(needConverttiTicket);
		System.out.println(ret);
	}
	
	@Test
	public void testCTZC_14_FS(){
		Ticket needConverttiTicket = new Ticket();
		needConverttiTicket.setCode("3,3,1,1,1,0,30,30,0,0,0,3,0,3");
		needConverttiTicket.setPassTypeId("FS");
		needConverttiTicket.setPlayId("24_ZC_14");
		String ret = Ticket2ShiTiDianCodeTool.convert(needConverttiTicket);
		System.out.println(ret);
	}

	//任9
	@Test
	public void test25_ZC_R9_DS(){
		Ticket needConverttiTicket = new Ticket();
		needConverttiTicket.setCode("-,-,3,0,0,3,3,-,0,0,3,3,-,-");
		needConverttiTicket.setPassTypeId("DS");
		needConverttiTicket.setPlayId("25_ZC_R9");
		String ret = Ticket2ShiTiDianCodeTool.convert(needConverttiTicket);
		System.out.println(ret);
	}
	
	@Test
	public void test25_ZC_R9_FS(){
		Ticket needConverttiTicket = new Ticket();
		needConverttiTicket.setCode("3,0,30,0,3,0,3,0,0,-,-,-,-,-");
		needConverttiTicket.setPassTypeId("FS");
		needConverttiTicket.setPlayId("25_ZC_R9");
		String ret = Ticket2ShiTiDianCodeTool.convert(needConverttiTicket);
		System.out.println(ret);
	}
	
	//6场半全
	@Test
	public void test26_ZC_BQ_DS(){
		Ticket needConverttiTicket = new Ticket();
		needConverttiTicket.setCode("1,3,3,3,1,1,1,1,0,0,0,1");
		needConverttiTicket.setPassTypeId("DS");
		needConverttiTicket.setPlayId("26_ZC_BQ");
		String ret = Ticket2ShiTiDianCodeTool.convert(needConverttiTicket);
		System.out.println(ret);
	}
	
	@Test
	public void test26_ZC_BQ_FS(){
		Ticket needConverttiTicket = new Ticket();
		needConverttiTicket.setCode("1,1,3,3,0,31,10,3,31,3,31,10");
		needConverttiTicket.setPassTypeId("FS");
		needConverttiTicket.setPlayId("26_ZC_BQ");
		String ret = Ticket2ShiTiDianCodeTool.convert(needConverttiTicket);
		System.out.println(ret);
	}
	
	//4场进球
	@Test
	public void test27_ZC_JQ_DS(){
		Ticket needConverttiTicket = new Ticket();
		needConverttiTicket.setCode("2,2,1,1,2,3,3,0");
		needConverttiTicket.setPassTypeId("DS");
		needConverttiTicket.setPlayId("27_ZC_JQ");
		String ret = Ticket2ShiTiDianCodeTool.convert(needConverttiTicket);
		System.out.println(ret);
	}
	
	@Test
	public void test27_ZC_JQ_FS(){
		Ticket needConverttiTicket = new Ticket();
		needConverttiTicket.setCode("0,0,0,0123,0,0,0,0");
		needConverttiTicket.setPassTypeId("FS");
		needConverttiTicket.setPlayId("27_ZC_JQ");
		String ret = Ticket2ShiTiDianCodeTool.convert(needConverttiTicket);
		System.out.println(ret);
	}
	
	@Test
	public void testUpgradeBetContent(){
		String orignBetContent="59999,竞彩足球胜平负,T,(59)(1001>52-31)(1002>54-00)(1003>51-09)(1004>53-3)(1004>56-3),5串1,1,2";
		String newBetContent=Ticket2ShiTiDianCodeTool.upgradeBetContent(orignBetContent);
		assertTrue(StringUtils.equals(newBetContent, "59999,竞彩足球胜平负,T,(59)(1001>2-31)(1002>4-00)(1003>1-09)(1004>3-3)(1004>6-3),5串1,1,2"));
		
		orignBetContent="69999,竞彩足球胜平负,T,(69)(1001>62-31)(1002>64-00)(1003>61-09)(1004>63-3),4串1,1,2";
		newBetContent=Ticket2ShiTiDianCodeTool.upgradeBetContent(orignBetContent);
		assertTrue(StringUtils.equals(newBetContent, "69999,竞彩足球胜平负,T,(69)(1001>2-31)(1002>4-00)(1003>1-09)(1004>3-3),4串1,1,2"));
		
		orignBetContent="63333,竞彩足球胜平负,T,(63)(7303>16),单关,10,2";
		newBetContent=Ticket2ShiTiDianCodeTool.upgradeBetContent(orignBetContent);
		assertTrue(StringUtils.equals(newBetContent, "63333,竞彩足球胜平负,T,(63)(7303>16),单关,10,2"));
		
		orignBetContent="52222,竞彩足球胜平负,T,(52)(1002>244-2)(1003>03)(1004>994-0),3串1,3,2";
		newBetContent=Ticket2ShiTiDianCodeTool.upgradeBetContent(orignBetContent);
		assertTrue(StringUtils.equals(newBetContent, "52222,竞彩足球胜平负,T,(52)(1002>244-2)(1003>03)(1004>994-0),3串1,3,2"));
	}
	
	@Test
	public void testDowngradeBetContent(){
		String orignBetContent="59999,竞彩足球胜平负,T,(59)(1001>2-31)(1002>4-00)(1003>1-09)(1004>3-3)(1004>6-3),5串1,1,2";
		String newBetContent=Ticket2ShiTiDianCodeTool.downgradeBetContent(orignBetContent);
		assertTrue(StringUtils.equals(newBetContent, "59999,竞彩足球胜平负,T,(59)(1001>52-31)(1002>54-00)(1003>51-09)(1004>53-3)(1004>56-3),5串1,1,2"));
		
		orignBetContent="69999,竞彩足球胜平负,T,(69)(1001>2-31)(1002>4-00)(1003>1-09)(1004>3-3),4串1,1,2";
		newBetContent=Ticket2ShiTiDianCodeTool.downgradeBetContent(orignBetContent);
		assertTrue(StringUtils.equals(newBetContent, "69999,竞彩足球胜平负,T,(69)(1001>62-31)(1002>64-00)(1003>61-09)(1004>63-3),4串1,1,2"));
		
		orignBetContent="63333,竞彩足球胜平负,T,(63)(7303>16),单关,10,2";
		newBetContent=Ticket2ShiTiDianCodeTool.downgradeBetContent(orignBetContent);
		assertTrue(StringUtils.equals(newBetContent, "63333,竞彩足球胜平负,T,(63)(7303>16),单关,10,2"));
		
		orignBetContent="52222,竞彩足球胜平负,T,(52)(1002>244-2)(1003>03)(1004>994-0),3串1,3,2";
		newBetContent=Ticket2ShiTiDianCodeTool.downgradeBetContent(orignBetContent);
		assertTrue(StringUtils.equals(newBetContent, "52222,竞彩足球胜平负,T,(52)(1002>244-2)(1003>03)(1004>994-0),3串1,3,2"));
	}
	
}
