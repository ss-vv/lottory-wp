package com.unison.lottery.pm.persist;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.unison.lottery.pm.entity.ShowFollowPO;
import com.xhcms.commons.persist.Dao;

/**
 * 
 * @author yongli zhu
 *
 */
public interface ShowFollowDao extends Dao<ShowFollowPO> {
	
	List findShowFollowTOP(Date startTime, Date endTime, int top);
	
	Object findShowWin(Date startTime, Date endTime, BigInteger userId); 
	
	Object findFollowWin(Date startTime, Date endTime, BigInteger userId);
	
	List findShowTOP(Date startTime, Date endTime, int top);
}
