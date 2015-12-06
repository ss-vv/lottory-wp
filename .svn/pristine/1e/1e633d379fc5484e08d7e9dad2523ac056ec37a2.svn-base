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
 * @author jiajiancheng,wanglei
 * @version 1.0.0
 */
public class AjaxEditFBMatchAction extends BaseAction {

    private static final long serialVersionUID = 6385058823032919922L;
    private Logger log = LoggerFactory.getLogger(AjaxEditFBMatchAction.class);
    
    @Autowired
    private MatchService matchService;

    private Long id;

    private int status;
    
    private String halfScorePreset;
    private String scorePreset;

    private Data data;

    public String execute() {
    	log.info("修改竞彩足球比赛状态：id={}, status={}", new Object[]{id, status});
    	
        matchService.updateFBMatch(id, status);
        data = Data.success(null);
        
        return SUCCESS;
    }
    
    public String update(){
    	matchService.updateFBMatchScore(id, halfScorePreset, scorePreset);
    	data = Data.success(null);

        return SUCCESS;
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

	public String getHalfScorePreset() {
		return halfScorePreset;
	}

	public void setHalfScorePreset(String halfScorePreset) {
		this.halfScorePreset = halfScorePreset;
	}

	public String getScorePreset() {
		return scorePreset;
	}

	public void setScorePreset(String scorePreset) {
		this.scorePreset = scorePreset;
	}

}
