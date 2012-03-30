/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm.io.json;

import com.ehxnv.pvm.api.ProcessMetadata;
import com.ehxnv.pvm.execution.js.JavaScriptExecutionEnv;
import org.junit.Test;

import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit test for {@link JSONProcessLogicPopulator}.
 * 
 * @author Eka Lie
 */
public class JSONProcessLogicPopulatorTest {

    /**
     * Test of populateProcessLogic method, of class JSONProcessLogicPopulator.
     */
    @Test
    public void testPopulateProcessLogic() throws Exception {
        InputStream inputStream = JSONProcessLogicPopulator.class.getResourceAsStream("process_logic.js");

        JSONProcess process = new JSONProcess(new ProcessMetadata("process1", "1.0"));
        JSONProcessLogicPopulator.populateProcessLogic(inputStream, process);

        assertTrue(process.getExecutionEnvironment() instanceof JavaScriptExecutionEnv);
        
        JavaScriptExecutionEnv jsExecEnv = (JavaScriptExecutionEnv) process.getExecutionEnvironment();
        assertEquals(36, jsExecEnv.getJavaScript().length());
    }
}