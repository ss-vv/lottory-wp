package com.xhcms.lottery.admin.web.action.match;

import org.springframework.beans.factory.annotation.Autowired;
import com.xhcms.commons.lang.Data;
import com.xhcms.lottery.admin.web.action.BaseAction;
import com.xhcms.lottery.commons.persist.service.MatchColorService;

/**
 * 保存联赛背景色
 * @author Wang Lei
 *
 */
public class AjaxSaveColorAction extends BaseAction {

    private static final long serialVersionUID = 6385058823032919922L;

    @Autowired
    private MatchColorService matchColorService;

    private Long id;
    
    private String lotteryId;
    
    private String color;
    
    private String shortLeagueName;
    
    private Data data;

    public String execute() {

        data = Data.success(matchColorService.saveColor(lotteryId, color, id,shortLeagueName));

        return SUCCESS;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Data getData() {
        return data;
    }

	public String getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setShortLeagueName(String shortLeagueName) {
		this.shortLeagueName = shortLeagueName;
	}

	public String getShortLeagueName() {
		return shortLeagueName;
	}
}
