package com.unison.lottery.weibo.data.service.store.persist.dao.impl;

import java.util.List;
import java.util.Map;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import com.unison.lottery.weibo.data.service.store.persist.dao.LotteryCorpDao;
import com.unison.lottery.weibo.data.service.store.persist.entity.LotteryCorpPO;
import com.xhcms.commons.persist.hibernate.DaoImpl;

/**
 * 博彩公司DAO
 * 
 * @author Yang Bo
 */
@Repository
public class LotteryCorpDaoImpl extends DaoImpl<LotteryCorpPO> implements LotteryCorpDao {

	private static final long serialVersionUID = 4025326276164707394L;

	public LotteryCorpDaoImpl(){
		super(LotteryCorpPO.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LotteryCorpPO> listElites() {
		return createQuery("from LotteryCorpPO where eliteId>0").list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, String>> map() {
		String sql="select id, name from md_lottery_corp";
		Query query = createSQLQuery(sql.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LotteryCorpPO> listCorp() {
		return createQuery("from LotteryCorpPO").list();
	}
}
