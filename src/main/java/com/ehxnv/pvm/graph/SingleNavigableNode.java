/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm.graph;

/**
 * Represents a navigable node that have exactly 1 destination.
 * 
 * @author Eka Lie
 */
public interface SingleNavigableNode extends Node, Navigable {

    /**
     * Get next node name.
     * @return next node name
     */
    String getNextNodeName();
}
