package com.github.rcored.project_auto_pass.model.entities;

import lombok.*;

import java.util.Map;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Group {
    //id
    private String groupNameId;
    private Map<Integer, Account> accounts;
    //private String password;
    //private boolean favorite;

    public int firstAvailableAccountId(){
        int currentId = 1;
        for (int key : accounts.keySet()){
            if (currentId == key){
                currentId++;
            }else {
                break;
            }
        }
        return currentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return getGroupNameId().equals(group.getGroupNameId()) && getAccounts().equals(group.getAccounts());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGroupNameId(), getAccounts());
    }
}
