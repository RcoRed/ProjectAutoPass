package com.github.rcored.project_auto_pass.model.data.abstractions;


import com.github.rcored.project_auto_pass.model.entities.Account;

public interface AbstractPlatform {

    default void connect(Account account){
        System.out.println("Connected!");
    }

    default void update(Account oldAccount, Account newAccount){
        System.out.println("Updated?");
    }
}
