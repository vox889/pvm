/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm.data;

/**
 * Represents an integer data.
 * 
 * @author Eka Lie
 */
public class IntegerData extends Data<Integer> {

    /**
     * Constructor.
     * @param name data name
     * @param value data value
     */
    public IntegerData(final String name, final Integer value) {
        super(name, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getValue() {
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setValueInternal(final Object value) {
        try {
            Double dbl = Double.valueOf(String.valueOf(value));
            this.value = Integer.valueOf(dbl.intValue());
        } catch (NumberFormatException ex) {
            throw new InvalidValueException("Can't set " + value + " as the value of Integer data type", ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataType getType() {
        return DataType.INTEGER;
    }
}
