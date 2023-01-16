package org.tg.review_examples;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class App1 {
    public static void main(String[] args) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
        String key = "Shoxruh";
        String to_encrypt = "Shoxruh aka temada";

        byte[] KeyData = key.getBytes();
        SecretKeySpec KS = new SecretKeySpec(KeyData, "Blowfish");
        Cipher cipher = Cipher.getInstance("Blowfish");
        String encrypted = null;
        String decrypted = null;

        cipher.init(Cipher.ENCRYPT_MODE, KS);

        byte[] bytes = cipher.doFinal(to_encrypt.getBytes());
        encrypted = new String(Base64.getEncoder().encode(bytes));
        System.out.println(encrypted);

        cipher.init(Cipher.DECRYPT_MODE,KS);
        byte[] decr = cipher.doFinal(Base64.getDecoder().decode(encrypted.getBytes()));
        decrypted=new String(decr);

        System.out.println(decrypted);

    };



}
