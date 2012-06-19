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

package com.ehxnv.pvm.repository.db;

import com.ehxnv.pvm.api.Process;
import com.ehxnv.pvm.api.ProcessMetadata;
import com.ehxnv.pvm.api.data.DataModel;
import com.ehxnv.pvm.api.repository.ProcessAlreadyExistException;
import com.ehxnv.pvm.api.repository.ProcessNotExistException;
import com.ehxnv.pvm.api.repository.ProcessRepository;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Unit test for HSQLProcessRepository.
 * @author Eka Lie
 */
public class HSQLProcessRepositoryTest {

    /** Temporary folder rule. **/
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    /**
     * Test basic CRUD of HSQLProcessRepository.
     * @throws Exception if any exception occurred during testing which is should NOT happen
     */
    @Test
    public void testAll() throws Exception {
        File tempFile = temporaryFolder.newFile("test.db");
        ProcessRepository processRepository = new HSQLProcessRepository(tempFile.getAbsolutePath());
        processRepository.initialize();

        // create dummy process
        ProcessMetadata processMetadata = new ProcessMetadata("process1", "1.0");
        Process processMock = EasyMock.createMock(Process.class);
        EasyMock.expect(processMock.getMetadata()).andReturn(processMetadata).anyTimes();
        EasyMock.expect(processMock.getInputDataModel()).andReturn(null).anyTimes();
        EasyMock.replay(processMock);

        // validate that there is no previous process with the given metadata
        assertNull(processRepository.findProcess(processMetadata));

        // add to repository and validate that the process exist
        processRepository.addProcess(processMock);
        Process process = processRepository.findProcess(processMetadata);
        assertNotNull(process);
        assertEquals(processMetadata, process.getMetadata());
        assertNull(process.getInputDataModel());

        // validate that adding the same process to repository will throw a ProcessAlreadyExistException
        try {
            processRepository.addProcess(processMock);
            fail("should throw ProcessAlreadyExistException when adding a process with the same metadata");
        } catch (ProcessAlreadyExistException ex) { }

        // try to find non-existent process
        assertNull(processRepository.findProcess(new ProcessMetadata("randomProcess", "1.0")));
        EasyMock.verify(processMock);

        // update existing process
        Process updatedProcessMock = EasyMock.createMock(Process.class);
        EasyMock.expect(updatedProcessMock.getMetadata()).andReturn(processMetadata).anyTimes();
        EasyMock.expect(updatedProcessMock.getInputDataModel()).andReturn(new DataModel("input"));
        EasyMock.replay(updatedProcessMock);

        processRepository.updateProcess(processMetadata, updatedProcessMock);

        Process updatedProcess = processRepository.findProcess(processMetadata);
        assertNotNull(updatedProcess);
        assertEquals(processMetadata, updatedProcess.getMetadata());
        assertNull(updatedProcess.getInputDataModel());

        // try to update non existing process
        try {
            processRepository.updateProcess(new ProcessMetadata("crazyProcess", "2.0"), updatedProcessMock);
            fail("should throw ProcessNotExistException when updating non exising process");
        } catch (ProcessNotExistException ex) { }

        // get all available processes from repository
        Set<Process> processes = processRepository.getProcesses();
        assertEquals(1, processes.size());
        assertEquals(processMetadata, processes.iterator().next().getMetadata());

        // delete process from repository and validate that it's successful
        assertTrue(processRepository.deleteProcess(processMetadata));
        assertNull(processRepository.findProcess(processMetadata));

        processRepository.addProcess(processMock);
        processRepository.shutdown();
    }
}
