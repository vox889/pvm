package com.ehxnv.pvm.api.data;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit test for {@link DataDefinition}.
 *
 * @author Eka Lie
 */
public class DataDefinitionTest {

    /**
     * Test of getDataName method, of class DataDefinition.
     */
    @Test
    public void testGetDataName() throws Exception {
        DataDefinition dataDefinition = new DataDefinition("dataName", DataType.BOOLEAN);
        assertEquals("dataName", dataDefinition.getDataName());
    }

    /**
     * Test of getDataType method, of class DataDefinition.
     */
    @Test
    public void testGetDataType() throws Exception {
        DataDefinition dataDefinition = new DataDefinition("dataName", DataType.BOOLEAN);
        assertEquals(DataType.BOOLEAN, dataDefinition.getDataType());
    }
}
