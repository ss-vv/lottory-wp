package com.unison.lottery.weibo.dataservice.commons.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * xml数据转换为java日期类型的适配类，此类一般用于model中date类型的xml注解适配
 * @author 彭保星
 * @since 2014年10月10日上午10:43:30
 */
public class XmlDateAdapter extends XmlAdapter<String, Date> {

	 private String pattern = "yyyy/MM/dd HH:mm:ss";
	 DateFormat fmt =new SimpleDateFormat(pattern);
	@Override
	public Date unmarshal(String v) throws Exception {
		// TODO Auto-generated method stub
		return fmt.parse(v);
	}

	@Override
	public String marshal(Date v) throws Exception {
		// TODO Auto-generated method stub
		return fmt.format(v);
	}

}
