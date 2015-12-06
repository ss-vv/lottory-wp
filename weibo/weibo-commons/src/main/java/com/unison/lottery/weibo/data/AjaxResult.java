package com.unison.lottery.weibo.data;

/**
 * Ajax 请求的响应结果。
 * 
 * @author Yang Bo
 */
public class AjaxResult<T> extends Result {
	private T data;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	public static <T> AjaxResult<T> successResult(T data){
		AjaxResult<T> result = new AjaxResult<T>();
		result.setData(data);
		result.setSuccess(true);
		result.setDesc("成功");
		result.setResultCode("1");
		return result;
	}
	
	public static <T> AjaxResult<T> failResult(String code, String desc){
		AjaxResult<T> result = new AjaxResult<T>();
		result.setSuccess(false);
		result.setDesc(desc);
		result.setResultCode(code);
		return result;
	}
}
