package com.xhcms.lottery.admin.web.action.match;

import org.springframework.beans.factory.annotation.Autowired;
import com.xhcms.commons.lang.Data;
import com.xhcms.lottery.admin.web.action.BaseAction;
import com.xhcms.lottery.commons.persist.service.MatchColorService;

/**
 * 更新联赛背景色
 * @author Wang Lei
 *
 */
public class AjaxUpdateLeagueShortNameAction extends BaseAction {

    private static final long serialVersionUID = 6385058823032919922L;

    @Autowired
    private MatchColorService matchColorService;

    private Long id;
    
    private String newLeagueShortName;

    private Data data;

    public String execute() {
    	int code = -3;
    	try {
    		code = matchColorService.updateShortName(newLeagueShortName, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
        data = Data.success(code);
        return SUCCESS;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Data getData() {
        return data;
    }

	public void setNewLeagueShortName(String newLeagueShortName) {
		this.newLeagueShortName = newLeagueShortName;
	}
}
