package com.xhcms.lottery.commons.persist.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.data.BetSchemePreset;
import com.xhcms.lottery.commons.persist.dao.BetSchemePresetDao;
import com.xhcms.lottery.commons.persist.entity.BetSchemePresetPO;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.PresetAward;

/**
 * 预兑奖方案dao
 * @author Wang Lei
 *
 */
@SuppressWarnings("unchecked")
public class BetSchemePresetDaoImpl extends DaoImpl<BetSchemePresetPO> implements BetSchemePresetDao {

	private static final long serialVersionUID = -1191120485192650758L;
	public BetSchemePresetDaoImpl() {
		super(BetSchemePresetPO.class);
	}
	
	@Override
	public List<BetSchemePresetPO> list(BetSchemePreset betSchemePreset, Date from, Date to,
			Paging paging) {
		PagingQuery<BetSchemePresetPO> pq = pagingQuery(paging);
		if(null != from && null != to){
			pq.add(Restrictions.ge("createdTime", from)).add(Restrictions.lt("createdTime", to));
		}
		
		if(null != betSchemePreset){
			if(betSchemePreset.getStatus() != 0){
				pq.add(Restrictions.eq("status", betSchemePreset.getStatus()));
			}
			if(betSchemePreset.getId() != null){
				pq.add(Restrictions.eq("id", betSchemePreset.getId()));
			}
			if(StringUtils.isNotBlank(betSchemePreset.getSponsor())){
				pq.add(Restrictions.eq("sponsor", betSchemePreset.getSponsor()));
			}
			if(StringUtils.isNotBlank(betSchemePreset.getLotteryId())){
				pq.add(Restrictions.eq("lotteryId", betSchemePreset.getLotteryId()));
			}
		}
		List<BetSchemePresetPO> list = pq.desc("id").list();
		return list;
	}

	@Override
	public List<Object[]> findAllowPrizesJC(List<Long> matchIds, String lotteryId) {
		return createSQLQuery("" +
				"SELECT COUNT(distinct r.scheme_id) AS asd,r.scheme_id FROM `lt_bet_match` AS r,(SELECT COUNT(distinct a.scheme_id) AS ss,a.scheme_id" +
				" FROM `lt_bet_match` AS a,`lt_bet_scheme` AS b left join `lt_bet_scheme_preset` bs on b.id=bs.id  WHERE bs.id is null and a.scheme_id=b.id AND b.status=:status AND b.preset_award=:preset_award " +
				" AND b.lottery_id = :lotteryId AND a.match_id IN (:matchIds)" +
				" GROUP BY a.scheme_id HAVING ss<=:count ) AS ccc WHERE r.scheme_id=ccc.scheme_id GROUP BY r.scheme_id HAVING asd <=:count")
				.setParameter("status", EntityStatus.TICKET_BUY_SUCCESS)
				.setParameter("preset_award", PresetAward.Not_PresetAward.getValue())
				.setParameter("lotteryId", lotteryId)
				.setParameterList("matchIds", matchIds)
				.setParameter("count", matchIds.size()).list();
	}

	@Override
	public List<BetSchemePresetPO> findByIds(List<Long> ids) {
		return createCriteria().add(Restrictions.in("id", ids)).list();
	}
	
	@Override
	public List<BetSchemePresetPO> findFollowSchemes(Long followedSchemeId) {
		return createCriteria().add(Restrictions.eq("followedSchemeId", followedSchemeId)).list();
	}

	@Override
	public List<Long> findAllowPrizesCTZC(String issueNumber, String playId) {
		String sql="select distinct b.id "
				+ "from lt_issueinfo a,lt_ct_bet_content c,lt_bet_scheme b left join lt_bet_scheme_preset d on b.id=d.id "
				+" where "
				+ "a.issue_number=c.issue_number "
				+ "and b.id=c.scheme_id "
				+ "and b.lottery_id=:lotteryId "
				+ "and c.play_id=:playId "
				+ "and c.issue_number=:issueNumber "
				+ "and a.status not in(:issueStatus) "
				+ "and a.play_id=:playId "
				+ "and d.id is null "
				+ "and b.status=:schemeStatus "
				+ "and b.preset_award=:presetAwardStatus";
		Query query = createSQLQuery(sql);
		query.setParameter("lotteryId", "CTZC");
		query.setParameter("playId", playId);
		query.setParameter("issueNumber", issueNumber);
		Set<Integer> issueStatus=new HashSet<Integer>();
		issueStatus.add(Constants.ISSUE_STATUS_NOTSALE);
		issueStatus.add(Constants.ISSUE_STATUS_SALE);
		query.setParameterList("issueStatus", issueStatus);
		query.setParameter("schemeStatus", EntityStatus.TICKET_BUY_SUCCESS);
		query.setParameter("presetAwardStatus", PresetAward.Not_PresetAward.getValue());
		List<Long> list = new ArrayList<Long>();
		List<BigInteger> list2 = query.list();
		for (int i = 0; i < list2.size(); i++) {
			list.add(list2.get(i).longValue());
		}
		return list;
	}
}
