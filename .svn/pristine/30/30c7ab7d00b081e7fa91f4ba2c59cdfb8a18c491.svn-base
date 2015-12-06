package com.unison.lottery.weibo.common.redis;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 对象在 redis 中的hash的key。<br/>
 * 使用本注解可以改变用类名构建key的约定。<br/>
 * name属性必须有且只有一个%s，例如：@ObjectKey(key="test:user:%s", nextIdKey="global:nextId:testuser")
 * @author Yang Bo
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ObjectKey {

	String key();
	
	String nextIdKey();
}
