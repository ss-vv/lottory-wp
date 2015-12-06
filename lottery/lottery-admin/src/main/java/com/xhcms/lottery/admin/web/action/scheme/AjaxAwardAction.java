package com.xhcms.lottery.admin.web.action.scheme;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.xhcms.commons.lang.Data;
import com.xhcms.lottery.admin.web.action.BaseAction;
import com.xhcms.lottery.commons.persist.service.BetSchemeService;

/**
 * 
 * <p>Title: 派奖</p>
 * <p>Description: </p>
 * <p>Copyright：Copyright (c) 2011</p>
 * <p>Company：XingHui Spirit (Beijing) Technical Co.,Ltd.</p>
 * 
 * @author wanged
 * @version 1.0.0
 */
public class AjaxAwardAction extends BaseAction {
	private static Logger logger = LoggerFactory.getLogger(AjaxAwardAction.class);
	
    private static final long serialVersionUID = 2785300945116125827L;

    @Autowired
    private BetSchemeService betSchemeService;
    
    private List<Long> id;
    
    private Data data = Data.success(getText("message.success"));
    
    public String execute(){
    	try {
    		logger.info("操作员：{}  开始执行[派奖]，方案id={}",getMyId(),id);
    		betSchemeService.award(getMyId(), id);
    	} catch (Exception e) {
    		logger.error(e.getMessage(), e);
    		data = Data.failure(e.getMessage());
    	}
    	
        return SUCCESS;
    }

    public Data getData() {
        return data;
    }

    public void setId(List<Long> id) {
        this.id = id;
    }
    
}
