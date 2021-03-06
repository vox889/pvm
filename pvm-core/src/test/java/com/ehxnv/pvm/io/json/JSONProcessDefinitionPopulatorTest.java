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

import com.ehxnv.pvm.api.ProcessMetadata;
import com.ehxnv.pvm.api.graph.JunctionNode;
import com.ehxnv.pvm.api.graph.Node;
import com.ehxnv.pvm.api.graph.ProcessNode;
import com.ehxnv.pvm.api.graph.StartNode;
import org.junit.Test;

import java.io.InputStream;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit test for {@link JSONProcessDefinitionPopulator}.
 * 
 * @author Eka Lie
 */
public class JSONProcessDefinitionPopulatorTest {

    /**
     * Test of populateProcessDefinition method (basic), of class JSONProcessDefinitionPopulator.
     */
    @Test
    public void testPopulateProcessDefinitionBasic() throws Exception {
        InputStream inputStream = getClass().getResourceAsStream("definition_basic.json");
        JSONProcess process = new JSONProcess(new ProcessMetadata("test_basic", "1.0"));

        JSONProcessDefinitionPopulator.populateProcessDefinition(inputStream, process);

        // assert process metadata
        ProcessMetadata metadata = process.getMetadata();
        assertEquals("test_basic", metadata.getName());
        assertEquals("1.0", metadata.getVersion());

        // assert process structure
        Node startNode = process.getStartNode();
        assertTrue(startNode != null);
        assertTrue(startNode instanceof JSONStartNode);
        assertEquals("start", startNode.getName());
        assertEquals("process1", ((StartNode) startNode).getNextNodeName());

        Node processNode = process.getNodeByName("process1");
        assertTrue(processNode != null);
        assertTrue(processNode instanceof JSONProcessNode);
        assertEquals("process1", processNode.getName());
        assertEquals("junction1", ((ProcessNode) processNode).getNextNodeName());

        Node junctionNode = process.getNodeByName("junction1");
        assertTrue(junctionNode != null);
        assertTrue(junctionNode instanceof JSONJunctionNode);
        assertEquals("junction1", junctionNode.getName());

        Set<String> nextPossibleNodeNames = ((JunctionNode) junctionNode).getNextPossibleNodeNames();
        assertTrue(nextPossibleNodeNames.contains("process2"));
        assertTrue(nextPossibleNodeNames.contains("process3"));

        Node processNode2 = process.getNodeByName("process2");
        assertTrue(processNode2 != null);
        assertTrue(processNode2 instanceof JSONProcessNode);
        assertEquals("process2", processNode2.getName());
        assertEquals("end1", ((ProcessNode) processNode2).getNextNodeName());

        Node processNode3 = process.getNodeByName("process3");
        assertTrue(processNode3 != null);
        assertTrue(processNode3 instanceof JSONProcessNode);
        assertEquals("process3", processNode3.getName());
        assertEquals("end1", ((ProcessNode) processNode3).getNextNodeName());

        Node endNode = process.getNodeByName("end1");
        assertTrue(endNode != null);
        assertTrue(endNode instanceof JSONEndNode);
        assertEquals("end1", endNode.getName());
    }
}
