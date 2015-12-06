package com.unison.lottery.weibo.dataservice.commons.parse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

public class ExtractEngineTest {

	private ExtractEngine<BeanForTest> engine;

	@Before
	public void setup(){
		//
		// 先定义待抽取文本的格式，包括两部分：
		//   1、分隔符（注意是正则表达式）
		//   2、属性的下标值（顺序，从0开始）
		//   3、日期格式，比如："yyyy-MM-dd HH:mm:ss"
		//
		TextDocument document = new TextDocument();
		document.setDelimiter("\\^");
		document.setDateFormat("yyyy-MM-dd HH:mm:ss");
		
		document.addField(0, "floatProp");
		document.addField(1, "booleanObjProp");
		document.addField(2, "booleanProp");
		document.addField(3, "decimalProp");
		document.addField(4, "strProp");
		document.addField(5, "intProp");
		document.addField(6, "longProp");
		document.addField(7, "longObjProp");
		document.addField(8, "dateProp");
		
		TextField subField = new TextField();
		subField.setDelimiter(",");
		
		subField.addField(0, "subProp1");
		subField.addField(1, "subProp2");
		subField.addField(2, "subProp3");
		
		document.addField(9, subField);
		
		document.addField(10, "child.childProp");
		
		engine = new ExtractEngine<BeanForTest>(document, BeanForTest.class);
	}
	
	@Test
	public void testChild() throws ExtractException{
		String content = "0.7^True^false^3.5123^中文^78^1234567^7890123^2013-12-02 17:12:34^abc,52345,true^child";
		BeanForTest bean = engine.extractBeanFromText(content);
		System.out.println(bean);
		assertEquals("child", bean.getChild().getChildProp());
	}
	
	@Test
	public void testBasic() throws ExtractException {
		String content = "0.7^True^false^3.5123^中文^78^1234567^7890123^2013-12-02 17:12:34";
		BeanForTest bean = engine.extractBeanFromText(content);
		System.out.println(bean);
		assertTrue(bean.getBooleanObjProp());
		assertFalse(bean.isBooleanProp());
		assertEquals(bean.getStrProp(), "中文");
	}

	
	@Test
	public void testSubProperty() throws ExtractException{
		String content = "0.7^True^false^3.5123^中文^78^1234567^7890123^2013-12-02 17:12:34^abc , 52345,true";
		BeanForTest bean = engine.extractBeanFromText(content);
		System.out.println(bean);
		assertEquals("abc", bean.getSubProp1());
		assertEquals(52345, bean.getSubProp2());
		assertTrue(bean.isSubProp3());
	}
	
	public static class TestTransformer {
		public String strProp(String in){
			return in.replaceAll("\\(.*\\)", "");
		}
	}
	
	@Test
	public void testTransform() throws ExtractException {
		engine.setTransformer(new TestTransformer());
		String content = "0.7^True^false^3.5123^中文(需要去掉)^78^1234567^7890123^2013-12-02 17:12:34";
		BeanForTest bean = engine.extractBeanFromText(content);
		System.out.println(bean);
		assertEquals(bean.getStrProp(), "中文");
	}
	
	@Test
	public void testTransferPropertyValueWhenNotSetTransformer() throws ExtractException{
		String attribute="strProp";
		String value="testString";
		String newValue=engine.transform(attribute, value);
		assertTrue(StringUtils.equals("testString", newValue));
		
		value=" testString ";
		newValue=engine.transform(attribute, value);
		assertTrue(StringUtils.equals("testString", newValue));
		
		value="  ";
		newValue=engine.transform(attribute, value);
		assertTrue(StringUtils.equals("", newValue));
	}
}
