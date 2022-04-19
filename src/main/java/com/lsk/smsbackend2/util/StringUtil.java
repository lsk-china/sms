package com.lsk.smsbackend2.util;

import java.util.Random;

public final class StringUtil {
    private static final String LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final Random RANDOM = new Random();

    public static boolean containsEmpty(String... strIn) {
        for (String str : strIn) {
            if (str == null || str.equals("")) {
                return true;
            }
        }
        return false;
    }
    public static boolean noEmpty(String... strIn) {
        return !containsEmpty(strIn);
    }
    public static String randomString(int len){
        String result = "";
        for (int i = 0; i < len; i++) {
            int index = RANDOM.nextInt(LETTERS.length());
            result += LETTERS.charAt(index);
        }
        return result;
    }
}
