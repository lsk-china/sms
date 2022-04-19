package com.lsk.smsbackend2.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public final class HashUtil {
    public static String sha256String(String stringIn) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(stringIn.getBytes(StandardCharsets.UTF_8));
            return byte2Hex(messageDigest.digest());
        } catch (Exception e) {
            // 明明这行代码不可能被执行到的说...
            throw new RuntimeException(e);
        }
    }
    private static String byte2Hex(byte[] bytes){
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i=0;i<bytes.length;i++){
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length()==1){
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }
}
