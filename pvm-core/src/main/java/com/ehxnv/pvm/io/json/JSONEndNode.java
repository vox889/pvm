/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm.io.json;

import com.ehxnv.pvm.api.execution.NodeHandler;
import com.ehxnv.pvm.execution.js.JavaScriptNodeHandler;
import com.ehxnv.pvm.graph.AbstractEndNode;

import static com.ehxnv.pvm.api.util.ValidationUtil.checkForNull;

/**
 * JSON based {@link com.ehxnv.pvm.api.graph.EndNode}.
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
