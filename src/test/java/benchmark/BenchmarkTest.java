package benchmark;

import com.m3.memcached.facade.Configuration;
import com.m3.memcached.facade.MemcachedClient;
import com.m3.memcached.facade.MemcachedClientFactory;
import com.m3.memcached.facade.adaptor.SpymemcachedAdaptor;
import com.m3.memcached.facade.adaptor.XmemcachedAdaptor;
import com.m3.memcached.facade.bean.SampleBean;
import com.m3.memcached.facade.bean.SampleResponse;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class BenchmarkTest {

    Logger log = LoggerFactory.getLogger(BenchmarkTest.class);

    MemcachedClient spymemcached;

    MemcachedClient xmemcached;

    @Before
    public void setupClients() throws Exception {
        Configuration spyConfig = Configuration.loadConfigFromProperties();
        spyConfig.setAdaptorClass(SpymemcachedAdaptor.class);
        spymemcached = MemcachedClientFactory.create(spyConfig);

        Configuration xConfig = Configuration.loadConfigFromProperties();
        xConfig.setAdaptorClass(XmemcachedAdaptor.class);
        xmemcached = MemcachedClientFactory.create(xConfig);
    }

    @Test
    public void setString() throws Exception {

        String key = "simply get request benchmark";
        String value = "12345678901234567890123456789012345678901234567890";

        int times = 200;

        {
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < times; i++) {
                spymemcached.set(key, 120, value);
            }
            double time = Double.valueOf(System.currentTimeMillis() - startTime) / times;
            log.info("spymemcached: " + time + " milliseconds");
        }
        {
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < times; i++) {
                xmemcached.set(key, 120, value);
            }
            double time = Double.valueOf(System.currentTimeMillis() - startTime) / times;
            log.info("xmemcached: " + time + " milliseconds");
        }

    }

    @Test
    public void getString() throws Exception {

        String key = "simply get request benchmark";
        String value = "12345678901234567890123456789012345678901234567890";
        spymemcached.set(key, 120, value);

        int times = 200;

        {
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < times; i++) {
                String result = spymemcached.get(key);
                assertThat(result, is(equalTo(value)));
            }
            double time = Double.valueOf(System.currentTimeMillis() - startTime) / times;
            log.info("spymemcached: " + time + " milliseconds");
        }
        {
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < times; i++) {
                String result = xmemcached.get(key);
                assertThat(result, is(equalTo(value)));
            }
            double time = Double.valueOf(System.currentTimeMillis() - startTime) / times;
            log.info("xmemcached: " + time + " milliseconds");
        }

    }

    @Test
    public void setSampleBean() throws Exception {

        String key = "simply get request benchmark";
        SampleBean value = new SampleBean();
        value.name = "12345678901234567890123456789012345678901234567890";

        int times = 200;

        {
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < times; i++) {
                spymemcached.set(key, 120, value);
            }
            double time = Double.valueOf(System.currentTimeMillis() - startTime) / times;
            log.info("spymemcached: " + time + " milliseconds");
        }
        {
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < times; i++) {
                xmemcached.set(key, 120, value);
            }
            double time = Double.valueOf(System.currentTimeMillis() - startTime) / times;
            log.info("xmemcached: " + time + " milliseconds");
        }

    }

    @Test
    public void getSampleBean() throws Exception {

        String key = "simply get request benchmark";
        SampleBean value = new SampleBean();
        value.name = "12345678901234567890123456789012345678901234567890";
        spymemcached.set(key, 120, value);

        int times = 200;

        {
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < times; i++) {
                SampleBean result = spymemcached.get(key);
                if (result == null) {
                    fail("No memcached servers!");
                }
                assertThat(result.name, is(equalTo(value.name)));
            }
            double time = Double.valueOf(System.currentTimeMillis() - startTime) / times;
            log.info("spymemcached: " + time + " milliseconds");
        }
        {
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < times; i++) {
                SampleBean result = xmemcached.get(key);
                assertThat(result.name, is(equalTo(value.name)));
            }
            double time = Double.valueOf(System.currentTimeMillis() - startTime) / times;
            log.info("xmemcached: " + time + " milliseconds");
        }

    }

    @Test
    public void setSampleResponse() throws Exception {

        String key = "simply get request benchmark";
        SampleResponse value = new SampleResponse();
        SampleBean sampleBean = new SampleBean();
        sampleBean.name = "12345678901234567890123456789012345678901234567890";
        value.setCode("var");
        value.setSampleBean(sampleBean);

        int times = 200;

        {
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < times; i++) {
                spymemcached.set(key, 120, value);
            }
            double time = Double.valueOf(System.currentTimeMillis() - startTime) / times;
            log.info("spymemcached: " + time + " milliseconds");
        }
        {
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < times; i++) {
                xmemcached.set(key, 120, value);
            }
            double time = Double.valueOf(System.currentTimeMillis() - startTime) / times;
            log.info("xmemcached: " + time + " milliseconds");
        }

    }

    @Test
    public void getSampleResponse() throws Exception {

        String key = "simply get request benchmark";

        SampleResponse value = new SampleResponse();
        SampleBean sampleBean = new SampleBean();
        sampleBean.name = "12345678901234567890123456789012345678901234567890";
        value.setCode("foo");
        value.setSampleBean(sampleBean);

        spymemcached.set(key, 120, value);

        int times = 200;

        {
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < times; i++) {
                SampleResponse result = spymemcached.get(key);
                if (result == null) {
                    fail("No memcached servers!");
                }
                assertThat(result.getCode(), is(equalTo(value.getCode())));
            }
            double time = Double.valueOf(System.currentTimeMillis() - startTime) / times;
            log.info("spymemcached: " + time + " milliseconds");
        }
        {
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < times; i++) {
                SampleResponse result = xmemcached.get(key);
                assertThat(result.getCode(), is(equalTo(value.getCode())));
            }
            double time = Double.valueOf(System.currentTimeMillis() - startTime) / times;
            log.info("xmemcached: " + time + " milliseconds");
        }

    }

}
