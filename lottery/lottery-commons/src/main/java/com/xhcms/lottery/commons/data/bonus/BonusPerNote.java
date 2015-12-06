package com.xhcms.lottery.commons.data.bonus;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class BonusPerNote {
	
	private float firstBonus;//一等奖
	public float getFirstBonus() {
		return firstBonus;
	}

	public void setFirstBonus(float firstBonus) {
		this.firstBonus = firstBonus;
	}

	public float getSecondBonus() {
		return secondBonus;
	}

	public void setSecondBonus(float secondBonus) {
		this.secondBonus = secondBonus;
	}

	private float secondBonus;//二等奖

	public static BonusPerNote parse(String str) {
		if(StringUtils.isNotBlank(str)){
			String[] splits = str.split(";");
			if(null!=splits){
				BonusPerNote bonusPerNote=new BonusPerNote();
				if(splits.length==1){
					bonusPerNote.setFirstBonus(Float.parseFloat(splits[0]));
					
				}
				else if(splits.length==2){
					bonusPerNote.setFirstBonus(Float.parseFloat(splits[0]));
					bonusPerNote.setSecondBonus(Float.parseFloat(splits[1]));
				}
				return bonusPerNote;
			}
		}
		return null;
	}
	
	public String toString(){
		return ReflectionToStringBuilder.toString(this,ToStringStyle.MULTI_LINE_STYLE);
	}

}
