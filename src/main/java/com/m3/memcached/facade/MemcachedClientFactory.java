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

/**
 * {@link MemcachedClient} Factory
 */
@Deprecated
public class MemcachedClientFactory {

    private MemcachedClientFactory() {
    }

    /**
     * Returns a new {@link MemcachedClient} instance
     *
     * @param config configuration
     * @return a new instance
     * @throws Exception something wrong
     */
    public static MemcachedClient create(Configuration config) throws Exception {
        return MemcachedClientPool.getMemcachedClient(config);
    }

}
