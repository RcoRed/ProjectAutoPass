package com.github.rcored.project_auto_pass.model.services;

import com.github.rcored.project_auto_pass.model.data.abstractions.AbstractAccountCrudRepository;
import com.github.rcored.project_auto_pass.model.entities.Account;
import com.github.rcored.project_auto_pass.model.entities.Group;

public class AccountCrudService {
    private AbstractAccountCrudRepository accountRepo;

    public AccountCrudService(AbstractAccountCrudRepository accountRepo) {
        this.accountRepo = accountRepo;
    }

    public Account createAccount(Group group, Account account){
        return accountRepo.create(group,account);
    }
}
