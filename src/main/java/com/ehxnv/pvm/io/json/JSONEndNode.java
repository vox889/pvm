/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm.io.json;

import com.ehxnv.pvm.execution.NodeHandler;
import com.ehxnv.pvm.execution.js.JavaScriptNodeHandler;
import com.ehxnv.pvm.graph.AbstractEndNode;
import com.ehxnv.pvm.graph.EndNode;
import static com.ehxnv.pvm.util.ValidationUtil.*;

/**
 * JSON based {@link EndNode}.
 * 
 * @author Eka Lie
 */
class JSONEndNode extends AbstractEndNode {

    /** JavaScript function name. **/
    private String jsFuncName;

    /**
     * Constructor.
     * @param name node name
     * @param jsFuncName JavaScript function name
     */
    public JSONEndNode(final String name, final String jsFuncName) {
        super(name);
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
