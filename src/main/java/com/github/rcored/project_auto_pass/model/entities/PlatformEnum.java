package com.github.rcored.project_auto_pass.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@AllArgsConstructor
//@Setter
@Getter
public enum PlatformEnum {
//    DEFAULT( (account) -> System.out.println("connect"),(oldAccount,newAccount) -> System.out.println("update?"))
//    //GOOGLE("GOOGLE", () -> System.out.println("connettendo a google")),
//    //META("META", () -> System.out.println("connettendo a meta")),
//    //TWITTER("TWITTER", () -> System.out.println("connettendo a twitter"))
//    ;
//    //FunctionalInterfaces
//    private final Consumer<Account> connect;
//    private final BiConsumer<Account, Account> update;
//
//    public void connect(Account account) {
//        connect.accept(account);
//    }
//
//    public void update(Account oldAccount, Account newAccount) {
//        update.accept(oldAccount,newAccount);
//    }
//
//    static PlatformEnum getPlatformFromName(String platformName){
//        return Arrays.stream(PlatformEnum.values()).filter(p -> p.name().equals(platformName)).findFirst().orElse(PlatformEnum.DEFAULT);
//    }

}