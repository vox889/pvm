package com.ehxnv.pvm.data;

import org.junit.Test;
import static org.junit.Assert.*;


/**
 * Unit test for {@link DataModel}.
 *
 * @author Eka Lie
 */
public class DataModelTest {

    /**
     * Test of getName method, of class DataModel.
     */
    @Test
    public void testGetName() throws Exception {
        DataModel model = new DataModel("myModel");
        assertEquals("myModel", model.getName());
    }

    /**
     * Test of addDataDefinition method, of class DataModel.
     */
    @Test
    public void testAddDataDefinition() throws Exception {
        DataModel model = new DataModel("myModel");
        DataDefinition dataDefinition1 = new DataDefinition("data1", DataType.INTEGER);
        model.addDataDefinition(dataDefinition1);
        assertEquals(1, model.getDataDefinitions().size());

        DataDefinition dataDefinition2 = new DataDefinition("data1", DataType.INTEGER);
        model.addDataDefinition(dataDefinition2);
        assertEquals(1, model.getDataDefinitions().size());
    }

    /**
     * Test of getDataDefinitions method, of class DataModel.
     */
    @Test
    public void testGetDataDefinitions() throws Exception {
        DataModel model = new DataModel("myModel");
        assertTrue(model.getDataDefinitions().isEmpty());

        DataDefinition dataDefinition1 = new DataDefinition("data1", DataType.INTEGER);
        model.addDataDefinition(dataDefinition1);
        assertEquals(1, model.getDataDefinitions().size());
    }
}
