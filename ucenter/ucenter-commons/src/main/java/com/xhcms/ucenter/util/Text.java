package com.xhcms.ucenter.util;


public class Text {

    /**
     * 获得字符串长度，1个中文字符算2个字符
     * 
     * @param str
     * @return
     */
    public static int getLength(String str) {
        int len = 0;
        for (int i = 0; i < str.length(); i++) {
            len = len + ((str.charAt(i) >= 0x4e00 && str.charAt(i) <= 0x9fa5) ? 2 : 1);
        }
        return len;
    }
}
