/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm.io;

/**
 * Exception thrown when expected process structure is not met.
 * 
 * @author Eka Lie
 */
public class ProcessStructureException extends Exception {

    /**
     * Constructor.
     * @param message exception message
     */
    public ProcessStructureException(final String message) {
        super(message);
    }

    /**
     * Constructor.
     * @param message exception message
     * @param cause exception cause
     */
    public ProcessStructureException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
