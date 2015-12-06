package com.xhcms.lottery.commons.persist.dao.impl;

import java.util.List;

import org.hibernate.Query;

import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.data.ctfb.CTBetContent;
import com.xhcms.lottery.commons.persist.dao.CTBetContentDao;
import com.xhcms.lottery.commons.persist.entity.CTBetContentPO;

/**
 * 传统足彩投注内容dao
 * @author yonglizhu,lei.wang
 *
 */
@SuppressWarnings("unchecked")
public class CTBetContentDaoImpl extends DaoImpl<CTBetContentPO> implements CTBetContentDao {

    private static final long serialVersionUID = 7267885448985701807L;

    public CTBetContentDaoImpl() {
        super(CTBetContentPO.class);
    }

    @Override
    public List<CTBetContentPO> findCtBetContent(Long schemeId) {
		StringBuffer sql = new StringBuffer();
		sql.append("from CTBetContentPO ").append("where schemeId = :schemeId");
		Query query = createQuery(String.valueOf(sql)).setLong("schemeId",
				schemeId);
		return query.list();
    }

	@Override
	public void batchInserts(List<CTBetContent> cTBetContents,Long schemeId,Long issueId) {
		if(null == cTBetContents || cTBetContents.isEmpty()){
			return;
		}
		StringBuffer sql = new StringBuffer("INSERT INTO lt_ct_bet_content (lottery_id,play_id,scheme_id,issue_id,issue_number,code,seed,choose_type) VALUES ");
		int count=0;
		for(CTBetContent ctBet:cTBetContents){
			if(count==11000){
				createSQLQuery(sql.substring(0,sql.length()-1)+";").executeUpdate();
				count=0;
				sql = new StringBuffer("INSERT INTO lt_ct_bet_content (lottery_id,play_id,scheme_id,issue_id,issue_number,code,seed,choose_type) VALUES ");
			}
			sql.append("('" + ctBet.getLotteryId()+ "', '" + ctBet.getPlayId() + "', " + schemeId + ", " + issueId + ", '" + ctBet.getIssueNumber() + "', '" + ctBet.getCode() + "'," + (null==ctBet.getSeed()?"null":" '" + ctBet.getSeed() + "'")+ ", " + ctBet.getChooseType() + "),");
			count++;
		}
		createSQLQuery(sql.substring(0,sql.length()-1)+";").executeUpdate();
	}

	@Override
	public List<Long> findBetSchemeIdsByIssueNumberAndPlayId(
			String issueNumber, String playId) {
		// TODO Auto-generated method stub
		return null;
	}
}
