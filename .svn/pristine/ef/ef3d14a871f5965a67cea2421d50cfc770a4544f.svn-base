package com.unison.lottery.weibo.data;

/**
 * @Description: 结果模型基础类 
 * @author 江浩翔   
 * @date 2013-10-16 下午2:46:20 
 * @version V1.0
 */
public class Result {
	
	/** 是否成功 */
	protected boolean success;
	
	/** 结果代码*/
	protected String resultCode;
	
	/** 结果描述*/
	protected String desc;
	
	/** 当前用户id */
	protected Long userId;
	
	public Result(){
		this.setSuccess(true);
	}
	
	public void fail(String msg){
		setSuccess(false);
		setDesc(msg);
	}
	
	public void fail(String code, String msg){
		setSuccess(false);
		setResultCode(code);
		setDesc(msg);
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
