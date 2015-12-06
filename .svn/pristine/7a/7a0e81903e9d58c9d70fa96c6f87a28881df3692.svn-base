package com.davcai.lottery.weibo.analyse.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SortList<E> {
	Logger logger = LoggerFactory.getLogger(SortList.class);
	@SuppressWarnings("unchecked")
	/**
	 * 
	 * @param list
	 * @param method
	 * @param sort 排序方式：升序asc 降序desc
	 */
	public void sort(List<E> list, final String method, final String sort){  
	        Collections.sort(list, new Comparator() {             
	            public int compare(Object a, Object b) {  
	                int ret = 0;  
	                try{  
	                    Method m1 = ((E)a).getClass().getMethod(method, null);  
	                    Method m2 = ((E)b).getClass().getMethod(method, null);  
	                    if(sort != null && "desc".equals(sort))//倒序  
	                        ret = m2.invoke(((E)b), null).toString().compareTo(m1.invoke(((E)a), null).toString());   
	                    else//正序  
	                        ret = m1.invoke(((E)a), null).toString().compareTo(m2.invoke(((E)b), null).toString());  
	                }catch(NoSuchMethodException ne){  
	                    logger.error("排序过程出错:{}",ne);
	                }catch(IllegalAccessException ie){  
	                	logger.error("排序过程出错:{}",ie); 
	                }catch(InvocationTargetException it){  
	                	logger.error("排序过程出错:{}",it); 
	                }  
	                return ret;  
	            }  
	         });  
	    }  
}

