package com.xhcms.ucenter.web.action.reg;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.xhcms.commons.lang.Data;
import com.xhcms.commons.mq.MessageSender;
import com.xhcms.exception.XHRuntimeException;
import com.xhcms.lottery.commons.data.User;
import com.xhcms.lottery.commons.event.SMSSendMessage;
import com.xhcms.ucenter.action.BaseAction;
import com.xhcms.ucenter.lang.AppCode;
import com.xhcms.ucenter.service.mail.IEmailService;
import com.xhcms.ucenter.service.user.IUserManager;
import com.xhcms.ucenter.service.user.IUserService;
import com.xhcms.ucenter.service.verify.IVerifyService;
import com.xhcms.ucenter.util.IDCardUtil;
import com.xhcms.ucenter.util.Text;

/**
 * @author jiajiancheng <br/>
 *         注册_填写基本信息
 */
public class RegistStep1Action extends BaseAction {
	private static final long serialVersionUID = -9103729044111552662L;
	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(RegistStep1Action.class);
	
    @Autowired
    private IVerifyService bindEmailService;
    @Autowired
    private IEmailService mailService;
    @Autowired
    private MessageSender messageSender;
	@Autowired
	private IUserService userService;
	@Autowired
	private IUserManager userManager;
    @Autowired
    private IVerifyService verifyMobileService;

	private String username;
	private String password;
	private String confirmPassword;
	
	private String realname;
	private String idnumber;
	
	private String email;
	private String mobile;

	private String validCode;

	private String referer;
	
	private String pid;

	@Override
	public String execute() throws Exception {
		if (isPost()) {
			if (Text.getLength(username) < 4 || Text.getLength(username) > 16) {
				addFieldError("username", getText("user.regist.UsernameLength"));
			}
			if (userService.getUserByUsername(username) != null) {
			    addFieldError("username", getText("user.regist.isExistUsername"));
			}
			if (!StringUtils.equals(password, confirmPassword)) {
				addFieldError("confirmPassword", getText("user.regist.MustEqualBetweenTwoPassword"));
			}
			if(Text.getLength(realname) < 2 || Text.getLength(realname) > 32){
			    addFieldError("realname", getText("user.regist.RealnameLength"));
			}
			Data idNumberData = IDCardUtil.IDCardValidate(idnumber);
            if (!idNumberData.isSuccess()) {
                addFieldError("idnumber", (String) idNumberData.getData());
            }
			if (userService.idNumberValid(idnumber)) {
                addFieldError("idnumber", getText("user.regist.isExistIdNumber"));
            }
			if (userService.emailValidAndBinded(email)){
			    addFieldError("email", getText("user.regist.isBindEmail"));
			}
			if (userService.mobileValidAndBinded(mobile)) {
				addFieldError("mobile", getText("user.regist.isBindMobile"));
			}
			if (!validateWaterCode()) {
			    addFieldError("validCode", getText("user.regist.IsNotValidCode"));
			}
			if (hasFieldErrors()) {
				return INPUT;
			}
			
			try {
				User userVO = new User();
				userVO.setUsername(username);
				userVO.setPassword(password);
				userVO.setRealname(realname);
				userVO.setIdNumber(idnumber);
				userVO.setMobile(mobile);
				userVO.setEmail(email);
				userVO.setIp(request.getHeader("X-Real-IP"));
				userVO.setCreatedTime(new Date());
				userVO.setLastLoginIp(request.getHeader("X-Real-IP"));
				userVO.setLastLoginTime(new Date());
				userVO.setLocked_time(new Date());
				userVO.setIsLocked(0);
				userVO.setPid(pid);
				userVO.setReferer(referer);
				userManager.regist(userVO);
				
				referer = (String) request.getSession().getAttribute("referer");
				
				request.getSession().removeAttribute("referer");
				
				sendEmail(userVO.getId(), username, email);
				sendMobileCode(userVO.getId(), userVO.getUsername(), mobile);
			} catch(Exception exp) {
				logger.error(exp.getMessage(), exp);
				addActionError("Regist Error:" + exp.getMessage());
			}
			
			
			return SUCCESS;

        } else {

            request.getSession().setAttribute("referer", StringUtils.isNotBlank(referer) ? referer : request.getHeader("referer"));
            
        }

		return INPUT;
	}

	/**
	 * 验证水印码
	 * 
	 * @return
	 */
	private boolean validateWaterCode() {
		String waterCodeInSession = (String) request.getSession(true).getAttribute("random");

		if (!StringUtils.isEmpty(validCode) && validCode.equals(waterCodeInSession)) {
			return true;
		}

		return false;
	}

	//发送验证邮件
	private void sendEmail(long uid, String username, String email) {
        // 生成验证码
        String code = bindEmailService.generateCode(uid, email);

        // 发送邮件
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("uid", uid);
        model.put("code", code);
        model.put("username", username);
        model.put("currentTime", com.xhcms.commons.util.Text.formatTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
        mailService.setMailTemplateFile("bindemail.vm");
        try {
            mailService.sendEmail(model, email, "验证邮箱");
        } catch (Exception e) {
            e.printStackTrace();
            throw new XHRuntimeException(AppCode.VERIFY_EMAIL_SEND_FAILURE);
        }
    }
	
	//发送手机验证码
	private void sendMobileCode(long uid, String username, String mobile){
		String code = verifyMobileService.generateCode(uid, mobile);
		SMSSendMessage sm = new SMSSendMessage();
		sm.setMobile(mobile);
		sm.setContent(code);
//		sm.setExt("00000");
//		sm.setMsgId("12345");
		messageSender.send(sm);
	}
	
	@RequiredStringValidator(key = "user.regist.MustInputUsername")
	@RegexFieldValidator(regex = "^[a-zA-Z0-9\u4e00-\u9fa5]+$", key = "user.regist.RegexErrorUsername")
	public void setUsername(String username) {
		this.username = username;
	}

	@RequiredStringValidator(key = "user.regist.MustInputPassword")
	@StringLengthFieldValidator(key = "user.regist.PasswordLength", minLength = "6", maxLength = "15")
	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	@RequiredStringValidator(key = "user.regist.MustInputConfirmPassword")
	@StringLengthFieldValidator(key = "user.regist.PasswordLength", minLength = "6", maxLength = "15")
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	@RequiredStringValidator(key = "user.regist.MustInputValidCode")
	public void setValidCode(String validCode) {
		this.validCode = validCode;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getValidCode() {
		return validCode;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

    public String getRealname() {
        return realname;
    }

    @RequiredStringValidator(key = "user.regist.MustInputRealname")
    @RegexFieldValidator(regex = "^[\u4e00-\u9fa5]+$", key = "user.regist.RegexErrorRealname")
    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getIdnumber() {
        return idnumber;
    }
    
    @RequiredStringValidator(key = "user.regist.MustInputIdnumber")
    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }
    
    public String getEmail() {
        return email;
    }

    @RequiredStringValidator(key="user.regist.MustInputEmail")
    @EmailValidator(key="user.regist.EmailFormat")
    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    @RequiredStringValidator(key="user.regist.MustInputMobile")
    @RegexFieldValidator(regex = "^1\\d{10}$", key = "user.regist.RegexErrorMobile")
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getReferer() {
        return referer;
    }

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

}
