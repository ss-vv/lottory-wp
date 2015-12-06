package com.unison.lottery.weibo.data.crawler.proxy.query.dao.entity;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 手机系统型号和操作系统版本号PO
 * @author 彭保星
 *
 * @since 2014年10月27日下午2:17:33
 */
@Entity
@Table(name="system_phone_version")
public class UserAgentPO {
	@Id
	private int id;
	private String systemVersion;  //系统版本号，Android 4.0.3 etc...
	private String phoneType; //手机型号
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSystemVersion() {
		return systemVersion;
	}
	public void setSystemVersion(String systemVersion) {
		this.systemVersion = systemVersion;
	}
	public String getPhoneType() {
		return phoneType;
	}
	public void setPhoneType(String phoneType) {
		this.phoneType = phoneType;
	}
	
	
}
