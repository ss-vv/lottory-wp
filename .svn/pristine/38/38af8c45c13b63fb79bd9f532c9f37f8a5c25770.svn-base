package com.unison.lottery.weibo.data.crawler.proxy;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.weibo.data.crawler.proxy.query.dao.UserAgentDao;
import com.unison.lottery.weibo.data.crawler.proxy.query.dao.entity.UserAgentPO;


/**
 * @author 彭保星
 *
 * @since 2014年10月27日下午6:05:49
 */
public class UserAgenChooserImpl implements UserAgentChooser {
	
	@Autowired
	public UserAgentDao userAgentDao;
	/* (non-Javadoc)
	 * @see com.unison.lottery.weibo.data.proxy.PhoneAndClientVersionChooser#chooseOne()
	 */
	@Override
	@Transactional
	public UserAgentPO randomChooseOne() {
		// TODO Auto-generated method stub
		UserAgentPO userAgentPO = null;
		List<UserAgentPO> userAgentPOs = userAgentDao.findAll();
		if (userAgentPOs != null && !userAgentPOs.isEmpty()) {
			userAgentPO = randomChoosOneFromList(userAgentPOs);
		}
		return userAgentPO;
	}
	private UserAgentPO randomChoosOneFromList(List<UserAgentPO> userAgentPOs) {
		// TODO Auto-generated method stub
		UserAgentPO userAgentPO;
		int size=userAgentPOs.size();
		Random random=new Random();
		int index=random.nextInt(size);
		userAgentPO=userAgentPOs.get(index);
		return userAgentPO;
	}

}
