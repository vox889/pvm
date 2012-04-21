package com.ehxnv.pvm.data.io.json;

import com.ehxnv.pvm.api.data.*;
import com.ehxnv.pvm.api.data.io.DataWriter;
import com.ehxnv.pvm.data.io.DataWriterFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for {@link JSONDataWriter}.
 * @author Eka Lie
 */
public class JSONDataWriterTest {

    /** Data writer instance. **/
    private DataWriter dataWriter;

    /**
     * {@inheritDoc}
     */
    @Before
    public void setUp() throws Exception {
        dataWriter = DataWriterFactory.createJSONDataWriter();
    }

    /**
     * Test of writeData method, of class JSONDataWriter.
     */
    @Test
    public void testWriteData() throws Exception {
        BooleanData booleanData = new BooleanData("boolData", false);
        DecimalData decimalData = new DecimalData("decData", BigDecimal.valueOf(101));
        IntegerData integerData = new IntegerData("intData", 889);
        StringData stringData = new StringData("strData", "HellSing");

        Set<Data> outputDatas = new HashSet<Data>();
        outputDatas.add(booleanData);
        outputDatas.add(decimalData);
        outputDatas.add(integerData);
        outputDatas.add(stringData);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        dataWriter.writeData(outputDatas, byteArrayOutputStream);

        String output = new String(byteArrayOutputStream.toByteArray());
        assertTrue(output.startsWith("{"));
        assertTrue(output.contains("\"boolData\":false"));
        assertTrue(output.contains("\"decData\":101"));
        assertTrue(output.contains("\"intData\":889"));
        assertTrue(output.contains("\"strData\":\"HellSing\""));
        assertTrue(output.endsWith("}"));
    }
}
