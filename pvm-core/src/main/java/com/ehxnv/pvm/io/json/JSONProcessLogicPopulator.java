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

package com.ehxnv.pvm.io.json;

import com.ehxnv.pvm.api.io.ProcessIOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Helper class to populate a {@link JSONProcess} JavaScript execution content.
 * 
 * @author Eka Lie
 */
class JSONProcessLogicPopulator {

    /** Logger. **/
    private static final Logger LOGGER = LoggerFactory.getLogger(JSONProcessLogicPopulator.class);

    /**
     * Populate JSONProcess JavaScript execution logic content from given input stream.
     * @param inputStream source input stream
     * @param process target JSONProcess
     * @throws ProcessIOException if any exception occurred while reading from input stream
     */
    public static void populateProcessLogic(final InputStream inputStream, final JSONProcess process) throws ProcessIOException {
        LOGGER.debug("Populating JSONProcess[{}] JavaScript execution logic", String.valueOf(process.getMetadata()));
        BufferedInputStream bis = new BufferedInputStream(inputStream);

        try {
            int availableBytes = bis.available();
            LOGGER.debug("Reading {} bytes of JavaScript execution logic content", availableBytes);

            byte[] buffer = new byte[availableBytes];
            bis.read(buffer);

            process.setJavascriptContent(new String(buffer));
        } catch (IOException ex) {
            throw new ProcessIOException("Error reading process logic file", ex);
        } finally {
            try {
                bis.close();
            } catch (IOException ex) {
                throw new ProcessIOException("Failed to close input stream", ex);
            }
        }
    }
}
