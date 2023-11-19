package com.github.rcored.project_auto_pass.model.data.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessLogicException extends Exception{

    private int type;

    /**
     * Constructs a new exception with the specified detail message and
     * cause.  <p>Note that the detail message associated with
     * {@code cause} is <i>not</i> automatically incorporated in
     * this exception's detail message.
     *
     * @param type    it can represent a specific type of exception,
     *                for more info go to see the info of the class that throws
     *                this exception
     * @param message the detail message (which is saved for later retrieval
     *                by the {@link #getMessage()} method).
     * @since 1.4
     */
    public BusinessLogicException(int type, String message) {
        super(message);
        this.type = type;
    }
}
