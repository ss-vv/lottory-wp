package com.unison.lottery.mc.uni;

/**
 * 商户端接口配置.
 * @author Yang Bo
 *
 */
public class InterfaceConfig {
	private String version;		 		// 接口版本
	private String partnerId; 	// 商户ID
	private String key;
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
}
