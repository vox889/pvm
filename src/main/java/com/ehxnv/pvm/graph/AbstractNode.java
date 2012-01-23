/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm.graph;
import static com.ehxnv.pvm.util.ValidationUtil.*;

/**
 * Abstract implementation of {@link Node}.
 * 
 * @author Eka Lie
 */
public abstract class AbstractNode implements Node {

    /** Node name. **/
    private String name;

    /**
     * Constructor.
     * @param name node name
     */
    public AbstractNode(final String name) {
        checkForNull("Node name", name);
        this.name = name;
    }

    /**
     * {@inheritDoc}
     */
    public String getName() {
        return name;
    }    
}
