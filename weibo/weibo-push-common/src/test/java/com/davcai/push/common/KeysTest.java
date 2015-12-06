package com.davcai.push.common;

import static org.junit.Assert.*;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import com.davcai.lottery.push.common.model.FootballMatchMessage;
import com.unison.lottery.weibo.common.nosql.impl.Keys;

public class KeysTest {
	@Test
	public void testPushMessageKey(){
		String pushMessageId="FOOTBALL:20141126303";
		FootballMatchMessage message=new FootballMatchMessage();
		message.setMatchId("20141126303");
		
		String key = Keys.pushMessageKey(message.getId(),message.getClass());
		assertTrue(StringUtils.equals(key, "id:FootballMatchMessage:"+pushMessageId));
	}

}
