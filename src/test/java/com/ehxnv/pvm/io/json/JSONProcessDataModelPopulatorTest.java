/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm.io.json;

import com.ehxnv.pvm.ProcessMetadata;
import com.ehxnv.pvm.data.DataDefinition;
import com.ehxnv.pvm.data.DataModel;
import com.ehxnv.pvm.data.DataType;
import java.io.InputStream;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit test for {@link JSONProcessDataModelPopulator}.
 * 
 * @author Eka Lie
 */
public class JSONProcessDataModelPopulatorTest {
    
    /**
     * Test of populateProcessInputDataModel method, of class JSONProcessDataModelPopulator.
     */
    @Test
    public void testPopulateProcessInputDataModel() throws Exception {
        InputStream inputStream = getClass().getResourceAsStream("model_input.json");
        JSONProcess process = new JSONProcess(new ProcessMetadata("test_basic", "1.0"));

        JSONProcessDataModelPopulator.populateProcessInputDataModel(inputStream, process);
        
        DataModel dataModel = process.getInputDataModel();
        final Object[][] dataDefDetails = new Object[][] {
                    { "first_name", DataType.STRING },
                    { "last_name", DataType.STRING },
                    { "age", DataType.INTEGER },
                    { "married", DataType.BOOLEAN },
                    { "salary", DataType.DECIMAL }};

        assertEquals(dataDefDetails.length, dataModel.getDataDefinitions().size());

        for (Object[] dataDefDetail : dataDefDetails) {
            boolean matched = false;
            for (DataDefinition dataDef : dataModel.getDataDefinitions()) {
                if (dataDef.getDataName().equals(dataDefDetail[0])
                        && dataDef.getDataType().equals(dataDefDetail[1])) {
                    matched = true;
                }
            }

            if (!matched) {
                fail();
            }
        }
    }
    
    /**
     * Test of populateProcessOutputDataModel method, of class JSONProcessDataModelPopulator.
     */
    @Test
    public void testPopulateProcessOutputDataModel() throws Exception {
        InputStream inputStream = getClass().getResourceAsStream("model_output.json");
        JSONProcess process = new JSONProcess(new ProcessMetadata("test_basic", "1.0"));

        JSONProcessDataModelPopulator.populateProcessOutputDataModel(inputStream, process);
        
        DataModel dataModel = process.getOutputDataModel();
        final Object[][] dataDefDetails = new Object[][] {
                    { "first_name", DataType.STRING },
                    { "last_name", DataType.STRING },
                    { "age", DataType.INTEGER },
                    { "married", DataType.BOOLEAN },
                    { "salary", DataType.DECIMAL }};

        assertEquals(dataDefDetails.length, dataModel.getDataDefinitions().size());

        for (Object[] dataDefDetail : dataDefDetails) {
            boolean matched = false;
            for (DataDefinition dataDef : dataModel.getDataDefinitions()) {
                if (dataDef.getDataName().equals(dataDefDetail[0])
                        && dataDef.getDataType().equals(dataDefDetail[1])) {
                    matched = true;
                }
            }

            if (!matched) {
                fail();
            }
        }
    }
}
