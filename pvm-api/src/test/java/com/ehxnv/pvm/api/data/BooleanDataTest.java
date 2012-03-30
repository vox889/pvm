package com.ehxnv.pvm.api.data;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit test for {@link BooleanData}.
 *
 * @author Eka Lie
 */
public class BooleanDataTest {

    /**
     * Test of getValue method, of class BooleanData.
     */
    @Test
    public void testGetValue() throws Exception {
        BooleanData data = new BooleanData("data", Boolean.TRUE);
        assertEquals(Boolean.TRUE, data.getValue());
    }

    /**
     * Test of setValue method, of class BooleanData.
     */
    @Test
    public void testGetType() throws Exception {
        BooleanData data = new BooleanData("data", Boolean.TRUE);
        assertEquals(DataType.BOOLEAN, data.getType());
    }
}
