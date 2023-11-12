package com.github.rcored.project_auto_pass.view;

import com.github.rcored.project_auto_pass.model.entities.Account;
import com.github.rcored.project_auto_pass.model.entities.Group;
import com.github.rcored.project_auto_pass.model.entities.PlatformEnum;
import com.github.rcored.project_auto_pass.model.entities.platforms.Platform;
import com.github.rcored.project_auto_pass.model.services.AccountCrudService;
import com.github.rcored.project_auto_pass.model.services.GroupCrudService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.StreamSupport;

public class InLineView {
    private static final char EXIT_KEY = 'q';
    private static Scanner console = new Scanner(System.in);
    private static GroupCrudService groupService;
    private static AccountCrudService accountService;
    public InLineView(GroupCrudService groupService,AccountCrudService accountService) {
        InLineView.groupService = groupService;
        InLineView.accountService = accountService;
    }

    public void start(){
        System.out.println("/*************\\");
        System.out.println("|**BENVENUTO**|");
        System.out.println("\\*************/");
        menu();
    }

    private static void menu(){
        char choiceKey;
        do {
            System.out.println();
            System.out.println("_____________ MENU PRINCIPALE _____________");
            System.out.println("|-- a ----> Crea un nuovo gruppo");
            System.out.println("|-- s ----> Visualizza i tuoi gruppi");
            System.out.println();
            System.out.println("|-- q ----> Chiudi programma");
            System.out.println("__________________________________________");
            choiceKey = console.nextLine().charAt(0);
            switch (choiceKey) {
                case 'q', 'Q' -> System.out.println("| ***  ARRIVEDERCI  *** |");
                case 'a', 'A' -> menuCreaGruppo();
                case 's', 'S' -> visualizzaListaGruppi();
                default -> System.out.println("| **  COMANDO NON CORRETTO  ** |");
            }
        } while ( !(choiceKey == EXIT_KEY || choiceKey + 32 == EXIT_KEY)); //+32 per prendere e/E dato che un char sotto sotto è un numero

    }

    private static void menuCreaGruppo(){
        String nomeGruppo;
        System.out.println();
        System.out.println("_________________________");
        System.out.println("Inserisci nome del gruppo");
        nomeGruppo = console.nextLine();
        Group group = groupService.createGroup(new Group(nomeGruppo,new ArrayList<Account>()));
        visualizzaGruppo(group);
    }

    private static void visualizzaListaGruppi(){
        List<Group> groupList = StreamSupport.stream(groupService.findAllGroup().spliterator(),false).toList();
        char choiceKey;
        do {
            System.out.println("____________________ MENU LISTA GRUPPI ____________________");
            System.out.println("|-- * ----> Inserisci il numero del GRUPPO da visualizzare");
            System.out.println();
            System.out.println("|-- q ----> Indietro");
            System.out.println("__________________________________________");
            System.out.println("| ***  GRUPPI trovati: " + groupList.size() + "  *** |");
            int counter = 1;
            for (Group group : groupList) {
                System.out.println("|-- " + counter++ + " ----> " + group.getGroupNameId());
            }
            System.out.println("___________________________________________________________");
            choiceKey = console.nextLine().charAt(0);
            switch (choiceKey) {
                case 'q', 'Q' -> System.out.println("| ***  INDIETRO  *** |");
                default -> {
                    int posizione = choiceKey - 49;
                    if (posizione < groupList.size() && posizione >= 0){
                        visualizzaGruppo(groupList.get(posizione));
                    }else {
                        System.out.println("| **  COMANDO NON CORRETTO  ** |");
                    }
                }
            }
        }while ( !(choiceKey == EXIT_KEY || choiceKey + 32 == EXIT_KEY)); //+32 per prendere e/E dato che un char sotto sotto è un numero


    }

    private static void visualizzaGruppo(Group group){
        char choiceKey;
        do {
            System.out.println();
            System.out.println("__________________ MENU GRUPPO __________________");
            System.out.println("|-- a ----> Inserisci un nuovo account");
            System.out.println("|-- non funge ----> Visualizza un account");
            System.out.println();
            System.out.println("|-- e ----> Elimina un account");
            System.out.println("|-- q ----> Indietro");
            System.out.println("_________________________________________________");
            System.out.println("| ***  " + group.getGroupNameId() + "  *** |");
            group.getAccounts().forEach(a -> System.out.println("|-- " + a.getId() + " ----> " + a.getName()));
            choiceKey = console.nextLine().charAt(0);
            switch (choiceKey) {
                case 'q', 'Q' -> System.out.println("| ***  INDIETRO  *** |");
                case 'a', 'A' -> creaAccount(group);
                case 's', 'S' -> visualizzaListaGruppi();
                default -> System.out.println("| **  COMANDO NON CORRETTO  ** |");
            }
        } while ( !(choiceKey == EXIT_KEY || choiceKey + 32 == EXIT_KEY)); //+32 per prendere e/E dato che un char sotto sotto è un numero
    }

    private static void creaAccount(Group group){
        String nomeAccount;
        String emailAccount;
        String passwordAccount;
        String idPlatform;

        System.out.println();
        System.out.println("Inserisci il NOME dell'account");
        nomeAccount = console.nextLine();
        System.out.println("Inserisci EMAIL dell'account");
        emailAccount = console.nextLine();
        System.out.println("Inserisci PASSWORD dell'account");
        passwordAccount = console.nextLine();
        System.out.println("Scegli tra le seguenti piattaforme disponibili: ");
        Platform.getPLATFORM_MAP().forEach((i,p) -> System.out.println("|-- " + i + " ----> " + p.getName()));
        System.out.println("Inserisci NUMERO della piattaforma");
        idPlatform = console.nextLine();
        int id = Integer.parseInt(idPlatform);
        accountService.createAccount(group,new Account(nomeAccount,emailAccount,passwordAccount,Platform.getPLATFORM_MAP().get(id)));
    }
}
