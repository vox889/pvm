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

package com.ehxnv.pvm.api.repository;

import com.ehxnv.pvm.api.Process;
import com.ehxnv.pvm.api.ProcessMetadata;

/**
 * Represents an repository of processes that allows:
 * <ul>
 *  <li>add process to repository</li>
 *  <li>update process in repository</li>
 *  <li>find process from repository</li>
 *  <li>delete process from repository</li>
 * </ul>
 * @author Eka Lie
 */
public interface ProcessRepository {

    /**
     * Initialize the repository before it can be used.
     */
    void initialize();

    /**
     * Shutdown the repository for any resource cleanup
     */
    void shutdown();

    /**
     * Add given process to repository.
     * @param process process to be added
     * @throws ProcessAlreadyExistException if given process already exist (by comparing process metadata)
     */
    void addProcess(Process process) throws ProcessAlreadyExistException;

    /**
     * Update a process information given it's metadata.
     * @param processMetadata process metadata
     * @param process updated process
     * @throws ProcessNotExistException if process can't be found (by comparing process metadata)
     */
    void updateProcess(ProcessMetadata processMetadata, Process process) throws ProcessNotExistException;

    /**
     * Find a process given it's metadata.
     * @param processMetadata process metadata
     * @return process or NULL if process can't be found
     */
    Process findProcess(ProcessMetadata processMetadata);

    /**
     * Delete a process given it's metadata.
     * @param processMetadata process metadata
     * @return true if process is deleted, false otherwise
     */
    boolean deleteProcess(ProcessMetadata processMetadata);
}
