/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm;

import com.ehxnv.pvm.data.Data;
import com.ehxnv.pvm.data.DataDefinition;
import com.ehxnv.pvm.data.DataModel;
import com.ehxnv.pvm.data.DataType;
import com.ehxnv.pvm.execution.ProcessExecutionException;
import com.ehxnv.pvm.execution.ProcessExecutor;
import com.ehxnv.pvm.execution.ProcessExecutorFactory;
import com.ehxnv.pvm.graph.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Abstract implementation of {@link ProcessVirtualMachine} which handles:
 * <ul>
 *   <li>Process state</li>
 *   <li>Basic process execution flow through executeProcessInternal(...)</li>
 * </ul>
 * 
 * @author Eka Lie
 */
public abstract class AbstractProcessVirtualMachine implements ProcessVirtualMachine {

    /** Logger. **/
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractProcessVirtualMachine.class);
    /** Flag to indicate whether the PVM have started or not. **/
    private boolean started = false;

    /**
     * Execute process internally.
     * @param process target process
     * @return process instance
     */
    protected ProcessInstance executeProcessInternal(final Process process, final Set<Data> inputDatas) throws ProcessExecutionException {
        checkState(true);

        if (!isValidInputDatas(inputDatas, process.getInputDataModel())) {
            throw new ProcessExecutionException("One of the input data doesn't defined in process input data model");
        }

        ProcessInstance processInstance = createProcessInstance();
        processInstance.setInputDatas(inputDatas);

        LOGGER.info("Process instance {} started on {}", processInstance.getId(), processInstance.getStartTime());
        LOGGER.info("Using {} process execution environment", process.getExecutionEnvironment().getName());

        // get the process executor based on process execution environment
        ProcessExecutor executor = ProcessExecutorFactory.createProcessExecutor(process.getExecutionEnvironment()); 

        // initialize process executor, passing process input data model, output data model and input datas
        executor.init(process, inputDatas);

        // navigate from start node and start executing and navigating node
        Node curNode = process.getStartNode();

        while (true) {

            Object executionResult = null;
            if (curNode instanceof ExecutableNode) {
                LOGGER.debug("Executing node \"{}\"", curNode.getName());
                executionResult = executor.executeNodeHandler(process, ((ExecutableNode) curNode).getNodeHandler());
            }

            if (!(curNode instanceof Navigable)) {
                break;
            }

            if (curNode instanceof SingleNavigableNode) {
                String nextNodeName = ((SingleNavigableNode) curNode).getNextNodeName();
                Node nextNode = process.getNodeByName(nextNodeName);
                LOGGER.debug("Transitioning to node \"{}\"", nextNode.getName());
                curNode = nextNode;
            } else if (curNode instanceof JunctionNode) {
                String nextNodeName = String.valueOf(executionResult);
                Node nextNode = process.getNodeByName(nextNodeName);

                JunctionNode junctionNode = (JunctionNode) curNode;
                if (!junctionNode.getNextPossibleNodeNames().contains(nextNodeName)) {
                    throw new ProcessExecutionException(executionResult + " is not one of " + junctionNode.getName() + " possible outcomes");
                }

                LOGGER.debug("Transitioning to node \"{}\"", nextNode.getName());
                curNode = nextNode;
            } else if (curNode instanceof MultiNavigableNode) {
                // TODO: support MultiNavigableNode execution
                throw new UnsupportedOperationException("Parallel navigation is not supported yet");
            } else {
                throw new ProcessExecutionException("Unknown node " + curNode);
            }
        }

        // tell the executor that we're finished using it
        executor.finish();

        processInstance.setOutputDatas(executor.getOutputDatas());
        processInstance.setEndTime(new Date());
        
        LOGGER.info("Process instance {} ended on {}", processInstance.getId(), processInstance.getEndTime());
        return processInstance;
    }

    /**
     * Get name of this PVM.
     * @return PVM name
     */
    protected abstract String getName();

    /**
     * Create a specific process instance.
     * @return process instance
     */
    protected abstract ProcessInstance createProcessInstance();

    /**
     * Get next process instance id.
     * @return next process instance id
     */
    protected abstract String getNextProcessInstanceId();

    /**
     * {@inheritDoc}
     */
    public void start() {
        checkState(false);
        LOGGER.info("{} PVM started on {}", getName(), new Date());
        startInternal();
        started = true;
    }

    /**
     * {@inheritDoc}
     */
    public void stop() {
        checkState(true);
        LOGGER.info("{} PVM stopped on {}", getName(), new Date());
        stopInternal();
        started = false;
    }

    /**
     * Check state of this process virtual machine
     * @param startedState true to check for started state, false otherwise
     */
    private void checkState(boolean startedState) {
        if (startedState) {
            if (!started) {
                throw new IllegalStateException(getName() + " is not yet started");
            }
        } else {
            if (started) {
                throw new IllegalStateException(getName() + " is already started");
            }
        }
    }

    /**
     * Internal start mechanism.
     */
    protected abstract void startInternal();

    /**
     * Internal stop mechanism.
     */
    protected abstract void stopInternal();

    /**
     * Determine if passed input datas is valid according to process data model. Each input data valid if:
     * <ul>
     *     <li>The data passed has been defined in the input model (based on data name)</li>
     *     <li>Data type of the data match with the one in the input model</li>
     * </ul>
     * @param inputDatas input datas
     * @param inputDataModel process input data model
     * @return true if all input data(s) are valid, alse otherwise
     */
    private boolean isValidInputDatas(final Set<Data> inputDatas, final DataModel inputDataModel) {
        Set<DataDefinition> dataDefinitions = inputDataModel.getDataDefinitions();
        Map<String, DataType> dataDefTypes = new HashMap<String, DataType>();
        for (DataDefinition dataDefinition : dataDefinitions) {
            dataDefTypes.put(dataDefinition.getDataName(), dataDefinition.getDataType());
        }
                
        for (Data data : inputDatas) {
            DataType matchedDataType = dataDefTypes.get(data.getName());
            if (matchedDataType == null || !matchedDataType.equals(data.getType())) {
                return false;
            }
        }

        return true;
    }
}
