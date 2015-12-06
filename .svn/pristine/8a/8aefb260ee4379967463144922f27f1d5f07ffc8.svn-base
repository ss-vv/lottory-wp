package com.xhcms.ucenter.web.action.my;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.commons.data.User;
import com.xhcms.ucenter.action.BaseAction;
import com.xhcms.ucenter.service.user.IUserManager;

/**
 * 
 * <p>Title: 用户基本信息</p>
 * <p>Description: </p>
 * <p>Copyright：Copyright (c) 2011</p>
 * <p>Company：XingHui Spirit (Beijing) Technical Co.,Ltd.</p>
 * 
 * @author jiajiancheng
 * @version 1.0.0
 */
public class InfoAction extends BaseAction {
    private static final long serialVersionUID = -8095609072314103979L;

    private int gender;

    private String province;

    private String city;

    private User user;

    @Autowired
    private IUserManager userManager;

    // 修改基本信息
    public String execute() {
        user = getSelf();
        if (StringUtils.isEmpty(province) || StringUtils.isEmpty(city)) {
            return INPUT;
        }
        modify();
        addActionMessage(getText("user.info.edit.Success"));
        return SUCCESS;
    }

    private void modify() {
        user.setGender(gender);
        user.setProvince(province);
        user.setCity(city);
        userManager.updateInfo(user);

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setProvince(String province) {
        this.province = province;
    }

}
