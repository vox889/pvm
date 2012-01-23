/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm.graph;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit test for {@link AbstractSingleNavigableNode}.
 *
 * @author Eka Lie
 */
public class AbstractSingleNavigableNodeTest {

    /**
     * Test of getNextNodeName method, of class AbstractSingleNavigableNode.
     */
    @Test
    public void testGetNextNodeName() {
        AbstractSingleNavigableNode instance = new AbstractSingleNavigableNode("SingleNavigableNode", "NextNode") {};
        assertEquals("SingleNavigableNode", instance.getName());
        assertEquals("NextNode", instance.getNextNodeName());
    }
}
