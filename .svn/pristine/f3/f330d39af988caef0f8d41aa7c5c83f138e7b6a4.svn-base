package com.xhcms.lottery.admin.web.action.groupfollow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.admin.persist.service.BetSchemeService;
import com.xhcms.lottery.admin.web.action.BaseListAction;

public class CancelRecommendSchemeAction extends BaseListAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5892291206694125686L;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BetSchemeService betSchemeService;
    
    private long id;
	
	@Override
	public String execute() throws Exception {
		try{
			betSchemeService.cancelRecommendScheme(id);
			addActionMessage("取消推荐方案成功。");
		}catch(Exception e){
			logger.warn("不能取消推荐方案。", e);
			addActionMessage("不能取消推荐方案。" + e.getMessage());
		}
		return SUCCESS;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
