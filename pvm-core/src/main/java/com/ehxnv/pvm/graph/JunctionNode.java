/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm.graph;

import java.util.Set;

/**
 * Represents a junction node. A junction node behaves similarly with process node
 * except that the execution of junction node should returns the next node name.
 * 
 * @author Eka Lie
 */
public interface JunctionNode extends Node, Navigable, ExecutableNode {

    /**
     * Get next possible node names.
     * @return next possible node names
     */
    Set<String> getNextPossibleNodeNames();
}
