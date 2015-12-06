package com.unison.weibo.admin.service.impl;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.Clob;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.unison.weibo.admin.service.ActivityDaoWeiboAdmin;
import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.ActivityDao;
import com.xhcms.lottery.commons.persist.entity.ActivityPO;

public class ActivityDaoWeiboAdminImpl extends DaoImpl<ActivityPO> implements  ActivityDaoWeiboAdmin{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1452345234L;

	public ActivityDaoWeiboAdminImpl()
	  {
	    super(ActivityPO.class);
	  }
	@Autowired
	private ActivityDao activityDao;

	  /**
	   * 下架
	   */
	  @Transactional
	  public void updatePutOffListVersion(Integer valueOf, List<Long> list) {
	    String clientVersion = String.valueOf(valueOf.intValue() + 1);
	    for (Long id : list) {
	      createSQLQuery("update lt_client_activity set status=:status,clientVersion=:clientVersion where id=:id")
	        .setParameter("clientVersion", clientVersion)
	        .setParameter("status", Integer.valueOf(1))
	        .setParameter("id", id).executeUpdate();
	    }
	    List<ActivityPO> activityPOs = findAll();

	    for (ActivityPO activityPO : activityPOs)
	      createSQLQuery("update lt_client_activity set clientVersion=:clientVersion where id=:id")
	        .setParameter("clientVersion", clientVersion)
	        .setParameter("id", activityPO.getId()).executeUpdate();
	  }

	  /**
	   * 上移一条记录
	   */
	@Transactional
	public void updatePutDownListVersuib(Integer versi, List<Long> parList) {
		Long LastID;
		for (Long id : parList)
		{
			//查找上一条id
			LastID = findLastID(id);
			if(LastID != null)
				//交换ID
				changeDoubleID(id, LastID);
			break;
		}
		
		//版本号加 1	    
		upDateVersion(versi);
	}
	/**
	 * 下移一条记录
	 */
	@Transactional
	public void updatePutUpListVersuib(Integer versi,
			List<Long> parList) {
		Long LastID;
		for (Long id : parList)
		{
			//查找下一条id
			LastID = findNetxID(id);
			if(LastID != null)
				//交换ID
				changeDoubleID(id, LastID);
			break;
		}
		
		//版本号加 1	    
		upDateVersion(versi);
		
	}
	/**
	 * 查找上一条记录的ID
	 * @param id
	 * @return
	 */
	private long findLastID(Long id)
	{
		List<ActivityPO> list = createQuery("from ActivityPO p where p.id<:id order by p.id DESC").setParameter("id", id).list();
		if(list.size()>0)
			for(ActivityPO act : list)
			{
				return act.getId();
			}
		return 0;
	}

	/**
	 * 查找下一条记录ID
	 * @param id
	 * @return
	 */
	private long findNetxID(Long id)
	{
		List<ActivityPO> list = createQuery("from ActivityPO p where p.id>:id order by p.id ASC").setParameter("id", id).list();
		if(list.size()>0)
			for(ActivityPO act : list)
			{
				return act.getId();
			}
		return 0;
	}

	/**
	 * 查找最小记录ID
	 * @param id
	 * @return
	 */
	private long findMinID(Long id)
	{
		List<ActivityPO> list = createQuery("from ActivityPO p where p.id>:id order by p.id ASC").setParameter("id", Long.parseLong("0")).list();
		if(list.size()>0)
			for(ActivityPO act : list)
			{
				return act.getId();
			}
		return 0;
	}
	@Transactional
	private long findMaxID()
	{
		List<ActivityPO> list = createQuery("from ActivityPO p order by p.id DESC").list();
		if(list.size()>0)
			for(ActivityPO act : list)
			{
				return act.getId();
			}
		return 0;
	}
	
	/**
	 * 修改ID
	 * @param oldID
	 * @param newID
	 */
	@Transactional
	private void changeID(Long oldID,Long newID)
	{
		createSQLQuery("update lt_client_activity set id=:id1 where id=:id2")
	    .setParameter("id1", newID)
	    .setParameter("id2", oldID).executeUpdate();
	}
	
	/**
	 * 交换ID
	 * @param id1
	 * @param id2
	 */
	@Transactional
	private void changeDoubleID(Long id1,Long id2)
	{
		Long tempID=12341234123L;
		//ID1和tempID交换
	    createSQLQuery("update lt_client_activity set id=:id1 where id=:id2")
	    .setParameter("id1", tempID)
	    .setParameter("id2", id1).executeUpdate();
	    //ID2设置成ID1
	    createSQLQuery("update lt_client_activity set id=:id1 where id=:id2")
        .setParameter("id1", id1)
        .setParameter("id2", id2).executeUpdate();
	    //tempID设成ID2
	    createSQLQuery("update lt_client_activity set id=:id1 where id=:id2")
        .setParameter("id1", id2)
        .setParameter("id2", tempID).executeUpdate();
	}
		
	/**
	 * 升级版本号
	 */
	@Transactional
	private void upDateVersion(Integer versi)
	{
		//版本号加 1
	    String clientVersion = String.valueOf(versi.intValue() + 1);
		List<ActivityPO> activityPOs = findAll();
	    for (ActivityPO activityPO : activityPOs)
	      createSQLQuery("update lt_client_activity set clientVersion=:clientVersion where id=:id")//应该用 1=1
	        .setParameter("clientVersion", clientVersion)
	        .setParameter("id", activityPO.getId()).executeUpdate();
	}

	@Transactional
	public void updatePutUpUpListVersuib(Integer paramInteger,
			List<Long> parList) {
		Long MaxID;
		for (Long id : parList)
		{
			//查找最大id
			MaxID = findMaxID();
			if(MaxID != null)
				//改变ID
				changeID(id, MaxID + 1L);
			break;
		}
		
		//版本号加 1	    
		upDateVersion(paramInteger);
		
	}

	/**
	 * 逆序排列
	 */
	@Transactional
	public List<ActivityPO> findAll() {
		 return createQuery("from ActivityPO p order by p.id DESC").list();
	}
	

}
