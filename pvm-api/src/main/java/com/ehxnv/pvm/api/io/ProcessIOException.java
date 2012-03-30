/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm.api.io;

/**
 * Exception thrown during process IO process i.e. reading/closing process.
 * 
 * @author Eka Lie
 */
public class ProcessIOException extends Exception {

    /**
     * Constructor.
     * @param message exception message
     */
    public ProcessIOException(final String message) {
        super(message);
    }

    /**
     * Constructor.
     * @param message exception message
     * @param cause exception cause
     */
    public ProcessIOException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
