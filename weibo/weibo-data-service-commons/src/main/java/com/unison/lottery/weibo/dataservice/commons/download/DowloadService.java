package com.unison.lottery.weibo.dataservice.commons.download;

import java.util.Map;

import com.unison.lottery.weibo.dataservice.commons.constants.DataInterfaceName;
import com.unison.lottery.weibo.dataservice.commons.model.DataInterfaceResponse;

public interface DowloadService {

	/**
	 * 
	 * @param dataInterfaceName 数据接口的名字,枚举类型 
	 * @return 数据接口返回的响应
	 */
	DataInterfaceResponse downloadToString(DataInterfaceName dataInterfaceName);

	/**
	 * 
	 * @param dataInterfaceName 数据接口的名字,枚举类型
	 * @return 数据接口返回的响应,包含保存的文件对象
	 */
	DataInterfaceResponse downloadToFile(DataInterfaceName dataInterfaceName);

	/**
	 * 
	 * @param dataInterfaceName 数据接口的名字,枚举类型
	 * @param extendParams  额外参数map
	 * @return 数据接口返回的响应,包含保存的文件对象
	 */
	DataInterfaceResponse downloadToFileWithExtendParams(
			DataInterfaceName dataInterfaceName, Map<String, String> extendParams);
	
	/**
	 * 
	 * @param dataInterfaceName 数据接口的名字,枚举类型
	 * @param extendParams 额外参数map
	 * @return 数据接口返回的响应
	 */
	DataInterfaceResponse downloadToStringWithExtendParams(DataInterfaceName dataInterfaceName,Map<String, String> extendParams);

}
