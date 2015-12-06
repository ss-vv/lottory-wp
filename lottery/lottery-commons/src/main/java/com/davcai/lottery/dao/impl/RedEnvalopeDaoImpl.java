package com.davcai.lottery.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import com.davcai.lottery.dao.RedEnvalopeDao;
import com.unison.lottery.weibo.data.service.store.persist.entity.RedEnvalopeGrabRecordPO;
import com.unison.lottery.weibo.data.service.store.persist.entity.RedEnvalopePO;
import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.utils.DateUtils;

public class RedEnvalopeDaoImpl extends DaoImpl<RedEnvalopePO> implements RedEnvalopeDao {

	@Override
	@Transactional
	public long saveEnvalope(RedEnvalopePO redEnvalopePO) {
		Session session = getSessionFactory().getCurrentSession();
		return (long) session.save(redEnvalopePO);
	}

	@Override
	public RedEnvalopePO queryEnvalopeById(Long envalopeId) {
		return (RedEnvalopePO) createQuery("from RedEnvalopePO envalope where envalopeId=?").setLockMode("envalope", LockMode.UPGRADE).setLong(0, envalopeId).uniqueResult();
	}

	@Override
	@Transactional
	public void deleteRedEnvalopeById(long envalopeId) {
		session().createQuery("delete from RedEnvalopePO where envalopeId=?").setLong(0, envalopeId).executeUpdate();
	}

	@Override
	@Transactional
	public void update(RedEnvalopePO redEnvalopePO) {
		session().merge(redEnvalopePO);
	}

	@Override
	@Transactional
	public void save(RedEnvalopeGrabRecordPO record) {
		// TODO Auto-generated method stub
		super.save(record);
	}
	
	@Override
	@Transactional
	public List<RedEnvalopeGrabRecordPO> queryRedEnvalopeGrabHistory(Long envalopeId){
		
		return createQuery("select new RedEnvalopeGrabRecordPO(a.id,b.headImageURL,b.nickName,a.createTime,a.envalopeAmount,a.ltUserId) "
				+ "from RedEnvalopeGrabRecordPO a,UserPO b where a.ltUserId=b.id and "
				+ "a.envalopeId=? order by a.createTime desc").setLong(0, envalopeId).list();
	}

	@Override
	@Transactional
	public List<RedEnvalopePO> queryAllInvalidEnvalope() {
		Date date = DateUtils.getLastHoursTime(new Date(), 24);
		Criteria criteria = session().createCriteria(RedEnvalopePO.class)
				.add(Restrictions.and(Restrictions.eq("status", "0"), Restrictions.le("createTime", date)));
		criteria.setLockMode(LockMode.UPGRADE);
		return criteria.list();
	}
}
