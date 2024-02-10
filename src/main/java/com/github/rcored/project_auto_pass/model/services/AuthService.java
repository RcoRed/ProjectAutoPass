package com.github.rcored.project_auto_pass.model.services;

import com.github.rcored.project_auto_pass.security.hashing.abstractions.AbstractHashing;

import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class AuthService {
    private AbstractHashing hashing;

    private static Scanner console = new Scanner(System.in);

    public AuthService(AbstractHashing hashing) {
        this.hashing = hashing;
    }

    public String login(){
        System.out.println("inserisci la password");
        String pass = console.nextLine();

        try {
            return hashing.hash(pass);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("erroreeeeeeeeeeeeeeeeeeeeeeeeeee" + e);
        }

    }


}
