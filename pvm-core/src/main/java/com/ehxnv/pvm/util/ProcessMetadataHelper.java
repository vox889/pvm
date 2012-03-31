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

import com.ehxnv.pvm.api.ProcessMetadata;

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
