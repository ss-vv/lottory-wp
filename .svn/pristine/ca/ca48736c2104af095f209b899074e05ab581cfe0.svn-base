package com.unison.lottery.api.util;

import org.apache.commons.lang.StringUtils;

import com.unison.lottery.api.model.ClientVersion;

public class ClientVersionUtil {

	public static ClientVersion transferToClientVersionObj(String clientVersion) {
		ClientVersion clientVersionObj=new ClientVersion();
		int version = -1;
		String platform=null;
		try {
			if(StringUtils.isNotBlank(clientVersion)) {
				String[] arr = clientVersion.split("-");
				if(arr.length >= 3) {
					String versionSuffexs = arr[2];
					version = Integer.parseInt(versionSuffexs.replaceAll("\\.", ""));
					platform=arr[0];
				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		clientVersionObj.setPlatform(platform);
		clientVersionObj.setVersionNumber(version);
		return clientVersionObj;
	}

}
