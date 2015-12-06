/**
 * 
 */
package com.xhcms.lottery.commons.persist.dao;

import java.util.List;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.persist.entity.CustomMadePO;

/**
 * @author Bean.Long
 *
 */
public interface CustomMadeDao extends Dao<CustomMadePO> {

	CustomMadePO findCustomMade(long userId, long followedUserId);

	List<CustomMadePO> findMyCustomMades(long userId, Paging paging);

	List<CustomMadePO> findFollowMeCustomMades(long userId, Paging paging);
	List<CustomMadePO> topFollowMeCustomMades(long userId, int maxResult);

	int countCustomMades(Long fuid);
}
