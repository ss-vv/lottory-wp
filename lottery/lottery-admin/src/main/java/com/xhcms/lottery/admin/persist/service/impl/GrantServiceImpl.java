package com.xhcms.lottery.admin.persist.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.admin.persist.service.GrantService;
import com.xhcms.lottery.commons.data.Grant;
import com.xhcms.lottery.commons.data.User;
import com.xhcms.lottery.commons.persist.dao.GrantDao;
import com.xhcms.lottery.commons.persist.entity.GrantPO;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.lottery.commons.persist.service.UserService;
import com.xhcms.lottery.lang.EntityStatus;

public class GrantServiceImpl implements GrantService {
	@Autowired
	private GrantDao grantDao;
	@Autowired
	private UserService userService;
	@Autowired
    private AccountService accountService;
	
	@Override
	@Transactional
	public void listGrant(Paging paging, long userId, int status, Date beginTime, Date endTime) {
		List<GrantPO> list = grantDao.find(paging, userId, beginTime, endTime, status);
		List<Grant> rets = new ArrayList<Grant>();
		
		Grant g = null;
		for(GrantPO po : list) {
			g = new Grant();
			BeanUtils.copyProperties(po, g);
			User u = userService.getUser(po.getUserId());
			if (u != null) {
				g.setUser(u);
			}
			rets.add(g);
		}
		paging.setResults(rets);
	}
	
	@Override
	@Transactional
	public List<Long> sponsorGrant(List<User> users, Grant grant, int userId) {
		List<Long> ids = new ArrayList<Long>();
		grant.setCreatedTime(new Date());
		grant.setOperatorId(userId);
		grant.setStatus(EntityStatus.GRANT_INIT);

		GrantPO po = null;
		for(User u : users) {
			//判断有没有账户信息
			if( accountService.getAccount(u.getId()).getId() != null ) {
				po = new GrantPO();
				grant.setUserId(u.getId());
				BeanUtils.copyProperties(grant, po);
				grantDao.save(po);
				ids.add(po.getId());
			}
		}
		return ids;
	}
	
	@Override
	@Transactional
	public void batchPass(List<Long> ids, int auditId) {
		if(ids == null || ids.size() <= 0) {
			return;
		}
		List<GrantPO> gras = grantDao.find(ids);
		
		for(GrantPO g : gras) {
			// 判断是否未审核
			if (g.getStatus() == EntityStatus.GRANT_INIT) {
				g.setStatus(EntityStatus.GRANT_AUDIT);
				accountService.grant(auditId, g.getUserId(), g.getAmount(), g.getNote());
			}
		}
	}

	@Override
	@Transactional
	public void batchReject(List<Long> ids, int auditId) {
		if(ids == null || ids.size() <= 0) {
			return;
		}
		List<GrantPO> gras = grantDao.find(ids);
		
		for(GrantPO g : gras) {
			// 判断是否未审核
			if (g.getStatus() == EntityStatus.GRANT_INIT) {
				g.setStatus(EntityStatus.GRANT_REJECT);
			}
		}
	}

}
