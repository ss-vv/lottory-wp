package com.xhcms.lottery.commons.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ShareUserScore implements Serializable {

	private static final long serialVersionUID = 4749523155924366826L;
	
	Map<String, CacheUserScore> usmap = new HashMap<String, CacheUserScore>();

	public Map<String, CacheUserScore> getUsmap() {
		return usmap;
	}

	public void setUsmap(Map<String, CacheUserScore> usmap) {
		this.usmap = usmap;
	}
}
