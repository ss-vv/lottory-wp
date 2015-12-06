package com.xhcms.lottery.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;

import org.apache.commons.lang.StringUtils;

import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.PlayType;

/**
 * 从文件读取文本并检查是否符合玩法规则
 * @author Wang Lei
 *
 */
public class ReadFile {
	public static HashSet<String> readTxt(File file,String oldContent,String playId) throws Exception {
		if(StringUtils.isBlank(playId) || null==file){
			return null;
		}
		String encoding = "UTF-8";
		HashSet<String> result = new HashSet<String>();
		if (file.exists() && file.canRead()
				&& (file.getName().lastIndexOf(".txt") < 0)) {
			InputStreamReader read = new InputStreamReader(new FileInputStream(
					file), encoding);
			BufferedReader buffer = new BufferedReader(read);
			addTxt(buffer,result,playId);
			if(StringUtils.isNotBlank(oldContent)){
				addTxt(oldContent.split("\n"),result,playId);
			}
			if (read != null) {
				read.close();
			}
		}
		return result;
	}
	
	private static void addTxt(BufferedReader buffer,HashSet<String> result,String playId) throws IOException{
		String lineTxt;
		while ((lineTxt = buffer.readLine()) != null) {
			lineTxt = lineTxt.trim();
			if(replaceContent(lineTxt,playId)){
				result.add(lineTxt);
			}
		}
	}
	
	private static void addTxt(String[] content,HashSet<String> result,String playId) throws IOException{
		if(content == null || content.length == 0){
			return;
		}
		String lineTxt;
		for(int i=0;i<content.length;i++){
			lineTxt = content[i].trim();
			if(replaceContent(lineTxt,playId)){
				result.add(lineTxt);
			}
		}
	}
	
	private static Boolean replaceContent(String lineTxt,String playId){
		String[] parms = initParameter(playId);
		boolean lenght = lineTxt.length()==Integer.parseInt(parms[0]);
		boolean content = lenght && checkContent(lineTxt,parms[1]);
		boolean fillCount = content && (StringUtils.isNotBlank(parms[2])?checkFillCount(lineTxt,parms[2]):true);
		return fillCount;
	}
	
	private static boolean checkFillCount(String lineTxt,String fill){
		int i=0;
		for(char c : lineTxt.toCharArray()){
			if(fill.contains(String.valueOf(c))){
				++i;
			}
		}
		return i==5;
	}
	
	private static boolean checkContent(String lineTxt,String content){
		for(char c : lineTxt.toCharArray()){
			if(!content.contains(String.valueOf(c))){
				return false;
			}
		}
		return true;
	}
	
	private static String[] initParameter(String playId){
		String[] parms = new String[3];
		if(Constants.PLAY_24_ZC_14.equals(playId)){
			parms[0]="14";
		}else if(Constants.PLAY_25_ZC_R9.equals(playId)){
			parms[0]="14";
			parms[2]="*";
		}else if(Constants.PLAY_26_ZC_BQ.equals(playId)){
			parms[0]="12";
		}else if(Constants.PLAY_27_ZC_JQ.equals(playId)){
			parms[0]="8";
		}else{
			throw new RuntimeException("暂不支持此玩法！");
		}
		String options=PlayType.getOptionByPlayId(playId);
		parms[1]=StringUtils.isBlank(parms[2])?options:options+","+parms[2];
		return parms;
	}
}
