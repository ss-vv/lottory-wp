package com.xhcms.ucenter.util;

import java.io.ByteArrayOutputStream;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.util.Calendar;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;

public class DiscuzAuthCodeUtils {
	public static final String RAND_SEED = "sdfakla@32144sdfsffxaplq6c.";
	
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
		"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	public static String authcodeEncode(String source, String key, int expiry) {  
        return authcode(source, key, DiscuzAuthcodeMode.Encode, expiry);  
  
    }  
	
	public static String authcodeEncode(String source, String key) {  
        return authcode(source, key, DiscuzAuthcodeMode.Encode, 0);  
  
    }  
	
	public static String authcodeDecode(String source, String key) {  
        return authcode(source, key, DiscuzAuthcodeMode.Decode, 0);  
  
	}
	
	public static String generateKey(String appkey, String saltkey) {
		return md5(appkey + saltkey);
	}
	
	public static String generateKey(long uid) {
		return md5(RAND_SEED + uid);
	}
	
	/**
	 * 解密和解密
	 * @param source
	 * @param key
	 * @param operation
	 * @param expiry
	 * @return
	 */
	private static String authcode(String source, String key,  
            DiscuzAuthcodeMode operation, int expiry) {  
        try {  
            if (source == null || key == null) {  
                return "";  
            }  
  
            //随机密码取值长度
            int ckey_length = 4;  
            
            String keya, keyb, keyc, cryptkey;  
            //密钥
            key = md5(key);  
            //密钥用加密和解密
            keya = md5(subString(key, 0, 16));
            //做数据完整性验证
            keyb = md5(subString(key, 16, 16));  
            //用于变化生成的密文
            keyc = ckey_length > 0 ? (operation == DiscuzAuthcodeMode.Decode ? subString(source, 0, ckey_length) :
            	subString(md5(getUnixMacroTime()+""), (-1)*ckey_length)) : "";

            cryptkey = keya + md5(keya + keyc);  
            
            String str = (String.format("%010d", expiry > 0 ? expiry + getUnixTimestamp() : 0)  + subString(md5(source + keyb), 0, 16) + source);
            
            byte[] string = operation == DiscuzAuthcodeMode.Decode ? base64Decode(source.substring(ckey_length)) : 
            	str.getBytes();
            
            
            int[] box = new int[256];
            for(int i = 0; i <= 255; i++) {
            	box[i] = i;
            }
            
            int[] rndkey = new int[256];
            for(int i = 0; i <= 255; i++) {
            	rndkey[i] = (int)cryptkey.charAt(i % cryptkey.length());
            }
            
            for(int j = 0, i = 0; i < 256; i++) {
            	j = (j + box[i] + rndkey[i]) % 256;
            	int tmp = box[i];
            	box[i] = box[j];
            	box[j] = tmp;
            }
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            StringBuffer sb = new StringBuffer();
            for(int a = 0, i = 0, j = 0; i < string.length; i++) {
            	a = (a + 1) % 256;
            	j = (j + box[a]) % 256;
            	int tmp = box[a];
            	box[a] = box[j];
            	box[j] = tmp;
           
            	byte t = (byte)string[i];
            	int k = toInt(t);
            	
            	int mixChar = (k^(box[(box[a] + box[j]) % 256]));
            	
            	baos.write(mixChar);
            	
            	sb.append((char)mixChar);
            }
            
            byte[] byteResult = baos.toByteArray();
            baos.close();
            
            if(operation == DiscuzAuthcodeMode.Decode) {
            	String result = new String(byteResult, "UTF-8");
            	String r = sb.toString();
            	long time = Long.parseLong(subString(r, 0, 10));
            	if((time == 0 || time - getUnixTimestamp() > 0) && 
            			subString(result, 10, 16).equals(subString(md5(subString(result, 26) + keyb), 0, 16))) {
            		return subString(result, 26);
            	} else {
            		return "";
            	}
            } else {
            	return keyc + base64Encode(byteResult).replaceAll("=", "");
            }
        } catch (Exception e) {  
        	e.printStackTrace();
            return "";  
        }
    }  
	

	private static String subString(String str, int startIndex, int length) {  
        if (startIndex >= 0) {  
            if (length < 0) {  
                length = length * -1;  
                if (startIndex - length < 0) {  
                    length = startIndex;  
                    startIndex = 0;  
                } else {  
                    startIndex = startIndex - length;  
                }  
            }
            if (startIndex > str.length()) {  
                return "";  
            }
        } else {  
            if (length < 0) {  
                return "";  
            } else {  
                if (length + startIndex > 0) {  
                    length = length + startIndex;  
                    startIndex = 0;  
                } else {  
                    return "";  
                }
            }  
        }  
  
        if (str.length() - startIndex < length) {  
  
            length = str.length() - startIndex;  
        }  
  
        return str.substring(startIndex, startIndex + length);  
    }  

	
	private static String subString(String str, int startIndex) {  
		if(startIndex >= 0) {
			return subString(str, startIndex, str.length());  
		} else {
			if(startIndex + str.length() <= 0)
				return "";
			int start = str.length() + startIndex - 1;
			return str.substring(start, start + Math.abs(startIndex));
		}
    }  
	
	private static String base64Encode(byte[] source) {
			return new String(Base64.encodeBase64(source));
	}
	
	private static byte[] base64Decode(String base64) {
			return Base64.decodeBase64(base64.getBytes());
	}
	
    public static int toInt(byte b) {  
        return (int) ((b + 256) % 256);  
    }  
    
    public static byte toByte(int b) {
    	return (byte)(b & 0xFF);
    }
    
	public static String randomString(int length) {  
        char[] CharArray = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k',  
                'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',  
                'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };  
        int clens = CharArray.length;  
        String sCode = "";  
        Random random = new Random();  
        for (int i = 0; i < length; i++) {  
            sCode += CharArray[Math.abs(random.nextInt(clens))];  
        }  
        return sCode;  
    } 

	public static long getUnixTimestamp() {  
        Calendar cal = Calendar.getInstance();  
        return cal.getTimeInMillis() / 1000;  
    } 
	
	public static Long getUnixMacroTime() {
        Calendar cal = Calendar.getInstance();  
        return cal.getTimeInMillis();  
	}
	
	public static String md5(String origin) {
		String resultString = null;

		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(resultString
					.getBytes()));
		} catch (Exception ex) {

		}
		return resultString;
	}
	
	public static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	public static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}
	

	public enum DiscuzAuthcodeMode {  
        Encode, 
        Decode
    }
    
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		String testKey = "822828Hpklt7kqAS";
		
		//加密
		//String auth = String.format("%s\t%s", DiscuzAuthCodeUtils.md5("lb11091208"), "123");
		
		//System.out.println(auth);
		//String result = DiscuzAuthCodeUtils.authcodeEncode(auth, testKey, 0);
		
		//解密
		//System.out.println(result);
		//System.out.println(DiscuzAuthCodeUtils.authcodeDecode(result, testKey));
		
		String b =  "d57f2gRTuXixPDQfUeZV82b5jvfrv0IErzXFNCwFNC8C1ChuNeFsjf%2FytKyZD73OBP%2BvysXr49%2FCvYtjLVX2pGM";
		b = URLDecoder.decode(b, "UTF-8");
		System.out.println(b);
		System.out.println(DiscuzAuthCodeUtils.authcodeDecode(b, DiscuzAuthCodeUtils.generateKey(testKey,
				"WUj0Wm7i")));
	}
}
