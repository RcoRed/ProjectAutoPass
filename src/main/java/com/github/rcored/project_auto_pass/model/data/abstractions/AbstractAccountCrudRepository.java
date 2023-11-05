package com.github.rcored.project_auto_pass.model.data.abstractions;

import com.github.rcored.project_auto_pass.model.entities.Account;
import com.github.rcored.project_auto_pass.model.entities.Group;

import java.util.Optional;

public interface AbstractAccountCrudRepository {

    Account create(Group group, Account account);

    Optional<Account> readById(Group group, int id);
    //Iterable<Account> read(String name);

    Account update(Group group, Account account);

    void deleteById(Group group, int id);

}
