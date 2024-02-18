package com.github.rcored.project_auto_pass.security.encryption.abstractions;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;

/** Implement Argon2 hashing by Argon2
 * @author Marco Martucci
 * @version 0.1.0
 * */
public interface AbstractEncryption {

    /**
     * Use this method to encrypt a text
     *
     * @param plainString the text that will be encrypted.
     * @param privateKey the key that will be used to encrypt the text.
     * @return The encrypted string version of plainString.
     */
    byte[] encrypt(String plainString, byte[] privateKey) throws SecurityException;

    /**
     * Use this method to hash a text
     *
     * @param encryptedByte the byte[] text that will be decrypted.
     * @param privateKey the byte[] key (best if 256 bit) that will be used to decrypt the text.
     * @param iV the IV used by the cipher to encrypt the plain string.
     * @return The plain string version of encryptedString.
     */
    String decrypt(byte[] encryptedByte, byte[] privateKey, byte[] iV) throws SecurityException;

    /** Use this method to take the cipher
     * @return The cipher.
     */
    Cipher getCipher();
}
