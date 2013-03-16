package com.m3.memcached.facade.impl;

import com.m3.memcached.facade.Configuration;
import com.m3.memcached.facade.bean.SampleBean;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class SpymemcachedClientImplTest {

    SpymemcachedClientImpl memcached = new SpymemcachedClientImpl();
    Configuration config = null;

    String namespace = "com.m3.memcached.facade.testing";

    List<InetSocketAddress> addresses = null;

    @Before
    public void setUp() throws Exception {
        if (config == null) {
            config = Configuration.loadConfigFromProperties();
        }
        memcached.initialize(config.getAddresses());
        addresses = config.getAddresses();
    }

    @After
    public void tearDown() throws Exception {
        memcached.shutdown();
    }

    @Test
    public void type() throws Exception {
        assertThat(SpymemcachedClientImpl.class, not(nullValue()));
    }

    @Test
    public void instantiation() throws Exception {
        SpymemcachedClientImpl target = new SpymemcachedClientImpl();
        assertThat(target, not(nullValue()));
    }

    @Test
    public void waitForConnectionReady_A$() throws Exception {
        memcached.waitForConnectionReady();
    }

    @Test
    public void initialize_A$List() throws Exception {
        memcached.initialize(addresses);
    }

    @Test
    public void initialize_A$List$String() throws Exception {
        memcached.initialize(addresses, namespace);
    }

    @Test
    public void getNamespace_A$() throws Exception {
        String actual = memcached.getNamespace();
        String expected = "default";
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void setNamespace_A$String() throws Exception {
        memcached.setNamespace(namespace);
    }

    @Test
    public void set_A$String$int$Object() throws Exception {
        String key = "something";
        int secondsToExpire = 0;
        Object value = new SampleBean();
        memcached.set(key, secondsToExpire, value);
    }

    @Test
    public void get_A$String() throws Exception {
        String key = "something";
        int secondsToExpire = 1;
        SampleBean value = new SampleBean();
        value.name = "foo";
        memcached.set(key, secondsToExpire, value);
        SampleBean actual = memcached.get(key);
        if (actual == null) {
            fail("No memcached servers!");
        }
        assertThat(actual.name, is(equalTo(value.name)));
    }

    @Test
    public void setAndEnsure_A$String$int$Object() throws Exception {
        String key = "setAndEnsure_A$String$int$Object";
        int secondsToExpire = 3;
        SampleBean value = new SampleBean();
        memcached.setAndEnsure(key, secondsToExpire, value);
    }

    @Test
    public void isInitialized_A$() throws Exception {
        boolean actual = memcached.isInitialized();
        boolean expected = true;
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void isInitialized_A$_NotYet() throws Exception {
        boolean actual = new SpymemcachedClientImpl().isInitialized();
        boolean expected = false;
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void initialize_A$List$String$long() throws Exception {
        List<InetSocketAddress> addresses = Arrays.asList(new InetSocketAddress("127.0.0.1", 11211));
        String namespace = null;
        long maxWaitMillis = 10L;
        memcached.initialize(addresses, namespace, maxWaitMillis);
    }

    @Test
    public void delete_A$String() throws Exception {
        String key = "SpymemcachedClientImpl_delete_A$String";
        memcached.set(key, 10, "foo");
        assertThat(memcached.get(key).toString(), is(equalTo("foo")));
        memcached.delete(key);
        Thread.sleep(1000L);
        assertThat(memcached.get(key), is(nullValue()));
    }

    @Test(expected = IllegalStateException.class)
    public void shutdown_A$_NotInitialized() throws Exception {
        SpymemcachedClientImpl impl = new SpymemcachedClientImpl();
        impl.shutdown();
    }

    @Test
    public void shutdown_A$() throws Exception {
        memcached.shutdown();
    }

}
