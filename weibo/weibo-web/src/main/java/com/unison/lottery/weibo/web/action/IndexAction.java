package com.unison.lottery.weibo.web.action;

import org.apache.struts2.ServletActionContext;

import com.unison.lottery.weibo.utils.HttpRequestDeviceUtils;

/**
 * @Description:控制不同的首页 
 * @author 江浩翔   
 * @date 2013-11-8 上午10:53:07 
 * @version V1.0
 */
public class IndexAction extends BaseAction {
	private static final long serialVersionUID = 1L;

	public String execute(){
		boolean isMobile = HttpRequestDeviceUtils.isMobileDevice(ServletActionContext.getRequest());
		if(isMobile){
			//return "mobile_index";
			return "notMobile_index";
		} else {
			return "notMobile_index";
		}
	} 
}
