package com.github.rcored.project_auto_pass.model.services.implementations;

import com.github.rcored.project_auto_pass.model.data.abstractions.AbstractGroupCrudRepository;
import com.github.rcored.project_auto_pass.model.data.exceptions.BusinessLogicException;
import com.github.rcored.project_auto_pass.model.data.exceptions.DataException;
import com.github.rcored.project_auto_pass.model.data.exceptions.EntityNotFoundException;
import com.github.rcored.project_auto_pass.model.entities.Account;
import com.github.rcored.project_auto_pass.model.entities.Group;
import com.github.rcored.project_auto_pass.model.services.abstractions.AbstractAccountService;
import com.github.rcored.project_auto_pass.model.services.abstractions.AbstractGroupService;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static com.github.rcored.project_auto_pass.model.data.Constants.GROUP_DIR_PATH;
import static com.github.rcored.project_auto_pass.model.data.Constants.JSON_TYPE;

public class CrudService implements AbstractGroupService, AbstractAccountService {

    /**
     *
     *
     *
     *
     *
     *
     *
     *
     *  BusinessLogicException  define a business exception by its type.
     *                          type 1 = the group file already exist, so u can't create the group with that groupNameId.
     *                          type 2 = the groupNameId can't contain a '.' (a dot)
     *                          type 3 = the group file does not exist
     *                          type 4 = the accountId key does not exist in the accounts map of the group
    */
    private AbstractGroupCrudRepository repo;

    public CrudService(AbstractGroupCrudRepository repo) {
        this.repo = repo;
    }

    //GROUP CRUD

    @Override
    public Group createGroup(Group group) throws DataException, BusinessLogicException {
        if (groupNameFileExist(group.getGroupNameId())){
            throw new BusinessLogicException(1,"Error the GROUP file already exist");
        }
        if (group.getGroupNameId().indexOf('.') == -1){
            throw new BusinessLogicException(2,"Error the NAME of the GROUP can't contain '.' (a dot)");
        }
        return repo.create(group);
    }

    @Override
    public Optional<Group> readGroupByNameId(String groupNameId) throws EntityNotFoundException, DataException {
        //Check if the groupNameId it's the full file name (groupNameId.json), and proceed to cancel .json from the groupNameId
        if (groupNameId.contains(JSON_TYPE)){
            groupNameId = groupNameId.replaceAll(JSON_TYPE,"");
        }
        return repo.readByNameId(groupNameId);
    }

    @Override
    public Group updateGroup(Group oldGroup, Group newGroup) throws BusinessLogicException, DataException {
        if (!groupNameFileExist(oldGroup.getGroupNameId())){
            throw new BusinessLogicException(3,"Error the GROUP file does not exist");
        }
        if (oldGroup.equals(newGroup)){     //The oldGroup and the newGroup are the same
            return oldGroup;
        }
        if (oldGroup.getGroupNameId().equals(newGroup.getGroupNameId())){   //The name of the group didn't change
            return repo.update(newGroup);
        }
        //If the name of the newGroup changed I need to delete the oldGroup file
        repo.update(newGroup);
        deleteGroupByNameId(oldGroup.getGroupNameId());
        return newGroup;
    }

    @Override
    public boolean deleteGroupByNameId(String groupNameId) {
        return repo.deleteByNameId(groupNameId);
    }

    @Override
    public Iterable<Group> findAllGroup() throws EntityNotFoundException, DataException {
        File[] files = new File(GROUP_DIR_PATH).listFiles();
        Collection<Group> groupList = new ArrayList<>();
        if (files != null){
            for (File file : files){
                groupList.add(readGroupByNameId((file.getName())).orElseThrow());
            }
        }
        return groupList;
    }

    @Override
    public boolean groupNameFileExist(String groupNameId) {
        return repo.groupNameFileExist(groupNameId);
    }

    //ACCOUNT CRUD

    @Override
    public Account createAccount(Group group, Account account) throws DataException, BusinessLogicException {
        if (!groupNameFileExist(group.getGroupNameId())){
            throw new BusinessLogicException(3,"Error the GROUP file does not exist");
        }
        account.setId(group.firstAvailableAccountId());
        group.getAccounts().put(account.getId(),account);
        repo.create(group);
        return account;
    }

    @Override//non so quanto senso abbia questo metodo dato che se gli sto passando il gruppo e l'id del account posso fare direttamente il .get() sulla mappa
    public Optional<Account> readAccountById(Group group, int accountId) throws BusinessLogicException{
        if (!groupNameFileExist(group.getGroupNameId())){
            throw new BusinessLogicException(3,"Error the GROUP file does not exist");
        }
        return Optional.ofNullable(group.getAccounts().get(accountId));
    }

    @Override
    public Account updateAccount(Group group, Account account) throws EntityNotFoundException, DataException, BusinessLogicException{
        if (!groupNameFileExist(group.getGroupNameId())){
            throw new BusinessLogicException(3,"Error the GROUP file does not exist");
        }
        if (group.getAccounts().get(account.getId()).equals(account)){  //If the accounts are the same
            return account;
        }
        group.getAccounts().put(account.getId(),account);
        repo.update(group);
        return account;
    }

    @Override
    public boolean deleteAccountById(Group group, int accountId) throws EntityNotFoundException, DataException, BusinessLogicException{
        if (!groupNameFileExist(group.getGroupNameId())){
            throw new BusinessLogicException(3,"Error the GROUP file does not exist");
        }
        Account account = group.getAccounts().get(accountId);
        if (account == null){
            throw new BusinessLogicException(4,"Error the accountId key does not exist in the accounts map of the group");
        }
        group.getAccounts().remove(accountId);
        repo.update(group);
        return true;
    }
}
