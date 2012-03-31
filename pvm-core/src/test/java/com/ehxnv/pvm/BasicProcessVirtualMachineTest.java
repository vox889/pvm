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

import com.ehxnv.pvm.api.Process;
import com.ehxnv.pvm.api.ProcessInstance;
import com.ehxnv.pvm.api.data.BooleanData;
import com.ehxnv.pvm.api.data.Data;
import com.ehxnv.pvm.api.data.DecimalData;
import com.ehxnv.pvm.api.execution.ProcessExecutionException;
import com.ehxnv.pvm.api.io.ProcessDataModelException;
import com.ehxnv.pvm.api.io.ProcessIOException;
import com.ehxnv.pvm.api.io.ProcessReader;
import com.ehxnv.pvm.api.io.ProcessStructureException;
import com.ehxnv.pvm.io.ProcessReaderFactory;
import org.junit.Test;

import java.io.File;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Unit test for {@link BasicProcessVirtualMachine}.
 * 
 * @author Eka Lie
 */
public class BasicProcessVirtualMachineTest {
    
    /**
     * Test of execute method, of class BasicProcessVirtualMachine.
     */
    @Test
    public void testExecuteWithMarriedEqTrue() throws ProcessIOException, ProcessStructureException, ProcessDataModelException, ProcessExecutionException {
        ProcessReader processReader = ProcessReaderFactory.createJSONProcessReader();
        File file = new File("src/test/resources/com/ehxnv/pvm/basic_1.0.zip");

        Process basicProcess = processReader.readProcess(file);

        BasicProcessVirtualMachine machine = new BasicProcessVirtualMachine();
        machine.start();
        
        Set<Data> inputDatas = new HashSet<Data>();
        inputDatas.add(new BooleanData("married", Boolean.TRUE));
        inputDatas.add(new DecimalData("salary", new BigDecimal(10000)));

        ProcessInstance processInstance = machine.execute(basicProcess, inputDatas);

        Set<Data> outputDatas = processInstance.getOutputDatas();
        assertEquals(3, outputDatas.size());

        for (Data data : outputDatas) {
            if (data.getName().equals("score")) {
                assertEquals(60, data.getValue());
            } else if (data.getName().equals("salary")) {
                assertEquals(BigDecimal.valueOf(100000.0d), data.getValue());
            } else if (data.getName().equals("status")) {
                assertEquals("REJECTED", data.getValue());
            } else {
                fail();
            }
        }
    }

    /**
     * Test of execute method, of class BasicProcessVirtualMachine.
     */
    @Test
    public void testExecuteWithMarriedEqFalse() throws ProcessIOException, ProcessStructureException, ProcessDataModelException, ProcessExecutionException {
        ProcessReader processReader = ProcessReaderFactory.createJSONProcessReader();
        File file = new File("src/test/resources/com/ehxnv/pvm/basic_1.0.zip");

        Process basicProcess = processReader.readProcess(file);

        BasicProcessVirtualMachine machine = new BasicProcessVirtualMachine();
        machine.start();

        Set<Data> inputDatas = new HashSet<Data>();
        inputDatas.add(new BooleanData("married", Boolean.FALSE));
        inputDatas.add(new DecimalData("salary", new BigDecimal(0)));

        ProcessInstance processInstance = machine.execute(basicProcess, inputDatas);

        Set<Data> outputDatas = processInstance.getOutputDatas();
        assertEquals(3, outputDatas.size());

        for (Data data : outputDatas) {
            if (data.getName().equals("score")) {
                assertEquals(110, data.getValue());
            } else if (data.getName().equals("salary")) {
                assertEquals(BigDecimal.valueOf(0.0d), data.getValue());
            } else if (data.getName().equals("status")) {
                assertEquals("REJECTED", data.getValue());
            } else {
                fail();
            }
        }
    }}
