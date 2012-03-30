/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm.graph;

import com.ehxnv.pvm.api.graph.EndNode;

/**
 * Abstract implementation of {@link com.ehxnv.pvm.api.graph.EndNode}.
 * 
 * @author Eka Lie
 */
public abstract class AbstractEndNode extends AbstractNode implements EndNode {

    /**
     * Constructor.
     * @param name node name
     */
    public AbstractEndNode(final String name) {
        super(name);
    }    
}
