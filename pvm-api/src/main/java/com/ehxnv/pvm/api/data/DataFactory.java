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
