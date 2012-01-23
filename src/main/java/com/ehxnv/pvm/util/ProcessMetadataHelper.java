/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm.util;

import com.ehxnv.pvm.ProcessMetadata;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Helper class to get a process metadata.
 * 
 * @author Eka Lie
 */
public abstract class ProcessMetadataHelper {

    /** Pattern to match process metadata from a given string. **/
    private static final Pattern PROCESS_METADATA_PATTERN = Pattern.compile("^([a-zA-Z0-9]+)_([0-9](\\.[0-9])*)$");
    /** Default process version to be used in case metadata can't be extracted. **/
    private static final String DEFAULT_VERSION = "1.0";

    /**
     * Get process metadata from a given string.
     * @param str target string
     * @return process metadata
     */
    public static ProcessMetadata getMetadata(final String str) {
        Matcher matcher = PROCESS_METADATA_PATTERN.matcher(str);
        if (matcher.matches()) {
            return new ProcessMetadata(matcher.group(1), matcher.group(2));
        } else {
            return new ProcessMetadata(str, DEFAULT_VERSION);
        }
    }
}
