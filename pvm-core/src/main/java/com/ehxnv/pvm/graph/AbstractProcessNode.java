/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm.graph;

import com.ehxnv.pvm.api.graph.ProcessNode;

/**
 * Abstract implementation of {@link com.ehxnv.pvm.api.graph.ProcessNode}.
 * 
 * @author Eka Lie
 */
public abstract class AbstractProcessNode extends AbstractSingleNavigableNode implements ProcessNode {

    /**
     * Constructor.
     * @param name node name
     * @param nextNodeName next node name
     */
    public AbstractProcessNode(final String name, final String nextNodeName) {
        super(name, nextNodeName);
    }    
}
