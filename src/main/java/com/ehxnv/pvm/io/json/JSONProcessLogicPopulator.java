/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm.io.json;

import com.ehxnv.pvm.io.ProcessIOException;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
