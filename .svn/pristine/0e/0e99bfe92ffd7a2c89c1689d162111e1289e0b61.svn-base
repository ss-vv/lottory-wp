package com.unison.lottery.wap.action;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.mq.MessageSender;
import com.xhcms.lottery.commons.data.Account;
import com.xhcms.lottery.commons.data.User;
import com.xhcms.lottery.commons.event.SMSSendMessage;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.lottery.commons.persist.service.VerifyException;
import com.xhcms.ucenter.action.BaseAction;
import com.xhcms.ucenter.lang.Constant;
import com.xhcms.ucenter.persistent.service.IVerifyService;
/**
 * <p>Title: 绑定手机</p>
 * <p>Description: </p>
 * <p>Copyright：Copyright (c) 2011</p>
 * 
 * @author peng.qi
 * 
 */
public class BindMobileAction extends BaseAction {

	private static final long serialVersionUID = 3391192340016380061L;

	@Autowired
    private AccountService accountService;
	@Autowired
	private IVerifyService verifyMobileService;
 	@Autowired
    private MessageSender messageSender;
	
	private Account account;
    private User user;
    private String phoneNum;
    private String verifyCode;
    private String inputVerifyCode;
    
    private String btnSumit;

    public String getBtnSumit() {
		return btnSumit;
    }


	public void setBtnSumit(String btnSumit) {
		this.btnSumit = btnSumit;
	}


	public String getInputVerifyCode() {
		return inputVerifyCode;
	}


	public void setInputVerifyCode(String inputVerifyCode) {
		this.inputVerifyCode = inputVerifyCode;
	}


	public String getVerifyCode() {
		return verifyCode;
	}


	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}


	public String getPhoneNum() {
    	return phoneNum;
    }
    
    
    public void setPhoneNum(String phoneNum) {
    	this.phoneNum = phoneNum;
    }

    private int actionType;

	public int getActionType() {
		return actionType;
	}


	public void setActionType(int actionType) {
		this.actionType = actionType;
	}


	/**
     * 账户明细
     */
    @Override
    public String execute() {
        user  = (User)request.getSession().getAttribute(Constant.USER_KEY);
        if(user != null){
        	account = accountService.getAccount(user.getId());
        	return SUCCESS;
        }
        else{
        	return LOGIN;
        }
    }
    
    
    public String verifyCode(){
		user = (User) request.getSession().getAttribute(Constant.USER_KEY);
		if (user != null) {
			account = accountService.getAccount(user.getId());
			if (StringUtils.isBlank(phoneNum)) {
				addActionError(getText("user.regist.mustinputmobile"));
				return INPUT;
			}else{
				sendMobileCode(user.getId(),user.getUsername(),phoneNum);
			}
		} else {
			return LOGIN;
		}
		return SUCCESS;
    }
    
    public String bindMobile(){
    	user = (User) request.getSession().getAttribute(Constant.USER_KEY);
    	if (user != null) {
			account = accountService.getAccount(user.getId());
			if(isPost()){
				if (StringUtils.isBlank(inputVerifyCode)) {
					addActionError(getText("user.verify.mobile.mustInputVerifyCode"));
					return INPUT;
				}else{
					if(inputVerifyCode.equalsIgnoreCase(verifyCode)){
						
						try {
							verifyMobileService.verify(user.getId(), verifyCode);
						} catch (VerifyException e) {
							addActionError(getText("error."+e.getErrorCode()));
							return INPUT;
						}
						
						
					}else{
						addActionError(getText("error.10002"));
						return INPUT;
					}
				}
			}else{
				return INPUT;
			}
			
		} else {
			return LOGIN;
		}
    	return SUCCESS;
    }
    
    public String unbindMobile(){
    	
    	if(isPost()){
        	user = (User) request.getSession().getAttribute(Constant.USER_KEY);
        	if (user != null) {
        		account = accountService.getAccount(user.getId());
        		
        		//如果点击的是“获取验证码”
        		if(btnSumit.equals("获取验证码")){
        			sendMobileCode(user.getId(),user.getUsername(),user.getMobile());
        			return INPUT;
        		}else{
        			if(StringUtils.isBlank(inputVerifyCode)){
            			addActionError(getText("user.verify.mobile.mustInputVerifyCode"));
            			return INPUT;
            		}
        			verifyMobileService.unbindMobile(user.getId(),inputVerifyCode);
        		}
    			
    			
    		} else {
    			return LOGIN;
    		}
        	return SUCCESS;
    	}else{
    		return INPUT;
    	}
    	

    }

    //发送手机验证码
	private void sendMobileCode(long uid, String username, String mobile){
		verifyCode = verifyMobileService.generateCode(uid, mobile);
		SMSSendMessage sm = new SMSSendMessage();
		sm.setMobile(mobile);
		sm.setContent(verifyCode);
//		sm.setExt("00000");
//		sm.setMsgId("12345");
		messageSender.send(sm);
	}
    
    public Account getAccount() {
        return account;
    }
    
    public void setAccount(Account account) {
		this.account = account;
	}
    
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
