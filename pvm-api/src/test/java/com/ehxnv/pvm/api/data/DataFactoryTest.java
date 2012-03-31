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
