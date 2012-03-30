package com.ehxnv.pvm.data;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for {@link IntegerData}.
 *
 * @author Eka Lie
 */
public class IntegerDataTest {

    /**
     * Test of getValue method, of class IntegerData.
     */
    @Test
    public void testGetValue() throws Exception {
        IntegerData data = new IntegerData("data", 100);
        assertEquals(Integer.valueOf(100), data.getValue());
    }

    /**
     * Test of getType method, of class IntegerData.
     */
    @Test
    public void testGetType() throws Exception {
        IntegerData data = new IntegerData("data", 100);
        assertEquals(DataType.INTEGER, data.getType());
    }
    
    /**
     * Test of setValue method, of class IntegerData.
     */
    @Test(expected = InvalidValueException.class)
    public void testSetValue() {
        IntegerData data = new IntegerData("data", 100);
        data.setValue("12345a");
    }
}
