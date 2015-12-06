package com.xhcms.lottery.commons.persist.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session; 
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.ActivityDao;
import com.xhcms.lottery.commons.persist.entity.ActivityPO;

@Transactional
public class ActivityDaoImpl extends DaoImpl<ActivityPO> implements ActivityDao{

	private static final long serialVersionUID = -8866968344416859253L;

    public ActivityDaoImpl() {
        super(ActivityPO.class);
    }
	@SuppressWarnings("unchecked")
	@Override
	public List<ActivityPO> findAll() {
		return createQuery("from ActivityPO where 1=1").list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ActivityPO> findAllStatus(Integer status) {
		return createQuery("from ActivityPO where status=:status order by id desc").setParameter("status", status).list();
	}
	
	
	@Override
	@Transactional
	public Long createActivity(ActivityPO activityPO) {
		Session session = getSessionFactory().getCurrentSession();
		Serializable id = session.save(activityPO);
		return Long.parseLong(id.toString());
		
	}
	@Override
	public ActivityPO getActivityById(Long id) {
		return get(id);
	}
	@Override
	@Transactional
	public boolean deleteActivityFromList(List<Long> list) {
		if(list != null && list.size() > 0){
			for(Long id : list){
				deleteById(id);
			}
			return true;
		}
		return false;
	}
	@SuppressWarnings("unchecked")
	@Override
	public String findMaxVersion() {
      String sql = "select max(t.clientVersion) from  ActivityPO t  where 1=1";
      List<Object> version = createQuery(sql).list();
		if(version != null && version.size() > 0){
			return String.valueOf(version.get(0));
		}
		return null;
	}
	@Override
	@Transactional
	public void updateAllVersion(Integer valueOf, List<Long> list) {
		String clientVersion = String.valueOf(valueOf + 1);
		List<ActivityPO> delete0ActivityPOs = new ArrayList<ActivityPO>();
		for(Long id : list){
			ActivityPO ac = get(id);
			if(ac != null && ac.getStatus() == 0 ){
				delete0ActivityPOs.add(ac);
			} 
		}
		if(delete0ActivityPOs.size() != 0){
			List<ActivityPO> activityPOs = findAllStatus(0);
			if(activityPOs != null && activityPOs.size() > 0){
				for(ActivityPO activityPO : activityPOs){
					createSQLQuery("update lt_client_activity set clientVersion=:clientVersion where id=:id")
					.setParameter("clientVersion", clientVersion)
					.setParameter("id", activityPO.getId()).executeUpdate();
				}
			}
		} 
		deleteActivityFromList(list);
	}
	@Transactional
	@Override
	public void updatePutOnListVersion(Integer valueOf, List<Long> list) {
		String clientVersion = String.valueOf(valueOf + 1);
		for(Long id : list){
			createSQLQuery("update lt_client_activity set status=:status,clientVersion=:clientVersion where id=:id")
             .setParameter("clientVersion", clientVersion)
             .setParameter("status", 0)
             .setParameter("id", id).executeUpdate();
		}
		List<ActivityPO> activityPOs = findAllStatus(0);
		
		for(ActivityPO activityPO : activityPOs){
			createSQLQuery("update lt_client_activity set clientVersion=:clientVersion where id=:id")
             .setParameter("clientVersion", clientVersion)
             .setParameter("id", activityPO.getId()).executeUpdate();
		}
	}

}
