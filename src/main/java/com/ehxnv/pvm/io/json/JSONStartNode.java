/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm.io.json;

import com.ehxnv.pvm.execution.NodeHandler;
import com.ehxnv.pvm.execution.js.JavaScriptNodeHandler;
import com.ehxnv.pvm.graph.AbstractStartNode;
import com.ehxnv.pvm.graph.StartNode;
import static com.ehxnv.pvm.util.ValidationUtil.*;

/**
 * JSON based {@link StartNode}.
 * 
 * @author Eka Lie
 */
class JSONStartNode extends AbstractStartNode {

    /** JavaScript function name. **/
    private String jsFuncName;

    /**
     * Constructor.
     * @param name node name
     * @param nextNodeName next node name
     * @param jsFuncName JavaScript function name
     */
    public JSONStartNode(final String name, final String nextNodeName, final String jsFuncName) {
        super(name, nextNodeName);
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
