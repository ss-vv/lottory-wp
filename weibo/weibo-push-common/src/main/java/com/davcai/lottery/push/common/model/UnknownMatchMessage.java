package com.davcai.lottery.push.common.model;

public class UnknownMatchMessage extends MatchMessage {

	@Override
	public boolean isSame(PushMessage oldPushMessage) {
		return true;
	}

	@Override
	public MessageType getType() {
		return null;
	}

	@Override
	public void checkAndSetShouldUnsubscribe() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getStateDesc() {
		// TODO Auto-generated method stub
		return null;
	}

}
