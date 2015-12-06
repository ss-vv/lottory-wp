package com.unison.lottery.weibo.data.service.store.persist.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.unison.lottery.weibo.data.service.store.persist.dao.BBLotteryCorpDao;
import com.unison.lottery.weibo.data.service.store.persist.entity.BBLotteryCorpPO;

@Repository
public class BBLotteryCorpDaoImpl extends BasicDaoImpl<BBLotteryCorpPO> implements
		BBLotteryCorpDao {


	private static final long serialVersionUID = 4025326276164707394L;

	public BBLotteryCorpDaoImpl(){
		super(BBLotteryCorpPO.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, String>> map() {
		String sql="select id, name from md_bb_lottery_corp";
		Query query = createSQLQuery(sql.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BBLotteryCorpPO> listCorp() {
		return createQuery("from BBLotteryCorpPO").list();
	}
}
