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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static com.ehxnv.pvm.api.util.ValidationUtil.*;

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
