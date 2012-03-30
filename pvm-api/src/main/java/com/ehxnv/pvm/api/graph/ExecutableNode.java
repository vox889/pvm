/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm.api.graph;

import com.ehxnv.pvm.api.execution.NodeHandler;

/**
 * Mark that a node can be executed.
 * 
 * @author Eka Lie
 */
public interface ExecutableNode {

    /**
     * Get the node handler responsible for this node execution.
     * @return node handler
     */
    NodeHandler getNodeHandler();
}
