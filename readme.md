# Memcached Client Facade

## A simple wrapper API for memcached clients

'memacached-client-facade' provides a pluggable wrapper API for memcached clients and developers can access memcached servers transparently.

Currently supports following 2 libraries, and it's also possible to add new adaptors.

## Getting Started

### Maven

```xml
<dependencies>
  <dependency>
    <groupId>com.m3</groupId>
    <artifactId>memcached-client-facade</artifactId>
    <version>1.0.1</version>
  </dependency>
</dependencies>
```

## Usage

```java
import com.m3.memcached.facade.*;

Configuration config = new Configuration();
config.setAdaptorClassName("com.m3.memcached.facade.adaptor.SpymemcachedAdaptor");
config.setAddressesAsString("server1:11211,server2:11211"); // csv format
config.setNamespace("com.m3.memcached.example");

MemcachedClient memcached = MemcachedClientFactory.create(config);

Thread.sleep(500L);
String toBeCached = new java.util.Date().toString();
memcached.set("stopped time", 1, toBeCached); // whitespace in cache key will be replaced to underscore
Thread.sleep(500L);
assertThat(memcached.get("stopped time"), is(equalTo(toBeCached))); // "Wed Oct 12 00:01:54 JST 2011"
Thread.sleep(1000L);
assertThat(memcached.get("stopped time"), is(nullValue())); // null
```

or configure with `memcached.properties` as follows:

```
clientAdaptorClassName=com.m3.memcached.facade.adaptor.SpymemcachedAdaptor
serverAddresses=server1:11211,server2:11211
namespace=com.m3.memcached.example
```

```java
import com.m3.memcached.facade.*;

Configuration config = Configuration.loadConfigFromProperties();
MemcachedClient memcached = MemcachedClientFactory.create(config);
```

### Adaptors for concrete implementation

- com.m3.memcached.facade.adaptor.SpymemcachedAdaptor

  for http://code.google.com/p/spymemcached/

- com.m3.memcached.facade.adaptor.XmemcachedAdaptor

  for http://code.google.com/p/xmemcached/


## License

```
 Copyright 2011 - 2012 M3, Inc.

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 either express or implied. See the License for the specific language
 governing permissions and limitations under the License.
```

