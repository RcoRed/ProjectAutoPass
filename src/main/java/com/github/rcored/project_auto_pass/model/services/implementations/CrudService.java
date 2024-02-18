package com.github.rcored.project_auto_pass.model.services.implementations;

/** Implement all the CRUD operation for a Group (and an Account)
 * @author Marco Martucci
 * @version 0.1.0
 * @implNote BusinessLogicException: define a business exception by its type.
 *                 <p>type 1 = the group file already exist, so u can't create the group with that groupNameId.</p>
 *                 <p>type 2 = the groupNameId can't contain a '.' (a dot)</p>
 *                 <p>type 3 = the group file does not exist</p>
 *                 <p>type 4 = the accountId key does not exist in the accounts map of the group</p>
 */
//public class CrudService implements AbstractGroupService, AbstractAccountService {
//
//    /** the repository implementation (injected). */
//    private AbstractCrudRepository repo;
//
//    /** Create the Service (constructor)
//     * <p> repo: the repository that is utilized by the Service </p>
//     */
//    public CrudService(AbstractCrudRepository repo) {
//        this.repo = repo;
//    }
//
//    //GROUP CRUD
//    /** Call the repo.create() method to crate a Group.
//     * @param group It's the Group that will be created.
//     * @return The same Group that you created.
//     * @exception BusinessLogicException
//     *                  <p>It's thrown when the Group file already exist, has type 1.</p>
//     *                  <p>It's thrown when the name of the Group contains a '.'(dot), has type 2.</p>
//     * @since 0.1.0
//     * */
//    @Override
//    public Group createGroup(Group group) throws DataException, BusinessLogicException {
//        if (groupNameFileExist(group.getGroupNameId())){
//            throw new BusinessLogicException(1,"Error the GROUP file already exist. " + group);
//        }
//        if (group.getGroupNameId().indexOf('.') == -1){
//            throw new BusinessLogicException(2,"Error the NAME of the GROUP can't contain '.' (a dot). " + group);
//        }
//        return repo.create(group);
//    }
//
//    /** Call the repo.readGroupByNameId() method to readById a Group.
//     * @param groupNameId It's the name of the Group that you want to readById. The name can contain the .json type, if so it will be cut
//     * @return An Optional<Group> with the group inside if exists, null otherwise.
//     * @since 0.1.0
//     * */
//    @Override
//    public Optional<Group> readGroupByNameId(String groupNameId) throws EntityNotFoundException, DataException {
//        //Check if the groupNameId it's the full file name (groupNameId.json), and proceed to cancel .json from the groupNameId
//        if (groupNameId.contains(JSON_TYPE)){
//            groupNameId = groupNameId.replaceAll(JSON_TYPE,"");
//        }
//        return repo.readById(groupNameId);
//    }
//
//    /** Call the repo.update() method that will create the newGroup, then it will call deleteGroupByNameId() method to delete the oldGroup.
//     * @param oldGroup It's the old Group that you want to update.
//     * @param newGroup It's the new Group that will take the place of the old one.
//     * @return The new Group (newGroup).
//     * @exception BusinessLogicException it's thrown when the oldGroup does not exist, so you can't update it, has type 3.
//     * @since 0.1.0
//     * */
//    @Override
//    public Group updateGroup(Group oldGroup, Group newGroup) throws BusinessLogicException, DataException {
//        if (!groupNameFileExist(oldGroup.getGroupNameId())){
//            throw new BusinessLogicException(3,"Error the GROUP file does not exist");
//        }
//        if (oldGroup.equals(newGroup)){     //The oldGroup and the newGroup are the same
//            return oldGroup;
//        }
//        if (oldGroup.getGroupNameId().equals(newGroup.getGroupNameId())){   //The name of the group didn't change
//            return repo.update(newGroup);
//        }
//        //If the name of the newGroup changed I need to delete the oldGroup file
//        repo.update(newGroup);
//        deleteGroupByNameId(oldGroup.getGroupNameId());
//        return newGroup;
//    }
//
//    /** Call the repo.deleteByNameId() method to delete a Group
//     * @param groupNameId It's the name of the Group file that will be deleted.
//     * @return TRUE if the Group file it's deleted successfully, FALSE otherwise.
//     * @since 0.1.0
//     * */
//    @Override
//    public boolean deleteGroupByNameId(String groupNameId) {
//        return repo.deleteById(groupNameId);
//    }
//
//    /** It will find all the Group in the GROUPS_DIR_PATH, so all the Group created.
//     * @return Iterable<Group> with all the Group that it find in the GROUP_DIT_PATH. It will be empty otherwise.
//     * @exception DataException It's thrown when an error occur reading the directory.
//     * @exception EntityNotFoundException It's thrown when the directory does not exist.
//     * @since 0.1.0
//     * */
//    @Override
//    public Iterable<Group> findAllGroup() throws EntityNotFoundException, DataException {
//        File[] files = new File(GROUPS_DIR_PATH).listFiles();
//        Collection<Group> groupList = new ArrayList<>();
//        if (files != null){
//            for (File file : files){
//                groupList.add(readGroupByNameId((file.getName())).orElseThrow());
//            }
//        }
//        return groupList;
//    }
//
//    /** Call the repo.groupNameFileExist() method that will search if the Group exist.
//     * @param groupNameId It's the name of the Group file that will be searched the existence.
//     * @return TRUE if the Group file exists, FALSE otherwise.
//     * @since 0.1.0
//     * */
//    @Override
//    public boolean groupNameFileExist(String groupNameId) {
//        return repo.existById(groupNameId);
//    }
//
//    //ACCOUNT CRUD
//    /** Call the repo.create() method. First it will set the first available id to the Account,
//     * then it will add the Account inside the Accounts Map of the Group,
//     * finally it will call the repo.create() method that will create (or update) the Group with the Account inside.
//     * @param account It's the Account that you want to create.
//     * @param group It's the Group where you want to create the Account.
//     * @return The Account that you created.
//     * @exception BusinessLogicException It's thrown when the Group file does not exist, has type 3.
//     * @since 0.1.0
//     * */
//    @Override
//    public Account createAccount(Group group, Account account) throws DataException, BusinessLogicException {
//        if (!groupNameFileExist(group.getGroupNameId())){
//            throw new BusinessLogicException(3,"Error the GROUP file does not exist");
//        }
//        account.setId(group.firstAvailableAccountId());
//        group.getAccounts().put(account.getId(),account);
//        repo.create(group);
//        return account;
//    }
//
//    /** Call repo.update() method, it will update the account with the same id, if the 2 Account are equals it will do nothing.
//     * @param account It's the new Account that will take the place of the already existing Account with the same id.
//     *                If the old Account and the new Account (account) are the same it will do nothing and return account.
//     * @param group It's the Group where the Account to update is present.
//     * @return The updated Account (account).
//     * @exception EntityNotFoundException It's thrown if the id of the new Account (account) is not present in the Group Map.
//     * @exception BusinessLogicException It's thrown if the Group file does not exist, has type 3.
//     * @since 0.1.0
//     * */
//    @Override
//    public Account updateAccount(Group group, Account account) throws EntityNotFoundException, DataException, BusinessLogicException{
//        if (!groupNameFileExist(group.getGroupNameId())){
//            throw new BusinessLogicException(3,"Error the GROUP file does not exist");
//        }
//        if (group.getAccounts().get(account.getId()) == null){
//            throw new EntityNotFoundException("Error the accountId key does not exist in the accounts map of the group",new Throwable());
//        }
//        if (group.getAccounts().get(account.getId()).equals(account)){  //If the accounts are the same
//            return account;
//        }
//        group.getAccounts().put(account.getId(),account);
//        repo.update(group);
//        return account;
//    }
//
//    /** Call the repo.update() method, it will remove the Account with the given id (account id) from the Group Map
//     * and then calls repo.update() method.
//     * @param accountId It's the id of the Account you want to remove.
//     * @param group It's the Group where the Account will be removed from
//     * @return TRUE if the Account is successfully removed, or if the account does not exist in the group.
//     * @exception BusinessLogicException It's thrown if the Group file does not exist, has type 3.
//     * @since 0.1.0
//     * */
//    @Override
//    public boolean deleteAccountById(Group group, int accountId) throws DataException, BusinessLogicException{
//        if (!groupNameFileExist(group.getGroupNameId())){
//            throw new BusinessLogicException(3,"Error the GROUP file does not exist");
//        }
//        Account account = group.getAccounts().get(accountId);
//        if (account == null){
//            return true;
//        }
//        group.getAccounts().remove(accountId);
//        repo.update(group);
//        return true;
//    }
//}
