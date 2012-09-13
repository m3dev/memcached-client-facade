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
    public static final String DEFAULT_PROPERTIES_FILENAME = "memcached.properties";

    /**
     * Key for client adaptor class name
     */
    public static final String KEY_CLIENT_ADAPTOR_CLASS_NAME = "clientAdaptorClassName";

    /**
     * Key for server addresses
     */
    public static final String KEY_CLIENT_SERVER_ADDRESSES = "serverAddresses";

    /**
     * Key for namespace
     */
    public static final String KEY_NAMESPACE = "namespace";


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
     * @return configuration
     * @throws IOException            when failed loading
     * @throws ClassNotFoundException when invalid class name is specified
     */
    public static Configuration loadConfigFromProperties() throws IOException, ClassNotFoundException {
        return loadConfigFromProperties(DEFAULT_PROPERTIES_FILENAME);
    }

    /**
     * Loads settings from the properties file
     *
     * @param properties properties file name
     * @return configuration
     * @throws IOException            when failed loading
     * @throws ClassNotFoundException when invalid class name is specified
     */
    public static Configuration loadConfigFromProperties(String properties) throws IOException, ClassNotFoundException {
        Configuration config = new Configuration();
        Properties props = new Properties();
        props.load(Configuration.class.getClassLoader().getResourceAsStream(properties));
        config.setAdaptorClassName(props.getProperty(KEY_CLIENT_ADAPTOR_CLASS_NAME));
        String addresses = props.getProperty(KEY_CLIENT_SERVER_ADDRESSES);
        if (addresses != null) {
            config.setAddressesAsString(addresses);
        }
        String namespace = props.getProperty(KEY_NAMESPACE);
        if (namespace != null) {
            config.setNamespace(namespace);
        }
        return config;
    }

    public Class<? extends MemcachedClientAdaptor> getAdaptorClass() {
        return adaptorClass;
    }

    public void setAdaptorClass(Class<? extends MemcachedClientAdaptor> adaptorClass) {
        this.adaptorClass = adaptorClass;
    }

    @SuppressWarnings("unchecked")
    public void setAdaptorClassName(String adaptorClassName) throws ClassNotFoundException {
        if (adaptorClassName == null) {
            throw new IllegalArgumentException("adaptorClassName should not be null.");
        }
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
            String[] splittedByColon = address.split(":");
            if (splittedByColon.length == 2) {
                this.addresses.add(new InetSocketAddress(splittedByColon[0], Integer.valueOf(splittedByColon[1])));
            } else {
                throw new IllegalArgumentException("Invalid address format (" + address + ")");
            }
        }
    }

}
