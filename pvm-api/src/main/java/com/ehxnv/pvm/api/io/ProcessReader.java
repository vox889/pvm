/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm.api.io;

import com.ehxnv.pvm.api.Process;
import java.io.File;

/**
 * A process reader responsible for reading a {@link Process} from a given file.
 * 
 * @author Eka Lie
 */
public interface ProcessReader {
 
    /**
     * Read a process from a given file.
     * @param file source file
     * @return process
     * @throws ProcessIOException if any IO exception occurred while reading file
     * @throws ProcessStructureException if expected process structure is not met
     * @throws ProcessDataModelException if expected process data model is not met
     */
    Process readProcess(File file) throws ProcessIOException, ProcessStructureException, ProcessDataModelException;
}
