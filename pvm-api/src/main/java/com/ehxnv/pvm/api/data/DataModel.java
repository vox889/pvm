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

package com.ehxnv.pvm.api.data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Collections;
import static com.ehxnv.pvm.api.util.ValidationUtil.*;

/**
 * Represents a data model. A data model has a name and comprises of a list of data definition.
 * 
 * @author Eka Lie
 */
public class DataModel implements Serializable {

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
