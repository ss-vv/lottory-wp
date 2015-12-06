package com.unison.lottery.api.util;

import com.unison.lottery.api.model.SchemePrivacy;

public class SchemePrivacyUtil {

	public SchemePrivacy showTypeTranslateToSchemePrivacy(int showTypeNumber) {
		int followSchemePrivacy=-1;
		int privacy=-1;
		switch(showTypeNumber){
			case 0:{followSchemePrivacy=0;privacy=0;break;}
			case 2:{followSchemePrivacy=2;break;}
			case 3:{followSchemePrivacy=3;break;}
			case 5:{privacy=2;break;}
			case 6:{privacy=3;break;}
			default:{break;}
		}
		SchemePrivacy result=new SchemePrivacy();
		result.setFollowSchemePrivacy(followSchemePrivacy);
		result.setPrivacy(privacy);
		return result;
	}

}
