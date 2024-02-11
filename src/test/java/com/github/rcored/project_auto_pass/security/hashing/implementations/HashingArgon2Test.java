package com.github.rcored.project_auto_pass.security.hashing.implementations;

import com.github.rcored.project_auto_pass.security.hashing.abstractions.AbstractHashing;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.github.rcored.project_auto_pass.security.SecurityTestCostants.*;
import static org.junit.jupiter.api.Assertions.*;

class HashingArgon2Test {

    AbstractHashing hashing;

    @BeforeEach
    void setUp() throws NoSuchAlgorithmException {
        hashing = new HashingArgon2();
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void hash() {
        try {
            byte[] hashedByte = hashing.hash(PLAIN_KEY);

            Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id,ARGON2_MIN_SALT,ARGON2_MIN_HASH);

            assertTrue(argon2.verify(new String(hashedByte),PLAIN_KEY.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            fail(e.getMessage(),e.getCause());
        }
    }

    @Test
    void verify() {
        try {
            Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id,ARGON2_MIN_SALT,ARGON2_MIN_HASH);
            String hashedKey = argon2.hash(ARGON2_ITERATION,ARGON2_MEMORY,ARGON2_PARALLELISM,PLAIN_KEY.getBytes());

            assertTrue(hashing.verify(hashedKey.getBytes(),PLAIN_KEY));
        } catch (NoSuchAlgorithmException e) {
            fail(e.getMessage(),e.getCause());
        }
    }

    @Test
    void verify_should_not_be_verified() {
        try {
            Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id,ARGON2_MIN_SALT,ARGON2_MIN_HASH);
            String hashedKey = argon2.hash(ARGON2_ITERATION,ARGON2_MEMORY,ARGON2_PARALLELISM,PLAIN_KEY.getBytes());

            assertFalse(hashing.verify(hashedKey.getBytes(),PLAIN_KEY2));
        } catch (NoSuchAlgorithmException e) {
            fail(e.getMessage(),e.getCause());
        }
    }
}