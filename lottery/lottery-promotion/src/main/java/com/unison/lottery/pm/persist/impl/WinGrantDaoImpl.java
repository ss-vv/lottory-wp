package com.unison.lottery.pm.persist.impl;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.unison.lottery.pm.data.PromotionWinResult;
import com.unison.lottery.pm.entity.WinGrantPO;
import com.unison.lottery.pm.lang.WinGrant;
import com.unison.lottery.pm.persist.WinGrantDao;
import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.hibernate.DaoImpl;

public class WinGrantDaoImpl extends DaoImpl<WinGrantPO> implements WinGrantDao{

	private static final long serialVersionUID = 5934616990035857242L;
	
	protected  Logger log = LoggerFactory.getLogger(getClass());
	
	public WinGrantDaoImpl(){
		super(WinGrantPO.class);
	}
	
	@Override
	@SuppressWarnings({ "unchecked" })
	public List<PromotionWinResult> findPromotionWinTOP(int number,Long promotionId) {
		try {
			if(promotionId==null || promotionId<1){
				promotionId=1L;
			}
			List<PromotionWinResult> winResult = createQuery("SELECT new com.unison.lottery.pm.data.PromotionWinResult(w.username,sum(w.amount) AS totalGrant,sum(s.afterTaxBonus) as totalAfterTaxBonus,w.userId AS uid)" +
				"FROM WinGrantPO w,BetSchemePO s  where w.scheme=s.id and w.promotionId="+ promotionId +" GROUP BY w.userId order by totalAfterTaxBonus desc").setMaxResults((number<1 || number>50)?10:number).list();
			log.debug(winResult.toString());
			return winResult;
		} catch (Exception e) {
			log.error("查询累计竞彩活动ID=["+promotionId+"]失败。",e);
			return null;
		}
	}
	@Override
	public List<WinGrantPO> findWinGrantList(Paging paging, int status,
			Date from, Date to,WinGrantPO winGrant,Set<Long> promotionidSet) {
		PagingQuery<WinGrantPO> pq = pagingQuery(paging);
		if(from!=null){
			pq.add(Restrictions.ge("operatorTime",from));
		}
		if(to!=null){
			pq.add(Restrictions.le("operatorTime",to));
		}
		if (status != -1) {
            pq.add(Restrictions.eq("status", status));
        }
		if (winGrant !=null) {
			if(winGrant.getScheme()!=null && winGrant.getScheme().getId()!=null && winGrant.getScheme().getId()!=0 ){
				pq.add(Restrictions.eq("scheme", winGrant.getScheme()));
			}
			if(winGrant.getUserId()!=0){
				pq.add(Restrictions.eq("userId", winGrant.getUserId()));
			}
			if(StringUtils.isNotBlank(winGrant.getUsername())){
				pq.add(Restrictions.eq("username", winGrant.getUsername()));
			}
			if(StringUtils.isNotBlank(winGrant.getOperatorName())){
				pq.add(Restrictions.eq("operatorName", winGrant.getOperatorName()));
			}
			if(promotionidSet!=null){
				pq.add(Restrictions.in("promotionId", promotionidSet));
			}
			if(winGrant.getGranttypeId()!=0){
				pq.add(Restrictions.eq("granttypeId", winGrant.getGranttypeId()));
			}
		}
		List<WinGrantPO> list =pq.desc("grantTime").asc("operatorTime").list();
		paging.setResults(list);
		return list.size()>0?list:null;
	}
	
	@Override
	public List<WinGrantPO> find(Collection<Long> ids) {
		return topQuery(0).add(Restrictions.in("id", ids)).add(Restrictions.eq("status", WinGrant.submitStatus.unSubmit)).list();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Long> findIdsByPMIDandStatus(Long PMid, Integer status) {
        Criteria c = createCriteria()
                .setProjection(Property.forName("id"))
                .add(Restrictions.eq("promotionId", PMid))
                .add(Restrictions.eq("status", status));
        return c.list();
	}
}
