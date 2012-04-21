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

package com.ehxnv.pvm.data.io.json;

import com.ehxnv.pvm.api.data.Data;
import com.ehxnv.pvm.api.data.DataType;
import com.ehxnv.pvm.api.data.io.DataReader;
import com.ehxnv.pvm.api.data.io.DataStructureException;
import com.ehxnv.pvm.data.io.DataReaderFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for {@link JSONDataReader}.
 * @author Eka Lie
 */
public class JSONDataReaderTest {

    /** Data reader instance. **/
    private DataReader dataReader;

    /**
     * {@inheritDoc}
     */
    @Before
    public void setUp() throws Exception {
        dataReader = DataReaderFactory.createJSONDataReader();
    }

    /**
     * Test of readData (valid) method, of class JSONDataReader.
     */
    @Test
    public void testReadDataValid() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append('{');
        builder.append("\"booleanData\": true");
        builder.append(',');
        builder.append("\"stringData\": \"Hello World\"");
        builder.append(',');
        builder.append("\"intData\": 100");
        builder.append(',');
        builder.append("\"decimalData\": 0.01");
        builder.append('}');

        String validDataInStr = builder.toString();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(validDataInStr.getBytes());

        Set<Data> datas = dataReader.readData(inputStream);
        assertEquals(4, datas.size());
        for (Data data : datas) {
            DataType dataType = data.getType();
            String dataName = data.getName();
            Object dataValue = data.getValue();
            if (DataType.BOOLEAN.equals(dataType)) {
                assertEquals("booleanData", dataName);
                assertEquals(true, dataValue);
            } else if (DataType.STRING.equals(dataType)) {
                assertEquals("stringData", dataName);
                assertEquals("Hello World", dataValue);
            } else if (DataType.INTEGER.equals(dataType)) {
                assertEquals("intData", dataName);
                assertEquals(Integer.valueOf(100), dataValue);
            } else if (DataType.DECIMAL.equals(dataType)) {
                assertEquals("decimalData", dataName);
                assertEquals(BigDecimal.valueOf(0.01), dataValue);
            }
        }
    }

    /**
     * Test of readData (invalid data type) method, of class JSONDataReader.
     */
    @Test(expected = DataStructureException.class)
    public void testReadDataInvalidDataType() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append('{');
        builder.append("\"booleanData\": 1.2f");
        builder.append('}');

        String validDataInStr = builder.toString();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(validDataInStr.getBytes());

        dataReader.readData(inputStream);
    }
}
