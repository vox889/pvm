package com.ehxnv.pvm.api.data;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for {@link DecimalData}.
 *
 * @author Eka Lie
 */
public class DecimalDataTest {

    /**
     * Test of getValue method, of class DecimalData.
     */
    @Test
    public void testGetValue() throws Exception {
        DecimalData data = new DecimalData("data", BigDecimal.valueOf(1000));
        assertEquals(BigDecimal.valueOf(1000), data.getValue());
    }

    /**
     * Test of getType method, of class DecimalData.
     */
    @Test
    public void testGetType() throws Exception {
        DecimalData data = new DecimalData("data", BigDecimal.valueOf(1000));
        assertEquals(DataType.DECIMAL, data.getType());
    }

    /**
     * Test of setValue method, of class DecimalData.
     */
    @Test(expected = InvalidValueException.class)
    public void testSetValue() {
        DecimalData data = new DecimalData("data", BigDecimal.valueOf(1000));
        data.setValue("12345a");
    }
}
