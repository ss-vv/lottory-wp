package com.unison.lottery.weibo.dataservice.commons.parse;

import java.io.File;
import java.util.List;

import javax.script.Bindings;

import sun.org.mozilla.javascript.internal.NativeArray;

import com.unison.lottery.weibo.dataservice.commons.constants.DataInterfaceName;

@SuppressWarnings("restriction")
public interface ParseService {

	
	Bindings parseJSFromUTF8String(String js);

	Bindings parseJSFromFile(File jsFile, DataInterfaceName dataInterfaceName);

	List<List<String>> nativeArrayToListOfStringList(NativeArray nativeArray);

	List<List<Object>> nativeArrayToListOfObjectList(NativeArray nativeArray);

	List<String> nativeArrayToListOfString(NativeArray nativeArray);

	List<List<List<String>>> parseTextFromUTF8String(String str);

	List<List<List<String>>> parseTextFromFile(File file,
			DataInterfaceName currentdayodds);

	List<List<List<String>>> parseXmlFromFileWithDom(File xmlFile,DataInterfaceName dataInterfaceName);
	List<List<List<String>>> parseXmlFromStringWithDom(String xmlStr,DataInterfaceName dataInterfaceName);
	Object parseXmlFromFileWithJAXB(File xmlFile,DataInterfaceName dataInterfaceName);
	Object parseXmlFromStringWithJAXB(String xmlStr,DataInterfaceName dataInterfaceName);

	
}
