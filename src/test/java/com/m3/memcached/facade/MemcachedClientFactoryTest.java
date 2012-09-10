package com.m3.memcached.facade;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class MemcachedClientFactoryTest {

    @Test
    public void type() throws Exception {
        assertThat(MemcachedClientFactory.class, notNullValue());
    }

    @Test
    public void create_A$Configuration_XmemcachedAdaptor() throws Exception {
        Configuration config = new Configuration();
        config.loadConfigFromProperties();
        config.setAdaptorClassName("com.m3.memcached.facade.adaptor.XmemcachedAdaptor");
        MemcachedClient memcached = MemcachedClientFactory.create(config);
        Thread.sleep(500L);
        memcached.set("time", 2, new java.util.Date().toString());
        Thread.sleep(1000L);
        assertThat(memcached.get("time"), is(notNullValue()));
        Thread.sleep(2000L);
        assertThat(memcached.get("time"), is(nullValue()));
    }

}
