package com.unison.lottery.pm.persist.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.unison.lottery.pm.entity.ShowFollowPO;
import com.unison.lottery.pm.persist.ShowFollowDao;
import com.xhcms.commons.persist.hibernate.DaoImpl;

public class ShowFollowDaoImpl extends DaoImpl<ShowFollowPO> implements ShowFollowDao {
	private static final long serialVersionUID = 4138318064056797389L;

	public ShowFollowDaoImpl() {
		super(ShowFollowPO.class);
	}
	
	@Override
	public List findShowFollowTOP(Date startTime, Date endTime, int top) {
		List showFollowPOs = createSQLQuery(" select sponsor_id,sponsor,sum(after_tax_bonus) after_tax_bonus " +
				" from lt_bet_scheme where created_time>=? and created_time<=? and (is_show_scheme=1 or `type`=2) " +
				" and `status`=12 and sponsor_id not in (610,28,13) group by sponsor_id order by sum(after_tax_bonus) desc limit ? ")
				.setTimestamp(0, startTime).setTimestamp(1, endTime).setInteger(2, top).list();
		
		return showFollowPOs;
	}
	
	@Override
	public Object findShowWin(Date startTime, Date endTime, BigInteger userId) {
		Object showAmount = createSQLQuery(" select sum(after_tax_bonus) after_tax_bonus from lt_bet_scheme " +
				" where created_time>=? and created_time<=? and is_show_scheme=1  and status=12 and sponsor_id=? ")
				.setTimestamp(0, startTime).setTimestamp(1, endTime).setBigInteger(2, userId).uniqueResult();
		return showAmount;
	}
	
	@Override
	public Object findFollowWin(Date startTime, Date endTime, BigInteger userId) {
		Object followAmount = createSQLQuery(" select sum(after_tax_bonus) after_tax_bonus from lt_bet_scheme " +
				" where created_time>=? and created_time<=? and type=2  and status=12 and sponsor_id=? ")
				.setTimestamp(0, startTime).setTimestamp(1, endTime).setBigInteger(2, userId).uniqueResult();
		return followAmount;
	}
	
	@Override
	public List findShowTOP(Date startTime, Date endTime, int top) {
		List showDatas = createSQLQuery(" select count(s.id) num,s.sponsor_id,s.sponsor,sum(s.after_tax_bonus) winAmount, " +
				" sum(s.after_tax_bonus)/sum(s.total_amount) returnRatio from lt_bet_scheme s " +
				" where s.is_show_scheme=1 and s.`type`=0 and s.status in (5202,12) and date(s.created_time)>=? " +
				" and date(s.created_time)<=? group by s.sponsor_id having count(s.id)>10 " +
				" order by sum(s.after_tax_bonus)/sum(s.total_amount) desc limit ?")
				.setDate(0, startTime).setDate(1, endTime).setInteger(2, top).list();
		
		return showDatas;
	}
}
