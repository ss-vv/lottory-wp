package com.xhcms.lottery.admin.web.action.groupfollow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.admin.persist.service.BetSchemeService;
import com.xhcms.lottery.admin.web.action.BaseAction;

public class RecommendSchemeAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5892921206694125286L;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
    @Autowired
    private BetSchemeService betSchemeService;
    
    private long id;
	
	@Override
	public String execute() throws Exception {
		try{
			betSchemeService.recommendScheme(id);
			addActionMessage("设置推荐方案成功。");
		}catch(Exception e){
			logger.warn("不能设置推荐方案。", e);
			addActionMessage("不能保存推荐方案。" + e.getMessage());
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
