package com.m3.memcached.facade;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class MemcachedClientPoolTest {

    Configuration config = null;
    MemcachedClientPool pool = null;

    @Before
    public void setUp() throws Exception {
        if (config == null) {
            config = Configuration.loadConfigFromProperties();
        }
        pool = new MemcachedClientPool(config);
    }

    @After
    public void tearDown() throws Exception {
        pool.shutdown();
    }

    @Test
    public void type() throws Exception {
        assertThat(MemcachedClientPool.class, notNullValue());
    }

    @Test
    public void instantiation() throws Exception {
        assertThat(pool, notNullValue());
    }

    @Test
    public void getMemcachedClient_A$Configuration() throws Exception {
        MemcachedClient actual = MemcachedClientPool.getMemcachedClient(config);
        assertThat(actual, is(notNullValue()));
    }

    @Test
    public void shutdown_A$() throws Exception {
        pool.shutdown();
    }

    @Test
    public void finalize_A$() throws Throwable {
        pool.finalize();
    }

}
