/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm.data;

/**
 * Exception thrown when data value can't be set.
 * 
 * @author Eka Lie
 */
public class InvalidValueException extends RuntimeException {
    
    /**
     * Constructor.
     * @param message exception message
     */
    public InvalidValueException(final String message) {
        super(message);
    }

    /**
     * Constructor.
     * @param message exception message
     * @param cause exception cause
     */
    public InvalidValueException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
