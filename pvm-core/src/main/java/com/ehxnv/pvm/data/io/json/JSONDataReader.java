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
import com.ehxnv.pvm.api.data.io.DataReader;
import com.ehxnv.pvm.api.data.io.DataStructureException;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * DataReader implementation which reads data from JSON format.
 * @author Eka Lie
 */
public class JSONDataReader implements DataReader {

    /** Logger. **/
    private static final Logger LOGGER = LoggerFactory.getLogger(JSONDataReader.class);
    /** Data name pattern. **/
    private static final Pattern DATA_NAME_PATTERN = Pattern.compile("[a-z][a-zA-Z_]*");

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Data> readData(final InputStream inputStream) throws DataIOException, DataStructureException {
        LOGGER.debug("Reading JSON data from an input stream");

        InputStreamReader streamReader = new InputStreamReader(inputStream);
        JsonParser parser = new JsonParser();

        Set<Data> datas = new HashSet<Data>();
        try {
            JsonElement jsonDatas = parser.parse(new JsonReader(streamReader));
            if (!jsonDatas.isJsonObject()) {
                throw new DataStructureException("Input stream have to be a JSON object");
            }

            JsonObject jsonDataObj = jsonDatas.getAsJsonObject();

            for (Map.Entry<String, JsonElement> node : jsonDataObj.entrySet()) {
                String dataName = node.getKey();
                if (!DATA_NAME_PATTERN.matcher(dataName).matches()) {
                    throw new DataStructureException("Data name have to match " + DATA_NAME_PATTERN.pattern() + " pattern");
                }

                JsonElement jsonDataValue = node.getValue();
                if (!jsonDataValue.isJsonPrimitive()) {
                    throw new DataStructureException("Data type have to be a JSON primitive");
                }

                JsonPrimitive jsonDataPrimitive = jsonDataValue.getAsJsonPrimitive();
                Data data;
                
                if (jsonDataPrimitive.isBoolean()) {
                    data = new BooleanData(dataName, jsonDataPrimitive.getAsBoolean());
                } else if (jsonDataPrimitive.isString()) {
                    data = new StringData(dataName, jsonDataPrimitive.getAsString());
                } else if (jsonDataPrimitive.isNumber()) {
                    Number number = jsonDataPrimitive.getAsNumber();
                    if (number instanceof BigDecimal) {
                        data = new DecimalData(dataName, jsonDataPrimitive.getAsBigDecimal());
                    } else if (number instanceof Integer) {
                        data = new IntegerData(dataName, jsonDataPrimitive.getAsInt());
                    } else {
                        throw new DataStructureException("Unknown number type for " + dataName + " data");
                    }
                } else {
                    throw new DataStructureException("Unknown data type for " + dataName + " data");
                }

                datas.add(data);
                LOGGER.debug("Found data {}[{}] with value of {}", new Object[] {data.getName(), data.getType(), data.getValue()});
            }

        } catch (JsonIOException ex) {
            throw new DataIOException("Error reading datas from input stream", ex);
        } catch (JsonSyntaxException ex) {
            throw new DataStructureException("Invalid data structure from input stream", ex);
        } finally {
            IOUtils.closeQuietly(streamReader);
        }

        return datas;
    }
}
