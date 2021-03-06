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
package com.ehxnv.pvm.execution;

import com.ehxnv.pvm.api.execution.ExecutionEnv;
import com.ehxnv.pvm.api.execution.ProcessExecutor;
import com.ehxnv.pvm.execution.js.JavaScriptExecutionEnv;
import com.ehxnv.pvm.execution.js.JavaScriptProcessExecutor;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for {@link ProcessExecutorFactory}.
 * 
 * @author Eka Lie
 */
public class ProcessExecutorFactoryTest {

    /**
     * Test of createProcessExecutor method, of class ProcessExecutorFactory.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testCreateProcessExecutor() {
        ProcessExecutor result = ProcessExecutorFactory.createProcessExecutor(new JavaScriptExecutionEnv("some script"));
        assertTrue(result instanceof JavaScriptProcessExecutor);

        ExecutionEnv myEnv = new ExecutionEnv() {

            public String getName() {
                return "my env";
            }
        };
        ProcessExecutorFactory.createProcessExecutor(myEnv);
    }
}
