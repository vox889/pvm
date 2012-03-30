/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm.execution.js;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for {@link JavaScriptNodeHandler}.
 * 
 * @author Eka Lie
 */
public class JavaScriptNodeHandlerTest {
    
    /**
     * Test of getJsFunctionName method, of class JavaScriptNodeHandler.
     */
    @Test
    public void testGetJsFunctionName() {
        JavaScriptNodeHandler jsNodeHandler = new JavaScriptNodeHandler("myFunc");
        assertEquals("myFunc", jsNodeHandler.getJsFunctionName());
    }
}
