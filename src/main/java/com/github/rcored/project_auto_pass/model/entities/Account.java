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
    /** Represent the username linked to this Account */
    private String username;
    /** Represent the platform linked to this Account */
    private Platform platform;  //Platform associata a questo account

    /** Create the Account (constructor)
     * @param name the name of the Account that will be displayed by the client.
     * @param email the email linked to this Account.
     * @param password the password linked to this Account.
     * @param platform the platform linked to this Account.
     */
    public Account(String name, String email, String password, Platform platform) {
        this(name,email,password,null,platform);
    }

    /** Create the Account (constructor)
     * @param name the name of the Account that will be displayed by the client.
     * @param email the email linked to this Account.
     * @param password the password linked to this Account.
     * @param username the username linked to this Account.
     * @param platform the platform linked to this Account.
     */
    public Account(String name, String email, String password, String username, Platform platform) {
        this.name = name;
        this.platform = platform;
        this.email = email;
        this.password = password;
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account account)) return false;
        return getId() == account.getId() && Objects.equals(getName(), account.getName()) && Objects.equals(getEmail(), account.getEmail()) && Objects.equals(getPassword(), account.getPassword()) && Objects.equals(getUsername(), account.getUsername()) && Objects.equals(getPlatform(), account.getPlatform());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getEmail(), getPassword(), getUsername(), getPlatform());
    }
}
