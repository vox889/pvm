/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm.util;

/**
 * Utility class for validation.
 * 
 * @author Eka Lie
 */
public abstract class ValidationUtil {

    /**
     * Check that given object is not null. Throws IllegalArgumentException if object is null.
     * @param objName object name
     * @param obj target object
     */
    public static void checkForNull(final String objName, final Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException(objName + " can't be null");
        }
    }
}
