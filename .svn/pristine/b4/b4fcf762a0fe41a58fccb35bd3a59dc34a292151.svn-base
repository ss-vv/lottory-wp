package com.xhcms.lottery.commons.persist.dao.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.CriteriaImpl;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.BetSchemeDao;
import com.xhcms.lottery.commons.persist.dao.BetSchemeWithIssueInfoDao;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;
import com.xhcms.lottery.commons.persist.entity.BetSchemeWithIssueInfoPO;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.EntityType;

/**
 * 高频彩投注方案
 * 
 * @author jiajiancheng
 * 
 */
@SuppressWarnings("unchecked")
public class BetSchemeWithIssueInfoDaoImpl extends DaoImpl<BetSchemeWithIssueInfoPO> implements BetSchemeWithIssueInfoDao {

    private static final long serialVersionUID = -793097112883708694L;

    public BetSchemeWithIssueInfoDaoImpl() {
        super(BetSchemeWithIssueInfoPO.class);
    }

	@Override
	public List<BetSchemeWithIssueInfoPO> findByStatusWithCurrentTime(
			int status, java.sql.Date from, List<String> targetLotteryIdList) {
		Date targetTime=new Date();
		return findByStatusWithSpecifiedTime(
				status, from, targetLotteryIdList,targetTime);
	}

	@Override
	public List<BetSchemeWithIssueInfoPO> findByStatusWithSpecifiedTime(
			int status, java.sql.Date from, List<String> targetLotteryIdList, Date targetTime) {
		Timestamp now=new Timestamp(targetTime.getTime());
		Criteria c = createCriteria();
		c.createAlias("issueInfoPO", "iPO");
        if(status != -1){
            c.add(Restrictions.eq("status", status));
        }
        if(from != null){
            c.add(Restrictions.ge("createdTime", from));
        }
        if(null!=targetLotteryIdList&&!targetLotteryIdList.isEmpty()){
        	c.add(Restrictions.in("lotteryId",targetLotteryIdList ));
        }
        
        c.add(Restrictions.ge("iPO.ZMCloseTime", now));
        c.add(Restrictions.le("iPO.startTime", now));
        c.add(Restrictions.eq("iPO.valid", true));
        return c.list();
	}	
}
