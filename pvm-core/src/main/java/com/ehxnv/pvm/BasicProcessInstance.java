/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm;

import com.ehxnv.pvm.api.ProcessInstance;
import com.ehxnv.pvm.api.data.Data;

import java.util.Collections;
import java.util.Date;
import java.util.Set;

/**
 * Basic implementation of {@link ProcessInstance}.
 * 
 * @author Eka Lie
 */
public class BasicProcessInstance implements ProcessInstance {

    /** Process instance id. **/
    private String id;
    /** Process instance start time. **/
    private Date startTime;
    /** Process instance end time. **/
    private Date endTime;
    /** Process instance input data(s). **/
    private Set<Data> inputDatas;
    /** Process instance output data(s). **/
    private Set<Data> outputDatas;

    /**
     * Constructor.
     * @param id process instance id
     * @param startTime process instance start time
     */
    public BasicProcessInstance(final String id, final Date startTime) {
        this.id = id;
        this.startTime = startTime;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date getStartTime() {
        return startTime;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date getEndTime() {
        return endTime;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setEndTime(final Date endTime) {
        this.endTime = endTime;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Data> getInputDatas() {
        if (inputDatas == null) {
            return Collections.emptySet();
        } else {
            return Collections.unmodifiableSet(inputDatas);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setInputDatas(final Set<Data> inputDatas) {
        this.inputDatas = inputDatas;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Data> getOutputDatas() {
        if (outputDatas == null) {
            return Collections.emptySet();
        } else {
            return Collections.unmodifiableSet(outputDatas);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOutputDatas(final Set<Data> outputDatas) {
        this.outputDatas = outputDatas;
    }    
}
