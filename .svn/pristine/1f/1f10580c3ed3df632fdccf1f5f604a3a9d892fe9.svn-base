package com.unison.lottery.weibo.dataservice.parse;

import java.util.List;

/**
 * javascript 工具类。
 * 
 * @author Yang Bo
 */
public class JsUtils {
	
	/**
	 * @param nativeArray
	 * @return 二维数组。
	 */
	@SuppressWarnings("rawtypes")
	public static Object[][] nativeArrayTo2DimArray(Object nativeArray){
		List narray = (List) nativeArray;
		Object[][] javaArrays = new Object[narray.size()][];
		int i=0;
		for (Object element : narray){
			List embedArray = (List) element;
			javaArrays[i++] = embedArray.toArray();
		}
		return javaArrays;
	}

	/**
	 * @param nativeArray
	 * @return 一维数组
	 */
	@SuppressWarnings("rawtypes")
	public static Object[] nativeArrayTo1DimArray(Object nativeArray) {
		List narray = (List) nativeArray;
		return narray.toArray();
	}

}
