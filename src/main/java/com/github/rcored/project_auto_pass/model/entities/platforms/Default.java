package com.github.rcored.project_auto_pass.model.entities.platforms;

import com.github.rcored.project_auto_pass.model.entities.Account;
import lombok.Getter;

public class Default extends Platform{
    @Getter
    private static final Default DEFAULT;

    //blocco inizializzazione statico
    static {
        DEFAULT = new Default();    //creo default
    }

    private Default() {
        super(1,"default");
    }

    @Override
    public void connect(Account account) {
        System.out.println("Connected!");
    }

    @Override
    public void update(Account oldAccount, Account newAccount) {
        System.out.println("Updated?");
    }
}
//Singleton