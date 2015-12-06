package com.unison.lottery.api.registe.bo;


import java.awt.SystemColor;
import java.io.File;
import java.io.IOException;
import java.util.List; 

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.unison.lottery.api.login.hx.comm.Constants;
import com.unison.lottery.api.login.hx.httpclient.apidemo.EasemobChatGroups;
import com.unison.lottery.api.login.hx.httpclient.apidemo.EasemobIMUsers;
import com.unison.lottery.api.login.hx.httpclient.apidemo.HX_sendMassage;
import com.unison.lottery.api.login.service.HX_service;
import com.unison.lottery.api.model.User;
import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.response.model.RegisteResponse;
import com.unison.lottery.api.protocol.status.IStatusRepository;
import com.unison.lottery.api.protocol.status.ReturnStatus;
import com.unison.lottery.api.registe.util.CheckParamsUtil;
import com.unison.lottery.api.vGroup.data.GroupSecretkey;
import com.unison.lottery.weibo.common.service.IdGenerator;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.uc.persist.UserAccountDao;
import com.unison.lottery.weibo.uc.util.LoadUrlImageUtil;
import com.xhcms.commons.util.Text;
import com.xhcms.lottery.commons.data.DaVGroup;
import com.xhcms.lottery.commons.data.HX_user;
import com.xhcms.lottery.commons.persist.service.DaVGroupService;
import com.xhcms.lottery.commons.persist.service.HX_userService;
import com.xhcms.lottery.utils.DES;
import com.xhcms.ucenter.persistent.service.IUserManager;
import com.xhcms.ucenter.persistent.service.IUserValidIdManager;
import com.xhcms.ucenter.persistent.service.IVerifyService;
import com.xhcms.ucenter.persistent.service.exception.RegisteFailNickNameOrPhoneNumberIsBlankException;
import com.xhcms.ucenter.persistent.service.exception.RegisteFailPasswordIsBlankException;

public class RegisteBOImpl implements RegisteBO {
	
	@Autowired
	private IUserManager userManager;
	
	@Autowired
	private IStatusRepository statusRepository;

	private Logger logger=LoggerFactory.getLogger(getClass());

	@Autowired
	private IUserValidIdManager userValidManager;
	
	@Autowired
	private HX_userService hX_userService;
	
	@Autowired
	private DaVGroupService daVGroupService;
	
	@Autowired
	private IdGenerator idGenerator;
	
	@Autowired
	@Qualifier("registeService")
	private IVerifyService registeService;

	@Autowired
	private UserAccountDao userAccountDao;
	
	@Autowired
	private HX_service hX_service;
	
	@Autowired
	private com.xhcms.lottery.conf.SystemConf systemConf;
	
	@Override
	@Transactional
	public RegisteResponse regist(User user) {
//		RegisteResponse registeResponse = oldRegiste(user);
//		return registeResponse;
		RegisteResponse registeResponse=new RegisteResponse();
		if(allowSelfRegiste()){//允许自行注册
			registeResponse = oldRegiste(user);
		}
		else{
			ReturnStatus notAllowRegiste = statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.Registe.NOT_ALLOW);
			registeResponse.setReturnStatus(notAllowRegiste);
		}
		
		
		return registeResponse;
		
	}

	private boolean allowSelfRegiste() {
		return systemConf.isAllowSelfRegiste();
	}

	private RegisteResponse oldRegiste(User user) {
		RegisteResponse registeResponse=new RegisteResponse();
		ReturnStatus succStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.Registe.SUCC);
		ReturnStatus failStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.Registe.FAIL);
		ReturnStatus nickNameFail = statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.Registe.NICKNAME_FAIL);
		ReturnStatus nickNameIllegal = statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.Registe.NICKNAME_ILLEGAL);
		ReturnStatus verifyCodeFail = statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.Registe.SECURITY_CODE_FAIL);
		ReturnStatus registedCode = statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.Registe.REGISTERED);
		
		registeResponse.setReturnStatus(failStatus);
		try{
			if(null!=user){
				//验证昵称是否合法
				if(!CheckParamsUtil.isCheckNickname(user.getNickName())){
					registeResponse.setReturnStatus(nickNameIllegal);
					return registeResponse;
				}
				//验证码和是否已经注册
				if(StringUtils.isNotBlank(user.getPhoneNumber())){
					if(!isCheckVerifyCode(user.getPhoneNumber(), user.getSecurityCode())){
						registeResponse.setReturnStatus(verifyCodeFail);
						return registeResponse;
					}
					if(isResgisted(user.getPhoneNumber())){
						registeResponse.setReturnStatus(registedCode);
						return registeResponse;
					}
				}
				com.xhcms.lottery.commons.data.User userVo=change2UserVO(user);
				if(StringUtils.isBlank(userVo.getMobile())){
					userVo.setUsername(idGenerator.nextId()+"");
					userVo.setPassword(idGenerator.nextId()+"");
				} else {
					userVo.setUsername(userVo.getMobile());
				}
				boolean isExistNickName = userManager.isExistNickName(user.getNickName());
				if(!isExistNickName){
					String userId = userManager.regist4Client(userVo);
					if(StringUtils.isNotBlank(userId)){
						userAccountDao.saveWeiboUser(makeWeiboUser(userVo));//保存到redis中
					} else {
						return registeResponse;
					}
					String validId=userValidManager.createValidIdByLoginName(userVo.getUsername());
					//加入特定的群
					String groupid = validParams(user.getChannel());
					if(StringUtils.isNotBlank(groupid)){
						//注册环形账号和将其添加到指定的v群里面
//						makeHX_usernameAndHX_password(user.getNickName(), userId);
						HX_user hx_user = hX_service.registeHx_user(user.getNickName(), userId);
						ObjectNode addUserToGroupNode = EasemobChatGroups.addUserToGroup(groupid, hx_user.getUsername());
						if(!(addUserToGroupNode.get("data") != null && StringUtils.equals(addUserToGroupNode.get("data").get("result").toString(), "true"))){
							logger.info("昵称为{},加入特定的群失败...{}", user.getNickName(), addUserToGroupNode.toString());
							registeResponse.setReturnStatus(failStatus);
							return registeResponse;
						}
						registeResponse.setHxUsername(hx_user.getUsername());
						registeResponse.setHxPassword(hx_user.getPassword());
						noticeGroup(groupid,user.getNickName());
						logger.info("昵称为{},加入特定的群成功...", user.getNickName());
					}
					registeResponse.setValidId(validId);
					registeResponse.setNickname(userVo.getNickName());
					registeResponse.setImageUrl(userVo.getHeadImageURL());
					registeResponse.setReturnStatus(succStatus);
				} else {
					registeResponse.setReturnStatus(nickNameFail);
				}
			}
		}
//		catch(RegisteFailNotNewUserException registeFailNotNewUserException){
//			ReturnStatus notNewUserStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.Registe.FAIL_NOT_NEW_USER);
//			registeResponse.setReturnStatus(notNewUserStatus);
//			logger.info("loginName:{} have been used!",user.getLoginName());
//		}
		catch(RegisteFailPasswordIsBlankException registeFailPasswordOrUserNameIsBlankException){
			ReturnStatus passwordIsBlankStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.Registe.FAIL_USERNAME_AND_PASSWORD_SHOULD_NOT_BLANK);
			registeResponse.setReturnStatus(passwordIsBlankStatus);
			logger.info("password should not blank !");
		}
		catch(RegisteFailNickNameOrPhoneNumberIsBlankException registeFailRealNameOrPhoneNumberIsBlankException){
			ReturnStatus realNameAndPhoneNumberIsBlankStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.Registe.FAIL_REALNAME_AND_PHONENUMBER_SHOULD_NOT_BLANK);
			registeResponse.setReturnStatus(realNameAndPhoneNumberIsBlankStatus);
			logger.info("loginName:{}'s nickname and phoneNumber should not blank !",user.getLoginName());
		}
		catch(Exception e){
			e.printStackTrace();
			registeResponse.setReturnStatus(failStatus);
		}
		return registeResponse;
	}

	private boolean isResgisted(String phoneNumber) {
		return userManager.fingUserByPhoneNumber(phoneNumber);
	}

	private boolean isCheckVerifyCode(String phoneNumber, String code) {
		String verifyCode = registeService.haveVerifyCodeByPhoneNumber(phoneNumber);
		if(StringUtils.equals(code, verifyCode)){
			return true;
		}
		return false;
	}

	private com.xhcms.lottery.commons.data.User change2UserVO(User user) {
		com.xhcms.lottery.commons.data.User userVo=new com.xhcms.lottery.commons.data.User();
		userVo.setUsername(user.getLoginName());
		userVo.setPassword(user.getPassword());
		userVo.setRealname(user.getRealName());
		userVo.setMobile(user.getPhoneNumber());
		userVo.setPid(user.getChannel());
		if(StringUtils.equals(user.getPlatType(), "0")){
			userVo.setSinaWeiboToken(user.getToken());
			userVo.setSinaWeiboUid(user.getUid());
		}else if (StringUtils.equals(user.getPlatType(), "1")) {
			userVo.setWeixinToken(user.getToken());
			userVo.setWeixinUid(user.getUid());
		}else if (StringUtils.equals(user.getPlatType(), "2")) {
			userVo.setQqConnectToken(user.getToken());
			userVo.setQqConnectUid(user.getUid());	
		}
		try {
			if(StringUtils.isNotBlank(user.getImageUrl())){
				File file = LoadUrlImageUtil.getUrlImage(user.getImageUrl());
				userVo.setHeadImageURL(LoadUrlImageUtil.saveHeadImageFile(file));
			}
			userVo.setNickName(user.getNickName());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return userVo;
	}
	
	/**
     * 注册环信账号和密码（账号：昵称的MD5, 密码： 昵称+定长字符串的MD5）
     * @param nickname
     * @param userId 用户的id
     * @return
     */
	private HX_user makeHX_usernameAndHX_password(String nickname, String userId) {
		HX_user hx_user = new HX_user();
		//注册请求
		 	ObjectNode datanode = JsonNodeFactory.instance.objectNode();
		 	datanode.put("username", Text.MD5Encode(userId + Constants.APPKEY));
	        datanode.put("password", Text.MD5Encode(userId + VONames.HX_USE_STRINE));
	        datanode.put("nickname", nickname);
	        ObjectNode createNewIMUserSingleNode = EasemobIMUsers.createNewIMUserSingle(datanode);
	        if (null != createNewIMUserSingleNode && !createNewIMUserSingleNode.has("message")) {
	        	logger.info("注册IM用户[单个]: " + createNewIMUserSingleNode.toString());
	        	if(StringUtils.equals(createNewIMUserSingleNode.get("statusCode").toString() , "200")){
	        		hx_user.setUsername(Text.MD5Encode(userId + Constants.APPKEY));
	        		hx_user.setPassword(Text.MD5Encode(userId + VONames.HX_USE_STRINE));
	        		//成功后保存入库
	        		hX_userService.saveOrUpdateHX_user(hx_user, userId, nickname);
	        	}
	        }
		return hx_user;
	}

	
	private String validParams(String channel) {
		String reslut = null;
		try {
			String decryptStr = DES.decryptDES(channel, GroupSecretkey.secretKey, "utf-8");
			if(StringUtils.isNotBlank(decryptStr)){
				String[] strs = decryptStr.split("[+]");
				logger.info("环信groupId: {} , 大V id : {} , clientVersion : {}" , strs[0], strs[1], strs[2]);
				List<DaVGroup> daVGroups = daVGroupService.findDaVGroupByGroupId(strs[0]);
				if(isValid(daVGroups, strs)){
					reslut = strs[0];
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reslut;
	}
	
	private boolean isValid(List<DaVGroup> daVGroups, String[] strs) {
		boolean result = false;
		for(DaVGroup daVGroup : daVGroups){
			if(daVGroup != null && StringUtils.equals(strs[1], daVGroup.getVid()) && StringUtils.equals(strs[2], daVGroup.getClientVersion())){
				result = true;
			}
		}
		return result;
	}
	
	/**
	 * 通知群里谁加入了
	 * @param groupid
	 * @param nickName
	 */
    private void noticeGroup(String groupid, String nickName) {
    	String jsonStr =  "{ \"type\" : \"6\" , \"nickName\" :\"" + nickName + "\"}";
    	HX_sendMassage hx_sendMassage = new HX_sendMassage();
		if(hx_sendMassage.sendMsg2Group(groupid, jsonStr)){
			logger.info("通知群成功，nickName= {}", nickName);
		} else {
			logger.info("通知群失败，nickName= {}", nickName);
		}
	}
    
    private com.unison.lottery.weibo.data.WeiboUser makeWeiboUser(com.xhcms.lottery.commons.data.User userVo){
    	WeiboUser weiboUser = new WeiboUser();
		BeanUtils.copyProperties(userVo, weiboUser);
    	return weiboUser;
    }
}
