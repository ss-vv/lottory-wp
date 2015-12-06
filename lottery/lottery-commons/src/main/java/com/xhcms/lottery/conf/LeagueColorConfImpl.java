package com.xhcms.lottery.conf;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import com.xhcms.lottery.commons.persist.service.MatchColorService;

public class LeagueColorConfImpl implements LeagueColorConf {

	private long matchColorTimeout;
	
	private long beginTime = System.currentTimeMillis();
	
	@Autowired
	private MatchColorService matchColorService;

	public MatchColorService getMatchColorService() {
		return matchColorService;
	}

	public void setMatchColorService(MatchColorService matchColorService) {
		this.matchColorService = matchColorService;
	}

	Map<String, String> colorMap = new HashMap<String, String>();

	Map<String, String> leagueNameShortNameMap = new HashMap<String, String>();

	Map<String, String> shortNameLeageNameMap = new HashMap<String, String>();

	public Map<String, String> getColorMap() {
		return colorMap;
	}
	
	public void init() {
		initLeagueColor();
		initLeagueName();
	}

	public void initLeagueColor() {
		colorMap = matchColorService.loadLeagueNameColorMap();
	}

	public void initLeagueName() {
		leagueNameShortNameMap = matchColorService.loadLeagueNameShortName();
		Set<String> leagueNameSet = leagueNameShortNameMap.keySet();
		for (String leagueName : leagueNameSet) {
			shortNameLeageNameMap.put(leagueNameShortNameMap.get(leagueName),
					leagueName);
		}
	}

	@Override
	public Map<String, String> getLeagueNameShortNameMap() {
		isReloadLeagueName();
		return leagueNameShortNameMap;
	}

	@Override
	public Map<String, String> getShortNameLeagueNameMap() {
		isReloadLeagueName();
		return shortNameLeageNameMap;
	}

	public long getMatchColorTimeout() {
		return matchColorTimeout;
	}

	public void setMatchColorTimeout(long matchColorTimeout) {
		this.matchColorTimeout = matchColorTimeout;
	}

	protected void isReloadLeagueName() {
		if(System.currentTimeMillis() - beginTime >= matchColorTimeout) {
			initLeagueColor();
			initLeagueName();
			beginTime = System.currentTimeMillis();
		}
	}
}
