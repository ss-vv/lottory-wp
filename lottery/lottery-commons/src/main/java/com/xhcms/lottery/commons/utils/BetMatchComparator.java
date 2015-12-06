package com.xhcms.lottery.commons.utils;

import java.util.Comparator;
import java.util.Date;

import com.xhcms.lottery.commons.data.BetMatch;

public class BetMatchComparator< T extends BetMatch > implements Comparator<T> {

	@Override
	public int compare(T arg0, T arg1) {
		BetMatch betMatch1=arg0;
		BetMatch betMatch2=arg1;
		Date playingTimeOfBetMatch1 = betMatch1.getPlayingTime();
		Date playingTimeOfBetMatch2 = betMatch2.getPlayingTime();
		int codeOfBetMatch1 = Integer.parseInt(betMatch1.getCode());
		int codeOfBetMatch2 = Integer.parseInt(betMatch2.getCode());
		
		return playingTimeOfBetMatch1.compareTo(playingTimeOfBetMatch2)==0?codeOfBetMatch1-codeOfBetMatch2:playingTimeOfBetMatch1.compareTo(playingTimeOfBetMatch2);
	}

}
