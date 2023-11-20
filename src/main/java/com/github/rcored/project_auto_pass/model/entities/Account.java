package com.github.rcored.project_auto_pass.model.entities;

import com.github.rcored.project_auto_pass.model.entities.platforms.Platform;
import lombok.*;

import java.util.Objects;

/** Represent an Account
 * @author Marco Martucci
 * @version 0.1.0
 * */
@Setter
@Getter
@ToString
public class Account {
    /** Represent the id of the Account */
    private int id;
    /** Represent the name of the Account that will be displayed to the client */
    private String name;
    /** Represent the email linked to this Account */
    private String email;
    /** Represent the password linked to this Account */
    private String password;
    /** Represent the platform linked to this Account */
    private Platform platform;  //Platform associata a questo account

    /** Create the Account (constructor)
     * @param name the name of the Account that will be displayed by the client
     * @param email the email linked to this Account.
     * @param password the password linked to this Account
     * @param platform the platform linked to this Account
     */
    public Account(String name, String email, String password,Platform platform) {
        this.name = name;
        this.platform = platform;
        this.email = email;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return getId() == account.getId() && getName().equals(account.getName()) && getEmail().equals(account.getEmail()) && getPassword().equals(account.getPassword()) && getPlatform().equals(account.getPlatform());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getEmail(), getPassword(), getPlatform());
    }
}
