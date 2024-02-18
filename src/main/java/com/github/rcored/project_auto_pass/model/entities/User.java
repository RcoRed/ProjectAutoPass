package com.github.rcored.project_auto_pass.model.entities;

import com.github.rcored.Main;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;
import java.util.Objects;

/** Represent a User
 * @author Marco Martucci
 * @version 0.1.0
 */
@Getter
@Setter
@ToString
public class User {
    /** Represent the password to enter the application and view the Groups */
    private byte[] password;
    /** Represent the favorite Group of the User that will be viewed first */
    private String favorite;
    /** Represent the configuration for every Group */
    private Map<String,Config> configMap;
    /** Represent the user */
    private static User user;

    public User(byte[] password, String favorite, Map<String,Config> configMap) {
        this.password = password;
        this.favorite = favorite;
        this.configMap = configMap;
        User.user = this;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        User.user = user;
    }
}
