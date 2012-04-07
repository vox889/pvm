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

import com.ehxnv.pvm.api.Process;
import com.ehxnv.pvm.api.ProcessMetadata;
import com.ehxnv.pvm.api.repository.ProcessAlreadyExistException;
import com.ehxnv.pvm.api.repository.ProcessNotExistException;
import com.ehxnv.pvm.api.repository.ProcessRepository;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;

/**
 * Database based process repository.
 * @author Eka Lie
 */
public abstract class DatabaseBasedProcessRepository implements ProcessRepository {

    /** Entity manager factory instance. **/
    private EntityManagerFactory entityManagerFactory;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize() {
        entityManagerFactory = buildEntityManagerFactory();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void shutdown() {
        entityManagerFactory.close();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addProcess(final Process process) throws ProcessAlreadyExistException {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(new DbProcess(process));
            tx.commit();
        } catch (PersistenceException ex) {
            tx.rollback();
            throw new ProcessAlreadyExistException(process.getMetadata());
        } finally {
            em.close();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateProcess(final ProcessMetadata processMetadata, final Process process) throws ProcessNotExistException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            DbProcess dbProcess = (DbProcess) createFindProcessByMetadataQuery(entityManager, processMetadata).getSingleResult();
            dbProcess.updateFrom(process);
        } catch (NoResultException ex) {
            throw new ProcessNotExistException(processMetadata, ex);
        } finally {
            entityManager.close();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Process findProcess(final ProcessMetadata processMetadata) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            DbProcess dbProcess = (DbProcess) createFindProcessByMetadataQuery(entityManager, processMetadata).getSingleResult();
            return dbProcess.getProcess();
        } catch (NoResultException ex) {
            return null;
        } finally {
            entityManager.close();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteProcess(final ProcessMetadata processMetadata) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        try {
            DbProcess dbProcess = (DbProcess) createFindProcessByMetadataQuery(entityManager, processMetadata).getSingleResult();
            tx.begin();
            entityManager.remove(dbProcess);
            tx.commit();
            return true;
        } catch (NoResultException ex) {
            return false;
        } finally {
            entityManager.close();
        }
    }

    /**
     * Create a query which can be used to get a process based on it's metadata.
     * @param entityManager loaned entity manager
     * @param processMetadata target process metadata
     * @return query
     */
    private static final Query createFindProcessByMetadataQuery(final EntityManager entityManager, final ProcessMetadata processMetadata) {
        Query query = entityManager.createQuery("FROM DbProcess dbp WHERE dbp.processName = :procName AND dbp.processVersion = :procVer");
        query.setParameter("procName", processMetadata.getName());
        query.setParameter("procVer", processMetadata.getVersion());
        return query;
    }

    /**
     * How should subclass build the entity manager factory?
     * @return entity manager factory built by subclass
     */
    protected abstract EntityManagerFactory buildEntityManagerFactory();
}
