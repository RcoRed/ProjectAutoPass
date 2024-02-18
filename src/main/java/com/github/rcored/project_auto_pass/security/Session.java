package com.github.rcored.project_auto_pass.security;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/** Represent the current session of the User
 * @author Marco Martucci
 * @version 0.1.0
 */
@ToString
public class Session {

    /** Represent the hashed password that will be used to do CRUD operation */
    private static byte[] hPassword;
    /** Represent the date (hours,minutes,seconds,millisecond) of the login of the User */
    private static Date loginDate;

    /** Create the Session (constructor)
     * @param hPassword the hashed plain password that will be used to do CRUD operation.
     */
    public Session(byte[] hPassword){
        Session.hPassword = hPassword;
        Session.loginDate = new Date();
    }

    public static byte[] getHPassword() {
        return hPassword;
    }

    public static void setHPassword(byte[] hPassword) {
        Session.hPassword = hPassword;
    }

    public static Date getLoginDate() {
        return loginDate;
    }

    public static void setLoginDate(Date loginDate) {
        Session.loginDate = loginDate;
    }
}
