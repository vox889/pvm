/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm.api.data;

import java.math.BigDecimal;

/**
 * Represents a data type.
 * 
 * @author Eka Lie
 */
public enum DataType {
    
    BOOLEAN(Boolean.class),

    STRING(String.class),
    
    INTEGER(Integer.class),

    DECIMAL(BigDecimal.class);

    /** Java class representation of the type. **/
    private Class clazz;

    /**
     * Constructor.
     * @param clazz Java class representation of type
     */
    private DataType(final Class clazz) {
        this.clazz = clazz;
    }

    /**
     * Get data type from a string representation.
     * @param str data type string representation
     * @return data type or NULL if none data type can be derived from the string
     */
    public static DataType fromString(final String str) {
        for (DataType dataType : values()) {
            if (dataType.toString().equalsIgnoreCase(str)) {
                return dataType;
            }
        }

        return null;
    }

    /**
     * Get Java class representation of the type.
     * @return Java class representation of the type
     */
    public Class getDataClass() {
        return clazz;
    }
}
