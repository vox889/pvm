/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm.graph;
import com.ehxnv.pvm.api.graph.SingleNavigableNode;

import static com.ehxnv.pvm.api.util.ValidationUtil.checkForNull;

/**
 * Abstract implementation of {@link SingleNavigableNode}.
 * 
 * @author Eka Lie
 */
public abstract class AbstractSingleNavigableNode extends AbstractNode implements SingleNavigableNode {
    
    /** Next node name. **/
    private String nextNodeName;

    /**
     * Constructor.
     * @param name node name
     * @param nextNodeName next node name
     */
    public AbstractSingleNavigableNode(final String name, final String nextNodeName) {
        super(name);
        checkForNull("Next node name", name);
        this.nextNodeName = nextNodeName;
    }

    /**
     * {@inheritDoc}
     */
    public String getNextNodeName() {
        return nextNodeName;
    }    
}
