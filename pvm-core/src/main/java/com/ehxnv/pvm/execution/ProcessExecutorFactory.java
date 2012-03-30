/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm.execution;

import com.ehxnv.pvm.api.execution.ExecutionEnv;
import com.ehxnv.pvm.api.execution.ProcessExecutor;
import com.ehxnv.pvm.execution.js.JavaScriptExecutionEnv;
import com.ehxnv.pvm.execution.js.JavaScriptProcessExecutor;

/**
 * Factory class responsible for creating {@link ProcessExecutor}.
 * 
 * @author Eka Lie
 */
public abstract class ProcessExecutorFactory {
    
    /**
     * Create a process executor based on process execution environment.
     * @param env process execution environment
     * @return process executor suited for the environment
     */
    public static ProcessExecutor createProcessExecutor(final ExecutionEnv env) {
        if (env instanceof JavaScriptExecutionEnv) {
            JavaScriptExecutionEnv javaScriptExecEnv = (JavaScriptExecutionEnv) env;
            return new JavaScriptProcessExecutor(javaScriptExecEnv.getJavaScript());
        } else {
            throw new UnsupportedOperationException("Unsupported execution environment");
        }
    }
}
