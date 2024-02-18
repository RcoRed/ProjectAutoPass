package com.github.rcored.project_auto_pass.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/** Represent a Config
 * @author Marco Martucci
 * @version 0.1.0
 */
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Config {
    /** Represent the encryption method */
    private String encryptionMethod;
    /** Represent IV used to encrypt the Group.*/
    private byte[] IV;
}
