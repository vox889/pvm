/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm.graph;

/**
 * Abstract implementation of {@link EndNode}.
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
