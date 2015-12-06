package com.unison.lottery.weibo.common.redis;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 指定哪个属性被当做 redis Hash中对象的id字段。<br/>
 * 用来修改约定值“id”。
 * 
 * @author Yang Bo
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ObjectId {
	String value() default "id";
}
