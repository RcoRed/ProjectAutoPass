package com.github.rcored.project_auto_pass.security.hashing.abstractions;

import java.security.NoSuchAlgorithmException;

public interface AbstractHashing {

    /** Use this method to hash a text
     * @param plainString the text that will be hashed.
     * @return The hashed string of the plainString.
     */
    String hash(String plainString) throws NoSuchAlgorithmException;

    /** Use this method verify the plain text with the hashed text
     * @param plainString the plain text that you want to verify.
     * @param hashedString the hashed text.
     * @return TRUE if they are equals, FALSE otherwise.
     */
    boolean verify(String hashedString, String plainString) throws NoSuchAlgorithmException;
}
