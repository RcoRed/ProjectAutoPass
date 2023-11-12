package com.github.rcored.project_auto_pass.model.data.implementations;

import com.github.rcored.project_auto_pass.model.data.abstractions.AbstractAccountCrudRepository;
import com.github.rcored.project_auto_pass.model.entities.Account;
import com.github.rcored.project_auto_pass.model.entities.Group;
import com.google.gson.Gson;
import lombok.*;

import java.io.*;
import java.util.Optional;

@AllArgsConstructor
@Setter
@Getter
public class AccountCrudRepository implements AbstractAccountCrudRepository {

    private static final String JSON_TYPE = ".json";
    private static final String JSON_DIR_PATH = "groups/";
    private Gson gson;

    @Override
    public Account create(Group group, Account account) {
        try (FileOutputStream output = new FileOutputStream(JSON_DIR_PATH + group.getGroupNameId() + JSON_TYPE);
                PrintWriter pw = new PrintWriter(output)){
            account.setId(group.firstAvailableAccountId());
            group.getAccounts().add(account);
            gson.toJson(group,pw);
            return account;
        }catch (IOException e) {    //da cambiare
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Account> readById(Group group, int id) {
        try (Reader reader = new FileReader(group.getGroupNameId() + ".json")) {
            //find the account with the id we need
            return gson.fromJson(reader,Group.class).getAccounts()
                                                    .stream()
                                                    .filter(a -> a.getId() == id)
                                                    .findFirst();
        } catch (FileNotFoundException e){  //if the file(group) does not exist
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IOException e) {    //da cambiare
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Account update(Group group, Account account) {
        readById(group, account.getId()).orElseThrow();
        return create(group, account);
    }

    @Override
    public void deleteById(Group group, int id) {
        try (FileOutputStream output = new FileOutputStream(group.getGroupNameId() + ".json");
             PrintWriter pw = new PrintWriter(output)){
            //recreating the collection without the account that mach the id
            group.setAccounts(group.getAccounts()
                    .stream()
                    .filter(a -> a.getId() != id)
                    .toList());
            //write the file
            gson.toJson(group,pw);
        } catch (FileNotFoundException e){  //if the file(group) does not exist
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IOException e) {    //da cambiare
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
