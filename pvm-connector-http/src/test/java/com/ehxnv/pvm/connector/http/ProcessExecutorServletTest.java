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

package com.ehxnv.pvm.connector.http;

import com.ehxnv.pvm.BasicProcessInstance;
import com.ehxnv.pvm.api.Process;
import com.ehxnv.pvm.api.ProcessInstance;
import com.ehxnv.pvm.api.ProcessMetadata;
import com.ehxnv.pvm.api.ProcessVirtualMachine;
import com.ehxnv.pvm.api.data.*;
import com.ehxnv.pvm.api.repository.ProcessRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static junit.framework.Assert.*;
import static org.easymock.EasyMock.*;

/**
 * Unit test for {@link ProcessExecutorServlet}.
 * @author Eka Lie
 */
public class ProcessExecutorServletTest {

    /** Process executor servlet. **/
    private ProcessExecutorServlet processExecutorServlet;
    /** Mock process repository for testing. **/
    private ProcessRepository mockProcessRepository;
    /** Mock process virtual machine for testing. **/
    private ProcessVirtualMachine mockProcessVirtualMachine;

    /**
     * {@inheritDoc}
     */
    @Before
    public void setUp() throws Exception {
        mockProcessRepository = createMock(ProcessRepository.class);
        mockProcessVirtualMachine = createMock(ProcessVirtualMachine.class);

        ProcessMetadata nonExistMetadata = new ProcessMetadata("nonexist", "1.0");
        ProcessMetadata process1Metadata = new ProcessMetadata("process", "1.0");

        Process mockProcess = createMock(Process.class);
        expect(mockProcessRepository.findProcess(eq(nonExistMetadata))).andReturn(null).anyTimes();
        expect(mockProcessRepository.findProcess(eq(process1Metadata))).andReturn(mockProcess).anyTimes();

        Calendar calendar = Calendar.getInstance();
        calendar.set(2012, 4, 21, 0, 0, 0);
        ProcessInstance processInstance = new BasicProcessInstance("p1", calendar.getTime());

        Set<Data> inputDatas = new HashSet<Data>();
        inputDatas.add(new BooleanData("married", false));
        inputDatas.add(new IntegerData("age", 25));
        processInstance.setInputDatas(inputDatas);

        Set<Data> outputDatas = new HashSet<Data>();
        outputDatas.add(new DecimalData("score", BigDecimal.valueOf(5000)));
        outputDatas.add(new StringData("line", "CoolLine"));
        processInstance.setOutputDatas(outputDatas);

        calendar.set(2012, 5, 21, 0, 0, 0);
        processInstance.setEndTime(calendar.getTime());

        expect(mockProcessVirtualMachine.execute((Process) anyObject(), (Set<Data>) anyObject())).andReturn(processInstance).anyTimes();

        replay(mockProcessRepository, mockProcessVirtualMachine);
        processExecutorServlet = new ProcessExecutorServlet(mockProcessRepository, mockProcessVirtualMachine);
    }

    /**
     * {@inheritDoc}
     */
    @After
    public void tearDown() throws Exception {
        verify(mockProcessRepository, mockProcessVirtualMachine);
    }

    /**
     * Test of doPost method, of class ProcessExecutorServlet.
     */
    @Test
    public void testDoPostWithNoParamSet() throws Exception {
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
        processExecutorServlet.doPost(mockHttpServletRequest, mockHttpServletResponse);

        assertEquals(HttpServletResponse.SC_BAD_REQUEST, mockHttpServletResponse.getStatus());
        assertEquals("process metadata can't be empty", mockHttpServletResponse.getErrorMessage());
    }

    /**
     * Test of doPost method, of class ProcessExecutorServlet.
     */
    @Test
    public void testDoPostWithOnlyProcessParamSet() throws Exception {
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();

        mockHttpServletRequest.addParameter("process", "process-1.0");
        processExecutorServlet.doPost(mockHttpServletRequest, mockHttpServletResponse);

        assertEquals(HttpServletResponse.SC_BAD_REQUEST, mockHttpServletResponse.getStatus());
        assertEquals("process input can't be empty", mockHttpServletResponse.getErrorMessage());
    }

    /**
     * Test of doPost method, of class ProcessExecutorServlet.
     */
    @Test
    public void testDoPostWithNonExistentProcess() throws Exception {
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();

        mockHttpServletRequest.addParameter("process", "nonexist_1.0");
        mockHttpServletRequest.addParameter("input", "some-dumb-unused-input");
        processExecutorServlet.doPost(mockHttpServletRequest, mockHttpServletResponse);

        assertEquals(HttpServletResponse.SC_BAD_REQUEST, mockHttpServletResponse.getStatus());
        assertEquals("Can't find process with metadata nonexist[1.0]", mockHttpServletResponse.getErrorMessage());
    }

    /**
     * Test of doPost method, of class ProcessExecutorServlet.
     */
    @Test
    public void testDoPostWithInvalidData() throws Exception {
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();

        mockHttpServletRequest.addParameter("process", "process_1.0");
        mockHttpServletRequest.addParameter("input", "some-dumb-unused-input");
        processExecutorServlet.doPost(mockHttpServletRequest, mockHttpServletResponse);

        assertEquals(HttpServletResponse.SC_BAD_REQUEST, mockHttpServletResponse.getStatus());
        assertEquals("Can't parse process input data", mockHttpServletResponse.getErrorMessage());
    }

    /**
     * Test of doPost method, of class ProcessExecutorServlet.
     */
    @Test
    public void testDoPostWithValidData() throws Exception {
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();

        mockHttpServletRequest.addParameter("process", "process_1.0");
        mockHttpServletRequest.addParameter("input", "{}");
        processExecutorServlet.doPost(mockHttpServletRequest, mockHttpServletResponse);

        assertEquals(HttpServletResponse.SC_OK, mockHttpServletResponse.getStatus());
        assertNull(mockHttpServletResponse.getErrorMessage());

        String outputContent = mockHttpServletResponse.getContentAsString();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2012, 4, 21, 0, 0, 0);
        Date expectedStartTime = calendar.getTime();
        calendar.set(2012, 5, 21, 0, 0, 0);
        Date expectedEndTime = calendar.getTime();

        assertTrue(outputContent.startsWith("{"));
        assertTrue(outputContent.contains("\"id\":\"p1\""));
        assertTrue(outputContent.contains("\"start_time\":\"" + String.valueOf(expectedStartTime) + "\""));
        assertTrue(outputContent.contains("\"end_time\":\"" + String.valueOf(expectedEndTime) + "\""));
        
        Matcher inputDataMatcher = Pattern.compile(".*\"input_datas\":\\{([^\\}]*)\\}.*").matcher(outputContent);
        assertTrue(inputDataMatcher.matches());
        String inputDataStr = inputDataMatcher.group(1);
        assertTrue(inputDataStr.contains("\"age\":25"));
        assertTrue(inputDataStr.contains("\"married\":false"));

        Matcher outputDataMatcher = Pattern.compile(".*\"output_datas\":\\{([^\\}]*)\\}.*").matcher(outputContent);
        assertTrue(outputDataMatcher.matches());
        String outputDataStr = outputDataMatcher.group(1);
        assertTrue(outputDataStr.contains("\"score\":5000"));
        assertTrue(outputDataStr.contains("\"line\":\"CoolLine\""));

        assertTrue(outputContent.endsWith("}"));
    }
}
