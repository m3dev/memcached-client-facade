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
import com.m3.memcached.facade.impl.ClientImpl;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

/**
 * Memcached Client Facade
 */
public class MemcachedClient {

    /**
     * Default namespace
     */
    public static final String DEFAULT_NAMESPACE = "default";

    /**
     * Client implementation instance
     */
    private ClientImpl clientImpl;

    /**
     * Returns MemcachedClient instance using the specified implementation by the client adaptor.
     *
     * @param clientAdaptor client adaptor
     * @throws Exception when failed creating an instance
     */
    MemcachedClient(MemcachedClientAdaptor clientAdaptor) throws Exception {
        Class<?> clazz = clientAdaptor.getClientImplClass();
        clientImpl = (ClientImpl) clazz.newInstance();
    }

    /**
     * Initializes memcached client with default namespace (connecting to the memcached servers and so on.)
     *
     * @param addresses server addresses
     * @throws IOException when failed initializing
     */
    public void initialize(List<InetSocketAddress> addresses) throws IOException {
        clientImpl.initialize(addresses);
    }

    /**
     * Initializes memcached client with namespace (connecting to the memcached servers and so on.)
     *
     * @param addresses server addresses
     * @param namespace key namespace
     * @throws IOException when failed initializing
     */
    public void initialize(List<InetSocketAddress> addresses, String namespace) throws IOException {
        clientImpl.initialize(addresses);
        clientImpl.setNamespace(namespace);
    }

    /**
     * Returns cached value or null
     *
     * @param key cache key
     * @param <T> value type
     * @return cached value or null
     * @throws IOException when failed accessing servers
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String key) throws IOException {
        ensureInitialized();
        return (T) clientImpl.get(key);
    }

    /**
     * Set value to memcached servers
     *
     * @param key             cache key
     * @param secondsToExpire seconds to expire
     * @param value           value
     * @param <T>             value type
     * @throws IOException when failed accessing servers or putting value
     */
    public <T> void set(String key, int secondsToExpire, T value) throws IOException {
        ensureInitialized();
        clientImpl.set(key, secondsToExpire, value);
    }

    /**
     * Set value to memcached servers and ensure completion
     *
     * @param key             cache key
     * @param secondsToExpire seconds to expire
     * @param value           value
     * @param <T>             value type
     * @throws IOException when failed accessing servers or putting value
     */
    public <T> void setAndEnsure(String key, int secondsToExpire, T value) throws IOException {
        ensureInitialized();
        clientImpl.setAndEnsure(key, secondsToExpire, value);
    }

    /**
     * Returns {@link ClientImpl} instance
     *
     * @return client implementation
     */
    public ClientImpl getClientImpl() {
        return clientImpl;
    }

    /**
     * Ensures this instance has already been initialized.
     *
     * @throws IllegalStateException when this instance hasn't been intialized yet
     */
    void ensureInitialized() throws IllegalStateException {
        if (clientImpl == null || !clientImpl.isInitialized()) {
            throw new IllegalStateException("Not yet initialized.");
        }
    }

}
