package com.xhcms.lottery.dc.persist.persister;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.xhcms.lottery.commons.persist.service.CGJTeamService;
import com.xhcms.lottery.dc.core.Persister;
import com.xhcms.lottery.dc.data.Match;

public class CGJTeamPersisterImpl implements Persister<Match> {

	private Logger log = LoggerFactory.getLogger(getClass());
	
	private CGJTeamService cgjTeamService;

	private String year;

	private List<String> betTypes;

	public void setCgjTeamService(CGJTeamService cgjTeamService) {
		this.cgjTeamService = cgjTeamService;
	}

	@Override
	public void persist(List<Match> data) {
		if(StringUtils.isNotBlank(year) &&
				null != betTypes) {
			for(String betType : betTypes) {
				log.debug("开始更新‘猜冠军’队伍信息，年份={},玩法ID={}", 
						year, betTypes);
				
				cgjTeamService.updateCGJTeam(data, year, betType);
			}
		} else {
			log.info("准备更新‘猜冠军’队伍信息，无效的年份={}或者玩法ID={}", 
					year, betTypes);
		}
	}

	public void setYear(String year) {
		this.year = year;
	}

	public void setBetTypes(List<String> betTypes) {
		this.betTypes = betTypes;
	}
}