/**
 * 
 */
package com.xhcms.lottery.commons.persist.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.commons.lang.Assert;
import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.CustomMade;
import com.xhcms.lottery.commons.data.CustomMadeDetail;
import com.xhcms.lottery.commons.data.User;
import com.xhcms.lottery.commons.persist.dao.CustomMadeDetailDao;
import com.xhcms.lottery.commons.persist.dao.CustomMadeDao;
import com.xhcms.lottery.commons.persist.dao.UserDao;
import com.xhcms.lottery.commons.persist.entity.CustomMadeDetailPO;
import com.xhcms.lottery.commons.persist.entity.CustomMadePO;
import com.xhcms.lottery.commons.persist.entity.UserPO;
import com.xhcms.lottery.commons.persist.service.CustomMadeService;
import com.xhcms.lottery.lang.AppCode;

/**
 * @author Bean.Long
 *
 */
public class CustomMadeServiceImpl implements CustomMadeService {
	@Autowired
	private CustomMadeDao customMadeDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private CustomMadeDetailDao customMadeDailyDao;
	
	@Override
	@Transactional(readOnly = true)
	public CustomMade getCustomMade(long userId, long followedUserId) {
		CustomMadePO po = customMadeDao.findCustomMade(userId, followedUserId);
		
		if(po != null) {
			CustomMade cm = copyBean(po);
			return cm;
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public void updateCustomMade(long userId, long fuid, CustomMade customMade) {
		Assert.isTrue(userId != fuid, AppCode.CM_CANTSELF);
		
		CustomMadePO po;
		if(customMade.getId() != null && customMade.getId() > 0) {
			//update
			po = customMadeDao.get(customMade.getId());
		} else {
			//create
			UserPO userPO = userDao.get(userId); 
			UserPO followUserPO = userDao.get(fuid);
			
			po = new CustomMadePO();
			po.setUserId(userId);
			po.setUsername(userPO.getUsername());
			po.setFollowedUser(followUserPO);
		}
		
		po.setMaxMoney(customMade.getMaxMoney());
		po.setMaxFollowCount(customMade.getMaxFollowCount());
		po.setStartTime(customMade.getStartTime());
		po.setEndTime(customMade.getEndTime());
		po.setPlayIds(customMade.getPlayIds());
		po.setFollowBuy(customMade.isFollowBuy());
		po.setFollowMultiple(customMade.getFollowMultiple());
		po.setGroupBuy(customMade.isGroupBuy());
		po.setGroupMoney(customMade.getGroupMoney());
		
		customMadeDao.saveOrUpdate(po);
	}

	@Override
	@Transactional
	public void deleteCustomMade(long id) {		
		CustomMadePO po = customMadeDao.get(id);
		po.setFollowBuy(false);
		po.setGroupBuy(false);
		customMadeDao.update(po);
	}
	
	@Override
	@Transactional(readOnly = true)
	public void listCustomMades(int type, long userId, Paging paging) {
		List<CustomMadePO> pos = Collections.emptyList();
		if(type == 0) {
			pos = customMadeDao.findMyCustomMades(userId, paging);
		} else if(type == 1) {
			pos = customMadeDao.findFollowMeCustomMades(userId, paging);
		}
		
		List<CustomMade> cms = new ArrayList<CustomMade>();
		for (CustomMadePO customMade : pos) {
			cms.add(copyBean(customMade));
		}
		paging.setResults(cms);
	}
	
	@Override
	@Transactional(readOnly = true)
	public void listCustomMadeDetails(long userId, long followedUserId, Paging paging) {
		
		List<CustomMadeDetailPO> pos = customMadeDailyDao.findCustomMadeDetails(userId, followedUserId, paging);
				
		List<CustomMadeDetail> cmds = new ArrayList<CustomMadeDetail>();
		for(CustomMadeDetailPO po : pos) {
			cmds.add(copyBean(po));
		}
		
		paging.setResults(cmds);
	}

	private CustomMade copyBean(CustomMadePO po) {
		if(po == null)
			return null;
		
		CustomMade cm = new CustomMade();
		BeanUtils.copyProperties(po, cm, new String[]{"followedUser"});
		
		if(po.getFollowedUser() != null) {
			User user = new User();
			BeanUtils.copyProperties(po.getFollowedUser(), user);
			cm.setFollowedUser(user);
		}

		return cm;
	}
	
	private CustomMadeDetail copyBean(CustomMadeDetailPO po) {
		if(po == null)
			return null;
		
		CustomMadeDetail cmd = new CustomMadeDetail();
		BeanUtils.copyProperties(po, cmd, new String[]{"customMade", "betScheme"});
		
		cmd.setFollowUsername(po.getCustomMade().getUsername());
		cmd.setSchemeId(po.getBetScheme().getId());
		cmd.setFollowedUsername(po.getCustomMade().getFollowedUser().getUsername());
		
		return cmd;
	}

	@Override
	@Transactional
	public int countCustomMades(Long fuid) {
		return customMadeDao.countCustomMades(fuid);
	}
}
