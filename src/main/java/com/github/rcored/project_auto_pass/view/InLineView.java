package com.github.rcored.project_auto_pass.view;

import com.github.rcored.project_auto_pass.model.entities.Account;
import com.github.rcored.project_auto_pass.model.entities.Group;
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
        List<Group> groupList = null;
        char choiceKey;
        do {
            groupList = StreamSupport.stream(groupService.findAllGroup().spliterator(),false).toList();
            System.out.println();
            System.out.println("____________________ MENU LISTA GRUPPI ____________________");
            System.out.println("|-- * ----> Inserisci il numero del GRUPPO da visualizzare");
            System.out.println();
            System.out.println("|-- r ----> Elimina un gruppo");
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
                case 'r', 'R' -> {
                    if (groupList.isEmpty()){
                        System.out.println("| *** NON CI SONO GRUPPI DA ELIMINARE *** |");
                        break;
                    }
                    int groupId;
                    System.out.println("| ***  INSERISCI IL GRUPPO CHE VUOI ELIMINARE  *** |");
                    System.out.println("|-- * ----> Elimina gruppo");
                    groupId = console.nextLine().charAt(0) - 49;
                    if (0 < groupId && groupId < groupList.size()){    //se account con id non esiste (primo controllo)
                        System.out.println("| *** NESSUN GRUPPO TROVATO CON QUESTO ID ***");
                        break;
                    }
                    Group group = groupList.get(groupId);
                    System.out.println("| ***  SEI SICURO DI VOLER ELIMINARE QUESTO ACCOUNT? ---> " +
                            group.getGroupNameId()
                    );
                    System.out.println("| -- q ----> NO | ANNULLA |");
                    System.out.println("| -- a ----> SI | ELIMINA |");
                    choiceKey = console.nextLine().charAt(0);
                    if (choiceKey == 'q' || choiceKey + 32 == 'q'){     //se annulla
                        choiceKey = ' ';    //in questo modo rifaccio il do while
                        break;
                    }
                    groupService.deleteByNameId(group.getGroupNameId());
                    System.out.println("| ***  GRUPPO ELIMINATO CORRETTAMENTE  *** |");
                }
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
            System.out.println("|-- s ----> Modifica gruppo");
            System.out.println("|-- * ----> Visualizza un account");
            System.out.println();
            System.out.println("|-- r ----> Elimina un account");
            System.out.println("|-- e ----> Elimina questo gruppo");
            System.out.println("|-- q ----> Indietro");
            System.out.println("_________________________________________________");
            System.out.println("| ***  " + group.getGroupNameId() + "  *** | <--- NOME GRUPPO -- |");
            System.out.println("| ***  ACCOUNT trovati : " + group.getAccounts().size() + "  *** |");
            group.getAccounts().forEach(a -> System.out.println("|-- " + a.getId() + " ----> " + a.getName()));
            choiceKey = console.nextLine().charAt(0);
            switch (choiceKey) {
                case 'q', 'Q' -> System.out.println("| ***  INDIETRO  *** |");
                case 'a', 'A' -> creaAccount(group);
                case 's', 'S' -> visualizzaListaGruppi();
                case 'e', 'E' -> {
                    System.out.println("| ***  SEI SICURO DI VOLER ELIMINARE QUESTO GRUPPO?  *** |");
                    System.out.println("| -- q ----> NO | ANNULLA |");
                    System.out.println("| -- a ----> SI | ELIMINA |");
                    choiceKey = console.nextLine().charAt(0);
                    if (choiceKey == 'q' || choiceKey + 32 == 'q'){     //se annulla
                        choiceKey = ' ';    //in questo modo rifaccio il do while
                        break;
                    }
                    choiceKey = 'q';        //in questo modo tornerò anche indietro
                    groupService.deleteByNameId(group.getGroupNameId());
                    System.out.println("| ***  GRUPPO ELIMINATO CORRETTAMENTE  *** |");
                }
                case 'r', 'R' -> {
                    if (group.getAccounts().size() == 0){
                        System.out.println("| *** NON CI SONO ACCOUNT DA ELIMINARE *** |");
                        break;
                    }
                    int accountId;
                    System.out.println("| ***  INSERISCI L'ACCOUNT CHE VUOI ELIMINARE  *** |");
                    System.out.println("|-- * ----> Elimina account");
                    accountId = console.nextLine().charAt(0) - 48;
                    if (group.getAccounts().stream().filter(a -> a.getId() == accountId).findFirst().isEmpty()){    //se account con id non esiste (primo controllo)
                        System.out.println("| *** NESSUN ACCOUNT TROVATO CON QUESTO ID ***");
                        break;
                    }
                    System.out.println("| ***  SEI SICURO DI VOLER ELIMINARE QUESTO ACCOUNT? ---> " +
                            group.getAccounts().stream()
                            .filter(a -> a.getId() == accountId)
                            .findFirst()
                            .orElseThrow()
                            .getName()
                    );
                    System.out.println("| -- q ----> NO | ANNULLA |");
                    System.out.println("| -- a ----> SI | ELIMINA |");
                    choiceKey = console.nextLine().charAt(0);
                    if (choiceKey == 'q' || choiceKey + 32 == 'q'){     //se annulla
                        choiceKey = ' ';    //in questo modo rifaccio il do while
                        break;
                    }
                    accountService.deleteById(group,accountId);
                    group.setAccounts(group.getAccounts().stream().filter(a -> a.getId() != accountId).toList());   //lo elimino dall'account che ho qui
                    System.out.println("| ***  ACCOUNT ELIMINATO CORRETTAMENTE *** |");
                }
                default -> {
                    int posizione = choiceKey - 48;
                    if ( posizione > 0 && posizione <= group.getAccounts().size()){
                        visualizzaAccount(group, posizione);
                    }else {
                        System.out.println("| **  COMANDO NON CORRETTO  ** |");
                    }
                }
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
        accountService.create(group,new Account(nomeAccount,emailAccount,passwordAccount,Platform.getPLATFORM_MAP().get(id)));
    }

    private static void visualizzaAccount(Group group, int accountId){
        Account account = accountService.readById(group, accountId).orElseThrow();
        char choiceKey;
        do {
            System.out.println();
            System.out.println("__________________ MENU ACCOUNT __________________");
            System.out.println("|-- s ----> Modifica account");
            System.out.println();
            System.out.println("|-- e ----> Elimina questo account");
            System.out.println("|-- q ----> Indietro");
            System.out.println("__________________________________________________");
            System.out.println("| ***  " + group.getGroupNameId() + "  *** | <--- NOME GRUPPO -- |");
            System.out.println("| ***  " + account.getName() + "  *** | <--- NOME ACCOUNT -- |");
            System.out.println("| EMAIL : " + account.getEmail());
            System.out.println("| PASSWORD : " + account.getPassword());
            System.out.println("| PIATTAFORMA : " + account.getPlatform().getName());
            choiceKey = console.nextLine().charAt(0);
            switch (choiceKey) {
                case 'q', 'Q' -> System.out.println("| ***  INDIETRO  *** |");
                case 's', 'S' -> System.out.println("modifica");
                case 'e', 'E' -> {
                    System.out.println("| ***  SEI SICURO DI VOLER ELIMINARE QUESTO ACCOUNT?  *** |");
                    System.out.println("| -- q ----> NO | ANNULLA |");
                    System.out.println("| -- a ----> SI | ELIMINA |");
                    choiceKey = console.nextLine().charAt(0);
                    if (choiceKey == 'q' || choiceKey + 32 == 'q'){     //se annulla
                        choiceKey = ' ';    //in questo modo rifaccio il do while
                        break;
                    }
                    choiceKey = 'q';        //in questo modo tornerò anche indietro
                    accountService.deleteById(group,accountId);
                    System.out.println("| ***  ACCOUNT ELIMINATO CORRETTAMENTE  *** |");
                }
                default -> System.out.println("| **  COMANDO NON CORRETTO  ** |");
            }
        } while ( !(choiceKey == EXIT_KEY || choiceKey + 32 == EXIT_KEY)); //+32 per prendere e/E dato che un char sotto sotto è un numero
    }

}
