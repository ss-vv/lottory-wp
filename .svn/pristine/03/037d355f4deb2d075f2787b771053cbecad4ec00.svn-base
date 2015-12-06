package com.xhcms.lottery.commons.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MakeQiutanURLKeyUtil {
	
    public static String makeClientKey(String msg, String baseinfoversion,
                                 String client, String transcode, String version) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        Object[] objects = new Object[] {transcode, msg, version, Integer.valueOf(client)};
        return a(objects);
    }

    private static String a(Object[] paramArrayOfObject) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        StringBuilder localStringBuilder = new StringBuilder(100);
        int i = paramArrayOfObject.length;
        for (int j = 0; j < i; j++)
            localStringBuilder.append(paramArrayOfObject[j]);
        localStringBuilder.append("china310MD5way");
        return a(localStringBuilder.toString().getBytes("utf-8"));
    }

    private static String a(byte[] bytes) throws NoSuchAlgorithmException {
        return new String(c(b(bytes)));
    }

    private static byte[] b(byte[] paramArrayOfByte) throws NoSuchAlgorithmException {
        try {
            byte[] arrayOfByte = MessageDigest.getInstance("MD5").digest(paramArrayOfByte);
            return arrayOfByte;
        } catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
            throw localNoSuchAlgorithmException;
        }
    }
    
    private static char[] c(byte[] paramArrayOfByte) {
        char[] a = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102 };
        int i = 0;
        int j = paramArrayOfByte.length;
        char[] arrayOfChar = new char[j << 1];
        for (int k = 0; k < j; k++) {
            int m = i + 1;
            arrayOfChar[i] = a[((0xF0 & paramArrayOfByte[k]) >>> 4)];
            i = m + 1;
            arrayOfChar[m] = a[(0xF & paramArrayOfByte[k])];
        }
        return arrayOfChar;
    }
}
