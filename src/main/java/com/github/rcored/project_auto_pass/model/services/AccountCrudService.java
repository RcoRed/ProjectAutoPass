package com.github.rcored.project_auto_pass.model.services;

import com.github.rcored.project_auto_pass.model.data.abstractions.AbstractAccountCrudRepository;
import com.github.rcored.project_auto_pass.model.entities.Account;
import com.github.rcored.project_auto_pass.model.entities.Group;

import java.util.AbstractCollection;
import java.util.Optional;

public class AccountCrudService {
    private AbstractAccountCrudRepository accountRepo;

    public AccountCrudService(AbstractAccountCrudRepository accountRepo) {
        this.accountRepo = accountRepo;
    }

    public Account create(Group group, Account account){
        return accountRepo.create(group,account);
    }

    public Optional<Account> readById(Group group, int accountId){
        return accountRepo.readById(group, accountId);
    }

    public void deleteById(Group group, int accountId){
        accountRepo.deleteById(group,accountId);
    }
}
