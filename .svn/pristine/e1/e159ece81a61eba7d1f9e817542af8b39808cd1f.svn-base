package com.xhcms.ucenter.web.action.my;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.commons.data.User;
import com.xhcms.ucenter.action.BaseAction;
import com.xhcms.ucenter.service.user.IUserManager;

/**
 * <p>Title: 修改基本信息</p>
 * <p>Description: </p>
 * <p>Copyright：Copyright (c) 2011</p>
 * <p>Company：XingHui Spirit (Beijing) Technical Co.,Ltd.</p>
 * 
 * @author jiajiancheng
 * @version 1.0.0
 */
public class ModifyInfoAction extends BaseAction {

    private static final long serialVersionUID = 2694964526490200203L;

    @Autowired
    private IUserManager userManager;

    private int gender;

    private String province;

    private String city;

    private User user;
    
    @Override
    public String execute() throws Exception {
        user = getSelf();
        if (StringUtils.isEmpty(province) || StringUtils.isEmpty(city)) {
            addFieldError("city", getText("user.info.MustInputCity"));
            return INPUT;
        }

        user.setGender(gender);
        user.setProvince(province);
        user.setCity(city);
        userManager.updateInfo(user);

        return super.execute();
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public User getUser() {
        return user;
    }

}
