/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm.data;

/**
 * Represents a string data.
 * 
 * @author Eka Lie
 */
public class StringData extends Data<String> {

    /**
     * Constructor.
     * @param name data name
     * @param value data value
     */
    public StringData(final String name, final String value) {
        super(name, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getValue() {
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setValueInternal(final Object value) {
        this.value = String.valueOf(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataType getType() {
        return DataType.STRING;
    }
}
