/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm.api.data;

/**
 * Represents a boolean data.
 * 
 * @author Eka Lie
 */
public class BooleanData extends Data<Boolean> {

    /**
     * Constructor.
     * @param name data name
     * @param value data value
     */
    public BooleanData(final String name, final Boolean value) {
        super(name, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean getValue() {
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setValueInternal(final Object value) {
        this.value = Boolean.valueOf(String.valueOf(value));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataType getType() {
        return DataType.BOOLEAN;
    }
}
