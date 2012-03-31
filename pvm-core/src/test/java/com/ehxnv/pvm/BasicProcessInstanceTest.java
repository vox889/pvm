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

package com.ehxnv.pvm;

import com.ehxnv.pvm.api.data.BooleanData;
import com.ehxnv.pvm.api.data.Data;
import org.junit.Test;

import java.util.Collections;
import java.util.Date;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit test for {@link BasicProcessInstance}.
 * 
 * @author Eka Lie
 */
public class BasicProcessInstanceTest {
    
    /**
     * Test of getStartTime method, of class BasicProcessInstance.
     */
    @Test
    public void testGetStartTime() {
        Date startTime = new Date();
        BasicProcessInstance instance = new BasicProcessInstance("id1", startTime);
        assertEquals(startTime, instance.getStartTime());
    }

    /**
     * Test of getId method, of class BasicProcessInstance.
     */
    @Test
    public void testGetId() {
        BasicProcessInstance instance = new BasicProcessInstance("id1", new Date());
        assertEquals("id1", instance.getId());
    }

    /**
     * Test of getEndTime and setEndTime method, of class BasicProcessInstance.
     */
    @Test
    public void testGetSetEndTime() {
        BasicProcessInstance instance = new BasicProcessInstance("id1", new Date());
        Date endTime = new Date();
        instance.setEndTime(endTime);
        assertEquals(endTime, instance.getEndTime());
    }

    /**
     * Test of getInputDatas and setInputDatas method, of class BasicProcessInstance.
     */
    @Test
    public void testGetSetInputDatas() {
        BasicProcessInstance instance = new BasicProcessInstance("id1", new Date());
        assertTrue(instance.getInputDatas().isEmpty());

        Set<Data> dumbDatas = Collections.<Data>singleton(new BooleanData("data1", Boolean.TRUE));
        instance.setInputDatas(dumbDatas);
        assertEquals(dumbDatas, instance.getInputDatas());
    }

    /**
     * Test of getOutputDatas and setOutputDatas method, of class BasicProcessInstance.
     */
    @Test
    public void testGetSetOutputDatas() {
        BasicProcessInstance instance = new BasicProcessInstance("id1", new Date());
        assertTrue(instance.getOutputDatas().isEmpty());

        Set<Data> dumbDatas = Collections.<Data>singleton(new BooleanData("data1", Boolean.FALSE));
        instance.setOutputDatas(dumbDatas);
        assertEquals(dumbDatas, instance.getOutputDatas());
    }
}
