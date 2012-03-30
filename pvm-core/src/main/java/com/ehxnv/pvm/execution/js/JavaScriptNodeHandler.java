/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm.execution.js;

import com.ehxnv.pvm.api.execution.NodeHandler;

import static com.ehxnv.pvm.api.util.ValidationUtil.checkForNull;

/**
 * Implementation of {@link NodeHandler) which calls JavaScript function name.
 * 
 * @author Eka Lie
 */
public class JavaScriptNodeHandler implements NodeHandler {
    
    /** JavaScript function name. **/
    private String jsFunctionName;

    /**
     * Constructor.
     * @param jsFunctionName JavaScript function name
     */
    public JavaScriptNodeHandler(final String jsFunctionName) {
        checkForNull("JavaScript function name", jsFunctionName);
        this.jsFunctionName = jsFunctionName;
    }

    /**
     * Get JavaScript function name.
     * @return JavaScript function name
     */
    public String getJsFunctionName() {
        return jsFunctionName;
    }    
}
