package com.github.rcored.project_auto_pass.security.encryption.abstractions;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.security.InvalidKeyException;

/** Implement Argon2 hashing by Argon2
 * @author Marco Martucci
 * @version 0.1.0
 * */
public interface AbstractEncryption {

    /** Use this method to encrypt a text
     * @param plainString the text that will be encrypted.
     * @param privateKey the key that will be used to encrypt the text.
     * @return The encrypted string version of plainString.
     */
    String encrypt(String plainString, String privateKey) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException;

    /** Use this method to hash a text
     * @param encryptedString the text that will be decrypted.
     * @param privateKey the key that will be used to decrypt the text.
     * @return The plain string version of encryptedString.
     */
    String decrypt(String encryptedString, String privateKey) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException;
}
