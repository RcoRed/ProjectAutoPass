package com.github.rcored.project_auto_pass.security.hashing.implementations;

import com.github.rcored.project_auto_pass.security.hashing.abstractions.AbstractHashing;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import static com.github.rcored.project_auto_pass.security.SecurityTestCostants.*;
import static org.junit.jupiter.api.Assertions.*;

class HashingJavaSHA3Test {

    AbstractHashing hashing;

    @BeforeEach
    void setUp() throws NoSuchAlgorithmException {
        hashing = new HashingJavaSHA3();
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void hash() {
        try {
            byte[] hashedByte = hashing.hash(PLAIN_KEY);

            assertArrayEquals(MessageDigest.getInstance(SHA3_256_HASH).digest(PLAIN_KEY.getBytes()), hashedByte);
        } catch (NoSuchAlgorithmException e) {
            fail(e.getMessage(),e.getCause());
        }
    }

    @Test
    void verify() {
        try {
            byte[] hashedByte = MessageDigest.getInstance(SHA3_256_HASH).digest(PLAIN_KEY.getBytes());

            assertTrue(hashing.verify(hashedByte,PLAIN_KEY));
        } catch (NoSuchAlgorithmException e) {
            fail(e.getMessage(),e.getCause());
        }
    }

    @Test
    void verify_should_not_be_verified() {
        try {
            byte[] hashedByte = MessageDigest.getInstance(SHA3_256_HASH).digest(PLAIN_KEY.getBytes());

            assertFalse(hashing.verify(hashedByte,PLAIN_KEY2));
        } catch (NoSuchAlgorithmException e) {
            fail(e.getMessage(),e.getCause());
        }
    }
}