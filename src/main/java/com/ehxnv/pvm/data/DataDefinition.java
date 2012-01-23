/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm.data;

import static com.ehxnv.pvm.util.ValidationUtil.*;

/**
 * Represents the definition of a particular data, which at the moment contains:
 * <ul>
 *   <li>data name</li>
 *   <li>data type</li>
 * </ul>
 * 
 * @author Eka Lie
 */
public class DataDefinition {

    /** Data name. **/
    private String dataName;
    /** Data type. **/
    private DataType dataType;

    /**
     * Constructor.
     * @param dataName data name
     * @param dataType data type
     */
    public DataDefinition(final String dataName, final DataType dataType) {
        checkForNull("Data name", dataName);
        this.dataName = dataName;
        this.dataType = dataType;
    }

    /**
     * Get data name.
     * @return data name
     */
    public String getDataName() {
        return dataName;
    }

    /**
     * Get data type.
     * @return data type
     */
    public DataType getDataType() {
        return dataType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        final DataDefinition other = (DataDefinition) obj;
        if ((this.dataName == null) ? (other.dataName != null) : !this.dataName.equals(other.dataName)) {
            return false;
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + (this.dataName != null ? this.dataName.hashCode() : 0);
        return hash;
    }
}
