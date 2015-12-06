package com.xhcms.lottery.admin.shiro.service;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShiroService {

	private static final Logger log = LoggerFactory.getLogger(ShiroService.class);
	
	private static final Subject currentUser;
	
	private ShiroService() {
	}
	
	static {
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
		SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		currentUser = SecurityUtils.getSubject();
	}

	public static Subject getSubject() {
		return currentUser;
	}
	
	public static void login(String username, String password) {
		if (!currentUser.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            token.setRememberMe(true);
            try {
                currentUser.login(token);
            } catch (Throwable tx) {
                log.error("user={}, login failure.", username);
            }
        }
	}
	
	public static Subject subject() {
		Subject subject = SecurityUtils.getSubject();
		return subject;
	}
	
	public static boolean isAuthorized() {
		Subject subject = SecurityUtils.getSubject();
		return subject.isAuthenticated();
	}
	
	public static Subject tokenLogin(String username, String password) {
		Subject subject = SecurityUtils.getSubject();
        try {
        	UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        	token.setRememberMe(true);
            subject.login(token);
        } catch (UnknownAccountException e) {
            log.info("用户={},登录失败，用户名/密码错误", username);
        } catch (IncorrectCredentialsException e) {
        	log.info("用户={},登录失败，用户名/密码错误", username);
        } catch (AuthenticationException e) {
        	log.info("用户={},登录失败！", username);
        }
        return subject;
	}
}