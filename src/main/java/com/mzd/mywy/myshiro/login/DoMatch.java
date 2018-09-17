package com.mzd.mywy.myshiro.login;

import com.google.common.io.ByteSource;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DoMatch {

    char hexDigits[] = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
            'e', 'f'};

    /**
     * 不加密
     *
     * @param password
     * @param salt
     * @param time
     * @return
     */
    public String Unencrypted(String password, String salt, int time) {
        String returnpasswrod = "";
        returnpasswrod = password + salt;
        return returnpasswrod;
    }

    /**
     * md5加密
     *
     * @param password
     * @param salt
     * @param time
     * @return
     */
    public String Md5(String password, String salt, int time) {
        String returnpasswrod = "";
        returnpasswrod = password + salt;
        for (int i = 0; i < time; i++) {
            returnpasswrod = getmd5(returnpasswrod);
        }
        return returnpasswrod;
    }

    public String  getmd5(String s) {
        try {
            byte[] strTemp = s.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void main(String[] args) {
        DoMatch doMatch=new DoMatch();
        System.out.println(doMatch.Md5("123", "test", 2));
    }
}
