package com.github.rcored.project_auto_pass.model.entities;

import lombok.*;


@NoArgsConstructor
@Setter
@Getter
@ToString
public class Account {

    private int id;
    //nome da visualizzare
    private String name;
    //nome della piattaforma a cui è associato questo account
    private String platformName;
    //attenzione
    private Platform platform;
    private String email;
    private String password;

    public Account(int id, String name, String platformName, String email, String password) {
        this.id = id;
        this.name = name;
        this.platformName = platformName;
        this.platform = Platform.getPlatformFromName(platformName);
        this.email = email;
        this.password = password;
    }
}
