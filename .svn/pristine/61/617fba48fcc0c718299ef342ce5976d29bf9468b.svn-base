package com.unison.lottery.weibo.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfficialId {
	private static Logger logger = LoggerFactory.getLogger(OfficialId.class);
	private static Properties props = new Properties(); 
	private static Map<Long, Long> officialUserIdMap = new HashMap<Long,Long>();
	private static List<Long> officialIds = new ArrayList<Long>();
	static{
		try {
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("active-user-id.properties"));
			String s = (String)props.get("userId");
			String[] uids = s.split("\\|");
			for (String uid : uids) {
				officialUserIdMap.put(Long.valueOf(uid),Long.valueOf(uid));
			}
			
		} catch (FileNotFoundException e) {
			logger.error("加载官方运营帐号错误",e);
		} catch (IOException e) {
			logger.error("加载官方运营帐号错误",e);
		}
	}
	public static Long get(Long key){
		return officialUserIdMap.get(key);
	}
	public static List<Long> getAll(){
		if(officialIds.size() > 0){
			return officialIds;
		} else {
			for (Long id  : officialUserIdMap.keySet()) {
				officialIds.add(id);
			}
			return officialIds;
		}
	}
}
