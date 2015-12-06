package com.xhcms.lottery.commons.persist.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.BetMatchDao;
import com.xhcms.lottery.commons.persist.entity.BetMatchPO;

@Repository
public class BetMatchDaoImpl extends DaoImpl<BetMatchPO> implements BetMatchDao {

    private static final long serialVersionUID = 6840079272755358377L;

    public BetMatchDaoImpl() {
        super(BetMatchPO.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<BetMatchPO> findBySchemeId(Long schemeId) {
        return createCriteria()
                .add(Restrictions.eq("schemeId", schemeId))
                .addOrder(Order.asc("code"))
                .list();
    }
    
    @Override
	public BetMatchPO find(Long schemeId, String subcode) {
		return (BetMatchPO) createQuery(
				"from BetMatchPO as b where b.schemeId=:schemeId and substr(b.code, 0, 4)=:subcode")
				.uniqueResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> findTop5Match(Date beginTime) {
		String sql="select bet.match_id,s.lottery_id,count(bet.match_id) c from lt_bet_scheme s,lt_bet_match bet where "
                  +" s.id=bet.scheme_id and s.created_time>:begintime and s.created_time <=now() and "
				  +" s.is_show_scheme=1 group by bet.match_id order by c desc,bet.match_id limit 0,5";
		
		return createSQLQuery(sql).setDate("begintime", beginTime).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BetMatchPO> findByMatchId(String matchId) {
		 return createCriteria().add(Restrictions.eq("matchId", matchId)).list();
	}

}
