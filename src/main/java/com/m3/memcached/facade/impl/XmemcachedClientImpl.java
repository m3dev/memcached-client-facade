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
package com.m3.memcached.facade.impl;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

import static com.m3.memcached.facade.util.Assertion.*;

/**
 * Concrete client implementation with Xmemcached
 */
public class XmemcachedClientImpl extends ClientImplBase {

    private static final Logger log = LoggerFactory.getLogger(SpymemcachedClientImpl.class);

    private MemcachedClient memcached;

    @Override
    public boolean isInitialized() {
        return memcached != null;
    }

    @Override
    public void initialize(List<InetSocketAddress> addresses) throws IOException {
        notNullValue("addresses", addresses);
        memcached = new XMemcachedClient(addresses);
    }

    @Override
    public void initialize(List<InetSocketAddress> addresses, String namespace) throws IOException {
        notNullValue("addresses", addresses);
        memcached = new XMemcachedClient(addresses);
        setNamespace(namespace);
    }

    @Override
    public void initialize(List<InetSocketAddress> addresses, String namespace, long maxWaitMillis) throws IOException {
        notNullValue("addresses", addresses);
        memcached = new XMemcachedClient(addresses);
        setNamespace(namespace);
        setMaxWaitMillis(maxWaitMillis);
    }

    @Override
    public <T> void set(String key, int secondsToExpire, T value) throws IOException {
        notNullValue("key", key);
        try {
            if (hasNoAvailableServer()) {
                return;
            }
            memcached.set(getKeyWithNamespace(key), secondsToExpire, value);
        } catch (Exception e) {
            String failedMessage = "Failed to set value on memcached! " +
                    "(key:" + key + ",secondsToExpire:" + secondsToExpire + ",value:" + value + ")";
            throw new IOException(failedMessage, e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> void setAndEnsure(String key, int secondsToExpire, T value) throws IOException {
        notNullValue("key", key);
        try {
            if (hasNoAvailableServer()) {
                return;
            }
            set(key, secondsToExpire, value);
            T cached = (T) memcached.get(getKeyWithNamespace(key));
            if (cached == null) {
                String failedMessage = "Failed to set value on memcached! " +
                        "(key:" + key + ",secondsToExpire:" + secondsToExpire + ",value:" + value + ")";
                throw new IOException(failedMessage);
            }
        } catch (Exception e) {
            String failedMessage = "Failed to set value on memcached! " +
                    "(key:" + key + ",secondsToExpire:" + secondsToExpire + ",value:" + value + ")";
            throw new IOException(failedMessage, e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(String key) throws IOException {
        notNullValue("key", key);
        try {
            if (hasNoAvailableServer()) {
                return null;
            }
            return (T) memcached.get(getKeyWithNamespace(key));
        } catch (Exception e) {
            String failedMessage = "Failed to get value on memcached! (key:" + key + ")";
            throw new IOException(failedMessage, e);
        }
    }

    @Override
    public void delete(String key) throws IOException {
        notNullValue("key", key);
        try {
            if (hasNoAvailableServer()) {
                return;
            }
            memcached.delete(getKeyWithNamespace(key));
        } catch (Throwable t) {
            String failedMessage = "Failed to delete key on memcached! (key:" + key + ")";
            throw new IOException(failedMessage, t);
        }
    }

    @Override
    public void shutdown() {
        if (memcached == null) {
            throw new IllegalStateException("Memcached client instance has not been initialized yet.");
        }
        try {
            if (!memcached.isShutdown()) {
                memcached.shutdown();
            }
        } catch (IOException e) {
            String message = "Failed to shutdown memcached client because " + e.getMessage();
            log.info(message);
            if (log.isDebugEnabled()) {
                log.debug(message, e);
            }
        }
    }

    private boolean hasNoAvailableServer() {
        boolean unavailable = memcached.getAvailableServers().isEmpty();
        if (unavailable && log.isDebugEnabled()) {
            log.debug("No available memcached servers now.");
        }
        return unavailable;
    }

}
