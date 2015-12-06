package com.davcai.lottery.platform.client.util;

import static org.junit.Assert.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.davcai.lottery.platform.client.anruizhiying.constants.ParamNames;
import com.davcai.lottery.platform.client.anruizhiying.util.AnRuiZhiYingSignUtil;
import com.davcai.lottery.platform.client.anruizhiying.util.AnRuiZhiYingUrlUtil;

public class AnRuiZhiYingUrlUtilTest {
	
	@Test
	public void testMakeFinalUrl(){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ParamNames.CHANNEL_ID, "803");
		params.put("transcode", "111");
		params.put(ParamNames.LOTTERY_ID, "20");
		params.put(ParamNames.WARE_ID, "94");
		params.put(ParamNames.WARE_ISSUE,"20110501");
		params.put(ParamNames.BATCH_ID, new BigInteger("20150110150600000001"));
		params.put(ParamNames.ADDFLAG, "0");
		params.put(ParamNames.BET_AMOUNT, 1);
		params.put(ParamNames.BET_CONTENT, "8841~[0,1,2,3,7+]~0/8845~[0,3,2]~0/8846~[0,1]~0$3X1~1~24@1683663l");
		params.put(ParamNames.REAL_NAME, "张三");
		params.put(ParamNames.ID_CARD, "4321000000");
		params.put(ParamNames.PHONE, "13312345678");
		
		LinkedHashSet<String> paramsShouldSign = new LinkedHashSet<String>();
		paramsShouldSign.add(ParamNames.CHANNEL_ID);
		paramsShouldSign.add(ParamNames.LOTTERY_ID);
		paramsShouldSign.add(ParamNames.WARE_ID);
		paramsShouldSign.add(ParamNames.BET_AMOUNT);
		paramsShouldSign.add(ParamNames.BET_CONTENT);
		
		String transCode = "101";
		String channelId = "802";
		String key="NP9WRYE85R";
		String finalParamsString = AnRuiZhiYingUrlUtil.makeFinalParamStringWithSign(transCode, channelId, params, paramsShouldSign,key);
		System.out.println("finalParamsString="+finalParamsString);
		
		List<String> toSignStrings=new ArrayList<String>();
		toSignStrings.add(channelId);
		toSignStrings.add(params.get(ParamNames.LOTTERY_ID).toString());
		toSignStrings.add(params.get(ParamNames.WARE_ID).toString());
		toSignStrings.add(params.get(ParamNames.BET_AMOUNT).toString());
		toSignStrings.add(params.get(ParamNames.BET_CONTENT).toString());
		String sign=AnRuiZhiYingSignUtil.sign(toSignStrings,key);
		
		
		Map<String, String> finalParamsMap=makeAttributesFromString(finalParamsString);
		String transCodeInUrl=(String) finalParamsMap.get(ParamNames.TRANSCODE);
		String channelIdInUrl=(String) finalParamsMap.get(ParamNames.CHANNEL_ID);
		String lotteryIdInUrl=(String) finalParamsMap.get(ParamNames.LOTTERY_ID);
		String wareIdInUrll=(String) finalParamsMap.get(ParamNames.WARE_ID);
		String wareIssueInUrl=(String) finalParamsMap.get(ParamNames.WARE_ISSUE);
		String batchIdInUrl=(String) finalParamsMap.get(ParamNames.BATCH_ID);
		String addFlagInUrl=(String) finalParamsMap.get(ParamNames.ADDFLAG);
		String betAmountInUrl=(String) finalParamsMap.get(ParamNames.BET_AMOUNT);
		String betContentInUrl=(String) finalParamsMap.get(ParamNames.BET_CONTENT);
		String realNameInUrl=(String) finalParamsMap.get(ParamNames.REAL_NAME);
		String idCardInUrl=(String) finalParamsMap.get(ParamNames.ID_CARD);
		String phoneInUrl=(String) finalParamsMap.get(ParamNames.PHONE);
		String signInUrl=(String) finalParamsMap.get(ParamNames.SIGN);
		
		assertTrue(StringUtils.equals(transCodeInUrl,"101"));
		assertTrue(StringUtils.equals(channelIdInUrl,"802"));
		assertTrue(StringUtils.equals(lotteryIdInUrl,"20"));
		assertTrue(StringUtils.equals(wareIdInUrll,"94"));
		assertTrue(StringUtils.equals(wareIssueInUrl,"20110501"));
		assertTrue(StringUtils.equals(batchIdInUrl,"20150110150600000001"));
		assertTrue(StringUtils.equals(addFlagInUrl,"0"));
		assertTrue(StringUtils.equals(betAmountInUrl,"1"));
		assertTrue(StringUtils.equals(betContentInUrl,"8841~[0,1,2,3,7%2b]~0/8845~[0,3,2]~0/8846~[0,1]~0$3X1~1~24@1683663l"));
		assertTrue(StringUtils.equals(realNameInUrl,"张三"));
		assertTrue(StringUtils.equals(idCardInUrl,"4321000000"));
		assertTrue(StringUtils.equals(phoneInUrl,"13312345678"));
		assertTrue(StringUtils.equals(signInUrl,sign));
	}

	private Map<String, String> makeAttributesFromString(String url) {
		Map<String,String> attributes=new HashMap<String,String>();
		
		String[] attributeSplits = url.split("&");
		String key4Attribute=null;
		String value4Attribute=null;
		String[] nameAndValue=null;
		for(String attributeSplit:attributeSplits){
			System.out.println("attributeSplit="+attributeSplit);
			nameAndValue = attributeSplit.split("=");
			if(null!=nameAndValue&&nameAndValue.length==2){
				key4Attribute=nameAndValue[0];
				value4Attribute=nameAndValue[1];
				attributes.put(key4Attribute, value4Attribute);
			}
			else{
				System.out.println("nameAndValue="+nameAndValue);
			}
			
		}
		return attributes;
	}
	

}
