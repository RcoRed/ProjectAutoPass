package com.github.rcored;

import com.github.rcored.project_auto_pass.model.data.implementations.AccountCrudRepository;
import com.github.rcored.project_auto_pass.model.data.implementations.GroupCrudRepository;
import com.github.rcored.project_auto_pass.model.entities.platforms.Default;
import com.github.rcored.project_auto_pass.model.entities.platforms.Platform;
import com.github.rcored.project_auto_pass.model.services.AccountCrudService;
import com.github.rcored.project_auto_pass.model.services.GroupCrudService;
import com.github.rcored.project_auto_pass.view.InLineView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main {
    public static void main(String[] args) {
//        Scanner console = new Scanner(System.in);
//        Group group = new Group("Group1", new ArrayList<Account>());
//        char ca = 'q';
//        do {
//            InLineView.start();
//            System.out.println("Hello world!");
//            GroupCrudRepository repo = new GroupCrudRepository(new GsonBuilder().setPrettyPrinting().create());
//            group.getAccounts().add(new Account(0, "prova1", Default.getDefault(), "email.prova@gmail.com", "ciccio4_!@234CFV"));
//            Group newGroup = repo.create(group);
//            System.out.println(group);
//            System.out.println(newGroup);
////            AccountCrudRepository repo = new AccountCrudRepository(1,
////                    new GsonBuilder().setPrettyPrinting().create());
////            Account account = repo.create(group, new Account(0, "connect", "ciccio2", "ciccio3", "ciccio4"));
////            System.out.println(account);
////            Account account2 = repo.readById(group,1).orElse(null);
////            System.out.println(account2);
//            group.getAccounts().forEach(a -> System.out.println(a.getName() + a.getPlatform()));
//
//            ca = console.nextLine().charAt(0);
//        }while (ca != 'q');
        Platform.getPLATFORM_MAP(); //carica le Platform
        ;
        System.out.println(Platform.getPLATFORM_MAP().get(1) instanceof Platform);
        System.out.println(Platform.getPLATFORM_MAP().get(1) instanceof Default);
        System.out.println(Platform.getPLATFORM_MAP().get(1));
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        InLineView view = new InLineView(new GroupCrudService(new GroupCrudRepository(gson)),
                new AccountCrudService(new AccountCrudRepository(gson)));
        view.start();
    }
}