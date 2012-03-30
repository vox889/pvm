/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm.api.util;

import org.junit.Test;

/**
 * Unit test for {@link ValidationUtil}.
 * 
 * @author Eka Lie
 */
public class ValidationUtilTest {

    /**
     * Test of checkForNull method, of class ValidationUtil.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCheckForNull() {
        ValidationUtil.checkForNull("testObj", null);
    }
}
