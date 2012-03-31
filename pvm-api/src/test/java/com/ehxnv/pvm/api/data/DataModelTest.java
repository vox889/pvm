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
