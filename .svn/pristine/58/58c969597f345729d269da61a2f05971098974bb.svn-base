/**
 * 
 */
package com.xhcms.ucenter.service.user.impl;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.commons.lang.Assert;
import com.xhcms.commons.util.Text;
import com.xhcms.lottery.commons.data.User;
import com.xhcms.lottery.commons.persist.dao.UserDao;
import com.xhcms.lottery.commons.persist.entity.UserPO;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.ucenter.exception.UCException;
import com.xhcms.ucenter.lang.AppCode;
import com.xhcms.ucenter.lang.EnumLoginStatus;
import com.xhcms.ucenter.lang.EnumLoginType;
import com.xhcms.ucenter.persist.dao.ISyncUserDao;
import com.xhcms.ucenter.service.user.IUserManager;
import com.xhcms.ucenter.util.DiscuzAuthCodeUtils;

/**
 * @author bean 用户基本操作
 */
public class UserManagerImpl implements IUserManager {
	private static Logger logger = Logger.getLogger(UserManagerImpl.class);
	
    @Autowired
    private UserDao userDao;
    @Autowired
    private AccountService accountService;
    @Autowired
    private ISyncUserDao syncUserDao;
    
    @Override
    @Transactional(readOnly = true)
    public boolean login(int loginType, String target, String password) {
        UserPO user = null;
        if (EnumLoginType.USERNAME.getValue() == loginType) {
            user = userDao.getUserByUsername(target);
        } else if (EnumLoginType.MOBILE.getValue() == loginType) {
        	user = userDao.getUserByVerifyedMobile(target);
        }

        if (user != null) {
            String pwd = Text.MD5Encode(password);
            // 判断用户密码 和 登录状态
            if (pwd.equals(user.getPassword()) && user.getStatus() != EnumLoginStatus.STATUS_DISABLE.getValue()) {
            	//同步用户
            	syncUserToBBS(user);
                return true;
            } else {
            	logger.debug(String.format("Login Failed by %s", target));
            }
        }
        return false;
    }

    @Transactional
    @Override
    public void updateUserLoginStatus(long uid, String lastLoginIp, Date lastLoginTime) {
        UserPO po = userDao.get(uid);
        if (po != null) {
            po.setLastLoginIp(lastLoginIp);
            po.setLastLoginTime(lastLoginTime);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void regist(User userVO) {
        UserPO user = userDao.getUserByUsername(userVO.getUsername());
        if (user != null) {
            throw new UCException("user.UserExist", "user already exist!");
        }

        user = new UserPO();

        // 注册信息
        user.setUsername(userVO.getUsername());
        user.setRealname(userVO.getRealname() == null ? "" : userVO.getRealname());
        user.setIdNumber(userVO.getIdNumber());

        String md5PWD = Text.MD5Encode(userVO.getPassword());
        user.setPassword(md5PWD);

        user.setQuestion(userVO.getQuestion() == null ? "" : userVO.getQuestion());
        user.setAnswer(userVO.getAnswer() == null ? "" : userVO.getAnswer());

        user.setEmail(userVO.getEmail());
        user.setMobile(userVO.getMobile());
        user.setVerifyStatus(userVO.getVerifyStatus());

        user.setPhone(userVO.getPhone());
        user.setAddress(userVO.getAddress());
        user.setGender(userVO.getGender());
        user.setBirthday(userVO.getBirthday());
        user.setProvince(userVO.getProvince());
        user.setCity(userVO.getCity());
        user.setStatus(userVO.getStatus());

        user.setIp(userVO.getIp());
        user.setCreatedTime(userVO.getCreatedTime());
        user.setLastLoginIp(userVO.getLastLoginIp());
        user.setLastLoginTime(userVO.getLastLoginTime());
        user.setLoginNumber(userVO.getLoginNumber());
        user.setLocked_time(userVO.getLocked_time());
        user.setIsLocked(userVO.getIsLocked());
        user.setPid(userVO.getPid());
        
        user.setReferer(userVO.getReferer());

        userDao.save(user);
        
        // 添加银行信息
        accountService.addAccount(user.getId(), user.getUsername());
        userVO.setId(user.getId());
        
        syncUserToBBS(user);
    }

    @Override
    public void closeUser(long userId) {
        UserPO u = userDao.get(userId);
        if (u != null) {
            u.setStatus((short) EnumLoginStatus.STATUS_DISABLE.getValue());
        }
    }

    @Override
    public void openUser(long userId) {
        UserPO u = userDao.get(userId);
        if (u != null) {
            u.setStatus((short) EnumLoginStatus.STATUS_ENABLE.getValue());
        }
    }

	@Override
	@Transactional
	public void updatePasswd(long userId, String oldPasswd, String newPasswd) {
		UserPO po = userDao.get(userId);
		if (po != null) {
			String pwd = Text.MD5Encode(oldPasswd);
			Assert.eq(pwd, po.getPassword(), AppCode.USER_PASSWORD_OLD_WRONG);
			po.setPassword(Text.MD5Encode(newPasswd));
		}
	}

    @Override
    @Transactional
    public void updatePasswd(long userId, String newPasswd) {
        UserPO po = userDao.get(userId);
        if (po != null) {
            po.setPassword(Text.MD5Encode(newPasswd));
        }
    }
	
    @Override
    @Transactional
    public void updateInfo(User user) {
        UserPO po = userDao.get(user.getId());
        if(po != null){
            po.setGender(user.getGender());
            po.setProvince(user.getProvince());
            po.setCity(user.getCity());
        }
    }

    @Override
    @Transactional
    public void updateMobile(Long uid, String newMobile) {
        UserPO po = userDao.get(uid);
        if (po != null) {
            po.setMobile(newMobile);
            po.setVerifyStatus(EntityStatus.VERIFY_MOBILE | po.getVerifyStatus());
        }
    }
    
    private boolean syncUserToBBS(UserPO user) {
    	if(syncUserDao.findMemberByUsername(user.getUsername()) != null) {
    		logger.debug("User is exists in bbs! " + user.getUsername());
    		syncDiscuzPassword(user.getId());
    		return true;
    	}
    	
    	try {
	    	//同步uc表
	    	syncUserDao.insertMember(user.getId(), user.getUsername(), DiscuzAuthCodeUtils.generateKey(user.getId()), 
	    			user.getEmail()==null?"":user.getEmail(), user.getIp()==null?"":user.getIp());
	    	//同步bbs
	    	syncUserDao.insertCommonMember(user.getId(), user.getUsername(), DiscuzAuthCodeUtils.generateKey(user.getId()), user.getEmail()==null?"":user.getEmail(), 
	    			user.getIp()==null?"":user.getIp(), 10 + "");
	    	syncUserDao.insertMemberCount(user.getId());
	    	
    	} catch (Throwable exp) {
    		logger.error(exp.getMessage(), exp);
    		return false;
    	}
    	
    	return true;
    }
    
    private boolean syncDiscuzPassword(Long uid) {
    	
    	try {
    		syncUserDao.updateMemberPassword(uid, DiscuzAuthCodeUtils.generateKey(uid));
    	} catch(Throwable exp) {
    		logger.error(exp.getMessage(), exp);
    		return false;
    	}
    		
    	return true;
    }
}
