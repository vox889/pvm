/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static com.ehxnv.pvm.util.ValidationUtil.*;

/**
 * Represents a data.
 * Note that getValue() and setValue() methods are especially made abstract here
 * to enforce JVM to treat <T> as the actual type instead of Object type.
 * @param <T> data value class
 * 
 * @author Eka Lie
 */
public abstract class Data<T> {

    /** Logger. **/
    private static final Logger LOGGER = LoggerFactory.getLogger(Data.class); 

    /** Data name. **/
    private String name;
    /** Data value. **/
    protected T value;

    /**
     * Constructor.
     * @param name data name
     * @param value data value
     */
    public Data(final String name, final T value) {
        checkForNull("Data name", name);
        this.name = name;
        this.value = value;
    }

    /**
     * Get data name.
     * @return data name
     */
    public String getName() {
        return name;
    }

    /**
     * Get data value.
     * @return data value
     */
    public abstract T getValue();

    /**
     * Set data value internally.
     * @param value data value
     * @throws InvalidValueException if data can't be set using given value
     */
    protected abstract void setValueInternal(final Object value) throws InvalidValueException;

    /**
     * Set data value.
     * @param value new data value
     */
    public void setValue(final Object value) throws InvalidValueException {
        // TODO: should we throw exception here if data can't be converted?
        T oldValue = this.value;
        setValueInternal(value);
        LOGGER.debug("Setting '{}' value from [{}] to [{}]", new Object[] {name, oldValue, this.value});
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

        final Data<T> other = (Data<T>) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    /**
     * Get data type.
     * @return data type
     */
    public abstract DataType getType();
}
