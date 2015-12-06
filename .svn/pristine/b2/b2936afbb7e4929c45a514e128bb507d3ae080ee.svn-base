package com.xhcms.lottery.dc.feed.web.action.cgj;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import com.opensymphony.xwork2.Action;
import com.xhcms.commons.lang.Data;
import com.xhcms.lottery.commons.persist.service.CGJTeamService;
import com.xhcms.lottery.dc.data.CGJTeam;
import com.xhcms.lottery.lang.PlayType;

public class AjaxSJBAction implements Action {

	@Autowired
	private CGJTeamService cgjTeamService;

	private Data data;
	
	public String jcsjbgj() {
		try {
			String playId = PlayType.JCSJBGJ.getShortPlayStr();
			Map<String, List<CGJTeam>> map = cgjTeamService.listTeamsCurrentSeason(playId);
			data = Data.success(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	@Override
	public String execute() throws Exception {
		return null;
	}
	
	public Data getData() {
		return data;
	}
	
}