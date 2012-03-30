package com.ehxnv.pvm.io;

import com.ehxnv.pvm.api.io.ProcessReader;
import com.ehxnv.pvm.io.json.JSONProcessReader;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for {@link ProcessReaderFactory}.
 *
 * @author Eka Lie
 */
public class ProcessReaderFactoryTest {
    
    /**
     * Test of createJSONProcessReader method, of class ProcessReaderFactory.
     */
    @Test
    public void testCreateJSONProcessReader() throws Exception {
        ProcessReader processReader = ProcessReaderFactory.createJSONProcessReader();
        assertTrue(processReader instanceof JSONProcessReader);
    }
}
