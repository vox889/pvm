/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm.api.data;

import java.math.BigDecimal;

/**
 * Factory object to create default {@link Data} based on {@link DataDefinition}.
 * 
 * @author Eka Lie
 */
public abstract class DataFactory {

    /**
     * Private constructor.
     */
    private DataFactory() {
        // do nothing
    }

    /**
     * Create a data with a default value from given data definition.
     * @param dataDef data definition
     * @return data with default value
     */
    public static Data createData(final DataDefinition dataDef) {
        if (DataType.BOOLEAN.equals(dataDef.getDataType())) {
            return new BooleanData(dataDef.getDataName(), Boolean.FALSE);
        } else if (DataType.DECIMAL.equals(dataDef.getDataType())) {
            return new DecimalData(dataDef.getDataName(), BigDecimal.ZERO);
        } else if (DataType.INTEGER.equals(dataDef.getDataType())) {
            return new IntegerData(dataDef.getDataName(), 0);
        } else if (DataType.STRING.equals(dataDef.getDataType())) {
            return new StringData(dataDef.getDataName(), "");
        }

        throw new UnsupportedOperationException(("Unsupported data type " + dataDef.getDataType()));
    }
}
