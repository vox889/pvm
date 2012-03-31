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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm.execution.js;

import com.ehxnv.pvm.api.Process;
import com.ehxnv.pvm.api.data.Data;
import com.ehxnv.pvm.api.data.InvalidValueException;
import com.ehxnv.pvm.api.data.StringData;
import com.ehxnv.pvm.api.io.ProcessReader;
import com.ehxnv.pvm.io.ProcessReaderFactory;
import org.junit.Test;

import java.io.File;
import java.util.Collections;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Unit test for {@link JavaScriptProcessExecutor}.
 * 
 * @author Eka Lie
 */
public class JavaScriptProcessExecutorTest {

    @Test
    public void testBlank() {
        JavaScriptProcessExecutor jsExec = new JavaScriptProcessExecutor("some script");
        assertTrue(jsExec.getInputDatas().isEmpty());
        assertTrue(jsExec.getOutputDatas().isEmpty());
    }

    /**
     * Test of initInternal method, of class JavaScriptProcessExecutor.
     */
    @Test
    public void testInitInternal() throws Exception {
        ProcessReader processReader = ProcessReaderFactory.createJSONProcessReader();
        File file = new File("src/test/resources/com/ehxnv/pvm/execution/js/simple_1.0.zip");

        Process process = processReader.readProcess(file);

        JavaScriptProcessExecutor jsExec = new JavaScriptProcessExecutor("");
        Data nameData = new StringData("name", "lanxigeek");
        Set<Data> inputDatas = Collections.singleton(nameData);

        jsExec.initInternal(process, inputDatas);
        assertEquals(2, jsExec.getInputDatas().size());
        assertEquals(2, jsExec.getOutputDatas().size());        
    }

    /**
     * Test of executeNodeHandlerInternal method, of class JavaScriptProcessExecutor.
     * (happy scenario)
     */
    @Test
    public void testExecuteNodeHandlerInternal() throws Exception {
        ProcessReader processReader = ProcessReaderFactory.createJSONProcessReader();
        File file = new File("src/test/resources/com/ehxnv/pvm/execution/js/simple_1.0.zip");

        Process process = processReader.readProcess(file);

        final String jsScript = "function f1(input, output) { output.name = \"Hello, \" + input.name }";
        JavaScriptProcessExecutor jsExec = new JavaScriptProcessExecutor(jsScript);
        Data nameData = new StringData("name", "lanxigeek");
        Set<Data> inputDatas = Collections.singleton(nameData);

        jsExec.initInternal(process, inputDatas);
        assertEquals(2, jsExec.getInputDatas().size());

        jsExec.executeNodeHandlerInternal(process, new JavaScriptNodeHandler("f1"));
        assertEquals(2, jsExec.getOutputDatas().size());

        for (Data data : jsExec.getOutputDatas()) {
            if ("name".equals(data.getName())) {
                assertEquals("Hello, lanxigeek", data.getValue());
            } else if ("salary".equals(data.getName())) {
                assertEquals(0, data.getValue());
            } else {
                fail("Unexpected data here!");
            }
        }
    }

    /**
     * Test of executeNodeHandlerInternal method, of class JavaScriptProcessExecutor.
     * (when try to modify input from script)
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testExecuteNodeHandlerInternal2() throws Exception {
        ProcessReader processReader = ProcessReaderFactory.createJSONProcessReader();
        File file = new File("src/test/resources/com/ehxnv/pvm/execution/js/simple_1.0.zip");

        Process process = processReader.readProcess(file);

        final String jsScript = "function f1(input, output) { input.name = \"this should not happen\" }";
        JavaScriptProcessExecutor jsExec = new JavaScriptProcessExecutor(jsScript);
        Data nameData = new StringData("name", "lanxigeek");
        Set<Data> inputDatas = Collections.singleton(nameData);

        jsExec.initInternal(process, inputDatas);
        jsExec.executeNodeHandlerInternal(process, new JavaScriptNodeHandler("f1"));
    }

    /**
     * Test of executeNodeHandlerInternal method, of class JavaScriptProcessExecutor.
     * (when data is set to invalid value e.g. integer data set to string)
     */
    @Test(expected = InvalidValueException.class)
    public void testExecuteNodeHandlerInternal3() throws Exception {
        ProcessReader processReader = ProcessReaderFactory.createJSONProcessReader();
        File file = new File("src/test/resources/com/ehxnv/pvm/execution/js/simple_1.0.zip");

        Process process = processReader.readProcess(file);

        final String jsScript = "function f1(input, output) { output.salary = \"ouch\"; }";
        JavaScriptProcessExecutor jsExec = new JavaScriptProcessExecutor(jsScript);
        Data nameData = new StringData("name", "lanxigeek");
        Set<Data> inputDatas = Collections.singleton(nameData);

        jsExec.initInternal(process, inputDatas);
        jsExec.executeNodeHandlerInternal(process, new JavaScriptNodeHandler("f1"));
    }
}
