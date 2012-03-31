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

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.objectweb.asm.Opcodes.*;

/**
 * An utility to build a class. This utility allows the addition of properties which
 * can be a read-only or writable properties.
 * 
 * @author Eka Lie
 */
public class ClassBuilder {

    /** Logger. **/
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassBuilder.class); 
    
    /** Byte-based class loader. **/
    private static final ByteClassLoader CLASS_LOADER = new ByteClassLoader();
    /** Class name. **/
    private String className;
    /** Class properties. **/
    private List<Property> properties;

    /**
     * Constructor.
     * @param name class name
     */
    public ClassBuilder(final String name) {
        this.className = name;
        this.properties = new ArrayList<Property>();
    }

    /**
     * Add a property to the class.
     * @param property target property
     * @return the class builder
     */
    public ClassBuilder addProperty(final Property property) {
        properties.add(property);
        return this;
    }

    /**
     * Build class based on properties added before hand.
     * @return class
     */
    public Class buildClass() {
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);

        // create class
        cw.visit(V1_5, ACC_PUBLIC, className, null, "java/lang/Object", null);
        addConstructor(cw);

        // add all properties
        for (Property prop : properties) {
            addProperty(cw, prop);
        }

        cw.visitEnd();

        byte[] bytes = cw.toByteArray();
        return CLASS_LOADER.loadClass(className, bytes);
    }

    /**
     * Add a constructor to the class
     * @param classWriter ASM class writer instance
     */
    private void addConstructor(final ClassWriter classWriter) {
        MethodVisitor mv = classWriter.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
        mv.visitInsn(RETURN);
        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

    /**
     * Add a property to the class
     * @param classWriter ASM class writer instance
     * @param property property
     */
    private void addProperty(final ClassWriter classWriter, final Property property) {
        addField(classWriter, property.name, property.type);

        if (property.accessorName == null) {
            LOGGER.debug("Adding default getter for {}[{}]", new Object[] {property.name, property.type});
            addGetter(classWriter, property.name, property.type);
        } else {
            LOGGER.debug("Adding getter [{}] for {}[{}]", new Object[] {property.accessorName, property.name, property.type});
            addGetter(classWriter, property.name, property.type, property.accessorName);
        }

        if (!property.readOnly) {
            if (property.mutatorName == null) {
                LOGGER.debug("Adding default setter for {}[{}]", new Object[] {property.name, property.type});
                addSetter(classWriter, property.name, property.type);
            } else {
                LOGGER.debug("Adding setter [{}] for {}[{}]", new Object[] {property.mutatorName, property.name, property.type});
                addSetter(classWriter, property.name, property.type, property.mutatorName);
            }
        }
    }

    /**
     * Add a field to the class.
     * @param classWriter ASM class writer instance
     * @param fieldName field name
     * @param fieldType field type
     */
    private void addField(final ClassWriter classWriter, final String fieldName, final Class fieldType) {
        classWriter.visitField(ACC_PRIVATE, fieldName, Type.getType(fieldType).getDescriptor(), null, null).visitEnd();
    }

    /**
     * Add a field getter method to the class.
     * @param classWriter ASM class writer instance
     * @param fieldName field name
     * @param fieldType field type
     */
    private void addGetter(final ClassWriter classWriter, final String fieldName, final Class fieldType) {
        String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        addGetter(classWriter, fieldName, fieldType, methodName);
    }

    /**
     * Add a field getter method to the class.
     * @param classWriter ASM class writer instance
     * @param fieldName field name
     * @param fieldType field type
     * @param getterName getter method name
     */
    private void addGetter(final ClassWriter classWriter, final String fieldName, final Class fieldType, final String getterName) {
        Type asmType = Type.getType(fieldType);
        String fieldTypeStr = asmType.getDescriptor();
        MethodVisitor mv = classWriter.visitMethod(ACC_PUBLIC, getterName, "()" + fieldTypeStr, null, null);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitFieldInsn(GETFIELD, className, fieldName, fieldTypeStr);
        mv.visitInsn(asmType.getOpcode(IRETURN));
        mv.visitMaxs(0, 0);
    }
    
    /**
     * Add a field setter method to a class.
     * @param classWriter ASM class writer instance
     * @param fieldName field name
     * @param fieldType field type
     */
    private void addSetter(final ClassWriter classWriter, final String fieldName, final Class fieldType) {
        String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        addSetter(classWriter, fieldName, fieldType, methodName);
    }

    /**
     * Add a field setter method to a class.
     * @param classWriter ASM class writer instance
     * @param fieldName field name
     * @param fieldType field type
     * @param setterName setter method name
     */
    private void addSetter(final ClassWriter classWriter, final String fieldName, final Class fieldType, final String setterName) {
        Type asmType = Type.getType(fieldType);
        String fieldTypeStr = asmType.getDescriptor();
        MethodVisitor mv = classWriter.visitMethod(ACC_PUBLIC, setterName, "(" + fieldTypeStr + ")V", null, null);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitVarInsn(asmType.getOpcode(ILOAD), 1);
        mv.visitFieldInsn(PUTFIELD, className, fieldName, fieldTypeStr);
        mv.visitInsn(RETURN);
        mv.visitMaxs(0, 0);
    }

    /**
     * POJO represents the class property.
     */
    public static abstract class Property {

        /** Property name. **/
        private String name;
        /** Property type. **/
        private Class type;
        /** Is property read only. **/
        private boolean readOnly;
        /** Property accessor method name. **/
        protected String accessorName;
        /** Property mutator method name. **/
        protected String mutatorName;

        /**
         * Constructor.
         * @param name property name
         * @param type property type
         * @param readOnly true if property is read only, false otherwise
         */
        public Property(final String name, final Class type, final boolean readOnly) {
            this.name = name;
            this.type = type;
            this.readOnly = readOnly;
        }

        public String getAccessorName() {
            return accessorName;
        }

        public String getMutatorName() {
            return mutatorName;
        }

        public String getName() {
            return name;
        }

        public boolean isReadOnly() {
            return readOnly;
        }

        public Class getType() {
            return type;
        }        
    }

    public static class ReadOnlyProperty extends Property {

        public ReadOnlyProperty(String name, Class type) {
            super(name, type, true);
        }

        public ReadOnlyProperty(String name, Class type, String accessorName) {
            this(name, type);
            this.accessorName = accessorName;
        }
    }

    public static class WritableProperty extends Property {

        public WritableProperty(String name, Class type) {
            super(name, type, false);
        }

        public WritableProperty(String name, Class type, String mutatorName) {
            this(name, type);
            this.mutatorName = mutatorName;
        }
    }

    /**
     * Custom class loader which loads class from a byte array.
     */
    private static final class ByteClassLoader extends ClassLoader {

        /**
         * Load class from a given byte array.
         * @param name class name
         * @param bytes class definition (in bytes)
         * @return class
         */
        public Class loadClass(final String name, final byte[] bytes) {
            return defineClass(name, bytes, 0, bytes.length);
        }
    }
}
