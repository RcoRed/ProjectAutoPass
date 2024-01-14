package com.github.rcored.project_auto_pass.model.services.abstractions;

import com.github.rcored.project_auto_pass.model.data.exceptions.BusinessLogicException;
import com.github.rcored.project_auto_pass.model.data.exceptions.DataException;
import com.github.rcored.project_auto_pass.model.data.exceptions.EntityNotFoundException;
import com.github.rcored.project_auto_pass.model.entities.Account;
import com.github.rcored.project_auto_pass.model.entities.Group;

import java.util.Optional;

public interface AbstractAccountService {

    Account createAccount(Group group, Account account) throws DataException, BusinessLogicException;
    Account updateAccount(Group group, Account account) throws EntityNotFoundException, DataException, BusinessLogicException;
    boolean deleteAccountById(Group group, int accountId) throws EntityNotFoundException, DataException, BusinessLogicException;
}
