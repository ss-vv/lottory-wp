package com.xhcms.lottery.lang;

public final class SchemeDisplayMode {

	public static int getDisplayMode(int schemeType, int isShowScheme, int isPublishShow) {
		int displayMode = -1;
		
		if ((isShowScheme == EntityType.SHOW_SCHEME || isPublishShow == EntityType.SHOW_SCHEME) 
				&& schemeType == 0) {
			displayMode = EntityType.DISPLAY_SHOW;
		} else if (schemeType == 0 || schemeType == 2) {
			displayMode = EntityType.DISPLAY_ALONE;
		} else if (schemeType == 1) {
			displayMode = EntityType.DISPLAY_GROUPBUY;
		}
		return displayMode;
	}

}