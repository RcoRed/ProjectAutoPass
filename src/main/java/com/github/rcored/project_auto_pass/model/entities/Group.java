package com.github.rcored.project_auto_pass.model.entities;

import lombok.*;

import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Group {
    //id
    private String groupNameId;
    private Collection<Account> accounts;
    //private String password;
    //private boolean favorite;

    public int firstAvailableAccountId(){
        int counter = 1;
        for (Account a : accounts){
            if (counter == a.getId()){
                counter++;
            }else {
                break;
            }
        }
        return counter;
    }
}
