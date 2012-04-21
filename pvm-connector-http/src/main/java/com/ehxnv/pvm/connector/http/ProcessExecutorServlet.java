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

package com.ehxnv.pvm.connector.http;

import com.ehxnv.pvm.api.Process;
import com.ehxnv.pvm.api.ProcessInstance;
import com.ehxnv.pvm.api.ProcessMetadata;
import com.ehxnv.pvm.api.ProcessVirtualMachine;
import com.ehxnv.pvm.api.connector.Connector;
import com.ehxnv.pvm.api.data.Data;
import com.ehxnv.pvm.api.data.InvalidValueException;
import com.ehxnv.pvm.api.data.io.DataIOException;
import com.ehxnv.pvm.api.data.io.DataReader;
import com.ehxnv.pvm.api.data.io.DataStructureException;
import com.ehxnv.pvm.api.execution.ProcessExecutionException;
import com.ehxnv.pvm.api.repository.ProcessRepository;
import com.ehxnv.pvm.data.io.DataReaderFactory;
import com.ehxnv.pvm.data.io.DataWriterFactory;
import com.ehxnv.pvm.data.io.json.JSONDataWriter;
import com.ehxnv.pvm.util.ProcessMetadataHelper;
import com.google.gson.JsonObject;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Set;

/**
 * A simple Http servlet based {@link Connector} which will:
 * <ul>
 *     <li>found a process from {@link ProcessRepository} given a process metadata from caller (through "process" Http parameter")</li>
 *     <li>execute a process using {@link ProcessVirtualMachine} given the process, using "input" Http parameter" passed from the caller</li>
 *     <li>returns executed process instance information to caller in JSON format</li>
 * </ul>
 * @author Eka Lie
 */
public class ProcessExecutorServlet extends HttpServlet implements Connector {

    /** Http parameter name for process metadata. **/
    private static final String PROCESS_PARAM_NAME = "process";
    /** Http parameter name for process input. **/
    private static final String INPUT_PARAM_NAME = "input";

    /** Process repository to be used for searching process based on caller given metadata. **/
    private ProcessRepository processRepository;
    /** Process virtual machine to be used for executing found process. **/
    private ProcessVirtualMachine processVirtualMachine;

    /**
     * Constructor.
     * @param processRepository     process repository instance
     * @param processVirtualMachine process virtual machine instance
     */
    public ProcessExecutorServlet(final ProcessRepository processRepository,
                                  final ProcessVirtualMachine processVirtualMachine) {
        this.processRepository = processRepository;
        this.processVirtualMachine = processVirtualMachine;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return "allow user to execute a process based on given process metadata (through Http parameter)";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "http-connector";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        // validate that process metadata is not empty
        String processMetadataStr = req.getParameter(PROCESS_PARAM_NAME);
        if (StringUtils.isBlank(processMetadataStr)) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "process metadata can't be empty");
            return;
        }

        // validate that process input data is not empty
        String processInput = req.getParameter(INPUT_PARAM_NAME);
        if (StringUtils.isBlank(processInput)) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "process input can't be empty");
            return;
        }

        // try lookup process based on the metadata in the repository
        ProcessMetadata processMetadata = ProcessMetadataHelper.getMetadata(processMetadataStr);
        Process process = processRepository.findProcess(processMetadata);
        if (process == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Can't find process with metadata " + processMetadata);
        } else {
            // read input data and execute the process
            DataReader dataReader = DataReaderFactory.createJSONDataReader();
            try {
                Set<Data> inputDatas = dataReader.readData(new ByteArrayInputStream(processInput.getBytes()));
                ProcessInstance processInstance = processVirtualMachine.execute(process, inputDatas);

                // return process instance information to the caller
                writeProcessInstanceAsResponse(processInstance, resp);
                resp.setStatus(HttpServletResponse.SC_OK);
            } catch (DataIOException ex) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Can't parse process input data");
            } catch (DataStructureException ex) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Can't parse process input data");
            } catch (InvalidValueException ex) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input data: " + ex.getMessage());
            } catch (ProcessExecutionException ex) {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error while executing: " + ex.getMessage());
            }
        }
    }

    /**
     * Write process instance information as Http response.
     * @param processInstance process instance
     * @param response Http response object
     * @throws IOException if any IO exception occurred while writing to Http response
     * @throws DataIOException if any IO exception while converting process instance information into specific data i.e. JSON
     */
    private void writeProcessInstanceAsResponse(final ProcessInstance processInstance, final HttpServletResponse response)
        throws IOException, DataIOException {

        JSONDataWriter jsonDataWriter = (JSONDataWriter) DataWriterFactory.createJSONDataWriter();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", processInstance.getId());
        jsonObject.addProperty("start_time", String.valueOf(processInstance.getStartTime()));
        jsonObject.addProperty("end_time", String.valueOf(processInstance.getEndTime()));
        jsonObject.add("input_datas", jsonDataWriter.writeData(processInstance.getInputDatas()));
        jsonObject.add("output_datas", jsonDataWriter.writeData(processInstance.getOutputDatas()));

        response.setContentType("text/json");
        response.getOutputStream().print(jsonObject.toString());
    }
}
