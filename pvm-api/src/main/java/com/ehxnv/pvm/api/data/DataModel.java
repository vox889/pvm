/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm.api.data;

import java.util.HashSet;
import java.util.Set;
import java.util.Collections;
import static com.ehxnv.pvm.api.util.ValidationUtil.*;

/**
 * Represents a data model. A data model has a name and comprises of a list of data definition.
 * 
 * @author Eka Lie
 */
public class DataModel {

    /** Model name. **/
    private String name;
    /** Model data definitions. **/
    private Set<DataDefinition> dataDefinitions;

    /**
     * Constructor.
     * @param name model name
     */
    public DataModel(final String name) {
        checkForNull("Data model name", name);
        this.name = name;
        this.dataDefinitions = new HashSet<DataDefinition>();
    }

    /**
     * Get model name.
     * @return model name
     */
    public String getName() {
        return name;
    }

    /**
     * Add a data definition to this model
     * @param dataDefinition data definition
     */
    public void addDataDefinition(final DataDefinition dataDefinition) {
        dataDefinitions.add(dataDefinition);
    }

    /**
     * Get data definitions associated to this model
     * @return model associated data definitions
     */
    public Set<DataDefinition> getDataDefinitions() {
        return Collections.unmodifiableSet(dataDefinitions);
    }
}
