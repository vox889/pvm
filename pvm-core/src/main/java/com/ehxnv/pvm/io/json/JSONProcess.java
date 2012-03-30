/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm.io.json;

import com.ehxnv.pvm.api.Process;
import com.ehxnv.pvm.api.ProcessMetadata;
import com.ehxnv.pvm.api.data.DataModel;
import com.ehxnv.pvm.api.execution.ExecutionEnv;
import com.ehxnv.pvm.api.graph.Node;
import com.ehxnv.pvm.api.graph.StartNode;
import com.ehxnv.pvm.execution.js.JavaScriptExecutionEnv;

import java.util.HashMap;
import java.util.Map;
/**
 * JSON based {@link Process}.
 * 
 * @author Eka Lie
 */
class JSONProcess implements Process {

    /** Start node. **/
    private StartNode startNode;
    /** This process metadata. **/
    private ProcessMetadata metadata;
    /** Map that holds all nodes, mapped by their name **/
    private Map<String, Node> nodeMap;
    /** JavaScript which contain execution logic. **/
    private String javascriptContent;
    /** The input data model. **/
    private DataModel inputDataModel;
    /** The output data model. **/
    private DataModel outputDataModel;

    /**
     * Constructor.
     * @param metadata process metadata
     */
    public JSONProcess(final ProcessMetadata metadata) {
        nodeMap = new HashMap<String, Node>();
        this.metadata = new ProcessMetadata(metadata.getName(), metadata.getVersion());
    }
    
    /**
     * Set the process start node.
     * @param node start node
     */
    public void setStartNode(final StartNode node) {
        this.startNode = node;
    }

    /**
     * Add a node to this process.
     * @param node node to be added
     */
    public void addNode(final Node node) {
        nodeMap.put(node.getName(), node);
    }

    /**
     * Set this process input data model.
     * @param inputDataModel input data model
     */
    public void setInputDataModel(final DataModel inputDataModel) {
        this.inputDataModel = inputDataModel;
    }

    /**
     * Set this process output data model.
     * @param outputDataModel output data model
     */
    public void setOutputDataModel(final DataModel outputDataModel) {
        this.outputDataModel = outputDataModel;
    }

    /**
     * Set this process JavaScript execution logic.
     * @param javascriptContent JavaScript execution logic
     */
    public void setJavascriptContent(final String javascriptContent) {
        this.javascriptContent = javascriptContent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProcessMetadata getMetadata() {
        return metadata;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Node getNodeByName(final String nodeName) {
        return nodeMap.get(nodeName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StartNode getStartNode() {
        return startNode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ExecutionEnv getExecutionEnvironment() {
        return new JavaScriptExecutionEnv(javascriptContent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataModel getInputDataModel() {
        return inputDataModel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataModel getOutputDataModel() {
        return outputDataModel;
    }
}
