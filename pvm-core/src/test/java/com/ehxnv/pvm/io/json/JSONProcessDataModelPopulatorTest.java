/*******************************************************************************
 * Copyright (c) 2012, Eka Heksanov Lie
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the organization nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL COPYRIGHT HOLDER BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/

package com.ehxnv.pvm.io.json;

import com.ehxnv.pvm.api.ProcessMetadata;
import com.ehxnv.pvm.api.data.DataDefinition;
import com.ehxnv.pvm.api.data.DataModel;
import com.ehxnv.pvm.api.data.DataType;
import org.junit.Test;

import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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
