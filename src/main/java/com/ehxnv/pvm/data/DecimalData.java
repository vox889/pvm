/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm.data;

import java.math.BigDecimal;

/**
 * Represents a decimal data.
 * 
 * @author Eka Lie
 */
public class DecimalData extends Data<BigDecimal> {

    /**
     * Constructor.
     * @param name data name
     * @param value data value
     */
    public DecimalData(final String name, final BigDecimal value) {
        super(name, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal getValue() {
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setValueInternal(final Object value) throws InvalidValueException {
        try {
            double dbl = Double.parseDouble(String.valueOf(value));
            this.value = BigDecimal.valueOf(dbl);
        } catch (NumberFormatException ex) {
            throw new InvalidValueException("Can't set " + value + " as the value of Decimal data type", ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataType getType() {
        return DataType.DECIMAL;
    }
}
