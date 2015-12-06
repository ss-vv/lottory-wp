package com.unison.lottery.itf;

import java.util.HashMap;
import java.util.Map;

public class NotifyBaseProcessor implements INotifyProcessor {

	
	private Map<String,?> resultMap = new HashMap<String, Object>();
	
	@Override
	public Map<String, ?> process(String cotent) {
		return resultMap;
	}

	

}
