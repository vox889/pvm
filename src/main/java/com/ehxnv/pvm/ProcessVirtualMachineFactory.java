/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm;

/**
 * Factory object to create {@link ProcessVirtualMachine}.
 * 
 * @author Eka Lie
 */
public abstract class ProcessVirtualMachineFactory {

    /**
     * Create process virtual machine based on given configuration.
     * @param config process virtual machine configuration
     * @return an instance of process virtual machine
     */
    public static ProcessVirtualMachine createProcessVirtualMachine(final Configuration config) {
        return new BasicProcessVirtualMachine();
    }
}
