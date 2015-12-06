package com.unison.lottery.weibo.dataservice.crawler.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 彭保星
 *
 * @since 2014年11月19日下午3:37:01
 */
public class BeanConvertUtil {
	private static Logger logger = LoggerFactory.getLogger(BeanConvertUtil.class);
	/**
	 * copy bean
	 * 
	 * @param list
	 * @param t
	 * @return
	 */
	public static <T> List<T> changePOToModel(List source, Class<T> t) {
		List<T> destList = null;
		if (source != null && !source.isEmpty()) {
			int size = source.size();
			destList = new ArrayList<T>();
			for (int i = 0; i < size; i++) {
				try {
					Object object = t.newInstance();
					BeanUtils.copyProperties(object, source.get(i));
					destList.add((T) object);
				} catch (InstantiationException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					logger.error("实例化失败:{}", e);
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					logger.error("BeanUtils copy error:{}", e);
				}
			}
		}
		return destList;

	}
	
	/**
	 * 将Map类型的list转换为javaBean类型的list
	 * 注意此处map的key值必须和javaBean中的属性值的名称一样，否则无法转换
	 * @param mapList
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> makeBean(List<Map<String,String>> mapList, T bean) throws Exception {
		List<T> matchEventModels = new ArrayList<>();
		Class<?> classType = bean.getClass();
		Method[] methods = classType.getDeclaredMethods();
		// 获取所有set方法
		Map<String, Method> allSetMethods = new HashMap<>();
		for (Method method : methods) {
			if (method.getName().contains("set")) {
				// 去除方法名前面的set，并将名字的第一个字符转化为小写
				StringBuffer sb = new StringBuffer(method.getName().substring(3));
				char c = sb.charAt(0);
				if(c>='A'&&c<='Z'){
					c=(char) (c+32);
				}
				sb.setCharAt(0,c);
				allSetMethods.put(sb.toString(),
						method);
			}
		}

		for (Map<String,String> object : mapList) {
			T t2 = null;
			try {
				t2 = (T) bean.getClass().newInstance();

			} catch (InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				logger.error("转换出错:{}", e);
			}
			for (String key : object.keySet()) {
				// 调用其底层方法
				addValue(object.get(key), allSetMethods.get(key), t2);
			}
			matchEventModels.add(t2);

		}
		return matchEventModels;
	}

	private static void addValue(Object value, Method method, Object bean) throws Exception {
		if (value != null) {
			try {
				String type = (method.getParameterTypes()[0]).getSimpleName();
				if (type.equals("String")) {
					// 第一个参数:从中调用基础方法的对象 第二个参数:用于方法调用的参数
					method.invoke(bean, new Object[] { value });
				} else if (type.equals("int") || type.equals("Integer")) {
					method.invoke(bean, new Object[] { new Integer(""
							+ value) });
				} else if (type.equals("long") || type.equals("Long")) {
					method.invoke(bean,
							new Object[] { new Long("" + value) });
				} else if (type.equals("boolean") || type.equals("Boolean")) {
					method.invoke(bean,
							new Object[] { Boolean.valueOf("" + value) });
				} else if (type.equals("Date")) {
					Date date = null;
					if (value.getClass().getName().equals("java.util.Date")) {
						date = (Date) value;
					} else if(value instanceof String){
						String format = ((String) value).indexOf(":") > 0 ? "yyyy-MM-dd hh:mm:ss"
								: "yyyy-MM-dd";
						date = DateFormateUtil.toDate(format, "" + value);
					}else if(value instanceof Long){
						date = new Date((Long)value);
					}
					if (date != null) {
						method.invoke(bean, new Object[] { date });
					}
				} else if (type.equals("byte[]")) {
					method.invoke(bean,
							new Object[] { new String(value + "").getBytes() });
				} else if(type.equals("double") || type.equals("Double")){
					method.invoke(bean, new Object[]{Double.valueOf(""+value)});
				}
			} catch (Exception e) {
				throw e;
			}
		}
	}
}
