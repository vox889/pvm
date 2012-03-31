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

package com.ehxnv.pvm.execution;

import com.ehxnv.pvm.api.Process;
import com.ehxnv.pvm.api.data.Data;
import com.ehxnv.pvm.api.execution.NodeHandler;
import com.ehxnv.pvm.api.execution.ProcessExecutionException;
import com.ehxnv.pvm.api.execution.ProcessExecutor;

import java.util.Set;

/**
 * Abstract implementation of {@link ProcessExecutor} which adds state checking on initialize, execution and cleanup phase.
 * 
 * @author Eka Lie
 */
public abstract class AbstractProcessExecutor implements ProcessExecutor {

    /** Represents the current state of the process executor. **/
    private State state = State.UNINITIALIZED;

    /**
     * {@inheritDoc}
     */
    public void init(final Process process, final Set<Data> datas) throws ProcessExecutionException {
        if (state != State.UNINITIALIZED) {
            throw new IllegalStateException("Failed to initialize process executor from non uninitialized state");
        }

        initInternal(process,  datas);
        state = State.READY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object executeNodeHandler(final Process process, final NodeHandler nodeHandler) throws ProcessExecutionException {
        if (state != State.READY) {
            throw new IllegalStateException("Failed to execute node handler from non ready state");
        }

        return executeNodeHandlerInternal(process, nodeHandler);
    }

    /**
     * {@inheritDoc}
     */
    public void finish() throws ProcessExecutionException {
        if (state != State.READY) {
            throw new IllegalStateException("Failed to finish process executor from non ready state");
        }

        finishInternal();
    }

    /**
     * Internal implementation of process executor to initialize itself.
     * @param process target process
     * @param datas execution input datas
     * @throws ProcessExecutionException if any exception occured during process execution
     */
    protected abstract void initInternal(Process process, Set<Data> datas) throws ProcessExecutionException;

    /**
     * Internal implementation of process executor to execute a particular node handler
     * @param process target process
     * @param nodeHandler target node handler
     * @return execution result
     * @throws ProcessExecutionException if any exception occured during process execution
     */
    protected abstract Object executeNodeHandlerInternal(Process process, NodeHandler nodeHandler) throws ProcessExecutionException;

    /**
     * Internal implementation of process executor for cleanup after process execution
     * @throws ProcessExecutionException if any exception occured during process execution
     */
    protected abstract void finishInternal() throws ProcessExecutionException;

    /**
     * Process executor avaiable states.
     */
    private static enum State {
        /** Process executor is not initialized and not ready to be used for execution. **/
        UNINITIALIZED,
        /** Process executor is ready to be used for execution. **/
        READY,
        /** Process executor has done his job. **/
        FINISHED
    }
}
