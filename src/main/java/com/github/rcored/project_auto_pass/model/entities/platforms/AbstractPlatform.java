package com.github.rcored.project_auto_pass.model.entities.platforms;

import com.github.rcored.project_auto_pass.model.entities.Account;

public interface AbstractPlatform {

    void connect(Account account);

    void update(Account oldAccount, Account newAccount);
}
