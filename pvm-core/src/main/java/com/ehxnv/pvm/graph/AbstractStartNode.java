/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm.graph;

import com.ehxnv.pvm.api.graph.StartNode;

/**
 * Abstract implementation of {@link com.ehxnv.pvm.api.graph.StartNode}.
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
