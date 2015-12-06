package com.xhcms.lottery.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.ActivityNotify;
import com.xhcms.lottery.commons.persist.dao.ActivityNotifyDao;
import com.xhcms.lottery.commons.persist.entity.ActivityNotifyPO;
import com.xhcms.lottery.service.ActivityNotifyService;

/**
 * @desc
 * @createTime 2012-12-5
 * @author lei.li@unison.net.cn
 * @version 1.0
 */
public class ActivityNotifyServiceImpl implements ActivityNotifyService {

	@Autowired
	private ActivityNotifyDao activityNotifyDao;
	
	@Override
	@Transactional
	public List<ActivityNotifyPO> listActivity(Paging paging, String activityName) {
		return activityNotifyDao.listActivity(paging, activityName);
	}

	@Override
	@Transactional
	public void add(ActivityNotifyPO notify) {
		activityNotifyDao.add(notify);
	}

	@Override
	@Transactional
	public void modify(ActivityNotify notify) {
		activityNotifyDao.modify(notify);
	}

	@Override
	@Transactional
	public void remove(Long activityNotifyId) {
		activityNotifyDao.remove(activityNotifyId);
	}
}