package com.unison.lottery.weibo.web.action.pc.ajax;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.unison.lottery.weibo.data.WeiboUserStatis;
import com.unison.lottery.weibo.web.action.BaseAction;
import com.unison.lottery.weibo.web.service.RecomendUserService;
/**
 * 中奖奖金排行榜
 * @author Administrator
 *
 */
public class BounsUserAction extends BaseAction{

	private static final long serialVersionUID = 5640447374368270850L;

	private String currentUserId;
	@Autowired
	private RecomendUserService recomendUserService;
	private List<WeiboUserStatis> data = new ArrayList<WeiboUserStatis>();
	public String execute(){
		currentUserId = getUserLaicaiWeiboId()+"";
		data=recomendUserService.getBonusTop10WeiboUser(currentUserId);
		return SUCCESS;
	}
	public List<WeiboUserStatis> getData() {
		return data;
	}
	public void setData(List<WeiboUserStatis> data) {
		this.data = data;
	}
	
}
