package com.github.rcored.project_auto_pass.model.entities;

import com.github.rcored.project_auto_pass.model.entities.platforms.Platform;
import lombok.*;


@NoArgsConstructor
@Setter
@Getter
@ToString
public class Account {
    private int id;
    private String name;    //nome da visualizzare
    //attenzione
    private Platform platform;  //Platform associata a questo account
    private String email;
    private String password;

    public Account(int id, String name, Platform platform, String email, String password) {
        this.id = id;
        this.name = name;
        this.platform = platform;
        this.email = email;
        this.password = password;
    }
}
