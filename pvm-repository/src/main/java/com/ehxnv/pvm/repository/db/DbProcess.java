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

import javax.persistence.*;

import com.ehxnv.pvm.api.Process;

import java.io.*;

/**
 * Database representation of Process.
 * @author Eka Lie
 */
@Entity
@Table(name = "PROCESS", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "PROCESS_NAME", "PROCESS_VERSION" })
})
public final class DbProcess {

    /** Process id. **/
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    /** Process name. **/
    @Column(name = "PROCESS_NAME", nullable = false)
    private String processName;
    /** Process version. **/
    @Column(name = "PROCESS_VERSION", nullable = false)
    private String processVersion;
    /** Process in bytes. **/
    @Lob
    @Column(name = "PROCESS_BYTES")
    private byte[] processBytes;

    /**
     * Default constructor for JPA purpose.
     */
    public DbProcess() {
        
    }

    /**
     * Constructor.
     * @param process target process
     */
    public DbProcess(final Process process) {
        updateFrom(process);
    }

    /**
     * Get a process as bytes.
     * @param process source process
     * @return process bytes
     */
    private final byte[] getProcessAsBytes(final Process process) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;

        try {
            oos = new ObjectOutputStream(baos);
            oos.writeObject(process);
        } catch (IOException ex) {
            throw new RuntimeException("Failed to serialize process as bytes", ex);
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }

        return baos.toByteArray();
    }

    /**
     * Update this db process from other process.
     * @param process other process
     */
    public void updateFrom(final Process process) {
        this.processName = process.getMetadata().getName();
        this.processVersion = process.getMetadata().getVersion();
        this.processBytes = getProcessAsBytes(process);
    }

    /**
     * Get the associated process.
     * @return associated process
     */
    public Process getProcess() {
        ByteArrayInputStream bais = new ByteArrayInputStream(processBytes);
        ObjectInputStream ois = null;

        try {
            ois = new ObjectInputStream(bais);
            return (Process) ois.readObject();
        } catch (IOException ex) {
            throw new RuntimeException("Failed to deserialize process from bytes", ex);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException("Failed to deserialize process from bytes", ex);
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    /**
     * Get process name.
     * @return process name
     */
    public String getProcessName() {
        return processName;
    }

    /**
     * Get process version.
     * @return process version
     */
    public String getProcessVersion() {
        return processVersion;
    }

    /**
     * Get underlying process as bytes
     * @return process as bytes
     */
    public byte[] getProcessBytes() {
        return processBytes;
    }
}
