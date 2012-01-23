/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm.execution.js;

import com.ehxnv.pvm.execution.ExecutionEnv;
import static com.ehxnv.pvm.util.ValidationUtil.*;

/**
 * Represents a JavaScript based {@link ExecutionEnv}.
 * 
 * @author Eka Lie
 */
public class JavaScriptExecutionEnv implements ExecutionEnv {

    /** JavaScript to be used inside the environment. **/
    private String javaScript;

    /**
     * Constructor.
     * @param javaScript JavaScript to be used inside the environment.
     */
    public JavaScriptExecutionEnv(final String javaScript) {
        checkForNull("JavaScript content", javaScript);
        this.javaScript = javaScript;
    }

    /**
     * {@inheritDoc}
     */
    public String getName() {
        return "JavaScript";
    }

    /**
     * Get the JavaScript content.
     * @return JavaScript content
     */
    public String getJavaScript() {
        return javaScript;
    }
}
