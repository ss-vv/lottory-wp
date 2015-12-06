package com.unison.lottery.weibo.data.crawler.proxy;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.data.crawler.proxy.query.dao.ImeiHtcMacDao;
import com.unison.lottery.weibo.data.crawler.proxy.query.dao.entity.ImeiMacHtcPO;

public class UserChooserImpl implements UserChooser {

	@Autowired
	private ImeiHtcMacDao imeiHtcMacDao;

	@Override
	public ImeiMacHtcPO randomChooseOne() {
		ImeiMacHtcPO user=null;
		List<ImeiMacHtcPO> users=imeiHtcMacDao.findAll();
		if(null!=users&&!users.isEmpty()){
			int size=users.size();
			Random random=new Random();
			int index=random.nextInt(size);
			user=users.get(index);
		}
		return user;
	}

}
