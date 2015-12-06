package com.unison.lottery.weibo.common.redis;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.unison.lottery.weibo.exception.DaoException;

/**
 * 实现 Redis Dao OHM 的工具类。
 * 
 * @author Yang Bo
 */
public class RedisDaoUtils {
	
	static public List<Field> gatherAllFields(Class<?> clazz) {
        List<Field> allFields = new ArrayList<Field>();
        Collections.addAll(allFields, clazz.getDeclaredFields());
        while ((clazz = clazz.getSuperclass()) != null) {
            allFields.addAll(gatherAllFields(clazz));
        }

        return Collections.unmodifiableList(allFields);
    }
	
	/**
	 * 获取model的id值。支持注解@ObjectId.
	 * 
	 * @param model
	 * @return
	 * @throws DaoException 如果没有 long 类型的 id.
	 */
	static public long getId(Object model){
		String id = null;
		try {
			String idFieldName = getIdPropertyName(model);
			id = BeanUtils.getProperty(model, idFieldName);
			return Long.parseLong(id);
		} catch (Exception e) {
			throw new DaoException("Can not get id of model!", e);
		}
	}

	static public String getIdPropertyName(Object model){
		String id = "id";
		Field field = getAnnotatedField(ObjectId.class, model);
		if (field != null){
			id = field.getName();
		}
		return id;
	}
	
	static public Field
	getAnnotatedField(Class<? extends Annotation> annotationClass, Object model) {
		Field[] fields = model.getClass().getDeclaredFields();
		for (Field fd : fields) {
			if (fd.isAnnotationPresent(annotationClass)){
				return fd;
			}
		}
		return null;
	}
	
	/**
	 * 从 Field 对象或者父类的同名字段中读取值。
	 * @param field
	 * @param propName
	 * @return null 如果没有指定的属性或者属性值为null。
	 */
	public static Object getValueWithParent(Object child, String propName) {
		Class<?> cls = child.getClass();
		try{
			Field field = cls.getDeclaredField(propName);
			field.setAccessible(true);
			Object value = field.get(child);
			return value;
		}catch(NoSuchFieldException e){
			Class<?> parentCls = cls.getSuperclass();
			if (parentCls == null){
				return null;
			}
			try {
				Field field = parentCls.getDeclaredField(propName);
				field.setAccessible(true);
				return field.get(child);
			} catch (NoSuchFieldException pe) {
				return null;
			} catch (Exception allExp){
				throw new DaoException("Can not get value of property: " + propName, e);
			}
		} catch (Exception e) {
			throw new DaoException("Can not get value of property: " + propName, e);
		}
	}
	
	/**
	 * @return -1 如果属性不存在或者属性值为null。
	 */
	public static long getLongValueWithParent(Object child, String propName) {
		Object idObj = getValueWithParent(child, propName);
		if (idObj == null) {
			return -1;
		}
		return (Long) idObj;
	}

	public static Field getFieldWithParent(Class<?> clazz, String propName) {
		try{
			Field field = clazz.getDeclaredField(propName);
			field.setAccessible(true);
			return field;
		}catch(NoSuchFieldException e){
			Class<?> parentCls = clazz.getSuperclass();
			if (parentCls == null){
				return null;
			}
			Field field;
			try {
				field = parentCls.getDeclaredField(propName);
				field.setAccessible(true);
				return field;
			} catch (Exception e1) {
				throw new DaoException("Can not get field '"+propName+", from "+clazz, e1);
			}
		} catch (Exception e) {
			throw new DaoException("Can not get field '"+propName+", from "+clazz, e);
		}
	}
}
