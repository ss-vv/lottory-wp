package com.unison.lottery.wap.action;

import java.util.Date;

import javax.servlet.http.Cookie;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.commons.data.User;
import com.xhcms.ucenter.action.BaseAction;
import com.xhcms.ucenter.lang.Constant;
import com.xhcms.ucenter.persistent.service.IUserManager;
import com.xhcms.ucenter.persistent.service.IUserService;
import com.xhcms.ucenter.util.Text;

public class RegisterAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IUserManager userManager;
	
	private String parnterID;
	private String username;
	private String password;
	private String confirmPassword;
	private String realname;
	private String phoneNum;
	private String referer;
	

	@Override
	public String execute()throws Exception {
		
	if (isPost()) {
		if (Text.getLength(username) < 4 || Text.getLength(username) > 16) {
			addFieldError("username", getText("user.regist.UsernameLength"));
		}
		if (userService.getUserByUsername(username) != null) {
		    addFieldError("username", getText("user.regist.isExistUsername"));
		}
		if(StringUtils.isEmpty(password)){
			addFieldError("password",getText("user.regist.MustInputPassword"));
		}
		if(StringUtils.isEmpty(confirmPassword)){
			addFieldError("confirmPassword",getText("user.regist.MustInputConfirmPassword"));
		}
		if (!StringUtils.equals(password, confirmPassword)) {
			addFieldError("confirmPassword", getText("user.regist.MustEqualBetweenTwoPassword"));
		}
		if (StringUtils.isEmpty(phoneNum)){
			addFieldError("phoneNum", getText("user.regist.MustInputMobile"));
		}
		else{
			if (userService.mobileValidAndBinded(phoneNum)) {
				addFieldError("phoneNum", getText("user.regist.isBindMobile"));
			}
		}
		if(StringUtils.isEmpty(realname)){
			addFieldError("realname", getText("user.regist.MustInputRealname"));
		}else{
			if(Text.getLength(realname) < 2){
				addFieldError("realname", getText("user.regist.RealnameTooShort"));
			}
			if(Text.getLength(realname) > 32){
				addFieldError("realname", getText("user.regist.RealnameTooLong"));
			}
		}
		
		if (hasFieldErrors()) {
			return INPUT;
		}
		
//		try {
			User userVO = new User();
			userVO.setUsername(username);
			userVO.setPassword(password);
			userVO.setRealname(realname);
			userVO.setMobile(phoneNum);
			userVO.setIp(request.getHeader("X-Real-IP"));
			userVO.setCreatedTime(new Date());
			userVO.setLastLoginIp(request.getHeader("X-Real-IP"));
			userVO.setLastLoginTime(new Date());
			userVO.setLocked_time(new Date());
			userVO.setIsLocked(0);
			userVO.setPid(Constant.PID);
			if(referer!=null && referer.length()>32){
				referer = referer.substring(0, 32);
			}
			userVO.setReferer(referer);
			
			Cookie[] cookies = request.getCookies();
			if(cookies !=null){
				for (Cookie cookie : cookies) {
					if(cookie.getName().equals(IndexAction.CHANNEL_COOKIE_NAME) && cookie.getValue()!=null){
						userVO.setPid(cookie.getValue());
					}
				}
			}
			
			userManager.regist(userVO);
			
//			referer = (String) request.getSession().getAttribute("referer");
			
//			request.getSession().removeAttribute("referer");
			request.getSession().setAttribute(Constant.USER_KEY, userVO);
			request.getSession().setAttribute(Constant.USER_LASTLOGINTIME,userVO.getLastLoginTime());
			request.getSession().setAttribute("isNewUser",true);
			
//		} catch(Exception exp) {
//			addActionError("Regist Error:" + exp.getMessage());
//		}
		
		
		return SUCCESS;

    } else {

        request.getSession().setAttribute("referer", StringUtils.isNotBlank(referer) ? referer : request.getHeader("referer"));
        
    }

	return INPUT;
	}
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}
	public String getParnterID() {
		return parnterID;
	}
	public void setParnterID(String parnterID) {
		this.parnterID = parnterID;
	}
}
