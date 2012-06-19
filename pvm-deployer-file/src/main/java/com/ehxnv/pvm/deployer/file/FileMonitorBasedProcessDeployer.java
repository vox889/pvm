package com.ehxnv.pvm.deployer.file;

import com.ehxnv.pvm.api.Process;
import com.ehxnv.pvm.api.io.ProcessDataModelException;
import com.ehxnv.pvm.api.io.ProcessIOException;
import com.ehxnv.pvm.api.io.ProcessReader;
import com.ehxnv.pvm.api.io.ProcessStructureException;
import com.ehxnv.pvm.api.repository.ProcessAlreadyExistException;
import com.ehxnv.pvm.api.repository.ProcessRepository;
import com.ehxnv.pvm.deployer.api.ProcessDeployer;
import com.ehxnv.pvm.deployer.api.ProcessDeployerException;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Implementation of {@link ProcessDeployer} which will monitor a directory at a specific interval for ".zip" process files.
 * It will monitor the directory for the following:
 * <ul>
 *     <li>New files in the directory. Each of this file will be read using given {@link ProcessReader} and will be deployed into {@link ProcessRepository}</li>
 *     <li>Removal of files in the directory. Removal of each file will resulted in undeployment of {@link Process} associated with the file from {@link ProcessRepository}</li>
 * </ul>
 * @author Eka Lie
 */
public class FileMonitorBasedProcessDeployer implements ProcessDeployer {

    /** Logger. **/
    private static final Logger LOGGER = LoggerFactory.getLogger(FileMonitorBasedProcessDeployer.class);
    /** Process file extension to be monitored. **/
    private static final String PROCESS_EXT = ".zip";

    /** Current state of this deployer. **/
    private State state;
    /** File monitor instance. **/
    private FileAlterationMonitor fileMonitor;
    /** Directory which this deployer will monitor. **/
    private File rootDirectory;
    /** Monitoring interval (milliseconds). **/
    private long monitorInterval;
    /** Process repository to deploy process into. **/
    private ProcessRepository processRepository;
    /** Process reader to read processes from file. **/
    private ProcessReader processReader;

    /**
     * Constructor.
     * @param rootDirectory directory which this deployer will monitor
     * @param monitorInterval monitoring interval (milliseconds)
     * @param processRepository process repository to deploy process into
     * @param processReader process reader to read processes from file
     */
    public FileMonitorBasedProcessDeployer(final File rootDirectory,
                                           final long monitorInterval,
                                           final ProcessRepository processRepository,
                                           final ProcessReader processReader) {
        if (!rootDirectory.isDirectory()) {
            throw new IllegalArgumentException("Monitored path must be a directory!");
        }

        Validate.notNull(processRepository);
        Validate.notNull(processReader);

        this.rootDirectory = rootDirectory;
        this.monitorInterval = monitorInterval;
        this.processRepository = processRepository;
        this.processReader = processReader;
        this.state = State.STOPPED;

    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "file-monitor-process-deployer";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return "monitor directory for process files which will be deployed into process repository";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        if (state != State.STOPPED) {
            throw new IllegalStateException("Deployer already started!");
        }

        fileMonitor = new FileAlterationMonitor(monitorInterval);

        IOFileFilter zipFileFilter = FileFilterUtils.and(FileFilterUtils.fileFileFilter(),
                                                         FileFilterUtils.suffixFileFilter(PROCESS_EXT));
        FileAlterationObserver fileObserver = new FileAlterationObserver(rootDirectory, zipFileFilter);
        fileObserver.addListener(new ProcessDeployerListener());

        fileMonitor.addObserver(fileObserver);

        try {
            fileMonitor.start();
            state = State.STARTED;
        } catch (Exception ex) {
            throw new ProcessDeployerException("Failed to start file monitoring", ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        if (state != State.STARTED) {
            throw new IllegalStateException("Deployer already stopped!");
        }

        try {
            fileMonitor.stop();
            state = State.STOPPED;
        } catch (Exception ex) {
            throw new ProcessDeployerException("Failed to stop file monitoring", ex);
        }
    }

    /**
     * Represents the state of this file monitor deployer.
     */
    private static enum State {
        /** File monitor is started. **/
        STARTED,
        /** File monitor is stopeed. **/
        STOPPED
    }

    private class ProcessDeployerListener extends FileAlterationListenerAdaptor {

        /**
         * {@inheritDoc}
         */
        @Override
        public void onFileCreate(final File file) {
            LOGGER.debug("Found {} in {}", file.getName(), rootDirectory.getAbsolutePath());
            try {
                Process process = processReader.readProcess(file);
                LOGGER.info("Deploying {} into process repository", process.getMetadata().toString());
                processRepository.addProcess(process);
                LOGGER.info("{} successfully deployed into process repository", process.getMetadata().toString());
            } catch (ProcessAlreadyExistException ex) {
                throw new ProcessDeployerException("Failed to deploy process to repository", ex);
            } catch (ProcessIOException ex) {
                throw new ProcessDeployerException("Failed to deploy process to repository", ex);
            } catch (ProcessStructureException ex) {
                throw new ProcessDeployerException("Failed to deploy process to repository", ex);
            } catch (ProcessDataModelException ex) {
                throw new ProcessDeployerException("Failed to deploy process to repository", ex);
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void onFileDelete(final File file) {
            LOGGER.debug("Found removal of {} in {}", file.getName(), rootDirectory.getAbsolutePath());
            try {
                Process process = processReader.readProcess(file);
                LOGGER.info("Undeploying {} from process repository", process.getMetadata().toString());
                processRepository.deleteProcess(process.getMetadata());
                LOGGER.info("{} successfully undeployed from process repository", process.getMetadata().toString());
            } catch (ProcessIOException ex) {
                throw new ProcessDeployerException("Failed to undeploy process to repository", ex);
            } catch (ProcessStructureException ex) {
                throw new ProcessDeployerException("Failed to undeploy process to repository", ex);
            } catch (ProcessDataModelException ex) {
                throw new ProcessDeployerException("Failed to undeploy process to repository", ex);
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void onStart(final FileAlterationObserver observer) {
            LOGGER.info("Monitoring {} for {} started", observer.getDirectory().getAbsolutePath(), PROCESS_EXT);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void onStop(final FileAlterationObserver observer) {
            LOGGER.info("Monitoring {} for {} stopped", observer.getDirectory().getAbsolutePath(), PROCESS_EXT);
        }
    }
}
