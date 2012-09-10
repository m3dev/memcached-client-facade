package com.m3.memcached.facade;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class MemcachedClientPoolTest {

    Configuration config = null;

    @Before
    public void setUp() throws Exception {
        if (config == null) {
            config = new Configuration();
            config.loadConfigFromProperties();
        }
    }

    @Test
    public void type() throws Exception {
        assertThat(MemcachedClientPool.class, notNullValue());
    }

    @Test
    public void getMemcachedClient_A$Configuration() throws Exception {
        MemcachedClient actual = MemcachedClientPool.getMemcachedClient(config);
        assertThat(actual, is(notNullValue()));
    }

}
