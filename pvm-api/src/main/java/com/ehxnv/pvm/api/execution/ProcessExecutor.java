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

package com.ehxnv.pvm.api.execution;

import com.ehxnv.pvm.api.Process;
import com.ehxnv.pvm.api.data.Data;
import java.util.Set;

/**
 * A process executor responsible for executing a process. An instance of process executor
 * maintains the execution state of process execution. It's not meant to be reusable.
 * A process executor has 3 phases:
 * <ul>
 *   <li>
 *       Initializing phase
 *      <p>This is the phase where process executor initialize itself before it get used for execution</p>
 *   </li>
 *   <li>
 *       Execution phase
 *       <p>In this phase, process executor is ready to be used for executing a particular node handler</p>
 *   </li>
 *   <li>
 *       CleanUp phase
 *       <p>The phase where process executor can cleanup resources it used during process execution</p>
 *   </li>
 * </ul>
 * 
 * @author Eka Lie
 */
public interface ProcessExecutor {

    /**
     * Initialize process executor.
     * @param process target process
     * @param datas input data(s)
     * @throws ProcessExecutionException if any exception occurred while initializing the executor
     */
    void init(Process process, Set<Data> datas) throws ProcessExecutionException;

    /**
     * Execute a node handler.
     * @param process target process
     * @param nodeHandler the node handler
     * @return node execution result
     * @throws ProcessExecutionException if any exception occurred while executing the node handler
     */
    Object executeNodeHandler(Process process, NodeHandler nodeHandler) throws ProcessExecutionException;

    /**
     * Tell the executor to do any necessary clean-up.
     * @throws ProcessExecutionException if any exception occurred while cleaning up
     */
    void finish() throws ProcessExecutionException;

    /**
     * Get the input data(s) used for process execution.
     * @return process execution input data(s)
     */
    Set<Data> getInputDatas();

    /**
     * Get the output data(s) resulting from process execution.
     * @return process execution output data(s)
     */
    Set<Data> getOutputDatas();
}
