package com.xhcms.lottery.commons.persist.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.util.Text;
import com.xhcms.lottery.commons.data.NewRegistUser;
import com.xhcms.lottery.commons.data.User;
import com.xhcms.lottery.commons.persist.dao.UserDao;
import com.xhcms.lottery.commons.persist.entity.UserPO;
import com.xhcms.lottery.commons.persist.service.UserService;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.utils.DES;

public class UserServiceImpl implements UserService {
	
	private Logger log = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserDao userDao;
	
	@Override
	@Transactional(readOnly = true)
    public List<User> listUser(Paging paging, Date from, Date to, int status, String username, String ip, String idNumber) {
	    List<UserPO> data = userDao.find(paging, from, to, status, username, ip, idNumber);
        List<User> results = new ArrayList<User>();
        for (UserPO po : data) {
            User u = new User();
            BeanUtils.copyProperties(po, u);
            results.add(u);
        }
        paging.setResults(results);
        return results;
    }
	
	@Override
	@Transactional(readOnly = true)
    public List<User> listUser(Paging paging, Date from, Date to, int status, String username, String ip, String idNumber, String mobile, String nickName) {
	    List<UserPO> data = userDao.find(paging, from, to, status, username, ip, idNumber, mobile, nickName);
        List<User> results = new ArrayList<User>();
        for (UserPO po : data) {
            User u = new User();
            BeanUtils.copyProperties(po, u);
            results.add(u);
        }
        paging.setResults(results);
        return results;
    }

    @Override
    @Transactional
    public void open(Collection<Long> ids) {
        userDao.updateStatus(ids, EntityStatus.NORMAL);
    }

    @Override
    @Transactional
    public void close(Collection<Long> ids) {
        userDao.updateStatus(ids, EntityStatus.LOCKED);
    }

	@Override
	@Transactional
	public User getUser(String username, String realname) {
		UserPO po = userDao.getUserByUsername(username);
		User u = null;
		if (null != po && realname.equals(po.getRealname())) {
			u = new User();
			BeanUtils.copyProperties(po, u);
		}
		return u;
	}

	@Override
	@Transactional
	public void update(User user) {
		UserPO userPO = userDao.get(user.getId());
		userPO.setSinaWeiboUid(user.getSinaWeiboUid());
		userPO.setSinaWeiboToken(user.getSinaWeiboToken());
		userPO.setQqConnectUid(user.getQqConnectUid());
		userPO.setQqConnectToken(user.getQqConnectToken());
		userPO.setWeixinUid(user.getWeixinUid());
		userPO.setWeixinPCUid(user.getWeixinPCUid());
		userPO.setWeixinUnionId(user.getWeixinUnionId());
		userPO.setWeixinToken(user.getWeixinToken());
		userPO.setAlipayUid(user.getAlipayUid());
		userPO.setAlipayToken(user.getAlipayToken());
		userPO.setNickName(user.getNickName());
		userPO.setHeadImageURL(user.getHeadImageURL());
	}
	@Override
	@Transactional
	public User getUser(long userId) {
		UserPO po = userDao.get(userId);
		User u = null;
		if (null != po) {
			u = new User();
			BeanUtils.copyProperties(po, u);
		}
		return u;
	}

    @Override
    @Transactional
    public void updateRealname(Long userId, String realname) {
        UserPO po = userDao.get(userId);
        if (null != po) {
            po.setRealname(realname);
        }
    }

    @Override
    @Transactional
    public void updateIdnumber(Long userId, String idnumber) {
        UserPO po = userDao.get(userId);
        if (null != po) {
            po.setIdNumber(idnumber);
        }
    }

	@Override
	@Transactional
	public String resetPassword(long userId) {
		// 产生随机码
		String pwd = ""+Math.abs(RandomUtils.nextInt(99999999));
		UserPO po = userDao.get(userId);
		po.setPassword(Text.MD5Encode(pwd));
		return pwd;
	}
	
	@Override
	@Transactional
	public int unlockUserAccount(long id) {
		UserPO po = userDao.get(id);
		po.setLoginFailureNumber(0);
		po.setIsLocked(0);
		Calendar cal = Calendar.getInstance();
		cal.set(1970, 0, 1, 0, 0, 0);
		Date d = cal.getTime();
		po.setLocked_time(d);
		
		return 1;
	}
	
	@Transactional
	@Override
	public List<NewRegistUser> findNewRegUserGroupByPid(Date from, Date to, String channel) {
		List<Object[]> users = userDao.findNewRegUserGroupByPid(from, to);
		Map<String, NewRegistUser> map = new HashMap<String, NewRegistUser>();
		if(null != users && users.size() > 0) {
			for(Object[] obj : users) {
				String date = String.valueOf(obj[0]);
				int count = Integer.parseInt(String.valueOf(obj[1]));
				String pid = String.valueOf(obj[2]);
				try {
					String encryptStr = DES.decryptDES(pid, "89wUzBcP", "utf-8");
					if(encryptStr.indexOf("android") >= 0) {
						pid = "android";
					} else if(encryptStr.indexOf("ios") >= 0) {
						pid = "ios";
					}
				} catch (Exception e) {
					log.info("非客户端渠道无法被DES，pid={}", pid);
				}
				if(StringUtils.isNotBlank(channel) && !pid.equals(channel)) {
					continue;
				}
				String key = date+pid;
				NewRegistUser newReg = map.get(key);
				if(null == newReg) {
					newReg = new NewRegistUser(date, pid, count);
				} else {
					count = count + newReg.getCount();
					newReg = new NewRegistUser(date, pid, count);
				}
				map.put(key, newReg);
			}
		}
		List<NewRegistUser> result = new ArrayList<NewRegistUser>(map.values());
		Collections.sort(result, new Comparator<NewRegistUser>() {

			@Override
			public int compare(NewRegistUser o1, NewRegistUser o2) {
				return - o1.getDate().compareTo(o2.getDate());
			}
			
		});
		return result;
	}
	 @Transactional
	 @Override
	 public void allow(long id){
		 userDao.updateUserAllow(id, 1);
	 }
	 @Transactional
	 @Override
	 public void unAllow(long id){
		  userDao.updateUserAllow(id, 0);
	 }
}
