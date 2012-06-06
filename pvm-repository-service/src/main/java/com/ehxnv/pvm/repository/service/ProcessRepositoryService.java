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

package com.ehxnv.pvm.repository.service;

import com.ehxnv.pvm.api.ProcessMetadata;
import com.ehxnv.pvm.api.Process;
import com.ehxnv.pvm.api.repository.ProcessAlreadyExistException;
import com.ehxnv.pvm.api.repository.ProcessNotExistException;
import com.ehxnv.pvm.api.repository.ProcessRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * An OSGi based process repository service which offer process repository services.
 * The lifecycle of the process repository itself is not accessible from service point of view.
 * The {@code initialize()} and {@code shutdown()} methods are not callable i.e. throwing {@code IllegalStateException}
 * when it's called.
 */
public class ProcessRepositoryService implements ProcessRepository {

    /** Logger. **/
    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessRepositoryService.class);

    /** Delegated process repository. **/
    private ProcessRepository processRepository;

    /**
     * Constructor.
     * @param processRepository process repository to use
     */
    public ProcessRepositoryService(final ProcessRepository processRepository) {
        this.processRepository = processRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize() {
        throw new IllegalStateException("Repository service can't be initialized");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void shutdown() {
        throw new IllegalStateException("Repository service can't be shutdown");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addProcess(final Process process) throws ProcessAlreadyExistException {
        processRepository.addProcess(process);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateProcess(final ProcessMetadata processMetadata, final Process process) throws ProcessNotExistException {
        processRepository.updateProcess(processMetadata, process);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Process findProcess(final ProcessMetadata processMetadata) {
        return processRepository.findProcess(processMetadata);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteProcess(final ProcessMetadata processMetadata) {
        return processRepository.deleteProcess(processMetadata);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Process> getProcesses() {
        return processRepository.getProcesses();
    }

    /**
     * Start this process repository service, which will:
     * <ul>
     *     <li>initialize process repository</li>
     *     <li>list down all available processes in repository</li>
     * </ul>
     */
    public void startService() {
        processRepository.initialize();

        Set<Process> availableProcesses = processRepository.getProcesses();
        if (availableProcesses.isEmpty()) {
            LOGGER.info("No processes deployed in process repository");
        } else {
            LOGGER.info("Available processes:");
            for (Process process : availableProcesses) {
                LOGGER.info(process.getMetadata().toString());
            }
        }
    }

    /**
     * Stop this process repository service, which will:
     * <ul>
     *     <li>shutdown process repository</li>
     * </ul>
     */
    public void stopService() {
        processRepository.shutdown();
    }
}
