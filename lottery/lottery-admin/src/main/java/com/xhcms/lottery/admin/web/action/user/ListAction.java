package com.xhcms.lottery.admin.web.action.user;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.admin.data.Admin;
import com.xhcms.lottery.admin.lang.AdminConstant;
import com.xhcms.lottery.admin.web.action.BaseListAction;
import com.xhcms.lottery.commons.persist.service.UserService;

public class ListAction extends BaseListAction {

    private static final long serialVersionUID = 8668189806299243579L;
    
    @Autowired
    private UserService userService;
    
    private int state = -1;
    private String username;
    private String nickName;
    private String ip;
    private String idNumber;
    private boolean markAdmin;
    private String mobile;
    
    @Override
    public String execute(){
        paging = wrapPaging();
        userService.listUser(paging, from, to, state, username, ip, idNumber, mobile, nickName);
        markRootToSession();
        return SUCCESS;
    }

    public void markRootToSession() {
    	HttpSession session = request.getSession();
		Admin user = (Admin)session.getAttribute(AdminConstant.USER);
		if(null != user && "root".equals(user.getUsername())) {
			markAdmin = true;
		}
    }
    
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

	public boolean isMarkAdmin() {
		return markAdmin;
	}

	public void setMarkAdmin(boolean markAdmin) {
		this.markAdmin = markAdmin;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
}
