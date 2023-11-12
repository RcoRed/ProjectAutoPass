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
    private String email;
    private String password;
    private Platform platform;  //Platform associata a questo account

    public Account(String name, String email, String password,Platform platform) {
        this.name = name;
        this.platform = platform;
        this.email = email;
        this.password = password;
    }
}
