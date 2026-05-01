package com.java.poc.java_basics.encryption;

import javax.crypto.Cipher;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class RSAEncryptionDemo {
    static String plainText = "test123";

    public static void main(String[] args) {
        try {
            // Get an instance of the RSA key generator
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(4096);

            // Generate the KeyPair
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            // Get the public and private key
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            System.out.println("Original Text  : " + plainText);


            // Encryption
            byte[] cipherTextArray = do_RSAEncryption(plainText, publicKey);
            String encryptedText = Base64.getEncoder().encodeToString(cipherTextArray);
            System.out.println("Encrypted Text : " + encryptedText);

            // Decryption
            String decryptedText = do_RSADecryption(cipherTextArray, privateKey);
            System.out.println("DeCrypted Text : " + decryptedText);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] do_RSAEncryption(String plainText, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");

        //Initialize Cipher for ENCRYPT_MODE
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        //Perform Encryption
        byte[] cipherText = cipher.doFinal(plainText.getBytes());

        return cipherText;
    }

    public static String do_RSADecryption(byte[] cipherTextArray, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");

        //Initialize Cipher for DECRYPT_MODE
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        //Perform Decryption
        byte[] decryptedTextArray = cipher.doFinal(cipherTextArray);

        return new String(decryptedTextArray);
    }
}

