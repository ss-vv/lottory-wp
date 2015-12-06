/**
 * 
 */
package com.xhcms.ucenter.persist.dao.hibernate;

import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.ucenter.persist.dao.IRegistSSOServiceDao;
import com.xhcms.ucenter.persist.entity.RegistSSOServicePO;

/**
 * @author bean
 *
 */
public class RegistSSOServiceDaoImpl extends DaoImpl<RegistSSOServicePO> implements
		IRegistSSOServiceDao {
	private static final long serialVersionUID = 8910332048307475587L;

	public RegistSSOServiceDaoImpl() {
		super(RegistSSOServicePO.class);
	}

	@Override
	public RegistSSOServicePO findRegistSSOServiceByServiceId(String serviceId) {
		return (RegistSSOServicePO) createQuery("from RegistSSOServicePO r where r.serviceId=?").setString(0, serviceId).uniqueResult();
	}
}
