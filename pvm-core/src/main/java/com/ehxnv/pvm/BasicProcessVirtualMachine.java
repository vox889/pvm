/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm;

import com.ehxnv.pvm.api.Process;
import com.ehxnv.pvm.api.ProcessInstance;
import com.ehxnv.pvm.api.data.Data;
import com.ehxnv.pvm.api.execution.ProcessExecutionException;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

/**
 * Basic implementation of {@link com.ehxnv.pvm.api.ProcessVirtualMachine}.
 * 
 * @author Eka Lie
 */
public class BasicProcessVirtualMachine extends AbstractProcessVirtualMachine {

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getName() {
        return "SingleThreadStateless";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ProcessInstance createProcessInstance() {
        return new BasicProcessInstance(getNextProcessInstanceId(), new Date());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected String getNextProcessInstanceId() {
        return UUID.randomUUID().toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProcessInstance execute(final Process process, final Set<Data> inputDatas) throws ProcessExecutionException {
        return executeProcessInternal(process, inputDatas);
    }

    /**
     * {@inheritDoc}
     */
    protected void startInternal() {
        // do nothing
    }

    /**
     * {@inheritDoc}
     */
    protected void stopInternal() {
        // do nothing
    }    
}
