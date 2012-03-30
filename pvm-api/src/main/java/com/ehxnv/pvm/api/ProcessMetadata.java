/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehxnv.pvm.api;
import static com.ehxnv.pvm.api.util.ValidationUtil.*;

/**
 * Represents a process metadata, which at the moment contains:
 * <ul>
 *   <li>process name</li>
 *   <li>process version</li>
 * </ul>
 * 
 * @author Eka Lie
 */
public class ProcessMetadata {
    
    /** Process name. **/
    private String name;
    /** Process version. **/
    private String version;

    /**
     * Constructor.
     * @param name process name
     * @param version process version
     */
    public ProcessMetadata(final String name, final String version) {
        checkForNull("Process name", name);
        checkForNull("Process version", version);
        this.name = name;
        this.version = version;
    }

    /**
     * Get process name.
     * @return process name
     */
    public String getName() {
        return name;
    }

    /**
     * Get process version.
     * @return process version
     */
    public String getVersion() {
        return version;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return name + "[" + version + "]";
    }    
}
