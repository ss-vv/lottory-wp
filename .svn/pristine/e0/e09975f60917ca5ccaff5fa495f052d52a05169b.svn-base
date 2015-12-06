package com.unison.lottery.weibo.web.action.pc.ajax;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.data.WeiboUserStatis;
import com.unison.lottery.weibo.uc.service.UserAccountService;
import com.unison.lottery.weibo.web.action.BaseAction;
import com.unison.lottery.weibo.web.service.UserStatisService;

public class WeiboUserBaseInfo extends BaseAction{

	private static final long serialVersionUID = 1L;
	
	private List<WeiboUser> data = new ArrayList<WeiboUser>();
	private String username;
	private String[] usernames;
	private WeiboUserStatis weibouserStatis;
	private String weiboUserId;
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private UserStatisService userStatisService;
	
	public String getUserInfo(){
		WeiboUser w = new WeiboUser();
		w.setUsername(username);
		WeiboUser w2 = userAccountService.findWeiboUserByUsername(username);
		w.setNickName(w2.getNickName());
		w.setHeadImageURL(w2.getHeadImageURL());
		w.setCity(w2.getCity());
		w.setId(w2.getId());
		data.add(w);
		return SUCCESS;
	}
	public String getUserInfos(){
		if(null == usernames){
			return SUCCESS;
		}
		for (String username : usernames) {
			WeiboUser w = new WeiboUser();
			w.setUsername(username);
			WeiboUser w2 = userAccountService.findWeiboUserByUsername(username);
			if(null != w2){
				w.setNickName(w2.getNickName());
				w.setHeadImageURL(w2.getHeadImageURL());
				w.setCity(w2.getCity());
				w.setId(w2.getId());
				w.setWeiboUserId(w2.getWeiboUserId());
			} else {
				w.setNickName("匿名");
				w.setHeadImageURL("http://www.davcai.com/images/default_face.jpg");
				w.setId(-1L);
				w.setWeiboUserId(-1L);
			}
			data.add(w);
		}
		return SUCCESS;
	}

	public String getWeibouserStatisData(){
		weibouserStatis = userStatisService.getWeiboUserStatisByWeiboUserId(weiboUserId);
		return SUCCESS;
	}
	public List<WeiboUser> getData() {
		return data;
	}
	public void setData(List<WeiboUser> data) {
		this.data = data;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setUsernames(String[] usernames) {
		this.usernames = usernames;
	}
	public WeiboUserStatis getWeibouserStatis() {
		return weibouserStatis;
	}
	public void setWeibouserStatis(WeiboUserStatis weibouserStatis) {
		this.weibouserStatis = weibouserStatis;
	}
	public String getWeiboUserId() {
		return weiboUserId;
	}
	public void setWeiboUserId(String weiboUserId) {
		this.weiboUserId = weiboUserId;
	}
	
}
