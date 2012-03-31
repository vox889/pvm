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
