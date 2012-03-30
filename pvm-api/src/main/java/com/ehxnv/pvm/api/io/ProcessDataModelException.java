/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm.api.io;

/**
 * Exception thrown when expected process data model is not met.
 * 
 * @author Eka Lie
 */
public class ProcessDataModelException extends Exception {

    /**
     * Constructor.
     * @param message exception message
     */
    public ProcessDataModelException(final String message) {
        super(message);
    }

    /**
     * Constructor.
     * @param message exception message
     * @param cause exception cause
     */
    public ProcessDataModelException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
