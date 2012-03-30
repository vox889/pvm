package com.ehxnv.pvm.api.data;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Unit test for {@link DataFactory}.
 *
 * @author Eka Lie
 */
public class DataFactoryTest {

    /**
     * Test of createData method, of class DataFactory.
     */
    @Test
    public void testCreateData() throws Exception {
        DataDefinition booleanDataDefinition = new DataDefinition("booleanData", DataType.BOOLEAN);
        Data booleanData = DataFactory.createData(booleanDataDefinition);
        assertEquals(booleanData.getName(), "booleanData");
        assertEquals(booleanData.getType(), DataType.BOOLEAN);
        assertEquals(booleanData.getValue(), Boolean.FALSE);

        DataDefinition decimalDataDefinition = new DataDefinition("decimalData", DataType.DECIMAL);
        Data decimalData = DataFactory.createData(decimalDataDefinition);
        assertEquals(decimalData.getName(), "decimalData");
        assertEquals(decimalData.getType(), DataType.DECIMAL);
        assertEquals(decimalData.getValue(), BigDecimal.ZERO);

        DataDefinition integerDataDefinition = new DataDefinition("integerData", DataType.INTEGER);
        Data integerData = DataFactory.createData(integerDataDefinition);
        assertEquals(integerData.getName(), "integerData");
        assertEquals(integerData.getType(), DataType.INTEGER);
        assertEquals(integerData.getValue(), 0);

        DataDefinition stringDataDefinition = new DataDefinition("stringData", DataType.STRING);
        Data stringData = DataFactory.createData(stringDataDefinition);
        assertEquals(stringData.getName(), "stringData");
        assertEquals(stringData.getType(), DataType.STRING);
        assertEquals(stringData.getValue(), "");
    }
}
