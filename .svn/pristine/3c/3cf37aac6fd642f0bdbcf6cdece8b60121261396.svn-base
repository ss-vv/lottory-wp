package com.unison.lottery.weibo.dataservice.commons.util;

import java.util.EnumMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.unison.lottery.weibo.dataservice.commons.constants.Constants;
import com.unison.lottery.weibo.dataservice.commons.constants.DataInterfaceName;

@Service
public class CharSetDeciderImpl implements CharSetDecider {

	private EnumMap<DataInterfaceName,String> charsetMap;
	
	public Map<DataInterfaceName,String> getCharsetMap() {
		return charsetMap;
	}

	public void setCharsetMap(Map<DataInterfaceName,String> charsetMap) {
		this.charsetMap = new EnumMap<DataInterfaceName, String>(charsetMap);
	}
	
	@Override
	public String decideCharsetFromName(DataInterfaceName dataInterfaceName) {
		String charset=null;
		if(null!=charsetMap&&!charsetMap.isEmpty()){
			charset=charsetMap.get(dataInterfaceName);
		}
		if(StringUtils.isBlank(charset)){
			charset=Constants.DEFAULT_CHAR_SET;
		}
		return charset;
	}

}
