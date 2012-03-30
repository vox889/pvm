/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
