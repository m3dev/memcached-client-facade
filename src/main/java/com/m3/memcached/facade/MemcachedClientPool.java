/*
 * Copyright 2011 - 2012 M3, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.m3.memcached.facade;

import com.m3.memcached.facade.adaptor.MemcachedClientAdaptor;
import com.m3.memcached.facade.util.Assertion;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Memcached Client Pool
 */
public final class MemcachedClientPool {

    /**
     * Default implementation
     */
    private static final String DEFAULT_CLIENT_ADAPTOR_NAME = "com.m3.memcached.facade.adaptor.SpymemcachedAdaptor";

    private Configuration config;
    private MemcachedClient client;

    public MemcachedClientPool() throws Exception {
        this(null);
    }

    public MemcachedClientPool(Configuration config) throws Exception {
        if (config == null) {
            config = new Configuration();
            config.loadConfigFromProperties();
        }
        this.config = config;

        // create new client instance
        Class<? extends MemcachedClientAdaptor> adaptorClass = config.getAdaptorClass();
        if (adaptorClass == null) {
            adaptorClass = (Class<? extends MemcachedClientAdaptor>) Class.forName(DEFAULT_CLIENT_ADAPTOR_NAME);
        }
        client = new MemcachedClient(adaptorClass.newInstance());
        Assertion.notNullValue("config.getAddresses()", config.getAddresses());
        client.initialize(config.getAddresses(), config.getNamespace());
    }

    public Configuration getConfig() {
        return this.config;
    }

    public MemcachedClient getClient() {
        return this.client;
    }

    public void shutdown() {
        client.shutdown();
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            super.finalize();
        } finally {
            shutdown();
        }
    }

    /**
     * Cached client instances
     */
    @Deprecated
    private static final Map<String, MemcachedClient> DEPRECATED_POOL = new ConcurrentHashMap<String, MemcachedClient>();

    /**
     * Returns a cached {@link MemcachedClient} instance
     *
     * @param config
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @Deprecated
    public static MemcachedClient getMemcachedClient(Configuration config) throws Exception {
        if (config == null) {
            config = new Configuration();
            config.loadConfigFromProperties();
        }
        String namespace = config.getNamespace();
        if (namespace == null) {
            config.setNamespace(MemcachedClient.DEFAULT_NAMESPACE);
        }

        // cached client instance
        MemcachedClient memcached = DEPRECATED_POOL.get(namespace);
        if (memcached != null) {
            return memcached;
        }

        // create new client instance
        Class<? extends MemcachedClientAdaptor> adaptorClass = config.getAdaptorClass();
        if (adaptorClass == null) {
            adaptorClass = (Class<? extends MemcachedClientAdaptor>) Class.forName(DEFAULT_CLIENT_ADAPTOR_NAME);
        }
        memcached = new MemcachedClient(adaptorClass.newInstance());
        Assertion.notNullValue("config.getAddresses()", config.getAddresses());
        memcached.initialize(config.getAddresses(), namespace);
        DEPRECATED_POOL.put(namespace, memcached);
        return memcached;
    }

}
