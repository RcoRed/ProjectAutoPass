package com.github.rcored.project_auto_pass.security.encryption.implementations;

import com.github.rcored.project_auto_pass.security.encryption.abstractions.AbstractEncryption;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

/** Implement Argon2 hashing by Argon2
 * @author Marco Martucci
 * @version 0.1.0
 * */
public class EncryptionAES implements AbstractEncryption {

    /** Represents encryption method that Java will use */
    private String encryptionMethod;
    /** Represents cipher that will be used */
    private Cipher cipher;

    /** Create EncryptionAES (constructor) */
    public EncryptionAES() throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException {
        this.encryptionMethod = "AES/CTR/PKCS5Padding";
        this.cipher = Cipher.getInstance(encryptionMethod,"BC");
    }

    /** Create EncryptionAES (constructor)
     * @param fullEncryptionMethod Represents encryption method that Java will use.
     * */
    public EncryptionAES(String fullEncryptionMethod) throws NoSuchPaddingException, NoSuchAlgorithmException {
        this.encryptionMethod = fullEncryptionMethod;
        this.cipher = Cipher.getInstance(encryptionMethod);
    }

    /** Create EncryptionAES (constructor)
     * @param block Represents block that will be used to reconstruct the full method that Java will use.
     * @param padding Represents padding that will be used to reconstruct the full method that Java will use.
     * */
    public EncryptionAES(String block, String padding) throws NoSuchPaddingException, NoSuchAlgorithmException {
        //recreation of the full encryption method
        this("AES/" + (block == null ? "CTR" : block) + "/" + (padding == null ? "ISO/IEC-9797-1" : padding));
    }

    /**
     * Use this method to encrypt a text
     *
     * @param plainString the text that will be encrypted.
     * @param privateKey the key that will be used to encrypt the text.
     * @return The encrypted string version of plainString.
     */
    @Override
    public byte[] encrypt(String plainString, byte[] privateKey) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        SecretKeySpec secretKey = new SecretKeySpec(privateKey, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(plainString.getBytes());
    }

    /**
     * Use this method to hash a text
     *
     * @param encryptedByte the byte[] text that will be decrypted.
     * @param privateKey the byte[] key (best if 256 bit) that will be used to decrypt the text.
     * @param iV the IV used by the cipher to encrypt the plain string.
     * @return The plain string version of encryptedString.
     */
    @Override
    public String decrypt(byte[] encryptedByte, byte[] privateKey, byte[] iV) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        SecretKeySpec secretKey = new SecretKeySpec(privateKey, "AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iV));
        return new String(cipher.doFinal(encryptedByte));
    }

    /** Use this method to take the cipher
     * @return The cipher.
     */
    @Override
    public Cipher getCipher() {
        return this.cipher;
    }
}
