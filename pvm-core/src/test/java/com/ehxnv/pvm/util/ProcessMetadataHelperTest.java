/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm.util;

import com.ehxnv.pvm.api.ProcessMetadata;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for {@link ProcessMetadataHelper}.
 * 
 * @author Eka Lie
 */
public class ProcessMetadataHelperTest {

    /**
     * Test of getMetadata method, of class ProcessMetadataHelper.
     */
    @Test()
    public void testGetMetadata() {
        ProcessMetadata result = ProcessMetadataHelper.getMetadata("ABC_1");
        assertEquals("ABC", result.getName());
        assertEquals("1", result.getVersion());

        result = ProcessMetadataHelper.getMetadata("ABCDE_1.2");
        assertEquals("ABCDE", result.getName());
        assertEquals("1.2", result.getVersion());

        result = ProcessMetadataHelper.getMetadata("ABCDEF");
        assertEquals("ABCDEF", result.getName());
        assertEquals("1.0", result.getVersion());
    }
}
