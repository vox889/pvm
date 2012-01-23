/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm;

import com.ehxnv.pvm.data.DecimalData;
import com.ehxnv.pvm.data.Data;
import com.ehxnv.pvm.data.BooleanData;
import com.ehxnv.pvm.execution.ProcessExecutionException;
import com.ehxnv.pvm.io.ProcessDataModelException;
import com.ehxnv.pvm.io.ProcessIOException;
import com.ehxnv.pvm.io.ProcessReader;
import com.ehxnv.pvm.io.ProcessReaderFactory;
import com.ehxnv.pvm.io.ProcessStructureException;
import java.io.File;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import static org.junit.Assert.*;

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
