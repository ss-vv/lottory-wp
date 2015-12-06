package com.xhcms.ucenter.web.action.verify;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.lang.Data;
import com.xhcms.exception.XHRuntimeException;
import com.xhcms.ucenter.action.BaseAction;
import com.xhcms.ucenter.service.user.IUserService;
import com.xhcms.ucenter.service.verify.IVerifyService;

/**
 * 微博手机号码修改
 * @author haoxiang.jiang@unison.net.cn
 * @time 2014-5-6 下午2:12:57
 */
public class WeiboMobileChangeAction extends BaseAction {

    private static final long serialVersionUID = -8133722634429412469L;

    @Autowired
    private IUserService userService;
    
    @Autowired
    private IVerifyService verifyMobileService;

    private String code;

    private String mobile;

    private String newMobile;
    
    private Data data;
    
    public String execute() {
        mobile = getSelf().getMobile();
        if (StringUtils.isEmpty(code) || StringUtils.isBlank(newMobile)) {
            data = Data.failure("验证码或新手机号码为空");
        	return SUCCESS;
        }
        try{
	        verifyMobileService.verify(getSelf().getId(), code);
	        mobile = userService.getUser(getSelf().getId()).getMobile();
	        data = Data.success(getText("user.verify.mobile.edit.success"));
	        return SUCCESS;
        } catch (XHRuntimeException e) {
			data = Data.failure("验证失败,请重试");
	    	return SUCCESS;
		} catch (Exception e) {
			data = Data.failure("验证失败,请重试");
	    	return SUCCESS;
		}
    }
    public String getMobile() {
        return mobile;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setNewMobile(String newMobile) {
        this.newMobile = newMobile;
    }

    public String getNewMobile() {
        return newMobile;
    }

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}
}
