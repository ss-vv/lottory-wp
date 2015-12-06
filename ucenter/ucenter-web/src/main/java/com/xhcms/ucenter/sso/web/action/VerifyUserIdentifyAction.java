/**
 * 
 */
package com.xhcms.ucenter.sso.web.action;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.lang.Data;
import com.xhcms.lottery.commons.data.User;
import com.xhcms.ucenter.action.BaseAction;
import com.xhcms.ucenter.lang.EnumLoginType;
import com.xhcms.ucenter.service.user.IUserManager;
import com.xhcms.ucenter.service.user.IUserService;
import com.xhcms.ucenter.sso.web.util.PassKeyUtils;

/**
 * @author bean
 * 输出的XML结果如下
 *<?xml version="1.0" encoding="utf-8"?>
 *<datas>
 *       <name><![CDATA[用户登录验证接口]]></name>
 *       <data>
 *               <result><![CDATA[false|true]]></result>
 *               <uid><![CDATA[0]]></uid>
 *               <username><![CDATA[]]></username>
 *               <nickname><![CDATA[]]></nickname>
 *       </data>
 *</datas>
 */
public class VerifyUserIdentifyAction extends BaseAction {
	private static final long serialVersionUID = 4711166444083674840L;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IUserManager userManager;
	
	private String username;
	private String password;
	private String passkey;
	
	private Data data = Data.success("");
	private User user = new User();
	
	@Override
	public String execute() throws Exception {
		if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			data = Data.failure("验证失败!");
		}
		
		if(data.isSuccess() && !PassKeyUtils.generatePasskey(0L, username).equals(passkey)) {
			log.info("key=" + PassKeyUtils.generatePasskey(0L, username) + ",passkey=" + passkey);
			data = Data.failure("非法请求!");
		}
		
		if(data.isSuccess()) {
			boolean loginStatus = userManager.login(EnumLoginType.USERNAME.getValue(), username , password);
			if(loginStatus == true) {
				user = userService.getUserByUsername(username);
				data = Data.success("验证成功!");
			} else {
				data = Data.failure("登录失败!");
			}
		}
		
		outputVerifyResult();
		
		return NONE;
	}
	
	private void outputVerifyResult() {
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n");
		sb.append("<datas>");
		sb.append("<name><![CDATA[用户登录验证接口]]></name>").append("\r\n");
		sb.append("<data>");
		sb.append("<result><![CDATA[" + data.isSuccess() + "]]></result>").append("\r\n");
		sb.append("<uid><![CDATA[" + user.getId() + "]]></uid>").append("\r\n");
		sb.append("<username><![CDATA[]]>" + (user.getUsername()!=null?user.getUsername():"") + "</username>").append("\r\n");
		sb.append("</data>").append("\r\n");
		sb.append("<desc><![CDATA[" + data.getData() + "]]></desc>").append("\r\n");
		sb.append("</datas>");
		

		try {
			response.setHeader("Content-Type", "application/xml;charset=utf-8");
			response.setHeader("Content-Length", sb.toString().getBytes("UTF-8").length + "");			
			response.getWriter().write(sb.toString());
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public String getPasskey() {
		return passkey;
	}

	public void setPasskey(String passkey) {
		this.passkey = passkey;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
