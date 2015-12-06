package com.unison.weibo.admin.action.user;

import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.common.service.LotteryService;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.SpecialUserBean;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.uc.data.AccountDealResult;
import com.unison.lottery.weibo.uc.service.UserAccountService;
import com.unison.weibo.admin.action.BaseAction;
import com.xhcms.lottery.lang.LotteryId;

public class SpecialUserAction extends BaseAction {

	private static final long serialVersionUID = -2019221515602575741L;

	@Autowired
	private LotteryService lotteryService;
	
	@Autowired
	private UserAccountService userAccountService;
	
	private PageResult<SpecialUserBean> pageResult;
	
	// -------------- add special user ---------------
	
	private String nickName;
	private LotteryId lotteryId;
	private WeiboUser weiboUser = new WeiboUser();
	private String passwordConfirm;
	
	public String addSpecialUser() throws Exception{
		String weiboUserId = userAccountService.findWeiboUserIdByNickName(nickName);
		if (weiboUserId == null){
			addActionMessage("昵称无效，找不到用户！");
			return execute();
		}
		lotteryService.addLotteryUser(weiboUserId, lotteryId);
		return execute();
	}
	
	public String registSpecialUser() throws Exception{
		if (!weiboUser.getPassword().equals(passwordConfirm)){
			addActionMessage("两次输入的密码不一致！");
			return SUCCESS;
		}
		AccountDealResult result = userAccountService.registSpecialUser(weiboUser);
		if (result.isSuccess()){
			addActionMessage(result.getDesc()+", 微博uid: " + result.getWeiboUser().getWeiboUserId());
		}else{
			addActionMessage(result.getDesc());
		}
		return SUCCESS;
	}
	
	@Override
	public String execute() throws Exception {
		pageResult = lotteryService.listAllSpecialUsers(null);
		return SUCCESS;
	}

	public PageResult<SpecialUserBean> getPageResult() {
		return pageResult;
	}

	public void setPageResult(PageResult<SpecialUserBean> pageResult) {
		this.pageResult = pageResult;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public LotteryId getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(LotteryId lotteryId) {
		this.lotteryId = lotteryId;
	}

	public WeiboUser getWeiboUser() {
		return weiboUser;
	}

	public void setWeiboUser(WeiboUser weiboUser) {
		this.weiboUser = weiboUser;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

}
