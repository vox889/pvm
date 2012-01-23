/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm.io;

import com.ehxnv.pvm.io.json.JSONProcessReader;

/**
 * Factory class responsible for creating {@link ProcessReader}.
 * 
 * @author Eka Lie
 */
public abstract class ProcessReaderFactory {
    
    /** Singleton JSON process reader instance. **/
    private static final ProcessReader JSON_PROCESS_READER = new JSONProcessReader();

    /**
     * Private constructor.
     */
    private ProcessReaderFactory() {
        // do nothing
    }

    /**
     * Create JSON based process reader.
     * @return JSON process reader
     */
    public static ProcessReader createJSONProcessReader() {
        return JSON_PROCESS_READER;
    }
}
