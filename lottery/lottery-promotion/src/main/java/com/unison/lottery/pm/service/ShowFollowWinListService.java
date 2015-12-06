package com.unison.lottery.pm.service;

import java.util.List;

import com.unison.lottery.pm.data.ShowFollowResult;

/**
 * @author yongli zhu
 */
public interface ShowFollowWinListService {
	void countShowWinListAndSave();
	
	void countFollowWinListAndSave();
	
	List<ShowFollowResult> findShowFollowTOP(int top, Long promotionId);
	
	List<ShowFollowResult> findShowTOP(int top, Long promotionId);
}
