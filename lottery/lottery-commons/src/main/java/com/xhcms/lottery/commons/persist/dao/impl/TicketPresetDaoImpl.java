package com.xhcms.lottery.commons.persist.dao.impl;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.TicketPresetDao;
import com.xhcms.lottery.commons.persist.entity.TicketPresetPO;


public class TicketPresetDaoImpl extends DaoImpl<TicketPresetPO> implements TicketPresetDao {

	private static final long serialVersionUID = 8727852171226779076L;

	public TicketPresetDaoImpl(){
		super(TicketPresetPO.class);
	}

	 @SuppressWarnings("unchecked")
    @Override
    public List<TicketPresetPO> findByScheme(Long schemeId, int status) {
        Criteria c = createCriteria().add(Restrictions.eq("schemeId", schemeId));
        if (status != -1) {
            c.add(Restrictions.eq("status", status));
        }
        return c.list();
    }

	@Override
	public void updateStatusByScheme(Collection<Long> sids, int oldStatus,
			int newStatus, String message) {
		 createQuery("update TicketPresetPO set status=:newStatus, message=:msg,FinalStateTime=:finalTime where status=:oldStatus and schemeId in (:sids)")
	        .setInteger("newStatus", newStatus).setString("msg", message)
	        .setTimestamp("finalTime", new Date())
	        .setInteger("oldStatus", oldStatus).setParameterList("sids", sids)
	        .executeUpdate();
	}

}
