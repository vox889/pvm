/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm.api;

import com.ehxnv.pvm.api.data.Data;
import java.util.Date;
import java.util.Set;

/**
 * Represents a process that have finished executed.
 * 
 * @author Eka Lie
 */
public interface ProcessInstance {

    /**
     * Get process instance id.
     * @return process instance id
     */
    String getId();

    /**
     * Get the time where this instance started.
     * @return start time
     */
    Date getStartTime();

    /**
     * Get the time where this instance stopped.
     * @return stop time
     */
    Date getEndTime();

    /**
     * Set the time where this instance stopped.
     * @param endTime stop time
     */
    void setEndTime(Date endTime);

    /**
     * Get input data(s).
     * @return input data(s)
     */
    Set<Data> getInputDatas();

    /**
     * Set input data(s)
     * @param inputDatas input data(s) 
     */
    void setInputDatas(Set<Data> inputDatas);

    /**
     * Get output data(s).
     * @return output data(s)
     */
    Set<Data> getOutputDatas();

    /**
     * Set output data(s)
     * @param outputDatas output data(s) 
     */
    void setOutputDatas(Set<Data> outputDatas);
}
