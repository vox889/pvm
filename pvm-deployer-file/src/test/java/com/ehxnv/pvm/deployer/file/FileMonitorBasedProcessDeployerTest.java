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

package com.ehxnv.pvm.deployer.file;

import com.ehxnv.pvm.api.Process;
import com.ehxnv.pvm.api.ProcessMetadata;
import com.ehxnv.pvm.api.io.ProcessReader;
import com.ehxnv.pvm.api.repository.ProcessRepository;
import com.ehxnv.pvm.deployer.file.FileMonitorBasedProcessDeployer;
import org.easymock.EasyMock;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;

import static org.easymock.EasyMock.*;

/**
 * Unit test for {@link FileMonitorBasedProcessDeployer}.
 * @author Eka Lie
 */
public class FileMonitorBasedProcessDeployerTest {

    /** Temporary folder rule. **/
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    /**
     * Test of start method, of FileMonitorBasedProcessDeployer class.
     */
    @Test(expected = IllegalStateException.class)
    public void testStart() throws Exception {
        FileMonitorBasedProcessDeployer fileMonitorBasedProcessDeployer = new FileMonitorBasedProcessDeployer(temporaryFolder.getRoot(),
                        1000, createMock(ProcessRepository.class), createMock(ProcessReader.class));
        fileMonitorBasedProcessDeployer.start();

        try {
            fileMonitorBasedProcessDeployer.start();
        } finally {
            fileMonitorBasedProcessDeployer.stop();
        }
    }

    /**
     * Test of stop method, of FileMonitorBasedProcessDeployer class.
     */
    @Test(expected = IllegalStateException.class)
    public void testStop() throws Exception {
        FileMonitorBasedProcessDeployer fileMonitorBasedProcessDeployer = new FileMonitorBasedProcessDeployer(temporaryFolder.getRoot(),
                        1000, createMock(ProcessRepository.class), createMock(ProcessReader.class));
        fileMonitorBasedProcessDeployer.start();
        fileMonitorBasedProcessDeployer.stop();
        fileMonitorBasedProcessDeployer.stop();
    }

    /**
     * Test the monitoring of new file i.e zip file inside monitored directory.
     * It should read the file and deploy this into process repository.
     */
    @Test
    public void testMonitorNewFile() throws Exception {
        ProcessMetadata processMetadata = new ProcessMetadata("test1", "1.0");

        Process processMock = createMock(Process.class);
        expect(processMock.getMetadata()).andReturn(processMetadata).anyTimes();

        ProcessReader processReaderMock = createMock(ProcessReader.class);
        expect(processReaderMock.readProcess(EasyMock.<File>anyObject())).andReturn(processMock).once();

        ProcessRepository processRepositoryMock = createMock(ProcessRepository.class);
        processRepositoryMock.addProcess(processMock);
        expectLastCall().once();

        replay(processMock, processReaderMock, processRepositoryMock);

        FileMonitorBasedProcessDeployer fileMonitorBasedProcessDeployer = new FileMonitorBasedProcessDeployer(temporaryFolder.getRoot(),
                        1000, processRepositoryMock, processReaderMock);
        fileMonitorBasedProcessDeployer.start();

        File dumbProcessFile = new File(temporaryFolder.getRoot(), "test1.zip");

        // test adding
        dumbProcessFile.createNewFile();

        fileMonitorBasedProcessDeployer.stop();

        verify(processMock, processReaderMock, processRepositoryMock);
    }

    /**
     * Test the monitoring of deletion of existing file i.e zip file inside monitored directory.
     * It should read the file for the process metadata and undeploy this process from process repository.
     */
    @Test
    public void testMonitorRemovalOfFile() throws Exception {
        ProcessMetadata processMetadata = new ProcessMetadata("test1", "1.0");

        Process processMock = createMock(Process.class);
        expect(processMock.getMetadata()).andReturn(processMetadata).anyTimes();

        ProcessReader processReaderMock = createMock(ProcessReader.class);
        expect(processReaderMock.readProcess(EasyMock.<File>anyObject())).andReturn(processMock).once();

        ProcessRepository processRepositoryMock = createMock(ProcessRepository.class);
        expect(processRepositoryMock.deleteProcess(processMetadata)).andReturn(true).once();

        replay(processMock, processReaderMock, processRepositoryMock);

        File dumbProcessFile = new File(temporaryFolder.getRoot(), "test1.zip");
        dumbProcessFile.createNewFile();

        FileMonitorBasedProcessDeployer fileMonitorBasedProcessDeployer = new FileMonitorBasedProcessDeployer(temporaryFolder.getRoot(),
                        1000, processRepositoryMock, processReaderMock);
        fileMonitorBasedProcessDeployer.start();

        // test removal
        dumbProcessFile.delete();

        fileMonitorBasedProcessDeployer.stop();

        verify(processMock, processReaderMock, processRepositoryMock);
    }
}
