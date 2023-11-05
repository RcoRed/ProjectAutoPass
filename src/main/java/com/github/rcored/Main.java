package com.github.rcored;

import com.github.rcored.project_auto_pass.model.data.implementations.GroupCrudRepository;
import com.github.rcored.project_auto_pass.model.entities.Account;
import com.github.rcored.project_auto_pass.model.entities.Group;
import com.github.rcored.project_auto_pass.model.entities.platforms.Default;
import com.github.rcored.project_auto_pass.model.entities.platforms.Platform;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        Group group = new Group("Group1", new ArrayList<Account>());
        char ca = 'q';
        do {

            System.out.println("Hello world!");
            GroupCrudRepository repo = new GroupCrudRepository(new GsonBuilder().setPrettyPrinting().create());
            group.getAccounts().add(new Account(0, "connect", new Default(), "ciccio3", "ciccio4"));
            Group newGroup = repo.create(group);
            System.out.println(group);
            System.out.println(newGroup);
//            AccountAccountCrudRepository repo = new AccountAccountCrudRepository(1,
//                    new GsonBuilder().setPrettyPrinting().create());
//            Account account = repo.create(group, new Account(0, "connect", "ciccio2", "ciccio3", "ciccio4"));
//            System.out.println(account);
//            Account account2 = repo.readById(group,1).orElse(null);
//            System.out.println(account2);
            group.getAccounts().forEach(a -> System.out.println(a.getName() + a.getPlatform()));

            ca = console.nextLine().charAt(0);
        }while (ca != 'q');
    }
}