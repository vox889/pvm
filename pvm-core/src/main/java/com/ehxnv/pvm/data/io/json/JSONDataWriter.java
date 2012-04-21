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

package com.ehxnv.pvm.data.io.json;

import com.ehxnv.pvm.api.data.*;
import com.ehxnv.pvm.api.data.io.DataIOException;
import com.ehxnv.pvm.api.data.io.DataWriter;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Set;

/**
 * DataWriter implementation which writes data in JSON format.
 * @author Eka Lie
 */
public class JSONDataWriter implements DataWriter<JsonObject> {

    /** Logger. **/
    private static final Logger LOGGER = LoggerFactory.getLogger(JSONDataWriter.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeData(final Set<Data> outputDatas, final OutputStream outputStream) throws DataIOException {
        LOGGER.debug("Found {} data to be written to JSON format (output stream)", outputDatas.size());

        JsonWriter jsonWriter = new JsonWriter(new OutputStreamWriter(outputStream));

        try {
            jsonWriter.beginObject();
            for (Data data : outputDatas) {
                jsonWriter.name(data.getName());

                DataType dataType = data.getType();
                if (DataType.BOOLEAN.equals(dataType)) {
                    BooleanData booleanData = (BooleanData) data;
                    jsonWriter.value(booleanData.getValue());
                } else if (DataType.DECIMAL.equals(dataType)) {
                    DecimalData decimalData = (DecimalData) data;
                    jsonWriter.value(decimalData.getValue());
                } else if (DataType.INTEGER.equals(dataType)) {
                    IntegerData integerData = (IntegerData) data;
                    jsonWriter.value(integerData.getValue());
                } else if (DataType.STRING.equals(dataType)) {
                    StringData stringData = (StringData) data;
                    jsonWriter.value(stringData.getValue());
                }
                
                LOGGER.debug("Writing {}[{}] with value {}", new Object[] {data.getName(), data.getType(), data.getValue()});
            }
            jsonWriter.endObject();
        } catch (IOException ex) {
            throw new DataIOException("Failed to write JSON output data into output stream", ex);
        } finally {
            IOUtils.closeQuietly(jsonWriter);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JsonObject writeData(final Set<Data> outputDatas) throws DataIOException {
        LOGGER.debug("Found {} data to be written to JSON format", outputDatas.size());

        JsonObject jsonObject = new JsonObject();
        for (Data data : outputDatas) {
            String dataName = data.getName();

            DataType dataType = data.getType();
            if (DataType.BOOLEAN.equals(dataType)) {
                BooleanData booleanData = (BooleanData) data;
                jsonObject.addProperty(dataName, booleanData.getValue());
            } else if (DataType.DECIMAL.equals(dataType)) {
                DecimalData decimalData = (DecimalData) data;
                jsonObject.addProperty(dataName, decimalData.getValue());
            } else if (DataType.INTEGER.equals(dataType)) {
                IntegerData integerData = (IntegerData) data;
                jsonObject.addProperty(dataName, integerData.getValue());
            } else if (DataType.STRING.equals(dataType)) {
                StringData stringData = (StringData) data;
                jsonObject.addProperty(dataName, stringData.getValue());
            }

            LOGGER.debug("Writing {}[{}] with value {}", new Object[] {data.getName(), data.getType(), data.getValue()});
        }

        return jsonObject;
    }
}
