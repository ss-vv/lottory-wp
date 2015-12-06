package com.unison.lottery.weibo.common.redis;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Reference {
	/**
	 * 引用的id属性名。默认是在属性名后加上"Id"后缀。
	 */
	String by() default "";
}
