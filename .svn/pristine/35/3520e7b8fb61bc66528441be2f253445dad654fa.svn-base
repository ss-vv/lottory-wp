package com.xhcms.lottery.commons.persist.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.lottery.commons.data.DaVGroup;
import com.xhcms.lottery.commons.data.User;
import com.xhcms.lottery.commons.persist.dao.DaVGroupDao;
import com.xhcms.lottery.commons.persist.dao.UserDao;
import com.xhcms.lottery.commons.persist.entity.DaVGroupPO;
import com.xhcms.lottery.commons.persist.entity.UserPO;
import com.xhcms.lottery.commons.persist.service.DaVGroupService;

@Transactional(readOnly=true)
public class DaVGroupServiceImpl implements DaVGroupService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserDao userDao;
    @Autowired
    private DaVGroupDao daVGroupDao;
	
	@Override
	public List<DaVGroup> listGroup(Integer firstResult, Integer pageMaxResult) {
		return null;
	}

	@Override
	public List<DaVGroup> findDaVGroupByGroupId(String groupid) {
		logger.info("大v群的群号：{}", groupid);
		List<DaVGroup> daVGroups = new ArrayList<DaVGroup>();
		if(StringUtils.isNotBlank(groupid)){
			List<DaVGroupPO> daVGroupPOs = daVGroupDao.findDaVGroupPOByGroupId(groupid);
			if(daVGroupPOs != null && !daVGroupPOs.isEmpty()){
				for(DaVGroupPO daVGroupPO : daVGroupPOs){
					DaVGroup daVGroup = new DaVGroup();
					BeanUtils.copyProperties(daVGroupPO, daVGroup);
					daVGroups.add(daVGroup);
				}
			}
		}
		return daVGroups;
	}

	@Override
	public List<User> listAll() {
		List<String> list = daVGroupDao.listAllVidsByDistinct();
		List<User> users = new ArrayList<>();
		for (String string : list) {
			UserPO userPO = userDao.findById(Long.parseLong(string));
			if(null != userPO){
				User user = new User();
				BeanUtils.copyProperties(userPO, user);
				users.add(user);
			}
		}
		return users;
	}
}
