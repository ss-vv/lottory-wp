package com.unison.lottery.weibo.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class FilterHtmlTagUtil {

	private static Logger logger = LoggerFactory
			.getLogger(FilterHtmlTagUtil.class);
	
	/**
	 * 过滤所有String 类型的属性值
	 * @param object
	 */
	@Around("(execution(public * com.unison.lottery.weibo.*.service.impl.*.update*(..))"
			+ "||execution(public * com.unison.lottery.weibo.*.service.impl.*.add*(..))"
			+ "||execution(public * com.unison.lottery.weibo.*.service.impl.*.create*(..)))"
			+ "&&!execution(public * com.unison.lottery.weibo.msg.service.impl.MessageServiceImpl.*(..))")
	public static Object filerFromAllProperties(final ProceedingJoinPoint proceedingJoinPoint) {
		try {
			Object[] args=proceedingJoinPoint.getArgs();
			for (int i = 0; i < args.length; i++) {
				Object object = args[i];
				if (object instanceof String) {
					args[i] = Jsoup.clean((String) object, new Whitelist());
					continue;
				}
				String className = object.getClass().getName();
				Field[] fields = Class.forName(className).getDeclaredFields();
				for (Field field : fields) {
					Object val = invokeGet(object, field.getName());
					if (null != val && val instanceof String) {
						String valString = (String) val;
						if (StringUtils.isNotBlank(valString)) {
							Whitelist whitelist = new Whitelist();
							String newString = Jsoup.clean(valString, whitelist);
							invokeSet(object, field.getName(), newString);
							logger.info("过滤了对象{}的{}域，原值：{},新值：{}", new Object[]{
                                object.getClass().getName(),
                                field.getName(),
                                valString,
                                newString
							});
						}
					}
				}
			}
			return proceedingJoinPoint.proceed(args);
		} catch (ClassNotFoundException e) {
			logger.error("", e);
		} catch (Throwable e) {
			logger.error("", e);
		}
		return null;
	}

	/**
	 * java反射bean的get方法
	 * @param objectClass
	 * @param fieldName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Method getGetMethod(Class objectClass, String fieldName) {
		StringBuffer sb = new StringBuffer();
		sb.append("get");
		sb.append(fieldName.substring(0, 1).toUpperCase());
		sb.append(fieldName.substring(1));
		try {
			return objectClass.getMethod(sb.toString());
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

	/**
	 * java反射bean的set方法
	 * @param objectClass
	 * @param fieldName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Method getSetMethod(Class objectClass, String fieldName) {
		try {
			Class[] parameterTypes = new Class[1];
			Field field = objectClass.getDeclaredField(fieldName);
			parameterTypes[0] = field.getType();
			StringBuffer sb = new StringBuffer();
			sb.append("set");
			sb.append(fieldName.substring(0, 1).toUpperCase());
			sb.append(fieldName.substring(1));
			Method method = objectClass
					.getMethod(sb.toString(), parameterTypes);
			return method;
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

	/**
	 * 执行set方法
	 * @param o执行对象
	 * @param fieldName属性
	 * @param value值
	 */
	public static void invokeSet(Object o, String fieldName, Object value) {
		Method method = getSetMethod(o.getClass(), fieldName);
		try {
			method.invoke(o, new Object[] { value });
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	/**
	 * 执行get方法
	 * @param o执行对象
	 * @param fieldName属性
	 */
	public static Object invokeGet(Object o, String fieldName) {
		Method method = getGetMethod(o.getClass(), fieldName);
		try {
			return method.invoke(o, new Object[0]);
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}
}
