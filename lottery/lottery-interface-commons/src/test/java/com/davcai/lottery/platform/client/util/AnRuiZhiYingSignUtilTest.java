package com.davcai.lottery.platform.client.util;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.davcai.lottery.platform.client.anruizhiying.constants.ParamNames;
import com.davcai.lottery.platform.client.anruizhiying.util.AnRuiZhiYingSignUtil;

public class AnRuiZhiYingSignUtilTest {
	@Test
	public void testSign(){
		List<String> toSignStrings=new ArrayList<String>();
		toSignStrings.add("802");
		toSignStrings.add("20");
		toSignStrings.add("94");
		toSignStrings.add("1");
		toSignStrings.add("10848~[客胜1-5]~0/1084~[客胜1-5]~0$2X1~1~1@1683663l");
		String sign = AnRuiZhiYingSignUtil.sign(toSignStrings, "NP9WRYE85R");
		System.out.println("sign="+sign);
	}
	
	@Test
	public void testVerify(){
		String sign="063A1441CF848FB75577ED19FF2E67DE";
		String ticketOrderResultNotifyContent="802^20~1686341~1~63401:周四004(0):[胜@1.41]~20150204163642~1";
		String key="NP9WRYE85R";
		String channelId="802";
		boolean result = AnRuiZhiYingSignUtil.verifySign4OrderTicketResult(sign, ticketOrderResultNotifyContent, key, channelId);
		assertTrue(result);
	}

}
