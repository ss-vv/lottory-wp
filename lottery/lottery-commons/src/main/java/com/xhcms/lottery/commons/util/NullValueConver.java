package com.xhcms.lottery.commons.util;

import java.math.BigDecimal;
import java.util.Date;

public class NullValueConver {
	
	public static String stringToConver(String value){
		
		return value==null?"":value;
		
	}
	public static double doubleToConver(Double d){
		
		return d==null?0:d;
	}
	
	public static Integer intergerToConver(Integer i){
		
		return i==null?0:i;
	}
	
	public static Long dateToConver(Date d){
		
		return d==null?new Date().getTime():d.getTime();
	}
	
	public static Long longToConver(Long l){
		
		return l==null?0:l;
	}
	
	public static Boolean booleanToConver(Boolean b){
		
		return b==null?false:b;
	}
	
	public static Float floatToConver(Float f){
		
		return f==null?0:f;
	}
	
	public static String bigDecimalToConver(BigDecimal b){
		
		return b==null?"0":b.toString();
	}
	
	public static Date longToConver_(Long l){
		
		return l==null?new Date():new Date(l);
	}
	
	public static BigDecimal bigDecimalToConver(String d){
		
		return d==null?new BigDecimal(0).setScale(2, BigDecimal.ROUND_FLOOR):new BigDecimal(d).setScale(2, BigDecimal.ROUND_FLOOR);
	}

}
