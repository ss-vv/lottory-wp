package com.xhcms.lottery.commons.persist.service.impl;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.lottery.commons.data.HX_user; 
import com.xhcms.lottery.commons.persist.dao.HX_userDao;
import com.xhcms.lottery.commons.persist.entity.HX_userPO;
import com.xhcms.lottery.commons.persist.service.HX_userService;
@Transactional
public class HX_userServiceImpl implements HX_userService {

	@Autowired
    private HX_userDao hX_userDao;
	
	@Override
	public HX_user getHX_user(Long id) {
		HX_user hx_user = new HX_user();
		HX_userPO hx_userPO = hX_userDao.findHx_userByUserPOId(String.valueOf(id));
		if(hx_userPO != null){
			hx_user.setPassword(hx_userPO.getHx_password());
			hx_user.setUsername(hx_userPO.getHx_username());
			hx_user.setUserId(String.valueOf(hx_userPO.getUserId()));
			hx_user.setId(hx_userPO.getId());
		}
		return hx_user;
	}

	@Override
	public Long saveOrUpdateHX_user(HX_user hx_user, String userId, String nickName) {
		HX_userPO hx_userPO = new HX_userPO();
		if(hx_user != null){
			hx_userPO.setHx_password(hx_user.getPassword());
			hx_userPO.setHx_username(hx_user.getUsername());
			hx_userPO.setNickname(nickName);
			hx_userPO.setUserId(Long.valueOf(userId));
		}
		return hX_userDao.createHX_user(hx_userPO);
	}

	@Override
	public HX_user findHX_userByUsername(String username) {
		HX_user hx_user = new HX_user();
		HX_userPO hx_userPO = hX_userDao.findHx_userByUserPOName(username);
		if(hx_userPO != null){
			hx_user.setPassword(hx_userPO.getHx_password());
			hx_user.setUsername(hx_userPO.getHx_username());
			hx_user.setUserId(String.valueOf(hx_userPO.getUserId()));
			hx_user.setNickName(hx_userPO.getNickname());
		}
		return hx_user;
	}

	@Override
	public List<HX_user> findHX_userByUsernames(List<String> usernames) {
		 List<HX_user> hx_users = new ArrayList<HX_user>();
		 List<HX_userPO> hx_userPOs = hX_userDao.findHx_userByUserPONames(usernames);
		 if(hx_userPOs != null && !hx_userPOs.isEmpty()){
			 HX_user hx_user = new HX_user();
			 for(HX_userPO hUserPO : hx_userPOs){
				 hx_user.setNickName(hUserPO.getNickname());
				 hx_user.setUserId(String.valueOf(hUserPO.getUserId()));
				 hx_users.add(hx_user);
			 }
		 }
		 return hx_users;
	}

	@Override
	public HX_user getHXuserByNickName(String name) {
		HX_user hx_user = new HX_user();
		HX_userPO hx_userPO = hX_userDao.findHx_userByNickName(name);
		if(hx_userPO != null){
			hx_user.setPassword(hx_userPO.getHx_password());
			hx_user.setUsername(hx_userPO.getHx_username());
			hx_user.setUserId(String.valueOf(hx_userPO.getUserId()));
			hx_user.setNickName(hx_userPO.getNickname());
		}
		return hx_user;
	}

	@Override
	public void updateHX_user(HX_user hx_user) {
		HX_userPO hx_userPO = new HX_userPO();
		if(hx_user != null){
			hx_userPO.setHx_password(hx_user.getPassword());
			hx_userPO.setHx_username(hx_user.getUsername());
			hx_userPO.setNickname(hx_user.getNickName());
			hx_userPO.setUserId(Long.valueOf(hx_user.getUserId()));
			hx_userPO.setId(hx_user.getId());
		}
		hX_userDao.updateHX_user(hx_userPO);
	}

	@Override
	public Object[] findUserInfoByUsername(String useranme) {
		return hX_userDao.findHxUserByUsername(useranme);
	}

//	@Override
//	public HX_user makeHX_user(Long id, String channel, String nickname) {
//		HX_user hx_user = getHX_user(id);
//		//注册环信账号和密码，并将其入库
//		if(StringUtils.isBlank(hx_user.getUsername()) && StringUtils.isBlank(hx_user.getPassword())){
//			 hx_user = makeHX_usernameAndHX_password(nickname, id);
//		} 
//		return hx_user;
//	}
//
//	private HX_user makeHX_usernameAndHX_password(String nickname, Long id) {
//		
//		return null;
//	}
}
