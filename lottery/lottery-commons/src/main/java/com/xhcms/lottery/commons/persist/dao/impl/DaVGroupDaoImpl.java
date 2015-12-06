package com.xhcms.lottery.commons.persist.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;

import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.data.DaVGroup;
import com.xhcms.lottery.commons.persist.dao.DaVGroupDao;
import com.xhcms.lottery.commons.persist.entity.DaVGroupPO;

public class DaVGroupDaoImpl extends DaoImpl<DaVGroupPO> implements DaVGroupDao{

	private static final long serialVersionUID = 5603497235385527803L;

	public DaVGroupDaoImpl() {
		super(DaVGroupPO.class);
	}
	
	@Override
	public List<DaVGroupPO> listGroupPO(Integer firstResult,
			Integer pageMaxResult) {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DaVGroupPO> findDaVGroupPOByGroupId(String groupid) {
		if(StringUtils.isNotBlank(groupid)){
			List<DaVGroupPO> daVGroupPOs = createQuery("from DaVGroupPO d where d.groupid = " + groupid).list();
			if(daVGroupPOs != null && !daVGroupPOs.isEmpty()){
				return daVGroupPOs;
			}
		}
		return null;
	}

	@Override
	public List<String> listAllVidsByDistinct() {
		List<String> vids = createQuery("select distinct vid from DaVGroupPO d").list();
		return vids;
	}
}
