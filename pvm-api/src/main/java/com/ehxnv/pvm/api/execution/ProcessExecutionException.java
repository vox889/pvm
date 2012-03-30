/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm.api.execution;

/**
 * Exception thrown when there is a problem during process execution.
 * 
 * @author Eka Lie
 */
public class ProcessExecutionException extends Exception {

    /**
     * Constructor.
     * @param message exception message
     */
    public ProcessExecutionException(final String message) {
        super(message);
    }

    /**
     * Constructor.
     * @param message exception message
     * @param cause exception cause
     */
    public ProcessExecutionException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
