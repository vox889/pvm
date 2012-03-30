/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm.api.graph;

/**
 * Represents a navigable node that can have 1 to N destination.
 * 
 * @author Eka Lie
 */
public interface MultiNavigableNode extends Node, Navigable {

    /**
     * Get next node names.
     * @return next node names
     */
    String[] getNextNodesNames();
}
