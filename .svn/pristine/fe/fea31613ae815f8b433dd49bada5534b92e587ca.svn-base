package com.unison.lottery.api.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.api.login.service.ILoginService;
import com.unison.lottery.api.model.ClientVersion;
import com.unison.lottery.api.model.User;
import com.xhcms.lottery.commons.persist.service.UserService;

public class AllowReturnResultServiceImpl implements AllowReturnResultService {

	@Autowired
	private ILoginService loginService;
	@Autowired
	private UserService userService;

	@Override
	@Transactional(readOnly=true)
	public boolean allow(User user) {
		boolean result=false;
		if(null!=user){
			User userAfterLogin=loginService.tryGetAsAuthenticate(user);
			if(null!=userAfterLogin){
				com.xhcms.lottery.commons.data.User userWithInfo=userService.getUser(Long.parseLong(userAfterLogin.getId()));
				if(null!=userWithInfo&&userWithInfo.getAllowSeeImportantContentInClient()==1){//允许查看客户端的重要内容
					user.setId(userAfterLogin.getId());
					result=true;
				}
			}
			
		}
		return result;
	}

	@Override
	public boolean shouldDecideAllowReturnResult(String clientVersion) {
		boolean result=false;
		ClientVersion clientVersionObj = ClientVersionUtil.transferToClientVersionObj(clientVersion);
		if(null!=clientVersionObj){
			if(clientVersionObj.isAndroid()){
				if(clientVersionObj.getVersionNumber()>=105){//android大于1.0.5版本才会控制用户访问
					result=true;
				}
			}
			else if(clientVersionObj.isIOS()){//ios都可以控制用户访问
				result=true;
			}
		}
		return result;
	}

}
