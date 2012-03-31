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

package com.ehxnv.pvm.util;

import com.ehxnv.pvm.util.ClassBuilder.Property;
import com.ehxnv.pvm.util.ClassBuilder.ReadOnlyProperty;
import com.ehxnv.pvm.util.ClassBuilder.WritableProperty;
import org.junit.Test;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Unit test for {@link ClassBuilder}.
 * 
 * @author Eka Lie
 */
public class ClassBuilderTest {

    /**
     * Test of buildClass method, of class ClassBuilder.
     */
    @Test
    public void testBuildClass() throws Exception {
        ClassBuilder builder = new ClassBuilder("input");
        Property[] props = { new ReadOnlyProperty("prop1", String.class),
                             new WritableProperty("prop2", Integer.class),
                             new ReadOnlyProperty("prop3", Boolean.class),
                             new WritableProperty("prop4", BigDecimal.class) };

        for (Property prop : props) {
            builder.addProperty(prop);
        }

        Class clazz = builder.buildClass();
        assertNotNull(clazz);

        // test if object can be created
        Object obj = clazz.newInstance();
        assertNotNull(obj);

        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propDescriptors = beanInfo.getPropertyDescriptors(); 
        propDescriptors = Arrays.copyOfRange(propDescriptors, 1, propDescriptors.length);

        for (int i = 0; i < propDescriptors.length; i++) {
            PropertyDescriptor propDescriptor = propDescriptors[i];
            assertEquals(props[i].getName(), propDescriptor.getName());
            assertEquals(props[i].getType(), propDescriptor.getPropertyType());
            assertEquals(props[i].isReadOnly(), (propDescriptor.getWriteMethod() == null));
        }
    }
}
