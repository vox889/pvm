/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm.io.json;

import com.ehxnv.pvm.execution.NodeHandler;
import com.ehxnv.pvm.execution.js.JavaScriptNodeHandler;
import com.ehxnv.pvm.graph.AbstractJunctionNode;
import com.ehxnv.pvm.graph.JunctionNode;
import java.util.Set;
import static com.ehxnv.pvm.util.ValidationUtil.*;

/**
 * JSON based {@link JunctionNode}.
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
