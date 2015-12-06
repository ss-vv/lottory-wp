package com.davcai.lottery.platform.client.chooser;

import java.util.HashMap; 
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.davcai.lottery.platform.client.ILotteryPlatformClient;
import com.davcai.lottery.platform.constants.LotteryInterfaceName;
import com.xhcms.lottery.lang.LotteryPlatformId;

@Service
public class LotteryPlatformChooserImpl implements
		ILotteryPlatformChooser {
	
	private Map<String,ILotteryPlatformClient> map4Clients=new HashMap<String,ILotteryPlatformClient>();
	

	@Override
	public ILotteryPlatformClient chooseOnePlatformClient(String platformId,LotteryInterfaceName lotteryInterfaceName) {
		if(StringUtils.isBlank(platformId)||null==lotteryInterfaceName){
			return null;
		}
		ILotteryPlatformClient client=null;
		String key=platformId+"_"+lotteryInterfaceName;
		if(map4Clients.containsKey(key)){
			client=map4Clients.get(key);
		}
		return client;
	}


	public Map<String,ILotteryPlatformClient> getMap4Clients() {
		return map4Clients;
	}


	public void setMap4Clients(Map<String,ILotteryPlatformClient> map4Clients) {
		this.map4Clients = map4Clients;
	}

	
}
