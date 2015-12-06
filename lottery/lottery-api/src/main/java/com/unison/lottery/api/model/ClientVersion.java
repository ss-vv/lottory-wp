package com.unison.lottery.api.model;

import org.apache.commons.lang.StringUtils;

public class ClientVersion {
	
	private int versionNumber;
	public int getVersionNumber() {
		return versionNumber;
	}
	public void setVersionNumber(int versionNumber) {
		this.versionNumber = versionNumber;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	private String platform;
	
	public boolean isAndroid(){
		return StringUtils.equals(platform, "android");
	}
	public boolean isIOS() {
		return StringUtils.equals(platform, "ios");
	}

}
