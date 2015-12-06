/**
 * 
 */
package com.xhcms.lottery.dc.persist.service;

import java.util.List;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.Bet;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.CustomMade;
import com.xhcms.lottery.commons.data.CustomMadeAvaiableScheme;

/**
 * @author Bean.Long
 *
 */
public interface CustomMadeService {
	public List<CustomMadeAvaiableScheme> listCustomMadeAvaiableSchemes();
	public void betFollow(CustomMade customMade, BetScheme betScheme, Bet bet);
	public void purchase(CustomMade cm, Long origalShemeId, int buyMoney);
	public void updateCustomMadeStatus(Long id, int custommadeStatusYes);
	public List<CustomMade> listCustomMades(long sponsorId, Paging paging);
	public List<CustomMade> listAllCustomMades (long sponsorId);
	public boolean checkCustomMadeDaily(Long userId, Long followedUserId, int maxMoney, int i,
			int maxFollowCount);
	public boolean checkCustomMadeCount(Long userId, Long followedUserId, int maxFollowCount);
	public int checkCustomMadeSum(Long userId, Long followUserId, int maxMoney, int buyMoney);
}
