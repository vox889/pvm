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

package com.ehxnv.pvm.server;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;

/**
 * Basic PVM server.
 */
public class Server {

    private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);

    public void start() {
        ServiceLoader<FrameworkFactory> serviceLoader = ServiceLoader.load(FrameworkFactory.class);
        FrameworkFactory frameworkFactory = serviceLoader.iterator().next();

        final Framework framework = frameworkFactory.newFramework(Collections.emptyMap());

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    framework.stop();
                } catch (BundleException ex) {
                    LOGGER.error("Failed to stop PVM server", ex);
                }
            }
        }));

        LOGGER.info("Starting PVM server");
        try {
            framework.start();

            BundleContext bundleContext = framework.getBundleContext();
            List<Bundle> installedBundles = new LinkedList<Bundle>();

            // TODO: get all bundles from "lib" and install them
            File libDir = new File("../lib");
            for (File file : libDir.listFiles()) {
                installedBundles.add(bundleContext.installBundle("file://" + file.getAbsolutePath()));
            }

            for (Bundle bundle : installedBundles) {
                bundle.start();
            }

            LOGGER.info("PVM Server started on {}", new Date());
            framework.waitForStop(0);
            LOGGER.info("PVM Server stopped at {}", new Date());
        } catch (BundleException ex) {
            LOGGER.error("Failed to start PVM server", ex);
        } catch (InterruptedException ex) {
            LOGGER.error("Failed to stop PVM server", ex);
        } finally {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}
