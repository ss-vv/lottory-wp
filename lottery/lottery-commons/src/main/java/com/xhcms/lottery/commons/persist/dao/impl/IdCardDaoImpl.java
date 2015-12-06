package com.xhcms.lottery.commons.persist.dao.impl;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.IdCardDao;
import com.xhcms.lottery.commons.persist.entity.IdCardPO;
import com.xhcms.lottery.lang.EntityStatus;

public class IdCardDaoImpl extends DaoImpl<IdCardPO> implements IdCardDao {

    private static final long serialVersionUID = 7545729531761610624L;

    public IdCardDaoImpl() {
        super(IdCardPO.class);
    }

    @Override
    public List<IdCardPO> find(Paging paging, String username, int status, int type, Date from, Date to) {
        PagingQuery<IdCardPO> pq = pagingQuery(paging);
        if (StringUtils.isNotEmpty(username)) {
            pq.add(Restrictions.like("username", username, MatchMode.ANYWHERE));
        }
        if (status > -1) {
            pq.add(Restrictions.eq("status", status));
        }
        if (type > -1) {
            pq.add(Restrictions.eq("type", type));
        }
        if (from != null) {
            pq.add(Restrictions.ge("createTime", from));
        }
        if (to != null) {
            pq.add(Restrictions.le("createTime", to));
        }
        return pq.list();
    }

    @Override
    public void updateStatus(Collection<Long> id, int status, Long adminId, String admin) {
        if (status == EntityStatus.ID_CARD_PASS || status == EntityStatus.ID_CARD_UNPASS) {
            createQuery(
                    "update IdCardPO set status=:s, adminId=:adminId, admin=:admin where id in (:ids) and status =:status")
                    .setInteger("s", status).setLong("adminId", adminId).setString("admin", admin)
                    .setInteger("status", EntityStatus.ID_CARD_INIT).setParameterList("ids", id).executeUpdate();
        }
    }

}
