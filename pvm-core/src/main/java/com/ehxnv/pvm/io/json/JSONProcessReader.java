/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm.io.json;

import com.ehxnv.pvm.api.ProcessMetadata;
import com.ehxnv.pvm.api.Process;
import com.ehxnv.pvm.api.io.ProcessDataModelException;
import com.ehxnv.pvm.api.io.ProcessIOException;
import com.ehxnv.pvm.api.io.ProcessReader;
import com.ehxnv.pvm.api.io.ProcessStructureException;
import com.ehxnv.pvm.util.ProcessMetadataHelper;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import static com.ehxnv.pvm.api.util.ValidationUtil.checkForNull;

/**
 * Implementation of {@link ProcessReader} which reads process from a ZIP file which
 * should contains:
 * <ul>
 *   <li>process definition file i.e. definition.json</li>
 *   <li>process logic file i.e. logic.js</li>
 *   <li>process input data model file i.e. input.json</li>
 *   <li>process output data model file i.e. output.json</li>
 * </ul>
 * 
 * @author Eka Lie
 */
public class JSONProcessReader implements ProcessReader {

    /** Logger. **/
    private static final Logger LOGGER = LoggerFactory.getLogger(JSONProcessReader.class); 
    /** Process definition file name. */
    private static final String DEFINITION_FILE_NAME = "definition.json";
    /** Process logic file name. */
    private static final String LOGIC_FILE_NAME = "logic.js";
    /** Process input data model file name. **/
    private static final String INPUT_DATA_MODEL_FILE_NAME = "input.json";
    /** Process output data model file name. **/
    private static final String OUTPUT_DATA_MODEL_FILE_NAME = "output.json";

    /**
     * {@inheritDoc}
     */
    @Override
    public Process readProcess(final File file) throws ProcessIOException, ProcessStructureException, ProcessDataModelException {
        // validate process file before we try to read it
        validateProcessFile(file);

        LOGGER.debug("Reading JSONProcess from {}", file.getAbsolutePath());

        // open process file
        ZipFile zif = null;
        try {
            zif = new ZipFile(file);
        } catch (ZipException ex) {
            throw new ProcessIOException("Failed to open ZIP file " + file.getName(), ex);
        } catch (IOException ex) {
            throw new ProcessIOException("Failed to open ZIP file " + file.getName(), ex);
        }

        // create metadata from file name and create the JSON process
        ProcessMetadata metadata = ProcessMetadataHelper.getMetadata(FilenameUtils.getBaseName(file.getName()));
        JSONProcess process = new JSONProcess(metadata);

        // populate process from zip file and close if
        try {
            populateProcessFromFile(zif, process);
        } finally {
            // close process file
            try {
                zif.close();
            } catch (IOException ex) {
                throw new ProcessIOException("Failed to close ZIP file " + file.getName(), ex);
            }
        }

        return process;
    }

    /**
     * Populate process definition from a process file (ZIP).
     * @param file process file
     * @param definitionEntry process definition entry
     * @param process target process
     * @throws ProcessIOException if any IO exception occurred during reading or closing of process file
     * @throws ProcessStructureException if expected process definition structure is not met
     */
    private void populateProcessDefinition(final ZipFile file, final ZipEntry definitionEntry, final JSONProcess process) throws ProcessIOException, ProcessStructureException {
        InputStream definitionInputStream = openInputStream(file, definitionEntry, DEFINITION_FILE_NAME);
        try {
            JSONProcessDefinitionPopulator.populateProcessDefinition(definitionInputStream, process);
        } catch (ProcessIOException ex) {
            throw ex;
        } catch (ProcessStructureException ex) {
            throw ex;
        } finally {
            closeInputStream(definitionInputStream, DEFINITION_FILE_NAME);
        }
    }

    /**
     * Populate process logic from a process file (ZIP).
     * @param file process file
     * @param logicEntry process logic entry
     * @param process target process
     * @throws ProcessIOException if any IO exception occurred during reading or closing of process file
     */
    private void populateProcessLogic(final ZipFile file, final ZipEntry logicEntry, final JSONProcess process) throws ProcessIOException {
        InputStream logicInputStream = openInputStream(file, logicEntry, LOGIC_FILE_NAME);
        try {
            JSONProcessLogicPopulator.populateProcessLogic(logicInputStream, process);
        } catch (ProcessIOException ex) {
            throw ex;
        } finally {
            closeInputStream(logicInputStream, LOGIC_FILE_NAME);
        }
    }

    /**
     * Populate process input data model from a process file (ZIP).
     * @param file process file
     * @param inputDataModelEntry process input data model entry
     * @param process target process
     * @throws ProcessIOException if any IO exception occurred during reading or closing of process file
     * @throws ProcessDataModelException if expected process input data model is not met
     */
    private void populateProcessInputDataModel(final ZipFile file, final ZipEntry inputDataModelEntry, final JSONProcess process) throws ProcessIOException, ProcessDataModelException {
        InputStream inputDataModelInputStream = openInputStream(file, inputDataModelEntry, INPUT_DATA_MODEL_FILE_NAME);
        try {
            JSONProcessDataModelPopulator.populateProcessInputDataModel(inputDataModelInputStream, process);
        } catch (ProcessIOException ex) {
            throw ex;
        } finally {
            closeInputStream(inputDataModelInputStream, INPUT_DATA_MODEL_FILE_NAME);
        }
    }

    /**
     * Populate process output data model from a process file (ZIP).
     * @param file process file
     * @param outputDataModelEntry process output data model entry
     * @param process target process
     * @throws ProcessIOException if any IO exception occurred during reading or closing of process file
     * @throws ProcessDataModelException if expected process output data model is not met
     */
    private void populateProcessOutputDataModel(final ZipFile file, final ZipEntry outputDataModelEntry, final JSONProcess process) throws ProcessIOException, ProcessDataModelException {
        InputStream outputDataModelInputStream = openInputStream(file, outputDataModelEntry, OUTPUT_DATA_MODEL_FILE_NAME);
        try {
            JSONProcessDataModelPopulator.populateProcessOutputDataModel(outputDataModelInputStream, process);
        } catch (ProcessIOException ex) {
            throw ex;
        } finally {
            closeInputStream(outputDataModelInputStream, OUTPUT_DATA_MODEL_FILE_NAME);
        }
    }

    /**
     * Validate process file for the following conditions:
     * <ul>
     *   <li>File can't be null</li>
     *   <li>File must exist</li>
     *   <li>File must be an ordinary file</li>
     * </ul>
     * @param file process file
     * @throws ProcessIOException if any of the conditions above not met
     */
    private void validateProcessFile(final File file) throws ProcessIOException {
        checkForNull("Process file", file);

        if (!file.exists()) {
            throw new ProcessIOException("Process file doesn't exist");
        }

        if (file.isDirectory()) {
            throw new ProcessIOException("Process file must be an ordinary file");
        }
    }

    /**
     * Populate JSON process from ZIP file.
     * @param file source ZIP file
     * @param process target JSON process
     * @throws ProcessIOException if there is any IO exception occurred during reading/closing ZIP file
     * @throws ProcessStructureException if ZIP file doesn't contains expected process structure
     * @throws ProcessDataModelException if ZIP file doesn't contains expected process data model
     */
    private void populateProcessFromFile(final ZipFile file, final JSONProcess process) throws ProcessIOException, ProcessStructureException, ProcessDataModelException {
        ZipEntry definitionEntry = getZipEntryFromZipFile(file, DEFINITION_FILE_NAME, "process definition");
        ZipEntry logicEntry = getZipEntryFromZipFile(file, LOGIC_FILE_NAME, "process logic");
        ZipEntry inputDataModelEntry = getZipEntryFromZipFile(file, INPUT_DATA_MODEL_FILE_NAME, "process input data model");
        ZipEntry outputDataModelEntry = getZipEntryFromZipFile(file, OUTPUT_DATA_MODEL_FILE_NAME, "process output data model");

        populateProcessDefinition(file, definitionEntry, process);
        populateProcessLogic(file, logicEntry, process);
        populateProcessInputDataModel(file, inputDataModelEntry, process);
        populateProcessOutputDataModel(file, outputDataModelEntry, process);
    }

    /**
     * Get a ZIP entry from a given ZIP file.
     * @param file ZIP file
     * @param entryName ZIP entry name
     * @param entryDesc ZIP entry description
     * @return ZIP entry
     * @throws ProcessStructureException if ZIP entry can't be found
     */
    private ZipEntry getZipEntryFromZipFile(final ZipFile file, final String entryName, final String entryDesc) throws ProcessStructureException {
        ZipEntry entry = file.getEntry(entryName);
        if (entry == null) {
            throw new ProcessStructureException("Can't find " + entryDesc + " file " + entryName);
        }        

        return entry;
    }

    /**
     * Open an input stream given a ZIP file and ZIP entry.
     * @param file ZIP file
     * @param entry ZIP entry
     * @param streamName stream name
     * @return the input stream
     * @throws ProcessIOException if there is an IO exception occurred during opening
     */
    private InputStream openInputStream(final ZipFile file, final ZipEntry entry, final String streamName) throws ProcessIOException {
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream(entry);
            return inputStream;
        } catch (IOException ex) {
            throw new ProcessIOException("Failed to read " + streamName, ex);
        }
    }

    /**
     * Close an input stream.
     * @param inputStream input stream
     * @param streamName stream name
     * @throws ProcessIOException if there is an IO exception occurred during closing
     */
    private void closeInputStream(final InputStream inputStream, final String streamName) throws ProcessIOException {
        try {
            inputStream.close();
        } catch (IOException ex) {
            throw new ProcessIOException("Failed to close " + streamName, ex);
        }
    }
}
