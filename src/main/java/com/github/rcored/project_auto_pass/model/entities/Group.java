package com.github.rcored.project_auto_pass.model.entities;

import lombok.*;

import java.util.Map;
import java.util.Objects;

/** Represent a Group of Accounts
 * @author Marco Martucci
 * @version 0.1.0
 */
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Group {
    /** Represent the id of a group, and also the name of the file json */
    private String groupNameId;
    /** Represent the Accounts linked to this Group.
     *  The key (Integer) it's also the account id */
    private Map<Integer, Account> accounts;
    //private String password;

    /** Gets the first available id (key) in @accounts
     *  @return An int representing the first id that can be associated to an Account
     */
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
