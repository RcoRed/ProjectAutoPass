package com.github.rcored.project_auto_pass.model.entities;

import com.github.rcored.project_auto_pass.model.entities.platforms.Platform;
import lombok.*;

import java.util.Objects;

@Setter
@Getter
@ToString
public class Account {
    private int id;
    private String name;    //nome da visualizzare
    private String email;
    private String password;
    private Platform platform;  //Platform associata a questo account

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
