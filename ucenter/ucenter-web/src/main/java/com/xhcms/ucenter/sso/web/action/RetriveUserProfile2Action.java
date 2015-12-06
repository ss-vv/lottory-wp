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
import com.xhcms.ucenter.service.user.IUserService;
import com.xhcms.ucenter.sso.web.util.PassKeyUtils;

/**
 * @author bean
 * <?xml version="1.0" encoding="utf-8"?>
 * <datas>
 *		<name><![CDATA[取得用户信息接口]]></name>
 *		<data>
 *			<result><![CDATA[false]]></result>
 *			<uid><![CDATA[ 0 ]]></uid>
 *			<username><![CDATA[  ]]></username>
 *			<nickname><![CDATA[  ]]></nickname>
 *			<gender><![CDATA[ ]]></gender>
 *			<email><![CDATA[ ]]></email>
 *			<msn><![CDATA[ ]]></msn>
 *			<avatar><![CDATA[ ]]></avatar>
 *		</data>
 * </datas>
 */
public class RetriveUserProfile2Action extends BaseAction {
	private static final long serialVersionUID = 2900327806757393838L;
	
	@Autowired
	IUserService userService;
	
	private Long uid;
	private String username;
	private String passkey;
	
	private Data data = Data.success(""); 
	private User user = new User();
	
	@Override
	public String execute() throws Exception {
		if(uid == null && username == null) {
			data = Data.failure("参数错误");
		}
		
		if(data.isSuccess() &&
				!PassKeyUtils.generatePasskey(uid, username).equals(passkey)) {
			data = Data.failure("非法访问");
		}
		
		if(data.isSuccess()) {
			if(uid != null) {
				user = userService.getUser(uid);
			} else if(StringUtils.isNotBlank(username)){
				user = userService.getUserByUsername(username);
			}
			
			if(user != null) {
				data = Data.success("操作成功!");
			} else {
				data = Data.failure("用户不存在!");
			}
		}
		
		outputVerifyResult();
		
		return NONE;
	}

	private void outputVerifyResult() {
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n");
		sb.append("<datas>");
		sb.append("<name><![CDATA[取得用户信息接口]]></name>").append("\r\n");
		sb.append("<data>");
		sb.append("<result><![CDATA[" + data.isSuccess() + "]]></result>").append("\r\n");
		sb.append("<uid><![CDATA[" + user.getId() + "]]></uid>").append("\r\n");
		sb.append("<username><![CDATA[" + (user.getUsername()!=null?user.getUsername():"") + "]]></username>").append("\r\n");
		sb.append("<gender><![CDATA[" + user.getGender() + "]]></gender>").append("\r\n");
		sb.append("<msn><![CDATA[]]></msn>").append("\r\n");
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
	
	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasskey() {
		return passkey;
	}

	public void setPasskey(String passkey) {
		this.passkey = passkey;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
