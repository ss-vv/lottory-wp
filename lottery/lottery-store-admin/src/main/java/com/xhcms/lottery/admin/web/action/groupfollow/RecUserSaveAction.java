package com.xhcms.lottery.admin.web.action.groupfollow;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.admin.web.action.BaseAction;
import com.xhcms.lottery.commons.persist.service.FollowService;

public class RecUserSaveAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5892921206694125686L;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
    @Autowired
    private FollowService followService;
    private List<String> lotteries;
    
    private long id;
	private long operatorId = 0;
	
	@Override
	public String execute() throws Exception {
		try{
			followService.recommendUser(id, lotteries, operatorId);
			addActionMessage("保存推荐用户成功。");
		}catch(Exception e){
			logger.warn("不能保存推荐用户。", e);
			addActionMessage("不能保存推荐用户，改用户很可能已经是推荐用户了。");
		}
		return SUCCESS;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<String> getLotteries() {
		return lotteries;
	}

	public void setLotteries(List<String> lotteries) {
		this.lotteries = lotteries;
	}

	public long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(long operatorId) {
		this.operatorId = operatorId;
	}

}
