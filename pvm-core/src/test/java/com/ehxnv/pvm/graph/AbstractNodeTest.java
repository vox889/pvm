/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm.graph;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for {@link AbstractNode}.
 *
 * @author Eka Lie
 */
public class AbstractNodeTest {

    /**
     * Test of getName method, of class AbstractNode.
     */
    @Test
    public void testGetName() {
        AbstractNode instance = new AbstractNode("DummyName") { };
        assertEquals("DummyName", instance.getName());
    }
}
