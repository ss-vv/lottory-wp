package com.xhcms.ucenter.web.action.reg;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.commons.data.User;
import com.xhcms.ucenter.action.BaseAction;
import com.xhcms.ucenter.lang.Constant;
import com.xhcms.ucenter.lang.EnumLoginType;
import com.xhcms.ucenter.service.user.IUserManager;
import com.xhcms.ucenter.service.user.IUserService;
import com.xhcms.ucenter.sso.principal.RemeberMeUsernamePasswordCredentials;
import com.xhcms.ucenter.sso.service.IAuthenticationManager;
import com.xhcms.ucenter.sso.service.UserProfile;
import com.xhcms.ucenter.sso.ticket.impl.GrantingTicket;
import com.xhcms.ucenter.sso.web.util.GrantingTicketCookieGenerator;

/**
 * <p>Title: 注册成功后，自动登录</p>
 * <p>Description: </p>
 * <p>Copyright：Copyright (c) 2011</p>
 * <p>Company：XingHui Spirit (Beijing) Technical Co.,Ltd.</p>
 * 
 * @author jiajiancheng
 * @version 1.0.0
 */
public class RegistStep2Action extends BaseAction {

    private static final long serialVersionUID = -5078758489596691900L;

    @Autowired
    private IAuthenticationManager authenticationManager;

    @Autowired
    private GrantingTicketCookieGenerator cookieGenerator;

    @Autowired
    private IUserService userServiceCache;

    @Autowired
    private IUserManager userManager;

    private String username;

    private String password;

    @Override
    public String execute() throws Exception {
        RemeberMeUsernamePasswordCredentials credentials = new RemeberMeUsernamePasswordCredentials(
                EnumLoginType.USERNAME.getValue(), username, password);

        UserProfile profile = authenticationManager.authenticate(credentials);
        if (profile != null) {
            request.getSession().setAttribute(Constant.USER_LASTLOGINTIME, profile.getLastLoginTime());
            // 更新用户登录信息
            userManager.updateUserLoginStatus(profile.getId(), request.getHeader("X-Real-IP"), new Date());

            // 记录cookie
            GrantingTicket grantingTicket = authenticationManager.createGrantingTicket(profile);
            cookieGenerator.addCookie(request, response, grantingTicket.getId());

            User user = userServiceCache.getUser(profile.getId());
            request.getSession().setAttribute(Constant.USER_KEY, user);

        }
        return SUCCESS;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
