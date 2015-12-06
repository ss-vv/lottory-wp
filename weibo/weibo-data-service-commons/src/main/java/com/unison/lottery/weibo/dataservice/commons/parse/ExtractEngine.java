package com.unison.lottery.weibo.dataservice.commons.parse;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.NumberConverter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文本格式抽取引擎。
 * <ul>
 * 特色：
 * <li>支持自动类型转换，如：true,True,false,False,long,int,BigDecimal.
 * <li>支持嵌套属性写法，如："parentAttr.childAttr"
 * </ul>
 * @author Yang Bo
 */
public class ExtractEngine<BEAN> {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private Class<BEAN> beanClass;
	
	private Object transformer;

	private TextDocument format;
	
	/**
	 * 准备一个抽取引擎。
	 * @param indexDefinition 每个属性在文本格式中的下标，从0开始。
	 */
	public ExtractEngine(TextDocument format, Class<BEAN> beanClass){
		this(format, beanClass, null);
	}
	

	/**
	 * 准备一个抽取引擎。
	 * @param indexDefinition 每个属性在文本格式中的下标，从0开始。
	 * @param transformer 转换引擎，如果有与属性同名的方法，则会被调用，传入原始串，用返回值作为bean属性值。
	 */
	public ExtractEngine(TextDocument format, Class<BEAN> beanClass, Object transformer){
		this.format = format;
		this.beanClass = beanClass;
		this.transformer = transformer;
		// 设置日期转换格式
		String fmt = format.getDateFormat();
		if(StringUtils.isNotBlank(fmt)){
			DateConverter converter = new DateConverter();
			converter.setPattern(fmt);
			ConvertUtils.register(converter, Date.class);
		}
		// 设置 BigDecimal 默认值
		NumberConverter numberConverter = new BigDecimalConverter(BigDecimal.ZERO);
		ConvertUtils.register(numberConverter, BigDecimal.class);
	}
	
	/**
	 * 按照注解的bean定义，解析格式化文本串，填充bean的属性。
	 * 
	 * @param bean 用注解定义的待填充对象
	 * @return 用文本信息填充属性后的bean，是传入的参数对象。null 如果内容串为空。
	 * @throws ExtractException 如果抽取失败
	 */
	public BEAN extractBeanFromText(String content) throws ExtractException{
		BEAN bean = null;
		if (StringUtils.isBlank(content)){
			return null;
		}
		try {
			bean = beanClass.newInstance();
			Map<String, String> propValueMap = constructPropValueMap(content, format);
			try {
				BeanUtils.populate(bean, propValueMap);
			} catch (InvocationTargetException e) {
				logger.error("不能转换属性的类型：properties={}", propValueMap);
				throw new ExtractException("不能转换属性的类型，填充bean对象失败!", e);
			}
		} catch (Exception e) {
			logger.error("不能创建 Bean 对象", e);
			throw new ExtractException("不能创建 Bean 对象！", e);
		}
		
		return bean;
	}

	// 递归构造“属性、值 Map”
	private Map<String, String> constructPropValueMap(String content, TextField fieldDef) throws ExtractException{
		String[] splitedFields = content.split(fieldDef.getDelimiter());
		Map<Integer, TextField> indexDef = fieldDef.getRecordsIndex();
		Map<String, String> propValueMap = new HashMap<>();

		for (int i=0; i<splitedFields.length; i++){
			TextField record = indexDef.get(i);
			if(record == null){
				continue;
			}
			String attribute = record.getPropertyName();
			String value = splitedFields[i];
			if (attribute != null){
				String changedValue = transform(attribute, value);
				propValueMap.put(attribute, changedValue);
			}else if (record.hasSubRecords()){
				Map<String, String> subMap = constructPropValueMap(value, record);
				propValueMap.putAll(subMap);
			}
		}
		return propValueMap;
	}

	/**
	 * 转换方法的约定是：参数、返回值都是String类型，只有一个参数，为需要转换的属性值。
	 * 如果attribute为空或者value为null，则返回null
	 * 将value去掉前后空白字符后，如果有对应的transformer，调用transformer与attribute同名的方法，进行额外的转换操作；
	 * 否则返回去掉前后空白字符后的value值（如果value去掉前后空白字符后是空字符串，则返回空字符串）
	 * @param attribute 属性名
	 * @param value 原始值
	 * @return 转换后的值
	 * @throws ExtractException 
	 */
	String transform(String attribute, String value) throws ExtractException {
		if(StringUtils.isBlank(attribute)||null==value){
			return null;
		}
		value=value.trim();
		if (transformer == null){
			return value;
		}
		Class<? extends Object> transClass = transformer.getClass();
		try {
			Method method = transClass.getMethod(attribute, String.class);
			return (String) method.invoke(transformer, value);
		} catch (NoSuchMethodException e){
			//logger.debug("没有属性对应的转换方法: {}", attribute);
		} catch (SecurityException |IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			logger.error("调用转换方法失败！属性：{},值为:{},异常为:{}", attribute, value,e);
			throw new ExtractException("调用转换方法失败！属性：{"+attribute+"},值为:{"+value+"}", e);
		} catch (Exception e) {
			logger.error("调用转换方法失败！属性：{},值为:{},异常为:{}", attribute,value, e);
		}
		
		//logger.error("不能对值进行变换，使用原始值！");
		return value;
	}


	public Object getTransformer() {
		return transformer;
	}


	public void setTransformer(Object transformer) {
		this.transformer = transformer;
	}


	public TextField getFormat() {
		return format;
	}


	public void setFormat(TextDocument format) {
		this.format = format;
	}
}
