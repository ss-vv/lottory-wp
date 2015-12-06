package com.unison.lottery.pm.persist.impl;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.unison.lottery.pm.persist.BetSchemeDao;
import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.EntityType;


/**
 * @author yongli zhu
 * 
 */
public class BetSchemeDaoImpl extends DaoImpl<BetSchemePO> implements BetSchemeDao {

	private static final long serialVersionUID = 9003306437608783628L;

	public BetSchemeDaoImpl() {
        super(BetSchemePO.class);
    }
	
	/**
	 * 查询出所有的晒单方案
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BigInteger> findByIsShow(String date) {
		return createSQLQuery("select id from lt_bet_scheme where substr(created_time,1,10)=? and is_show_scheme=? and following_count>?")
				.setString(0, date).setInteger(1, Constants.SHOW_SCHEME).setInteger(2, 0).list();
	}
	
	/**
	 * 更新跟单总金额
	 * @param showId
	 */
	@Override
	public void updateAmount(Long showId) {
		this.createSQLQuery("update lt_bet_scheme set follow_total_amount=" +
				"(select a.amount from ( select sum(s.total_amount) amount from lt_bet_scheme s " +
				"where s.followed_scheme_id="+showId+") a) where id="+showId).executeUpdate();
	}
	
	/**
	 * 返回竞彩足球一段时间内出票成功的不含合买的用户和金额
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findUserJCZQBetByDate(Date day){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Calendar start = Calendar.getInstance();
		start.setTime(day);
		start.set(Calendar.HOUR_OF_DAY, 0);
		start.set(Calendar.MINUTE, 0);
		start.set(Calendar.SECOND, 0);
		
		Calendar end = Calendar.getInstance();
		end.setTime(day);
		end.set(Calendar.HOUR_OF_DAY, 23);
		end.set(Calendar.MINUTE, 59);
		end.set(Calendar.SECOND, 59);
		
		String thatday=sdf.format(start.getTime());
//		System.out.println(thatday);
//		System.out.println(sdf2.format(start.getTime()));
//		System.out.println(sdf2.format(end.getTime()));
		return createSQLQuery("SELECT bs.sponsor_id, SUM(bs.buy_amount)  as buyAmount FROM lt_bet_scheme as bs left JOIN  pm_grant_voucher_byday p on CONCAT(?, '_', bs.sponsor_id)=p.id " +
				"WHERE bs.lottery_id='JCZQ' AND (bs.created_time BETWEEN ? AND ?) AND " +
				"bs.type IN ("+ EntityType.BET_TYPE_ALONE +", "+ EntityType.BET_TYPE_FOLLOW +") AND " +
				"bs.status IN (" + EntityStatus.TICKET_AWARDED +", " + EntityStatus.TICKET_BUY_SUCCESS +", " +
				"" + EntityStatus.TICKET_NOT_WIN +", " + EntityStatus.TICKET_NOT_AWARD +") AND p.id is null GROUP BY bs.sponsor_id having buyAmount>199")
				.setString(0, thatday)
				.setString(1, sdf2.format(start.getTime()))
				.setString(2, sdf2.format(end.getTime()))
				.list();
	}
	
}
