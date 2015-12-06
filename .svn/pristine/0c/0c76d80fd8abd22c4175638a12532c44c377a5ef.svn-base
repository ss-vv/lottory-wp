package com.xhcms.lottery.admin.web.action.preset;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.lang.Data;
import com.xhcms.lottery.admin.persist.service.BetSchemeService;
import com.xhcms.lottery.admin.web.action.BaseAction;

/**
 * 预派奖
 * @author Wang Lei
 *
 */
public class AjaxPresetAwardAction extends BaseAction {

	private static final long serialVersionUID = -3215312987872667810L;

	private static Logger logger = LoggerFactory.getLogger(AjaxPresetAwardAction.class);

    @Autowired
    private BetSchemeService betSchemeService;
    
    private List<Long> id;
    
    private Data data = Data.success(getText("message.success"));
    
    public String execute(){
    	try {
    		logger.info("操作员：{}  开始执行[预派奖]，方案id={}",getMyId(),id);
    		betSchemeService.presetAward(getMyId(), id);
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
