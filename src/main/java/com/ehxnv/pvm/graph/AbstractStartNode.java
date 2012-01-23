/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm.graph;

/**
 * Abstract implementation of {@link StartNode}.
 * 
 * @author Eka Lie
 */
public abstract class AbstractStartNode extends AbstractProcessNode implements StartNode {

    /**
     * Constructor.
     * @param name node name
     * @param nextNodeName next node name
     */
    public AbstractStartNode(final String name, final String nextNodeName) {
        super(name, nextNodeName);
    }
}
