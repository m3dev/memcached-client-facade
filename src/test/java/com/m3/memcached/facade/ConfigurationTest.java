package com.m3.memcached.facade;

import com.m3.memcached.facade.adaptor.MemcachedClientAdaptor;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import com.m3.memcached.facade.Configuration.*;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class ConfigurationTest {

    @Test
    public void type() throws Exception {
        assertThat(Configuration.class, notNullValue());
    }

    @Test
    public void instantiation() throws Exception {
        Configuration target = new Configuration();
        assertThat(target, notNullValue());
    }

    @Test
    public void loadConfigFromProperties_A$() throws Exception {
        Configuration config = Configuration.loadConfigFromProperties();
        assertThat(config.getAdaptorClass().toString(), is(equalTo("class com.m3.memcached.facade.adaptor.SpymemcachedAdaptor")));
        assertThat(config.getAddresses().get(0).toString(), is(equalTo("/127.0.0.1:11211")));
        assertThat(config.getNamespace().toString(), is(equalTo("myapp")));
    }

    @Test
    public void getAdaptorClass_A$() throws Exception {
        Configuration target = new Configuration();
        Object actual = target.getAdaptorClass();
        Object expected = null;
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void setAdaptorClassName_A$String() throws Exception {
        Configuration target = new Configuration();
        String adaptorClassName = "com.m3.memcached.facade.adaptor.SpymemcachedAdaptor";
        target.setAdaptorClassName(adaptorClassName);
    }

    @Test
    public void setAddressesAsString_A$String() throws Exception {
        Configuration target = new Configuration();
        String addresses = "localhost:11211,localhost:11212";
        target.setAddressesAsString(addresses);
    }

    @Test
    public void setAddressesAsStringArray_A$StringArray() throws Exception {
        Configuration target = new Configuration();
        String[] addresses = new String[]{};
        target.setAddressesAsStringArray(addresses);
    }

	@Test
	public void loadConfigFromProperties_A$String() throws Exception {
		String properties = "memcached2.properties";
		Configuration config = Configuration.loadConfigFromProperties(properties);
        assertThat(config.getAdaptorClass().toString(), is(equalTo("class com.m3.memcached.facade.adaptor.SpymemcachedAdaptor")));
        assertThat(config.getAddresses().get(0).toString(), is(equalTo("/127.0.0.1:11111")));
        assertThat(config.getNamespace().toString(), is(equalTo("xxx")));
    }

}
