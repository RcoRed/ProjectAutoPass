package com.github.rcored.project_auto_pass.model.entities.platforms;

import com.github.rcored.project_auto_pass.model.entities.Account;
import lombok.Getter;

/** Default Platform (Singleton)
 * @author Marco Martucci
 * @version 0.1.0
 */
public class Default extends Platform{
    /** Object reference of itself */
    @Getter
    private static final Default DEFAULT;

    //blocco inizializzazione statico
    static {
        DEFAULT = new Default();    //creo default
    }

    /** Create the Default Platform (constructor)
     * <p> id: 1 </p>
     * <p> name: default </p>
     */
    private Default() {
        super(1,"default");
    }

    @Override
    public void connect(Account account) {
        System.out.println("Connected!");
    }

    @Override
    public void update(Account oldAccount, Account newAccount) {
        System.out.println("Updated?");
    }
}