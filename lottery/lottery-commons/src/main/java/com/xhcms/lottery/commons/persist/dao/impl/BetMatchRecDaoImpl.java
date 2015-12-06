package com.xhcms.lottery.commons.persist.dao.impl;

import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.BetMatchRecDao;
import com.xhcms.lottery.commons.persist.entity.BetMatchRecPO;

@Repository
@SuppressWarnings("unchecked")
public class BetMatchRecDaoImpl extends DaoImpl<BetMatchRecPO> implements BetMatchRecDao {

    private static final long serialVersionUID = 6840079272755358377L;

    public BetMatchRecDaoImpl() {
        super(BetMatchRecPO.class);
    }

	@Override
	public List<BetMatchRecPO> findBySchemeId(Long id) {
		 return createCriteria()
	                .add(Restrictions.eq("schemeId", id))
	                .addOrder(Order.asc("code"))
	                .list();
	}

	@Override
	public List<BetMatchRecPO> findPOById(Long id) {
		    
		return createQuery("from BetMatchRecPO where id=:id").setLong("id",id).list();
	}

	@Override
	public List<Object[]> getMatchRecList(String type, String[] playtype,Paging p) {
		
		String sql="select rec.id,rec.scheme_id,rec.code,rec.play_id,rec.annotation,"
				  +" t.cn_code,t.home_team_name,t.guest_team_name,t.league_name,"
				  +" t.playing_time,t.entrust_deadline,rec.match_id";

		StringBuffer sb=new StringBuffer();
		sb.append(sql);
		if("fb".equals(type)){
			   sb.append(",t.concede_points ");
			   sb.append(" from lt_bet_match_rec rec,fb_match t");
	
		}else if("bb".equals(type)){

			   sb.append(" from lt_bet_match_rec rec,bb_match t");
			
		}else if("bjdc".equals(type)){
			
			   sb.append(" from lt_bet_match_rec rec,bjdc_match t");
		}
		
		      sb.append(" where rec.match_id=t.id and rec.play_id in (:type) and t.status=1");
		      sb.append(" limit :from,:result");
		return createSQLQuery(sb.toString())
				.setParameterList("type", playtype)
				.setParameter("from",(p.getPageNo()-1)*p.getMaxResults())
				.setParameter("result", p.getMaxResults()).list();
	}

	@Override
	public Integer getMatchCount(String type, String[] playtype) {
		String sql="select count(*)";
		StringBuffer sb=new StringBuffer();
		sb.append(sql);
		if("fb".equals(type)){
			sb.append(" from lt_bet_match_rec rec,fb_match t");
			
		}else if("bb".equals(type)){
			
			sb.append(" from lt_bet_match_rec rec,bb_match t");
		}else if("bjdc".equals(type)){
			
			sb.append(" from lt_bet_match_rec rec,bjdc_match t");
		}
		sb.append(" where rec.match_id=t.id and rec.play_id in (:type) and t.status=1");
		Object o=createSQLQuery(sb.toString()).setParameterList("type", playtype).uniqueResult();
		return Integer.parseInt(o.toString());
	}
}