package com.github.rcored.project_auto_pass.security.encryption.implementations;

import com.github.rcored.project_auto_pass.security.encryption.abstractions.AbstractEncryption;
import lombok.Getter;
import lombok.Setter;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Arrays;

/** Implement Argon2 hashing by Argon2
 * @author Marco Martucci
 * @version 0.1.0
 * */
@Getter
public class EncryptionAES implements AbstractEncryption {

    /** Represents encryption method that Java will use */
    private String encryptionMethod;
    /** Represents cipher that will be used */
    private Cipher cipher;
    private static final SecureRandom random = new SecureRandom();

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
    public byte[] encrypt(String plainString, byte[] privateKey) throws SecurityException {
        SecretKeySpec secretKey = new SecretKeySpec(privateKey, "AES");
        byte[] iv = new byte[16];
        random.nextBytes(iv);
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));
            return cipher.doFinal(plainString.getBytes());
        } catch (InvalidKeyException e) {
            throw new SecurityException("Error the key " + secretKey.getAlgorithm() + Arrays.toString(privateKey) +  " was not found." + e.getMessage(), e.getCause());
        } catch (InvalidAlgorithmParameterException e) {
            throw new SecurityException("Error the algorithm " + cipher.getAlgorithm() + " was not found." + e.getMessage(), e.getCause());
        } catch (IllegalBlockSizeException e) {
            throw new SecurityException("Error the block " + cipher.getAlgorithm() + " was not found." + e.getMessage(), e.getCause());
        } catch (BadPaddingException e) {
            throw new SecurityException("Error the padding " + cipher.getAlgorithm() + " was not found." + e.getMessage(), e.getCause());
        }
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
    public String decrypt(byte[] encryptedByte, byte[] privateKey, byte[] iV) throws SecurityException {
        SecretKeySpec secretKey = new SecretKeySpec(privateKey, "AES");
        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iV));
            return new String(cipher.doFinal(encryptedByte));
        } catch (InvalidKeyException e) {
            throw new SecurityException("Error the key " + secretKey.getAlgorithm() + Arrays.toString(privateKey) +  " is not valid." + e.getMessage(), e.getCause());
        } catch (InvalidAlgorithmParameterException e) {
            throw new SecurityException("Error the algorithm " + cipher.getAlgorithm() + " was not found." + e.getMessage(), e.getCause());
        } catch (IllegalBlockSizeException e) {
            throw new SecurityException("Error the block " + cipher.getAlgorithm() + " was not found." + e.getMessage(), e.getCause());
        } catch (BadPaddingException e) {
            throw new SecurityException("Error the padding " + cipher.getAlgorithm() + " was not found." + e.getMessage(), e.getCause());
        }

    }
}
