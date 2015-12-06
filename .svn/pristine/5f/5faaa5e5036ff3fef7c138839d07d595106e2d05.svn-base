
package com.xhcms.ucenter.web.action;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.lang.Data;
import com.xhcms.lottery.commons.data.User;
import com.xhcms.ucenter.action.BaseAction;
import com.xhcms.ucenter.lang.Constant;
import com.xhcms.ucenter.lang.EnumLoginType;
import com.xhcms.ucenter.service.user.IUserManager;
import com.xhcms.ucenter.service.user.IUserService;
import com.xhcms.ucenter.sso.discuz.DiscuzSynchronizer;
import com.xhcms.ucenter.sso.principal.RemeberMeUsernamePasswordCredentials;
import com.xhcms.ucenter.sso.service.IAuthenticationManager;
import com.xhcms.ucenter.sso.service.UserProfile;
import com.xhcms.ucenter.sso.ticket.IService;
import com.xhcms.ucenter.sso.ticket.impl.GrantingTicket;
import com.xhcms.ucenter.sso.ticket.impl.ServiceTicket;
import com.xhcms.ucenter.sso.web.util.GrantingTicketCookieGenerator;

/**
 * 
 * <p>Title: Ajax登录</p>
 * <p>Description: </p>
 * <p>Copyright：Copyright (c) 2010</p>
 * <p>Company：XingHui Spirit (Beijing) Technical Co.,Ltd.</p>
 * 
 * @author wanged
 * @version 0.0.1
 */
public class AjaxLoginAction extends BaseAction {
	
    private static final long serialVersionUID = -3043272678360233619L;
    
    private static final String SSO = "sso";
    
    private static final String FRAME = "frame";
    
    private static final String CLIENT = "client";
    
    @Autowired
    private IUserManager userManager;
    
    @Autowired
	private GrantingTicketCookieGenerator cookieGenerator;
	
    @Autowired
	private IAuthenticationManager authenticationManager;
	
	@Autowired
	private DiscuzSynchronizer discuzSynchronizer;
	
	@Autowired
	private IUserService userServiceCache;
	
	private String username;
	
	private String password;
	
	private String rememberMe;
	
	private Integer time;
	
	private Integer loginType;
	
	private String way;        // "sso", "frame", "client"
	
	private String ticket;
	
	private String reason; 
	
	private Data data;
	
	@Override
	public String execute() throws Exception {
		if(!isPost()) {
		    return LOGIN;
		}
		
		// 默认类型为用户名登录
        if (null == loginType) {
            loginType = EnumLoginType.USERNAME.getValue();
        }
        
        RemeberMeUsernamePasswordCredentials credentials = new RemeberMeUsernamePasswordCredentials(loginType, username, password);
        
        if(StringUtils.isBlank(rememberMe)){
            credentials.setRememberMe(false);
            credentials.setMaxAge(-1);
        }else{
            credentials.setRememberMe(true);
            // time值为正数时取设置过期时间为该值，否则设置为三个月过期
            credentials.setMaxAge((time != null && time > 0) ? time : 7889231);
        }
        
        UserProfile profile = authenticationManager.authenticate(credentials);
        
        //登录成功后台的处理流程
        IService service = (IService)request.getSession().getAttribute(Constant.SERVICE_KEY_IN_SESSION);

		// 登录成功
		if(profile != null) {
            //更新用户登录信息
            userManager.updateUserLoginStatus(profile.getId(), request.getRemoteAddr(), new Date());
            
            //记录cookie
            GrantingTicket grantingTicket = authenticationManager.createGrantingTicket(profile);
            cookieGenerator.addCookie(request, response, grantingTicket.getId());
            
            User user = userServiceCache.getUser(profile.getId());
            discuzSynchronizer.syncLoginStatus(request, response, user);

        	
            if(service != null) {
                ServiceTicket serviceTicket = authenticationManager.createServiceTicket(grantingTicket, service);
                grantingTicket.addSerice(service);
                
                response.sendRedirect(constructServiceUrl(service, serviceTicket.getId(), null));
                
                return NONE;
            }else if(CLIENT.equals(way)){
                data = Data.success(profile);
                return CLIENT;
            }
			return SUCCESS;
		}
		
		// 登录失败
		String message = getText("user.login.failure");
		if(SSO.equals(way)){
		    response.sendRedirect(constructServiceUrl(service, null, message));
            return NONE;
		}
		
		if(FRAME.equals(way)){
		    addActionError(message);
		}
		
		if(CLIENT.equals(way)){
		    data = Data.failure(message);
		    return CLIENT;
		}
		
	    return LOGIN;
	}
	
	private String constructServiceUrl(IService service, String ticket, String reason) {
        StringBuilder sb = new StringBuilder();
        sb.append(service.getId());
        String backUrl = service.getId();
        if(backUrl.indexOf('?') > 0) {
            sb.append('&');
        } else {
            sb.append('?');
        }
        
        if(ticket != null){
            sb.append("ticket=").append(ticket);
        }
        if(reason != null){
        	try {
				reason = URLEncoder.encode(reason, HTTP.UTF_8);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
            sb.append("reason=").append(reason);
        }
        return sb.toString();
    }
	
	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRememberMe(String rememberMe){
        this.rememberMe = rememberMe;
    }

    public void setTime(Integer time) {
		this.time = time;
	}

	public void setLoginType(Integer loginType) {
		this.loginType = loginType;
	}

	public Integer getLoginType() {
		return loginType;
	}

	public String getUsername() {
		return username;
	}

    public String getTicket(){
        return ticket;
    }

    public String getReason(){
        return reason;
    }

    public void setWay(String way){
        if(way != null){
            this.way = way.toLowerCase();
        }
    }

    public Data getData() {
        return data;
    }
    
}
