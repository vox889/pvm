/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm.graph;

import com.ehxnv.pvm.api.graph.JunctionNode;

import java.util.Set;

/**
 * Abstract implementation of {@link com.ehxnv.pvm.api.graph.JunctionNode}.
 * 
 * @author Eka Lie
 */
public abstract class AbstractJunctionNode extends AbstractNode implements JunctionNode {

    /** Next possible node names. **/
    private Set<String> nextPossibleNodeNames;

    /**
     * Constructor.
     * @param name node name
     * @param nextPossibleNodeNames next possible node names
     */
    public AbstractJunctionNode(final String name, final Set<String> nextPossibleNodeNames) {
        super(name);

        if (nextPossibleNodeNames == null || nextPossibleNodeNames.isEmpty()) {
            throw new IllegalArgumentException("Junction node next possible node names can't be null and have to be not empty");
        }

        this.nextPossibleNodeNames = nextPossibleNodeNames;
    }

    /**
     * {@inheritDoc}
     */
    public Set<String> getNextPossibleNodeNames() {
        return nextPossibleNodeNames;
    }    
}
