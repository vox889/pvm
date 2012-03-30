/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
