/**
 * 
 */
package com.xhcms.ucenter.persistent.service.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.commons.lang.Assert;
import com.xhcms.commons.util.Text;
import com.xhcms.exception.XHRuntimeException;
import com.xhcms.lottery.commons.data.User;
import com.xhcms.lottery.commons.persist.dao.UserDao;
import com.xhcms.lottery.commons.persist.entity.UserPO;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.ucenter.exception.UCException;
import com.xhcms.ucenter.lang.AppCode;
import com.xhcms.ucenter.lang.EnumLoginStatus;
import com.xhcms.ucenter.lang.EnumLoginType;
import com.xhcms.ucenter.lang.PasswordType;
import com.xhcms.ucenter.persistent.service.IUserManager;
import com.xhcms.ucenter.persistent.service.exception.LoginNameNotFoundException;
import com.xhcms.ucenter.persistent.service.exception.LoginNameOrPasswordBlankException;
import com.xhcms.ucenter.persistent.service.exception.PasswordWrongException;
import com.xhcms.ucenter.persistent.service.exception.RegisteFailNickNameOrPhoneNumberIsBlankException;
import com.xhcms.ucenter.persistent.service.exception.RegisteFailPasswordIsBlankException;

/**
 * @author bean 用户基本操作
 */
public class UserManagerImpl implements IUserManager {
	private static Logger logger = Logger.getLogger(UserManagerImpl.class);
	
    @Autowired
    private UserDao userDao;
    @Autowired
    private AccountService accountService;
    
    @Override
    @Transactional(readOnly = true)
    public boolean login(int loginType, String target, String password) {
        UserPO user = null;
        if (EnumLoginType.USERNAME.getValue() == loginType) {
            user = userDao.getUserByUsername(target);
        }

        if (user != null) {
            String pwd = Text.MD5Encode(password);
            // 判断用户密码
            if (pwd.equals(user.getPassword()) && user.getStatus() != EnumLoginStatus.STATUS_DISABLE.getValue()) {
            	//同步用户
//            	syncUserToBBS(user);
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
        user.setHeadImageURL(userVO.getHeadImageURL());
        user.setNickName(userVO.getNickName());
        user.setSinaWeiboUid(userVO.getSinaWeiboUid());
		user.setSinaWeiboToken(userVO.getSinaWeiboToken());
		user.setQqConnectUid(userVO.getQqConnectUid());
		user.setQqConnectToken(userVO.getQqConnectToken());
		/**
		 * TODO
		 * 客户端目前用WeixinUid保存unionId，需要把WeixinUnionId放入WeixinUid
		 * 使得网站微信登录注册的用户在客户端可以登录
		 */
		user.setWeixinUid(userVO.getWeixinUnionId());
		user.setWeixinPCUid(userVO.getWeixinPCUid());
		user.setWeixinUnionId(userVO.getWeixinUnionId());
		user.setWeixinToken(userVO.getWeixinToken());
		user.setAlipayUid(userVO.getAlipayUid());
		user.setAlipayToken(userVO.getAlipayToken());
        userDao.save(user);
        
        // 添加银行信息
        accountService.addAccount(user.getId(), user.getUsername());
        userVO.setId(user.getId());
        
//        syncUserToBBS(user);
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
    @Transactional(noRollbackFor=XHRuntimeException.class)
	public void updatePasswdForClient(long userId, String oldPasswd, String newPasswd,String type) {
		try{
			if(type.equals(PasswordType.LOGIN)){
				updatePasswd(userId,oldPasswd,newPasswd);
				
			}
			else if(type.equals(PasswordType.WITHDRAW)){
				accountService.checkAndUpdatePasswd(userId, oldPasswd,newPasswd);
			}
			
		}
		catch(XHRuntimeException e){
			throw e;
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

	@Override
	@Transactional
	public UserPO loginByNameAndPasswordMD5(String loginName,
			String passwordMD5) throws LoginNameNotFoundException,PasswordWrongException,LoginNameOrPasswordBlankException {
		UserPO result=null;
		if(StringUtils.isBlank(loginName)||StringUtils.isBlank(passwordMD5)){
			throw new LoginNameOrPasswordBlankException();
		}
		UserPO user = userDao.getUserByUsername(loginName);
		if(null==user){
			//绑定手机号的用户 针对网站用户使用
			user = userDao.getUserByVerifyedMobile(loginName);
			if(null == user){
				throw new LoginNameNotFoundException();
			}
		}
		if(!passwordMD5.equals(user.getPassword())){
			throw new PasswordWrongException();
		}
		if( user.getStatus() == EnumLoginStatus.STATUS_ENABLE.getValue()){
			userDao.updateLastLoginStatus(user);
			result=user;
			
		}
		
		return result;
	}
	
	@Override
	@Transactional
	public String regist4Client(User userVO)
			throws RegisteFailPasswordIsBlankException,RegisteFailNickNameOrPhoneNumberIsBlankException{
        if(StringUtils.isBlank(userVO.getPassword()) && isVaildUid(userVO)){
        	throw new RegisteFailPasswordIsBlankException();
        }
        if(isVaildUid(userVO) && StringUtils.isBlank(userVO.getMobile())){
        	throw new RegisteFailNickNameOrPhoneNumberIsBlankException();
        }
        UserPO user = new UserPO();

        // 注册信息
        user.setUsername(StringUtils.isBlank(userVO.getMobile()) ? userVO.getUsername() : userVO.getMobile());
        user.setRealname(StringUtils.isBlank(userVO.getRealname()) ? "" : userVO.getRealname());
        String md5PWD = Text.MD5Encode(userVO.getPassword());
        user.setPassword(md5PWD);
        user.setMobile(userVO.getMobile());
        user.setAnswer("");
        user.setQuestion("");
        user.setGender(0);
        
        user.setStatus(EnumLoginStatus.STATUS_ENABLE.getValue());
        user.setVerifyStatus(EntityStatus.VERIFY_MOBILE);//用户只有经过了手机号验证，才能注册，所以这里设置为已绑定手机号
        Date now=new Date();
        user.setCreatedTime(now);
        user.setLastLoginTime(now);
        user.setLoginNumber(1);
        
        user.setLocked_time(now);
        user.setIsLocked(Constants.UNLOCKED);
        user.setPid(userVO.getPid());
        
        //user.setReferer(userVO.getReferer());
        
        //第三方信     
        user.setNickName(userVO.getNickName());
        user.setSinaWeiboToken(userVO.getSinaWeiboToken());
        user.setSinaWeiboUid(userVO.getSinaWeiboUid());
        user.setWeixinToken(userVO.getWeixinToken());
        user.setWeixinUid(userVO.getWeixinUid());
        user.setQqConnectToken(userVO.getQqConnectToken());
        user.setQqConnectUid(userVO.getQqConnectUid());
        user.setHeadImageURL(userVO.getHeadImageURL());
        
        userDao.save(user);
        
        // 添加银行信息
        accountService.addAccount(user.getId(), user.getUsername());
        userVO.setId(user.getId());
		
        return user.getId().toString();
	}

	/**
	 * 验证uid是否为空
	 * @param userVO
	 * @return
	 */
	private boolean isVaildUid(User userVO) {
		if(StringUtils.isNotBlank(userVO.getSinaWeiboUid()) 
				|| StringUtils.isNotBlank(userVO.getWeixinUid()) ||  StringUtils.isNotBlank(userVO.getQqConnectUid())){
			return false;
		}
		return true;
	}

	@Override
	@Transactional
	public void updateIDNumber(User user) {
		UserPO po = userDao.get(user.getId());
        if(po != null){
        	po.setIdNumber(user.getIdNumber());
        }
	}
	
	@Transactional
	@Override
	public void updateRealName(User user) {
		UserPO po = userDao.get(user.getId());
        if(po != null){
        	po.setRealname(user.getRealname());
        }
	}

	@Override
	public boolean isExistNickName(String nickName) {
		return userDao.isExistByNickName(nickName);
	}

	@Override
	public UserPO loginByUidAndType(String uid, String type) {
		UserPO result=null;
		if(StringUtils.isBlank(uid)||StringUtils.isBlank(uid)){
			return result;
		}
		UserPO user = userDao.getUserByUidAndType(uid, type);
		if(null == user){
			return result;
		}
		if( user.getStatus() == EnumLoginStatus.STATUS_ENABLE.getValue()){
			userDao.updateLastLoginStatus(user);
			result=user;
		}
		return result;
	}

	@Override
	public boolean fingUserByPhoneNumber(String phoneNumber) {
		boolean result = false;
		UserPO po = userDao.getUserByPhoneNumberAndStatus0(phoneNumber);
        if(po != null){
            result = true;
        }
		return result;
	}
    
//    private boolean syncUserToBBS(UserPO user) {
//    	if(syncUserDao.findMemberByUsername(user.getUsername()) != null) {
//    		logger.debug("User is exists in bbs! " + user.getUsername());
//    		syncDiscuzPassword(user.getId());
//    		return true;
//    	}
//    	
//    	try {
//	    	//同步uc�?//	    	syncUserDao.insertMember(user.getId(), user.getUsername(), DiscuzAuthCodeUtils.generateKey(user.getId()), 
//	    			user.getEmail()==null?"":user.getEmail(), user.getIp()==null?"":user.getIp());
//	    	//同步bbs
//	    	syncUserDao.insertCommonMember(user.getId(), user.getUsername(), DiscuzAuthCodeUtils.generateKey(user.getId()), user.getEmail()==null?"":user.getEmail(), 
//	    			user.getIp()==null?"":user.getIp(), 10 + "");
//	    	syncUserDao.insertMemberCount(user.getId());
//	    	
//    	} catch (Throwable exp) {
//    		logger.error(exp.getMessage(), exp);
//    		return false;
//    	}
//    	
//    	return true;
//    }
//    
//    private boolean syncDiscuzPassword(Long uid) {
//    	
//    	try {
//    		syncUserDao.updateMemberPassword(uid, DiscuzAuthCodeUtils.generateKey(uid));
//    	} catch(Throwable exp) {
//    		logger.error(exp.getMessage(), exp);
//    		return false;
//    	}
//    		
//    	return true;
//    }
}
