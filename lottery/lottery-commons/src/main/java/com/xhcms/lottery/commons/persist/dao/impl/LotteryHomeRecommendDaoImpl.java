package com.xhcms.lottery.commons.persist.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.LotteryHomeRecommendDao;
import com.xhcms.lottery.commons.persist.entity.LtLotteryHomeRecommendPO;
import com.xhcms.lottery.lang.LotteryId;

@Repository
public class LotteryHomeRecommendDaoImpl extends DaoImpl<LtLotteryHomeRecommendPO> implements LotteryHomeRecommendDao{

	private static final long serialVersionUID = 6028956359657474323L;

	public LotteryHomeRecommendDaoImpl(){
		
		 super(LtLotteryHomeRecommendPO.class);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<LtLotteryHomeRecommendPO> getLtLotteryHomeRecommendByLotteryId(
			LotteryId l,boolean flag) {
		 List result=null;
              if(flag){
            	 result= createQuery("from LtLotteryHomeRecommendPO where status=0 and lotteryId =:lid order by deadlineTime").setString("lid", l.name()).setFirstResult(0).setMaxResults(3).list(); 
              }else{
            	 result= createQuery("from LtLotteryHomeRecommendPO where status=0 and lotteryId =:lid and deadlineTime >:time order by deadlineTime").setString("lid", l.name()).setDate("time", new Date()).setFirstResult(0).setMaxResults(3).list();  
              }
		return result;
	}
	@Override
	public Integer isOn(Long id) {
		Object o=createQuery("select count(id) from LtLotteryHomeRecommendPO where betMatchId=:id").setParameter("id", id).uniqueResult();
		return Integer.parseInt(o.toString());
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> getLtLotteryHomeRecommendBy(Paging p,
			String matchType) {
		   
		return createSQLQuery("select r.id,r.lottery_id,r.bet_match_id,r.status,r.created_time,"
				+ " r.update_time,b.scheme_id,b.match_id,b.code,b.play_id,b.annotation,r.deadline_time "
				+ " from lt_lottery_home_recommend r,lt_bet_match_rec b where r.bet_match_id=b.id "
				+ " and r.lottery_id=:lotteryid order by r.update_time desc")
		           .setParameter("lotteryid", matchType)
		           .setFirstResult((p.getPageNo()-1)*p.getMaxResults())
		           .setMaxResults(p.getMaxResults())
		           .list();
	}

	@Override
	public Integer getLtLotteryHomeRecommendCount(String matchType) {
	    Object o=createQuery("select count(id) from LtLotteryHomeRecommendPO where lotteryId=:lotteryid").setParameter("lotteryid", matchType).uniqueResult();
		return Integer.parseInt(o.toString());
	}

	@Override
	public void updateStatus(Long id, int status) {
	  
		 createSQLQuery("update lt_lottery_home_recommend set status=:status where id=:id")
		 .setParameter("status", status).setParameter("id",id).executeUpdate();
		
	}

}
