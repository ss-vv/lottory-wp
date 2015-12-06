package com.unison.lottery.weibo.web.action.pc;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.data.Result;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.lang.Constant;
import com.unison.lottery.weibo.uc.service.QQConnectService;
import com.unison.lottery.weibo.uc.service.SinaWeiboService;
import com.unison.lottery.weibo.web.action.BaseAction;

public class ShareAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	/** 发布到新浪微博标致 */
	private String sinaWeiboCheck;
	/** 发布到QQ互联标致 */
	private String qqConnectCheck;
	/** 分享的内容 */
	private String shareContent;
	private Result data = new Result();
	@Autowired
	private SinaWeiboService sinaWeiboService;
	@Autowired
	private QQConnectService qqConnectService;
	public String execute() {
		if (false == loadWeiboUser()) {
			data = new Result();
			data.setDesc("未登录");
			data.setSuccess(false);
			return SUCCESS;
		}
		if (StringUtils.isBlank(shareContent)) {
			data = new Result();
			data.setDesc("分享内容为空");
			data.setSuccess(false);
			return SUCCESS;
		}
		if (StringUtils.isBlank(qqConnectCheck)
				&& StringUtils.isBlank(sinaWeiboCheck)) {
			data = new Result();
			data.setDesc("请选择你想分享的网站");
			data.setSuccess(false);
			return SUCCESS;
		}
		if (StringUtils.isNotBlank(sinaWeiboCheck)) {
			data = sinaWeiboService.shareWeiboContent(user, shareContent);
		}
		if (StringUtils.isNotBlank(qqConnectCheck)) {
			data = qqConnectService.shareWeiboContent(user, shareContent);
		}
		return SUCCESS;
	}

	/** 将Session中的WeiboUser对象放入user */
	private boolean loadWeiboUser() {
		Object o = context.getSession().get(Constant.User.USER);
		if (null == o) {
			return false;
		}
		user = (WeiboUser) o;
		return true;
	}

	public String getSinaWeiboCheck() {
		return sinaWeiboCheck;
	}

	public void setSinaWeiboCheck(String sinaWeiboCheck) {
		this.sinaWeiboCheck = sinaWeiboCheck;
	}

	public String getShareContent() {
		return shareContent;
	}

	public void setShareContent(String shareContent) {
		this.shareContent = shareContent;
	}

	public Result getData() {
		return data;
	}

	public void setData(Result data) {
		this.data = data;
	}

	public void setQqConnectCheck(String qqConnectCheck) {
		this.qqConnectCheck = qqConnectCheck;
	}
}
