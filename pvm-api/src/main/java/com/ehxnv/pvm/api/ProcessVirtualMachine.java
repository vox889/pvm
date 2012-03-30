/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm.api;

import com.ehxnv.pvm.api.data.Data;
import com.ehxnv.pvm.api.execution.ProcessExecutionException;

import java.util.Set;

/**
 * Represents a process virtual machine. A process virtual machine is used to
 * execute a process.
 * 
 * @author Eka Lie
 */
public interface ProcessVirtualMachine {
    
    /** 
     * Start the process virtual machine.
     */
    void start();
    
    /**
     * Stop the process virtual machine.
     */
    void stop();
    
    /**
     * Execute a given process.
     * @param process target process
     * @param inputDatas input data(s)
     * @throws ProcessExecutionException if any exception occurred while executing the process
     * @return process instance of the given process
     */
    ProcessInstance execute(Process process, Set<Data> inputDatas) throws ProcessExecutionException;
}
