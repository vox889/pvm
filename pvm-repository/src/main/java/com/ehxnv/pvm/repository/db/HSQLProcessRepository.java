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

package com.ehxnv.pvm.repository.db;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.eclipse.persistence.jpa.PersistenceProvider;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of DatabaseBasedProcessRepository which uses HSQL as the underlying database.
 * @author Eka Lie
 */
public class HSQLProcessRepository extends DatabaseBasedProcessRepository {

    /** Database name to be used. **/
    private String dbName;

    /**
     * Constructor.
     * @param dbName database name
     */
    public HSQLProcessRepository(final String dbName) {
        this.dbName = dbName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected EntityManagerFactory buildEntityManagerFactory() {
        Map<String, Object> connectionProperties = new HashMap<String, Object>();
        connectionProperties.put(PersistenceUnitProperties.CLASSLOADER, this.getClass().getClassLoader());
        connectionProperties.put("javax.persistence.jdbc.url", "jdbc:hsqldb:file:" + dbName + ";shutdown=true");

        PersistenceProvider pp = new PersistenceProvider();
        return pp.createEntityManagerFactory("repository-hsql", connectionProperties);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getRepositoryName() {
        return "HSQL - " + dbName;
    }
}
