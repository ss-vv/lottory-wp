/**
 * 
 */
package com.xhcms.ucenter.sso.web.action;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.commons.data.User;
import com.xhcms.ucenter.action.BaseAction;
import com.xhcms.ucenter.exception.UCException;
import com.xhcms.ucenter.service.user.IUserService;
import com.xhcms.ucenter.sso.service.UserProfile;
import com.xhcms.ucenter.sso.ticket.ITicketRegistry;
import com.xhcms.ucenter.sso.ticket.impl.GrantingTicket;
import com.xhcms.ucenter.sso.ticket.impl.ServiceTicket;

/**
 * @author bean
 *
 */
public class RetriveUserProfileAction extends BaseAction {
	private static final long serialVersionUID = 1961824834866493521L;
	
	private static final Logger logger = Logger.getLogger(RetriveUserProfileAction.class);
	
	private String ticket;
	
	private String uid;
	
	private String username;
	
	@Autowired
	private ITicketRegistry ticketRegistry;
	
	@Autowired
	private IUserService userService;
	
	private UserProfile userProfile;
	
	@Override
	public String execute() throws Exception {
		try {
			if(StringUtils.isNotBlank(ticket)){
			    loadProfileByTicket();
			}else{
			    loadProfileById();
			}
		} catch (Exception e) {
			logger.warn("retrive user profile exception:" + e.getMessage());
		}
		
		return SUCCESS;
	}
	
	private void loadProfileById(){
	    long id = 0L;
	    if(StringUtils.isNotBlank(uid)){
    	    try{
    	        id = Long.parseLong(uid);
    	    }catch(NumberFormatException e){
    	    }
	    }
	    
	    User user = null;
	    if(id != 0L){
	        user = userService.getUser(id);
	    }
	    
	    if(user == null && StringUtils.isNotBlank(username)){
	        user = userService.getUserByUsername(username);
	    }
	    if(user != null){
    	    userProfile = new UserProfile();
            
            userProfile.setId(user.getId());
            userProfile.setUsername(user.getUsername());
            userProfile.setTruename(user.getRealname());
            userProfile.setLastLoginIp(user.getLastLoginIp());
            userProfile.setRefresh(false);
        }
	}
	
	private void loadProfileByTicket(){
	    ServiceTicket serviceTicket = (ServiceTicket)ticketRegistry.getTicket(ticket, ServiceTicket.class);
        if(serviceTicket == null) {
            //false
            throw new UCException("sso.InvalidServiceTicket", "Invalidate Service Ticket!");
        }
        
        GrantingTicket grantingTicket = 
            (GrantingTicket)ticketRegistry.getTicket(serviceTicket.getGrantingTicketId(), GrantingTicket.class);
        
        if(grantingTicket == null) {
            throw new UCException("sso.InvalidGantingTicket", "Invalidate Granting Ticket!");
        }
        
        userProfile = grantingTicket.getUserProfile();
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

    public void setUid(String uid){
        this.uid = uid;
    }

    public void setUsername(String username){
        this.username = username;
    }
    
	@JSON(name="userProfile")
	public UserProfile getUserProfile() {
		return userProfile;
	}
	
}
