/*******************************************************************************
 * Copyright (c) 2012, Eka Heksanov Lie
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the organization nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL COPYRIGHT HOLDER BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/

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
