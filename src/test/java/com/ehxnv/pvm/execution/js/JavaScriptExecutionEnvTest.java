/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm.execution.js;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit test for {@link JavaScriptExecutionEnv}.
 * 
 * @author Eka Lie
 */
public class JavaScriptExecutionEnvTest {
    
    /**
     * Test of getName method, of class JavaScriptExecutionEnv.
     */
    @Test
    public void testGetName() {
        JavaScriptExecutionEnv jsEnv = new JavaScriptExecutionEnv("my script");
        assertEquals("JavaScript", jsEnv.getName());
    }

    /**
     * Test of getJavaScript method, of class JavaScriptExecutionEnv.
     */
    @Test
    public void testGetJavaScript() {
        JavaScriptExecutionEnv jsEnv = new JavaScriptExecutionEnv("my script");
        assertEquals("my script", jsEnv.getJavaScript());
    }
}
