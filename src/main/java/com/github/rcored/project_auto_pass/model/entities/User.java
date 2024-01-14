package com.github.rcored.project_auto_pass.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

/** Represent a User and his config
 * @author Marco Martucci
 * @version 0.1.0
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class User {
    /** Represent the password to enter the application and view the Groups */
    private String password;
    /** Represent the favorite Group of the User that will be viewed first */
    private String favorite;

     /** Create the User (constructor)
     * @param password the password to enter the application and view the Groups
     */
    public User(String password) {
        this(password, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(getPassword(), user.getPassword()) && Objects.equals(getFavorite(), user.getFavorite());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPassword(), getFavorite());
    }
}
