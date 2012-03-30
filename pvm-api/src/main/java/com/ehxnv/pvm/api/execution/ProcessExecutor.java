/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
