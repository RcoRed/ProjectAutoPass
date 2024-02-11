package com.github.rcored.project_auto_pass.security.encryption.implementations;

import com.github.rcored.project_auto_pass.security.encryption.abstractions.AbstractEncryption;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;

import static com.github.rcored.project_auto_pass.security.SecurityTestCostants.*;
import static org.junit.jupiter.api.Assertions.*;

class EncryptionAESTest {

    AbstractEncryption encryption;
    byte[] byteKey;

    @BeforeEach
    void setUp() throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException {
        Security.addProvider(new BouncyCastleProvider());
        byteKey = MessageDigest.getInstance("SHA3-256").digest(PLAIN_KEY.getBytes());
        encryption = new EncryptionAES();
    }

    @AfterEach
    void tearDown() {
        Security.removeProvider("BC");
    }

    @Test
    void encrypt() {
        try {

            byteKey = MessageDigest.getInstance("SHA3-256").digest(PLAIN_KEY.getBytes());

            byte[] encryptedByte = encryption.encrypt(PLAIN_TEXT, byteKey);

            Cipher cipher = Cipher.getInstance("AES/CTR/PKCS5Padding","BC");
            SecretKeySpec secretKey = new SecretKeySpec(byteKey, "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(encryption.getCipher().getIV()));
            String decryptedText = new String(cipher.doFinal(encryptedByte));

            assertEquals(PLAIN_TEXT,decryptedText);

        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException |
                 BadPaddingException | IllegalBlockSizeException | InvalidAlgorithmParameterException e) {
            fail(e.getMessage(),e.getCause());
        }
    }

    @Test
    void decrypt() {


        try {
            Cipher cipher = Cipher.getInstance("AES/CTR/PKCS5Padding","BC");
            SecretKeySpec secretKey = new SecretKeySpec(byteKey, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedByte = cipher.doFinal(PLAIN_TEXT.getBytes());

            String decryptedString = encryption.decrypt(encryptedByte, byteKey, cipher.getIV());

            assertEquals(PLAIN_TEXT, decryptedString);

        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | NoSuchProviderException |
                 IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException e) {
            fail(e.getMessage(),e.getCause());
        }


    }
}