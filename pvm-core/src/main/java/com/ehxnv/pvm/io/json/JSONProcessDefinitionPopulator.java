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
import com.ehxnv.pvm.api.io.ProcessStructureException;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Helper class to populate a {@link JSONProcess} process definition from an input stream to an instance of process.
 * 
 * @author Eka Lie
 */
class JSONProcessDefinitionPopulator {

    /** Logger. **/
    private static final Logger LOGGER = LoggerFactory.getLogger(JSONProcessDefinitionPopulator.class);
    /** Start node name pattern. **/
    private static final Pattern START_NODE_PATTERN = Pattern.compile("^start$");
    /** Process node name pattern. **/
    private static final Pattern PROCESS_NODE_PATTERN = Pattern.compile("^process_([a-zA-Z][a-zA-Z0-9]*)$");
    /** Junction node name pattern. **/
    private static final Pattern JUNCTION_NODE_PATTERN = Pattern.compile("^junction_([a-zA-Z][a-zA-Z0-9]*)$");
    /** End node name pattern. **/
    private static final Pattern END_NODE_PATTERN = Pattern.compile("^end_([a-zA-Z][a-zA-Z0-9]*)$");
    /** Node patterns. **/
    private static final Pattern[] NODE_PATTERNS = new Pattern[]{START_NODE_PATTERN, PROCESS_NODE_PATTERN, JUNCTION_NODE_PATTERN, END_NODE_PATTERN};
    /** Node handler property name. **/
    private static final String HANDLER_PROPERTY = "handler";
    /** Node next property name. **/
    private static final String NEXT_PROPERTY = "next";

    /**
     * Populate a JSONProcess definition from an input stream.
     * @param inputStream input stream
     * @param process process
     * @throws ProcessIOException if any IO exception occurred
     * @throws ProcessStructureException if expected process definition structure is not met
     */
    public static void populateProcessDefinition(final InputStream inputStream, final JSONProcess process) throws ProcessIOException, ProcessStructureException {
        LOGGER.debug("Populating JSONProcess[{}] definition", String.valueOf(process.getMetadata()));

        InputStreamReader streamReader = new InputStreamReader(inputStream);
        JsonParser parser = new JsonParser();

        try {
            JsonElement jsonProcess = parser.parse(new JsonReader(streamReader));
            if (!jsonProcess.isJsonObject()) {
                throw new ProcessStructureException("Process definition have to be a JSON object");
            }

            JsonObject jsonProcessObj = jsonProcess.getAsJsonObject();

            Set<JSONNode> jsonNodes = getJSONNodes(jsonProcessObj);
            for (JSONNode node : jsonNodes) {
                String nodeName = node.getName();
                JSONNode.Type nodeType = node.getType();
                JsonObject nodeDefinition = node.getDefinition();

                if (JSONNode.Type.START.equals(nodeType)) {
                    setProcessStartNode(nodeName, nodeDefinition, process);
                    LOGGER.debug("Process start node set!");
                } else if (JSONNode.Type.PROCESS.equals(nodeType)) {
                    addProcessNodeToProcess(nodeName, nodeDefinition, process);
                    LOGGER.debug("Added process node '{}'", nodeName);
                } else if (JSONNode.Type.JUNCTION.equals(nodeType)) {
                    addJunctionNodeToProcess(nodeName, nodeDefinition, process);
                    LOGGER.debug("Added junction node '{}'", nodeName);
                } else if (JSONNode.Type.END.equals(nodeType)) {
                    addEndNodeToProcess(nodeName, nodeDefinition, process);
                    LOGGER.debug("Added end node '{}'", nodeName);
                }
            }
        } catch (JsonIOException ex) {
            throw new ProcessIOException("Error reading definition file", ex);
        } catch (JsonSyntaxException ex) {
            throw new ProcessStructureException("Invalid process definition structure", ex);
        } finally {
            IOUtils.closeQuietly(streamReader);
        }
    }

    /**
     * Set process start node.
     * @param nodeName start node name
     * @param nodeDefinition start node definition
     * @param process target process
     * @throws ProcessStructureException if start node definition is not as expectation
     */
    private static void setProcessStartNode(final String nodeName, final JsonObject nodeDefinition, final JSONProcess process) throws ProcessStructureException {
        if (!hasHandlerProperty(nodeDefinition)) {
            throw new ProcessStructureException("Start node definition must have a handler property");
        }

        if (!hasNextProperty(nodeDefinition)) {
            throw new ProcessStructureException("Start node definition must have a next property");
        }

        process.setStartNode(new JSONStartNode(nodeName, getNextProperty(nodeDefinition), getHandlerProperty(nodeDefinition)));
    }

    /**
     * Add a process node to process.
     * @param nodeName process node name
     * @param nodeDefinition process node definition
     * @param process target process
     * @throws ProcessStructureException if process node definition is not as expectation
     */
    private static void addProcessNodeToProcess(final String nodeName, final JsonObject nodeDefinition, final JSONProcess process) throws ProcessStructureException {
        if (!hasHandlerProperty(nodeDefinition)) {
            throw new ProcessStructureException("Process node " + nodeName + " definition must have a handler property");
        }

        if (!hasNextProperty(nodeDefinition)) {
            throw new ProcessStructureException("Process node " + nodeName + " definition must have a next property");
        }

        process.addNode(new JSONProcessNode(nodeName, getNextProperty(nodeDefinition), getHandlerProperty(nodeDefinition)));
    }

    /**
     * Add a junction node to process.
     * @param nodeName junction node name
     * @param nodeDefinition junction node definition
     * @param process target process
     * @throws ProcessStructureException if junction node definition is not as expectation
     */
    private static void addJunctionNodeToProcess(final String nodeName, final JsonObject nodeDefinition, final JSONProcess process) throws ProcessStructureException {
        if (!hasHandlerProperty(nodeDefinition)) {
            throw new ProcessStructureException("Junction node " + nodeName + " definition must have a handler property");
        }

        if (!hasJunctionNextProperty(nodeDefinition)) {
            throw new ProcessStructureException("Junction node " + nodeName + " definition must have a next property");
        }

        process.addNode(new JSONJunctionNode(nodeName, getJunctionNextProperty(nodeDefinition), getHandlerProperty(nodeDefinition)));
    }

    /**
     * Add an end node to process.
     * @param nodeName end node name
     * @param nodeDefinition end node definition
     * @param process target process
     * @throws ProcessStructureException if end node definition is not as expectation
     */
    private static void addEndNodeToProcess(final String nodeName, final JsonObject nodeDefinition, final JSONProcess process) throws ProcessStructureException {
        if (!hasHandlerProperty(nodeDefinition)) {
            throw new ProcessStructureException("End node " + nodeName + " definition must have a handler property");
        }

        process.addNode(new JSONEndNode(nodeName, getHandlerProperty(nodeDefinition)));
    }

    /**
     * Check if node definition has a handler property.
     * @param nodeDefinition node definition
     * @return true if node has a handler property, false otherwise
     */
    private static boolean hasHandlerProperty(final JsonObject nodeDefinition) {
        return (nodeDefinition.has(HANDLER_PROPERTY) && nodeDefinition.get(HANDLER_PROPERTY).getAsJsonPrimitive().isString());
    }

    /**
     * Get the handler property from a node definition.
     * @param nodeDefinition node definition
     * @return node handler property value
     */
    private static String getHandlerProperty(final JsonObject nodeDefinition) {
        return nodeDefinition.get(HANDLER_PROPERTY).getAsString();
    }

    /**
     * Check if node definition has a next property.
     * @param nodeDefinition node definition
     * @return true if node has a next property, false otherwise
     */
    private static boolean hasNextProperty(final JsonObject nodeDefinition) {
        return (nodeDefinition.has(NEXT_PROPERTY) && nodeDefinition.get(NEXT_PROPERTY).getAsJsonPrimitive().isString());
    }

    /**
     * Get the next property from a node definition.
     * @param nodeDefinition node definition
     * @return node next property value
     */
    private static String getNextProperty(final JsonObject nodeDefinition) {
        return nodeDefinition.get(NEXT_PROPERTY).getAsString();
    }

    /**
     * Check if junction node definition has a next property.
     * @param nodeDefinition node definition
     * @return true if junction node has a next property, false otherwise
     */
    private static boolean hasJunctionNextProperty(final JsonObject nodeDefinition) {
        boolean hasProp = nodeDefinition.has(NEXT_PROPERTY);

        if (hasProp) {
            JsonElement nextProp = nodeDefinition.get(NEXT_PROPERTY);
            hasProp = nextProp.isJsonArray();

            if (hasProp) {
                boolean allNextNodeNamesIsString = true;

                JsonArray nextPropArray = nextProp.getAsJsonArray();
                for (int i = 0, size = nextPropArray.size(); i < size; i++) {
                    if (!nextPropArray.get(i).isJsonPrimitive()) {
                        allNextNodeNamesIsString = false;
                        break;
                    }

                    if (!nextPropArray.get(i).getAsJsonPrimitive().isString()) {
                        allNextNodeNamesIsString = false;
                        break;
                    }
                }

                hasProp = allNextNodeNamesIsString;
            }
        }

        return hasProp;
    }

    /**
     * Get the next property from a junction node definition.
     * @param nodeDefinition junction node definition
     * @return junction node next property value
     */
    private static Set<String> getJunctionNextProperty(final JsonObject nodeDefinition) {
        Set<String> nextPossibleNodeNames = new HashSet<String>();
        JsonArray nextProp = nodeDefinition.get(NEXT_PROPERTY).getAsJsonArray();
        for (int i = 0, size = nextProp.size(); i < size; i++) {
            nextPossibleNodeNames.add(nextProp.get(i).getAsString());
        }
        return nextPossibleNodeNames;
    }

    /**
     * Get all JSON nodes from a given JSON representation of a process.
     * @param jsonProcessObj process JSON representation
     * @return JSON nodes
     * @throws ProcessStructureException if expected process structure is not met
     */
    private static Set<JSONNode> getJSONNodes(final JsonObject jsonProcessObj) throws ProcessStructureException {
        Set<JSONNode> jsonNodes = new HashSet<JSONNode>();
        int noOfStartNodes = 0;

        for (Entry<String, JsonElement> node : jsonProcessObj.entrySet()) {
            String nodePattern = node.getKey();

            Pattern matchedNodePattern = null;
            Matcher nodePatternMatcher = null;

            // for all available node patterns
            // validate if the current node key matches any of the pattern
            for (Pattern curNodePattern : NODE_PATTERNS) {
                nodePatternMatcher = curNodePattern.matcher(nodePattern);
                if (nodePatternMatcher.matches()) {
                    matchedNodePattern = curNodePattern;
                    break;
                }
            }

            if (matchedNodePattern == null) {
                throw new ProcessStructureException("Unknown node pattern " + nodePattern);
            }

            String nodeName = null;
            JSONNode.Type type = null;

            JsonElement nodeDefinition = node.getValue();
            if (!nodeDefinition.isJsonObject()) {
                throw new ProcessStructureException("Node definition have to be a JSON object");
            }

            if (START_NODE_PATTERN.equals(matchedNodePattern)) {
                nodeName = nodePattern;
                type = JSONNode.Type.START;
                noOfStartNodes++;

                // we can't have more than 1 start node, can't we?
                if (noOfStartNodes > 1) {
                    throw new ProcessStructureException("Process definition must have exactly 1 start node");
                }
            } else if (PROCESS_NODE_PATTERN.equals(matchedNodePattern)) {
                nodeName = nodePatternMatcher.group(1);
                type = JSONNode.Type.PROCESS;
            } else if (JUNCTION_NODE_PATTERN.equals(matchedNodePattern)) {
                nodeName = nodePatternMatcher.group(1);
                type = JSONNode.Type.JUNCTION;
            } else if (END_NODE_PATTERN.equals(matchedNodePattern)) {
                nodeName = nodePatternMatcher.group(1);
                type = JSONNode.Type.END;
            }

            jsonNodes.add(new JSONNode(nodeName, type, nodePattern, nodeDefinition.getAsJsonObject()));
        }

        return jsonNodes;
    }

    /**
     * Represents a JSONNode found in process definition.
     */
    private static class JSONNode {

        /** Node type. **/
        enum Type {

            START, PROCESS, JUNCTION, END
        }

        /** Node name. **/
        private String nodeName;
        /** Node type. **/
        private Type type;
        /** JSON key for this node. **/
        private String jsonKey;
        /** Reference to node element definition. **/
        private JsonObject nodeDefinition;

        /**
         * Constructor.
         * @param nodeName node name
         * @param type node type
         * @param jsonKey JSON key for this node
         * @param nodeDefinition reference to node element definition
         */
        public JSONNode(final String nodeName, final Type type, final String jsonKey, final JsonObject nodeDefinition ) {
            this.nodeName = nodeName;
            this.type = type;
            this.jsonKey = jsonKey;
            this.nodeDefinition = nodeDefinition;
        }

        /**
         * Get node name.
         * @return node name
         */
        public String getName() {
            return nodeName;
        }

        /**
         * Get node type.
         * @return node type
         */
        public Type getType() {
            return type;
        }

        /**
         * Get JSON key for this node.
         * @return node's JSON key
         */
        public String getJsonKey() {
            return jsonKey;
        }

        /**
         * Get node element definition.
         * @return node element definition
         */
        public JsonObject getDefinition() {
            return nodeDefinition;
        }
    }
}
