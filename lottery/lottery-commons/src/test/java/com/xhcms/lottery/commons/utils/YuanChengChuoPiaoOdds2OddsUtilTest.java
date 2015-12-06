package com.xhcms.lottery.commons.utils;

import org.junit.Assert;
import org.junit.Test;

import com.xhcms.lottery.commons.data.Ticket;

public class YuanChengChuoPiaoOdds2OddsUtilTest {
	@Test
	public void testConvert(){
		
		
		Ticket tt = new Ticket();
		tt.setPlayId("05_ZC_2");//
		tt.setActualCode("700251310^7003513^7005563^");	
		long start = System.currentTimeMillis();
		String ss = YuanChengChuoPiaoOdds2OddsUtil.convert(tt, "7002@2.500+@1.100+@2.200|7003@3.500|7004@4.500");//7001@1.500+@1.800|7003@2.500
		long stop = System.currentTimeMillis();
		System.out.println(stop-start);
		System.out.println(ss);
		
		/*
		Ticket t = new Ticket();
		t.setPlayId("05_ZC_2");
		
		t.setActualCode("56|4|001|3|51|4|002|3|51|4|003|0,1");
		String s = YuanChengChuoPiaoOdds2OddsUtil.convert(
				t, "56|4|001|3(3.20)|51|4|002|3(2.43)|51|4|003|0(2.55),1(2.94)");
		System.out.println(s);
		
		Assert.assertEquals("SPF@4-001:[胜=3.20]/BRQSPF@4-002:[胜=2.43]/BRQSPF@4-003:[负=2.55,平=2.94]", s);
		System.out.println(s);
		
		t.setPlayId("01_ZC_2");
		t.setActualCode("4|001|3,1|4|002|3");
		s = YuanChengChuoPiaoOdds2OddsUtil.convert(
				t, "4|001|3(3.20),1(3.60)|4|002|3(1.43)");
		Assert.assertEquals("3.20A3.60-1.43", s);
		System.out.println(s);
		t.setPlayId("05_ZC_2");
		t.setActualCode("51|4|001|1|52|4|002|33,23,99");
		s = YuanChengChuoPiaoOdds2OddsUtil.convert(
				t, "51|4|001|1(3.45)|52|4|002|33(50.00),23(25.00),99(250.000)");
		Assert.assertEquals("BRQSPF@4-001:[平=3.45]/BF@4-002:[3:3=50.00,2:3=25.00,平其他=250.000]", s);
		System.out.println(s);
		*/
	}
}
