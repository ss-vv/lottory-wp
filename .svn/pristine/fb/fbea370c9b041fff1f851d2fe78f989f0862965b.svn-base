/**
 * 
 */
package com.xhcms.lottery.admin.web.action.match;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.lang.Data;
import com.xhcms.lottery.admin.persist.service.MatchService;
import com.xhcms.lottery.admin.web.action.BaseAction;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright：Copyright (c) 2011</p>
 * <p>Company：XingHui Spirit (Beijing) Technical Co.,Ltd.</p>
 * 
 * @author jiajiancheng, wanglei
 * @version 1.0.0
 */
public class AjaxEditBBMatchAction extends BaseAction {

    private static final long serialVersionUID = 6385058823032919922L;
    private Logger log = LoggerFactory.getLogger(AjaxEditBBMatchAction.class);

    @Autowired
    private MatchService matchService;

    private Long id;

    private int status;
    private String finalScorePreset;

    private Data data;

    public String execute() {
    	log.info("修改竞彩篮球比赛状态：id={}, status={}", 
    			new Object[]{id, status});
    	
        matchService.updateBBMatch(id, status);
        data = Data.success(null);

        return SUCCESS;
    }
    
    public String update(){
    	matchService.updateBBMatchScore(id, finalScorePreset);
    	data = Data.success(null);

        return SUCCESS;
    }

    public String getFinalScorePreset() {
		return finalScorePreset;
	}

	public void setFinalScorePreset(String finalScorePreset) {
		this.finalScorePreset = finalScorePreset;
	}

	public void setId(Long id) {
        this.id = id;
    }


    public void setStatus(int status) {
        this.status = status;
    }


    public Data getData() {
        return data;
    }

}
