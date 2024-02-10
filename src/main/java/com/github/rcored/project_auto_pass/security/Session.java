package com.github.rcored.project_auto_pass.security;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/** Represent the current session of the User
 * @author Marco Martucci
 * @version 0.1.0
 */
@Setter
@Getter
@ToString
public class Session {

    /** Represent the hashed password that will be used to do CRUD operation */
    private String hPassword;
    /** Represent the date (hours,minutes,seconds,millisecond) of the login of the User */
    private Date loginDate;

    /** Create the Session (constructor)
     * @param hPassword the hashed plain password that will be used to do CRUD operation.
     */
    public Session(String hPassword){
        this.hPassword = hPassword;
        this.loginDate = new Date();
    }

}
