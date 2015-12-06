/**
 * 
 */
package com.xhcms.ucenter.vo.message;


/**
 * @author bean
 */
public class NewMessage {
	private long uid;
	private int newmsgCount;
	private int newNotifyCount;
	
	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
	public int getNewmsgCount() {
		return newmsgCount;
	}
	public void setNewmsgCount(int newmsgCount) {
		this.newmsgCount = newmsgCount;
	}
	public int getNewNotifyCount() {
		return newNotifyCount;
	}
	public void setNewNotifyCount(int newNotifyCount) {
		this.newNotifyCount = newNotifyCount;
	}
	
}
