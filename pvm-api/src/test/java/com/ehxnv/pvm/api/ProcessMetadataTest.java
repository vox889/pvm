/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm.api;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit test for {@link ProcessMetadata}.
 * 
 * @author Eka Lie
 */
public class ProcessMetadataTest {

    /**
     * Test of getName method, of class ProcessMetadata.
     */
    @Test
    public void testGetName() {
        ProcessMetadata metadata = new ProcessMetadata("metaName", "1.2");
        assertEquals("metaName", metadata.getName());
    }

    /**
     * Test of getVersion method, of class ProcessMetadata.
     */
    @Test
    public void testGetVersion() {
        ProcessMetadata metadata = new ProcessMetadata("metaName", "1.2");
        assertEquals("1.2", metadata.getVersion());
    }

    /**
     * Test of toString method, of class ProcessMetadata.
     */
    @Test
    public void testToString() {
        ProcessMetadata metadata = new ProcessMetadata("metaName", "1.2");
        assertEquals("metaName[1.2]", metadata.toString());
    }
}
