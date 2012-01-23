/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm.execution;

import com.ehxnv.pvm.execution.js.JavaScriptExecutionEnv;
import com.ehxnv.pvm.execution.js.JavaScriptProcessExecutor;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit test for {@link ProcessExecutorFactory}.
 * 
 * @author Eka Lie
 */
public class ProcessExecutorFactoryTest {

    /**
     * Test of createProcessExecutor method, of class ProcessExecutorFactory.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testCreateProcessExecutor() {
        ProcessExecutor result = ProcessExecutorFactory.createProcessExecutor(new JavaScriptExecutionEnv("some script"));
        assertTrue(result instanceof JavaScriptProcessExecutor);

        ExecutionEnv myEnv = new ExecutionEnv() {

            public String getName() {
                return "my env";
            }
        };
        ProcessExecutorFactory.createProcessExecutor(myEnv);
    }
}
