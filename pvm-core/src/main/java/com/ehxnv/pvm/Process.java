/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm;

import com.ehxnv.pvm.execution.ExecutionEnv;
import com.ehxnv.pvm.data.DataModel;
import com.ehxnv.pvm.graph.Node;
import com.ehxnv.pvm.graph.StartNode;

/**
 * Represents a process.
 * 
 * @author Eka Lie
 */
public interface Process {

    /**
     * Get process metadata.
     * @return process metadata
     */
    ProcessMetadata getMetadata();

    /**
     * Get start node of this process.
     * @return start node
     */
    StartNode getStartNode();

    /**
     * Get node by it's name.
     * @param nodeName node name
     * @return node or NULL if node with the given name can't be found
     */
    Node getNodeByName(String nodeName);

    /**
     * Get process execution environment.
     * @return process execution environment
     */
    ExecutionEnv getExecutionEnvironment();

    /**
     * Get process input data model.
     * @return process input data model
     */
    DataModel getInputDataModel();
    
    /**
     * Get process output data model.
     * @return process output data model
     */
    DataModel getOutputDataModel();
}
