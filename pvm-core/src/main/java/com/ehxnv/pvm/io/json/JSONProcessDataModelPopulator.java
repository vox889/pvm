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

import com.ehxnv.pvm.api.data.DataDefinition;
import com.ehxnv.pvm.api.data.DataModel;
import com.ehxnv.pvm.api.data.DataType;
import com.ehxnv.pvm.api.io.ProcessDataModelException;
import com.ehxnv.pvm.api.io.ProcessIOException;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Helper class to populate a {@link JSONProcess} data models.
 * 
 * @author Eka Lie
 */
class JSONProcessDataModelPopulator {

    /** Logger. **/
    private static final Logger LOGGER = LoggerFactory.getLogger(JSONProcessDataModelPopulator.class);
    /** Data model data name pattern. **/
    private static final Pattern DATA_NAME_PATTERN = Pattern.compile("[a-z][a-zA-Z_]*");

    /**
     * Read a data model from an input stream.
     * @param inputStream source input stream
     * @param process target process
     * @param dataModelName data model name
     * @return data model
     * @throws ProcessIOException if any exception occurred while reading from input stream
     * @throws ProcessDataModelException if expected data model structure or definition is not met
     */
    private static DataModel readDataModelFromInputStream(final InputStream inputStream, final JSONProcess process, final String dataModelName) throws ProcessIOException, ProcessDataModelException {
        LOGGER.debug("Populating JSONProcess[{}] {} data model", String.valueOf(process.getMetadata()), dataModelName);

        InputStreamReader streamReader = new InputStreamReader(inputStream);
        JsonParser parser = new JsonParser();

        DataModel dataModel = new DataModel(dataModelName);

        try {
            JsonElement jsonProcess = parser.parse(new JsonReader(streamReader));
            if (!jsonProcess.isJsonObject()) {
                throw new ProcessDataModelException("Process " + dataModelName + " data model have to be a JSON object");
            }

            JsonObject jsonProcessObj = jsonProcess.getAsJsonObject();

            for (Map.Entry<String, JsonElement> node : jsonProcessObj.entrySet()) {
                String dataName = node.getKey();
                if (!DATA_NAME_PATTERN.matcher(dataModelName).matches()) {
                    throw new ProcessDataModelException("Data name have to match " + DATA_NAME_PATTERN.pattern() + " pattern");
                }

                JsonElement jsonDataType = node.getValue();
                if (!jsonDataType.isJsonPrimitive() || !jsonDataType.getAsJsonPrimitive().isString()) {
                    throw new ProcessDataModelException("Data type have to be a JSON String");
                }

                String dataTypeStr = jsonDataType.getAsString();
                DataType dataType = DataType.fromString(dataTypeStr);
                if (dataType == null) {
                    throw new ProcessDataModelException("Unknown data type " + dataTypeStr);
                }

                dataModel.addDataDefinition(new DataDefinition(dataName, dataType));
                LOGGER.debug("Added '{}' [{}] to {} data model", new Object[]{dataName, dataType.toString(), dataModelName});
            }
        } catch (JsonIOException ex) {
            throw new ProcessIOException("Error reading process " + dataModelName + " data model file", ex);
        } catch (JsonSyntaxException ex) {
            throw new ProcessDataModelException("Invalid process " + dataModelName + " data model structure", ex);
        } finally {
            IOUtils.closeQuietly(streamReader);
        }
        
        return dataModel;
    }

    /**
     * Populate a JSONProcess input data model from input stream.
     * @param inputStream source input stream
     * @param process target process
     * @throws ProcessIOException if any exception occurred while reading from input stream
     * @throws ProcessDataModelException if expected data model structure or definition is not met
     */
    public static void populateProcessInputDataModel(final InputStream inputStream, final JSONProcess process) throws ProcessIOException, ProcessDataModelException {
        process.setInputDataModel(readDataModelFromInputStream(inputStream, process, "input"));
    }

    /**
     * Populate a JSONProcess output data model from input stream.
     * @param inputStream source input stream
     * @param process target process
     * @throws ProcessIOException if any exception occurred while reading from input stream
     * @throws ProcessDataModelException if expected data model structure or definition is not met
     */
    public static void populateProcessOutputDataModel(final InputStream inputStream, final JSONProcess process) throws ProcessIOException, ProcessDataModelException {
        process.setOutputDataModel(readDataModelFromInputStream(inputStream, process, "output"));
    }
}
