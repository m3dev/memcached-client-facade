package com.m3.memcached.facade.adaptor;

import com.m3.memcached.facade.impl.SpymemcachedClientImpl;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class SpymemcachedAdaptorTest {

    @Test
    public void type() throws Exception {
        assertThat(SpymemcachedAdaptor.class, notNullValue());
    }

    @Test
    public void instantiation() throws Exception {
        SpymemcachedAdaptor target = new SpymemcachedAdaptor();
        assertThat(target, notNullValue());
    }

    @Test
    public void getClientImplClass_A$() throws Exception {
        SpymemcachedAdaptor target = new SpymemcachedAdaptor();
        Object actual = target.getClientImplClass();
        Object expected = SpymemcachedClientImpl.class;
        assertThat(actual, is(equalTo(expected)));
    }

}
