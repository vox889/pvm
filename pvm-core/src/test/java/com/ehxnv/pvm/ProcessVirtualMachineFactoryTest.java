/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm;

import com.ehxnv.pvm.api.ProcessVirtualMachine;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for {@link ProcessVirtualMachineFactory}.
 * 
 * @author Eka Lie
 */
public class ProcessVirtualMachineFactoryTest {

    /**
     * Test of createProcessVirtualMachine method, of class ProcessVirtualMachineFactory.
     */
    @Test
    public void testCreateProcessVirtualMachine() {
        Configuration config = new Configuration();
        ProcessVirtualMachine pvm = ProcessVirtualMachineFactory.createProcessVirtualMachine(config);
        assertTrue(pvm instanceof BasicProcessVirtualMachine);
    }
}
