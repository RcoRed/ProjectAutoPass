package com.github.rcored.project_auto_pass.security.hashing.implementations;

import com.github.rcored.project_auto_pass.security.hashing.abstractions.AbstractHashing;
import lombok.Getter;
import lombok.Setter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/** Implement SHA3_256 hashing by Java
 * @author Marco Martucci
 * @version 0.1.0
 * */
@Getter
@Setter
public class HashingJavaSHA3_256 implements AbstractHashing {
    /** Represents hash method that Java will use */
    String hashMethod;

    /** Create HashingJavaSHA3_256 (constructor) */
    private HashingJavaSHA3_256() {
        this.hashMethod = "SHA3-256";
    }
    /** Create HashingJavaSHA3_256 (constructor)
     * @param hashMethod Represents hash method that Java will use.
     * */
    private HashingJavaSHA3_256(String hashMethod) {
        this.hashMethod = hashMethod;
    }


    /**
     * Use this method to hash a text
     *
     * @param plainString the text that will be hashed.
     * @return The hashed string of the plainString.
     */
    @Override
    public String hash(String plainString) throws NoSuchAlgorithmException {
        return Arrays.toString(MessageDigest.getInstance(hashMethod).digest(plainString.getBytes()));
    }

    /**
     * Use this method verify the plain text with the hashed text
     *
     * @param hashedString the hashed text.
     * @param plainString  the plain text that you want to verify.
     * @return TRUE if they are equals, FALSE otherwise.
     */
    @Override
    public boolean verify(String hashedString, String plainString) throws NoSuchAlgorithmException {
        return Arrays.toString(MessageDigest.getInstance(hashMethod).digest(plainString.getBytes())).equals(hashedString);
    }
}
