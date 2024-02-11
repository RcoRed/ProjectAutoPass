package com.github.rcored.project_auto_pass.security.hashing.implementations;

import com.github.rcored.project_auto_pass.security.hashing.abstractions.AbstractHashing;
import lombok.Getter;
import lombok.Setter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/** Implement SHA3 (default 256) hashing by Java
 * @author Marco Martucci
 * @version 0.1.0
 * */
@Getter
@Setter
public class HashingJavaSHA3 implements AbstractHashing {
    /** Represents hash method that Java will use */
    String hashMethod;

    /** Create HashingJavaSHA3 (constructor) */
    public HashingJavaSHA3() throws NoSuchAlgorithmException {
        this.hashMethod = "SHA3-256";
    }
    /** Create HashingJavaSHA3 (constructor)
     * @param hashMethod Represents hash method that Java will use.
     * */
    public HashingJavaSHA3(String hashMethod) {
        this.hashMethod = hashMethod;
    }

    /**
     * Use this method to hash a text
     *
     * @param plainString the text that will be hashed.
     * @return The hashed string of plainString.
     */
    @Override
    public byte[] hash(String plainString) throws NoSuchAlgorithmException {
        return MessageDigest.getInstance(hashMethod).digest(plainString.getBytes());
    }

    /** Use this method verify the plain text with the hashed text
     * @param plainString the plain text that you want to verify.
     * @param hashedByte the byte[] hashed text.
     * @return TRUE if they are equals, FALSE otherwise.
     */
    @Override
    public boolean verify(byte[] hashedByte, String plainString) throws NoSuchAlgorithmException {
        return Arrays.equals(MessageDigest.getInstance(hashMethod).digest(plainString.getBytes()), hashedByte);
    }
}
