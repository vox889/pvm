/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm.graph;

import com.ehxnv.pvm.execution.NodeHandler;

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
