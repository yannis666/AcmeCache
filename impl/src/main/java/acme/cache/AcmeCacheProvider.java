package acme.cache;

import javax.cache.Cache;
import javax.cache.CacheConfiguration;
import javax.cache.CacheManager;
import javax.cache.spi.CacheManagerFactoryProvider;

public class AcmeCacheProvider implements CacheManagerFactoryProvider {
    @Override
    public CacheManager createCacheManager(String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <K, V> Cache<K, V> createCache(String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public CacheConfiguration createCacheConfiguration() {
        throw new UnsupportedOperationException();
    }
}
