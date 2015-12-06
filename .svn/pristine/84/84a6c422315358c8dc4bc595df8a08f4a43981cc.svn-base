/**
 * 
 */
package com.xhcms.lottery.commons.persist.dao.impl;

import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.CustomMadeAvaiableSchemeDao;
import com.xhcms.lottery.commons.persist.entity.CustomMadeAvaiableSchemePO;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.EntityType;

/**
 * @author Bean.Long
 *
 */
@SuppressWarnings("unchecked")
public class CustomMadeAvaiableSchemeDaoImpl extends DaoImpl<CustomMadeAvaiableSchemePO> implements
		CustomMadeAvaiableSchemeDao {
	private static final long serialVersionUID = 5798579468932631586L;

	public CustomMadeAvaiableSchemeDaoImpl() {
		super(CustomMadeAvaiableSchemePO.class);
	}
	
	@Override
	public List<CustomMadeAvaiableSchemePO> listCustomMadeAvaiableSchemes(
			int status) {
		return createCriteria().add(Restrictions.eq("status", EntityStatus.CUSTOMMADE_STATUS_NO))
				.addOrder(Order.asc("createTime"))
				.list();
	}
}
