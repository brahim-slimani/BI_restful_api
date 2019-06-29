package com.slimani.rest_reporting.param.aesEncryption;


import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


public class AESCrypt {
    private static final String ALGORITHM = "AES";
    private static final String KEY = "8Anhf569xzcQKS83";

    public static String encrypt(String value) throws Exception
    {

        Key key = generateKey();
        Cipher c = Cipher.getInstance(AESCrypt.ALGORITHM);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(value.getBytes());
        String encryptedValue = new BASE64Encoder().encode(encVal);
        return encryptedValue;

    }

    public static String decrypt(String value) throws Exception
    {


        Key key = generateKey();
        Cipher c = Cipher.getInstance(AESCrypt.ALGORITHM);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decordedValue = new BASE64Decoder().decodeBuffer(value);
        byte[] decValue = c.doFinal(decordedValue);
        String decryptedValue = new String(decValue);
        return decryptedValue;

    }

    private static Key generateKey() throws Exception
    {
        Key key = new SecretKeySpec(AESCrypt.KEY.getBytes(),AESCrypt.ALGORITHM);
        return key;
    }
}
