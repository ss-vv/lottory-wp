package com.davcai.lottery.push.common.model;

public class PushMessageFactory {

	public static PushMessage createTemplate(String matchId, String matchType) {
		PushMessage pushMessage=null;
		try{
			MessageType messageType=MessageType.valueOf(matchType);
			switch(messageType){
				case FOOTBALL:{pushMessage=new FootballMatchMessage();((FootballMatchMessage)pushMessage).setMatchId(matchId);break;}
				case BASKETBALL:{pushMessage=new BasketballMatchMessage();((BasketballMatchMessage)pushMessage).setMatchId(matchId);break;}
				default:break;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return pushMessage;
	}

}
