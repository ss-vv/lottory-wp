package com.xhcms.lottery.commons.utils;

import java.util.Comparator;
import java.util.Date;

import com.xhcms.lottery.commons.persist.entity.MatchPlay;
import com.xhcms.lottery.commons.persist.entity.MixMatchPlay;

/**
 * 比较MixMatchPlay对象或其子类对象，先比较playingTime再比较code
 * @author 陈岩
 *
 * @param <T>
 */
public class MatchPlayComparator< T extends MatchPlay > implements Comparator<T> {


	@Override
	public int compare(T o1, T o2) {
		MatchPlay matchPlay1=o1;
		MatchPlay matchPlay2=o2;
		Date playingTimeOfMatchPlay1 = matchPlay1.getPlayingTime();
		Date playingTimeOfMatchPlay2 = matchPlay2.getPlayingTime();
		int codeOfMatchPlay1 = Integer.parseInt(matchPlay1.getCode());
		int codeOfMatchPlay2 = Integer.parseInt(matchPlay2.getCode());
		
		return playingTimeOfMatchPlay1.compareTo(playingTimeOfMatchPlay2)==0?codeOfMatchPlay1-codeOfMatchPlay2:playingTimeOfMatchPlay1.compareTo(playingTimeOfMatchPlay2);
	}

}
