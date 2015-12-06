package com.xhcms.lottery.conf;

import java.util.Map;

public interface LeagueColorConf {

	public void initLeagueColor();
	
    public void initLeagueName();
	
	public Map<String, String> getColorMap();
	
	public Map<String,String> getLeagueNameShortNameMap();
	
	public Map<String,String> getShortNameLeagueNameMap();
}
