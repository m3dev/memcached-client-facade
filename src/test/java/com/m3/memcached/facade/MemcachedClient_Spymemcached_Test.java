package com.m3.memcached.facade;

import com.m3.memcached.facade.adaptor.MemcachedClientAdaptor;
import com.m3.memcached.facade.adaptor.SpymemcachedAdaptor;
import com.m3.memcached.facade.bean.SampleBean;
import com.m3.memcached.facade.impl.ClientImpl;
import org.junit.Before;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class MemcachedClient_Spymemcached_Test {

    Configuration config = null;
    List<InetSocketAddress> addresses = null;
    MemcachedClientAdaptor clientAdaptor = new SpymemcachedAdaptor();

    @Before
    public void setUp() throws Exception {
        if (config == null) {
            config = Configuration.loadConfigFromProperties();
        }
        addresses = config.getAddresses();
    }

    @Test
    public void type() throws Exception {
        assertThat(MemcachedClient.class, notNullValue());
    }

    @Test
    public void instantiation() throws Exception {
        MemcachedClient target = new MemcachedClient(clientAdaptor);
        assertThat(target, notNullValue());
    }

    @Test
    public void getClientImpl_A$() throws Exception {
        MemcachedClient target = new MemcachedClient(clientAdaptor);
        ClientImpl actual = target.getClientImpl();
        assertThat(actual, is(notNullValue()));
    }

    @Test
    public void initialize_A$InetSocketAddress() throws Exception {
        MemcachedClient target = new MemcachedClient(clientAdaptor);
        target.initialize(addresses);
    }

    @Test
    public void initialize_A$InetSocketAddress$String() throws Exception {
        MemcachedClient target = new MemcachedClient(clientAdaptor);
        String namespace = null;
        target.initialize(addresses, namespace);
    }

    @Test
    public void initialize_A$List() throws Exception {
        MemcachedClient target = new MemcachedClient(clientAdaptor);
        target.initialize(addresses);
    }

    @Test
    public void initialize_A$List$String() throws Exception {
        MemcachedClient target = new MemcachedClient(clientAdaptor);
        String namespace = null;
        target.initialize(addresses, namespace);
    }

    @Test
    public void get_A$String() throws Exception {
        MemcachedClient target = new MemcachedClient(clientAdaptor);
        target.initialize(addresses);
        String key = "not_exist";
        Object actual = target.get(key);
        assertThat(actual, is(nullValue()));
    }

    @Test
    public void set_A$String$int$Object() throws Exception {
        MemcachedClient target = new MemcachedClient(clientAdaptor);
        target.initialize(addresses);
        String key = "something";
        int secondsToExpire = 1;
        SampleBean value = new SampleBean();
        target.set(key, secondsToExpire, value);
        SampleBean actual = target.get(key);
        assertThat(actual.name, is(equalTo(value.name)));
    }

    @Test(expected = IllegalStateException.class)
    public void ensureInitialized_A$() throws Exception {
        MemcachedClient target = new MemcachedClient(clientAdaptor);
        target.ensureInitialized();
    }

    @Test
    public void setAndEnsure_A$String$int$Object() throws Exception {
        MemcachedClient target = new MemcachedClient(clientAdaptor);
        target.initialize(addresses);
        String key = "something";
        int secondsToExpire = 1;
        SampleBean value = new SampleBean();
        target.setAndEnsure(key, secondsToExpire, value);
        SampleBean actual = target.get(key);
        assertThat(actual.name, is(equalTo(value.name)));
    }

}
