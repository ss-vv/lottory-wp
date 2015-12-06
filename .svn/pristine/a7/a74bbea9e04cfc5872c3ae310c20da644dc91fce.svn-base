package com.xhcms.lottery.mc.sms;

public interface SMSSendClientInterface {

	/**
	 * 发送发短信请求
	 * 如果SMSSendRequest对象的params属性为null，或者是空map，返回的SMSSendResult会返回表示params属性为空的状态码和相应消息文本；<br/>
	 * 如果SMSSendRequest对象的destPhones属性为null，或者是空列表，返回的SMSSendResult会返回表示destPhones属性为空的状态码和相应消息文本；<br/>
	 * 如果SMSSendRequest对象的params和destPhones属性都不空，则只会处理destPhones中符合手机号格式要求（11位，并且不以+86开头;或者以+86开头，14位）的项；<br/>
	 * SMSSendResult对象的failDestPhones包含所有处理失败的手机号<br/>
	 * SMSSendResult对象的succDestPhones包含所有发送请求成功的手机号<br/>
	 * SMSSendResult对象的wrongFormatDestPhones包含所有格式不符合要求的手机号<br/>
	 * @param smsSendRequest
	 * @return
	 */
	SMSSendResult sendSMS(SMSSendRequest smsSendRequest);

}
