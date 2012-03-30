package com.ehxnv.pvm.api.data;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for {@link StringData}.
 *
 * @author Eka Lie
 */
public class StringDataTest {

    /**
     * Test of getValue method, of class StringData.
     */
    @Test
    public void testGetValue() throws Exception {
        StringData data = new StringData("data", "hello world");
        assertEquals("hello world", data.getValue());
    }

    /**
     * Test of getType method, of class StringData.
     */
    @Test
    public void testGetType() throws Exception {
        StringData data = new StringData("data", "hello world");
        assertEquals(DataType.STRING, data.getType());
    }
}
