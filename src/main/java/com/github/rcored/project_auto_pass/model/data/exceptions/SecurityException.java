package com.github.rcored.project_auto_pass.model.data.exceptions;

/**
 * @author Marco Martucci
 * @version 0.1.0
 * @since 0.1.0
 * */
public class SecurityException extends Exception{

    /**
     * Constructs a new exception with the specified detail message and
     * cause.  <p>Note that the detail message associated with
     * {@code cause} is <i>not</i> automatically incorporated in
     * this exception's detail message.
     *
     * @param message the detail message (which is saved for later retrieval
     *                by the {@link #getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the
     *                {@link #getCause()} method).  (A {@code null} value is
     *                permitted, and indicates that the cause is nonexistent or
     *                unknown.)
     * @since 0.1.0
     */
    public SecurityException(String message, Throwable cause) {
        super(message, cause);
    }
}
