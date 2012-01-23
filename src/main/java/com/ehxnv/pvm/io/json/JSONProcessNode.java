/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm.io.json;

import com.ehxnv.pvm.execution.NodeHandler;
import com.ehxnv.pvm.execution.js.JavaScriptNodeHandler;
import com.ehxnv.pvm.graph.AbstractProcessNode;
import com.ehxnv.pvm.graph.ProcessNode;
import static com.ehxnv.pvm.util.ValidationUtil.*;

/**
 * JSON based {@link ProcessNode}.
 * 
 * @author Eka Lie
 */
class JSONProcessNode extends AbstractProcessNode {

    /** JavaScript function name. **/
    private String jsFuncName;

    /**
     * Constructor.
     * @param name node name
     * @param nextNodeName next node name
     * @param jsFuncName JavaScript function name
     */
    public JSONProcessNode(final String name, final String nextNodeName, final String jsFuncName) {
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
