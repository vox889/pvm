/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm.io.json;

import com.ehxnv.pvm.api.execution.NodeHandler;
import com.ehxnv.pvm.execution.js.JavaScriptNodeHandler;
import com.ehxnv.pvm.graph.AbstractJunctionNode;

import java.util.Set;

import static com.ehxnv.pvm.api.util.ValidationUtil.checkForNull;

/**
 * JSON based {@link com.ehxnv.pvm.api.graph.JunctionNode}.
 * 
 * @author Eka Lie
 */
class JSONJunctionNode extends AbstractJunctionNode {

    /** JavaScript function name. **/
    private String jsFuncName;

    /**
     * Constructor.
     * @param name node name
     * @param nextPossibleNodeNames next possible node names
     * @param jsFuncName JavaScript function name
     */
    public JSONJunctionNode(final String name, final Set<String> nextPossibleNodeNames, final String jsFuncName) {
        super(name, nextPossibleNodeNames);
        checkForNull("JavaScript function name", jsFuncName);
        this.jsFuncName = jsFuncName;
    }

    /**
     * {@inheritDoc}
     */
    public NodeHandler getNodeHandler() {
        return new JavaScriptNodeHandler(jsFuncName);
    }    
}
