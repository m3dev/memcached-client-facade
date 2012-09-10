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

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Memcached Client Configuration
 */
public class Configuration {

    /**
     * Default properties file name
     */
    public static final String DEFAULT_PROPERTIES_FILENAME = "memcached-client-facade.properties";

    /**
     * Key for client adaptor class name
     */
    public static final String KEY_CLIENT_ADAPTOR_CLASS_NAME = "client.adaptorClassName";

    /**
     * Key for server addresses
     */
    public static final String KEY_CLIENT_SERVER_ADDRESSES = "server.addresses";

    /**
     * Client adaptor class
     */
    private Class<? extends MemcachedClientAdaptor> adaptorClass;

    /**
     * Memcached server addresses
     */
    private List<InetSocketAddress> addresses;

    /**
     * Namespace (= prefix for all the memcached keys)
     */
    private String namespace = MemcachedClient.DEFAULT_NAMESPACE;

    /**
     * Loads settings from default properties file
     *
     * @throws IOException            when failed loading
     * @throws ClassNotFoundException when invalid class name is specified
     */
    public void loadConfigFromProperties() throws IOException, ClassNotFoundException {
        loadConfigFromProperties(DEFAULT_PROPERTIES_FILENAME);
    }

    /**
     * Loads settings from the properties file
     *
     * @param properties properties file name
     * @throws IOException            when failed loading
     * @throws ClassNotFoundException when invalid class name is specified
     */
    public void loadConfigFromProperties(String properties) throws IOException, ClassNotFoundException {
        Properties props = new Properties();
        props.load(this.getClass().getClassLoader().getResourceAsStream(properties));
        setAdaptorClassName(props.getProperty(KEY_CLIENT_ADAPTOR_CLASS_NAME));
        String addresses = props.getProperty(KEY_CLIENT_SERVER_ADDRESSES);
        if (addresses != null) {
            setAddressesAsString(addresses);
        }
    }

    public Class<? extends MemcachedClientAdaptor> getAdaptorClass() {
        return adaptorClass;
    }

    public void setAdaptorClass(Class<? extends MemcachedClientAdaptor> adaptorClass) {
        this.adaptorClass = adaptorClass;
    }

    @SuppressWarnings("unchecked")
    public void setAdaptorClassName(String adaptorClassName) throws ClassNotFoundException {
        this.adaptorClass = (Class<? extends MemcachedClientAdaptor>) Class.forName(adaptorClassName);
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public List<InetSocketAddress> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<InetSocketAddress> addresses) {
        this.addresses = addresses;
    }

    public void setAddressesAsString(String addresses) {
        setAddressesAsStringArray((addresses != null) ? addresses.split(",") : new String[]{addresses});
    }

    public void setAddressesAsStringArray(String[] addresses) {
        this.addresses = new ArrayList<InetSocketAddress>();
        for (String address : addresses) {
            String[] splitted = address.split(":");
            if (splitted.length == 2) {
                this.addresses.add(new InetSocketAddress(splitted[0], Integer.valueOf(splitted[1])));
            } else {
                throw new IllegalArgumentException("Invalid address format (" + address + ")");
            }
        }
    }

}
