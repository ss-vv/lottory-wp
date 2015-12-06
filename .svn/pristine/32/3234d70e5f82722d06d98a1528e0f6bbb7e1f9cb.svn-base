package com.davcai.lottery.push.common.model;

import static org.junit.Assert.*;

import org.junit.Test;

import com.davcai.lottery.push.common.model.PushMessage;
import com.davcai.lottery.push.common.model.PushMessageFactory;

public class PushMessageFactoryTest {
	
	@Test
	public void testCreate(){
		
		String matchId = "20141127401";
		String matchType = "FOOTBALL";
		PushMessage message=PushMessageFactory.createTemplate(matchId,matchType);
		assertTrue(message instanceof FootballMatchMessage);
		FootballMatchMessage footballMatchMessage=(FootballMatchMessage) message; 
		assertTrue(footballMatchMessage.getMatchId().equals("20141127401"));
		
		matchId = "20141127402";
		matchType = "BASKETBALL";
		message=PushMessageFactory.createTemplate(matchId,matchType);
		assertTrue(message instanceof BasketballMatchMessage);
		BasketballMatchMessage basketballMatchMessage=(BasketballMatchMessage) message; 
		assertTrue(basketballMatchMessage.getMatchId().equals("20141127402"));
	}

}
