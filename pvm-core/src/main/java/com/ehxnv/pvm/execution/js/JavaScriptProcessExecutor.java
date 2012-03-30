/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm.execution.js;

import com.ehxnv.pvm.api.Process;
import com.ehxnv.pvm.api.data.Data;
import com.ehxnv.pvm.api.data.DataDefinition;
import com.ehxnv.pvm.api.data.DataFactory;
import com.ehxnv.pvm.api.data.DataModel;
import com.ehxnv.pvm.api.execution.NodeHandler;
import com.ehxnv.pvm.api.execution.ProcessExecutionException;
import com.ehxnv.pvm.execution.AbstractProcessExecutor;
import org.mozilla.javascript.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static com.ehxnv.pvm.api.util.ValidationUtil.checkForNull;


/**
 * Implementation of {@link com.ehxnv.pvm.api.execution.ProcessExecutor} which uses Rhino JavaScript engine.
 * 
 * @author Eka Lie
 */
public class JavaScriptProcessExecutor extends AbstractProcessExecutor {

    /** Logger. **/
    private static final Logger LOGGER = LoggerFactory.getLogger(JavaScriptProcessExecutor.class);
    /** A flag to indicate that JavaScript context class shutter has been set. **/
    private static boolean classShutterUsed = false;
    /** Restricted class shutter to limit execution context i.e. not allowing importing of Java class from Rhino execution context. **/
    private static final ClassShutter RESTRICTED_CLASS_SHUTTER = new RestrictedClassShutter();

    /** JavaScript content. **/
    private String javaScript;
    /** JavaScript Context. **/
    private Context context;
    /** Execution scope. **/
    private ScriptableObject executionScope;
    /** Input object in execution scope. **/
    private ScriptableDataModel scriptableInput;
    /** Input object in execution scope. **/
    private ScriptableDataModel scriptableOutput;

    /**
     * Constructor.
     * @param javaScript JavaScript content
     */
    public JavaScriptProcessExecutor(final String javaScript) {
        checkForNull("JavaScript", javaScript);
        this.javaScript = javaScript;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initInternal(final Process process, final Set<Data> datas) throws ProcessExecutionException {
        context = Context.enter();
        
        if (!classShutterUsed) {
            context.setClassShutter(RESTRICTED_CLASS_SHUTTER);
            classShutterUsed = true;
        }

        executionScope = context.initStandardObjects();
        context.evaluateString(executionScope, javaScript, "logic", 1, null);
        executionScope.sealObject();

        DataModel inputDataModel = process.getInputDataModel();
        DataModel outputDataModel = process.getOutputDataModel();

        // initalize execution input and output object
        scriptableInput = new ScriptableDataModel(inputDataModel, true, datas);
        scriptableOutput = new ScriptableDataModel(outputDataModel, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Object executeNodeHandlerInternal(final Process process, final NodeHandler nodeHandler) throws ProcessExecutionException {
        if (!(nodeHandler instanceof JavaScriptNodeHandler)) {
            throw new UnsupportedOperationException("Only instance of JavaScriptNodeHandler are supported");
        }

        JavaScriptNodeHandler jsNodeHandler = (JavaScriptNodeHandler) nodeHandler;
        String jsFuncName = jsNodeHandler.getJsFunctionName();

        Object func = executionScope.get(jsNodeHandler.getJsFunctionName(), executionScope);
        if (func instanceof Function) {
            Function jsFunc = (Function) func;
            LOGGER.debug("Executing {}", jsFuncName);
            return jsFunc.call(context, executionScope, executionScope, new Object[] { scriptableInput, scriptableOutput });
        } else {
            throw new ProcessExecutionException(jsFuncName + " is not a function");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void finishInternal() throws ProcessExecutionException {
        Context.exit();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Data> getInputDatas() {
        if (scriptableInput == null) {
            return Collections.emptySet();
        } else {
            return scriptableInput.getDatas();
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Data> getOutputDatas() {
        if (scriptableOutput == null) {
            return Collections.emptySet();
        } else {
            return scriptableOutput.getDatas();
        }
    }

    /**
     * A class shutter that restricts dynamic loading of java classes.
     */
    private static class RestrictedClassShutter implements ClassShutter {

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean visibleToScripts(final String string) {
            return false;
        }
    }

    /**
     * A Scriptable that is based on {@link DataModel}.
     */
    private static class ScriptableDataModel implements Scriptable {

        /** Data(s). **/
        private Map<String, Data> datas;
        /** Is this meant for read-only? **/
        private boolean readOnly;

        /**
         * Constructor.
         * @param dataModel source data model
         * @param readOnly read-only?
         */
        public ScriptableDataModel(final DataModel dataModel, final boolean readOnly) {
            datas = new HashMap<String, Data>();
            for (DataDefinition dataDef : dataModel.getDataDefinitions()) {
                datas.put(dataDef.getDataName(), DataFactory.createData(dataDef));
            }
            
            this.readOnly = readOnly;
        }

        /**
         * Constructor.
         * @param dataModel source data model
         * @param readOnly read-only?
         * @param initialDatas initial data(s)
         */
        public ScriptableDataModel(final DataModel dataModel, final boolean readOnly, final Set<Data> initialDatas) {
            this(dataModel, readOnly);
            initData(initialDatas);
        }

        /**
         * Get associated data(s).
         * @return associated data(s)
         */
        public Set<Data> getDatas() {
            return Collections.unmodifiableSet(new HashSet<Data>(datas.values()));
        }

        /**
         * Initialize Scriptable with initial datas.
         * @param initDatas initial data(s)
         */
        private void initData(final Set<Data> initDatas) {
            for (Data initData : initDatas) {
                Data data = datas.get(initData.getName());
                if (data != null) {
                    data.setValue(initData.getValue());
                }
            }
        }

        /**
         * {@inheritDoc}
         */
        public void delete(final String dataName) {
            throw new UnsupportedOperationException("Data " + dataName + " can't be deleted");
        }

        /**
         * {@inheritDoc}
         */
        public void delete(final int i) {
            delete(String.valueOf(i));
        }

        /**
         * {@inheritDoc}
         */
        public Object get(final String dataName, final Scriptable scope) {
            return datas.get(dataName).getValue();
        }

        /**
         * {@inheritDoc}
         */
        public Object get(final int i, final Scriptable scope) {
            return get(String.valueOf(i), scope);
        }

        /**
         * {@inheritDoc}
         */
        public String getClassName() {
            return null;
        }

        /**
         * {@inheritDoc}
         */
        public Object getDefaultValue(final Class<?> type) {
            return null;
        }

        /**
         * {@inheritDoc}
         */
        public Object[] getIds() {
            return null;
        }

        /**
         * {@inheritDoc}
         */
        public Scriptable getParentScope() {
            return null;
        }

        /**
         * {@inheritDoc}
         */
        public Scriptable getPrototype() {
            return null;
        }

        /**
         * {@inheritDoc}
         */
        public boolean has(final String dataName, final Scriptable scope) {
            return (datas.get(dataName) != null);
        }

        /**
         * {@inheritDoc}
         */
        public boolean has(final int i, final Scriptable scope) {
            return has(String.valueOf(i), scope);
        }

        /**
         * {@inheritDoc}
         */
        public boolean hasInstance(final Scriptable scope) {
            return false;
        }

        /**
         * {@inheritDoc}
         */
        public void put(final String dataName, final Scriptable scope, final Object obj) {
            if (readOnly) {
                throw new UnsupportedOperationException("Data " + dataName + " is read-only");
            }

            Data data = datas.get(dataName);
            if (data != null) {
                data.setValue(obj);
            }
        }

        /**
         * {@inheritDoc}
         */
        public void put(final int i, final Scriptable scope, final Object obj) {
            put(String.valueOf(i), scope, obj);
        }

        /**
         * {@inheritDoc}
         */
        public void setParentScope(final Scriptable scope) {
        }

        /**
         * {@inheritDoc}
         */
        public void setPrototype(final Scriptable scope) {
        }
    }
}
