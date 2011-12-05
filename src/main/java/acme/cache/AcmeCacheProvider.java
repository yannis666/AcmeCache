package acme.cache;

import javax.cache.Cache;
import javax.cache.CacheBuilder;
import javax.cache.CacheConfiguration;
import javax.cache.CacheManager;
import javax.cache.OptionalFeature;
import javax.cache.Status;
import javax.cache.experimental.ConfigurationBuilder;
import javax.cache.spi.CachingProvider;
import javax.transaction.UserTransaction;
import java.util.Set;

public class AcmeCacheProvider implements CachingProvider {
    @Override
    public CacheManager createCacheManager(ClassLoader classLoader, String name) {
        return new CacheManagerImpl(name);
    }

    @Override
    public ClassLoader getDefaultClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    @Override
    public boolean isSupported(OptionalFeature optionalFeature) {
        return false;
    }

    private static class CacheManagerImpl implements CacheManager {
        private String name;

        public CacheManagerImpl(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public Status getStatus() {
            throw new UnsupportedOperationException();
        }

        @Override
        public <K, V> CacheBuilder<K, V> createCacheBuilder(String cacheName) {
            throw new UnsupportedOperationException();
        }

        @Override
        public <K, V> Cache<K, V> getCache(String cacheName) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Iterable<Cache<?, ?>> getCaches() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeCache(String cacheName) throws IllegalStateException {
            throw new UnsupportedOperationException();
        }

        @Override
        public UserTransaction getUserTransaction() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isSupported(OptionalFeature optionalFeature) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void registerImmutableClass(Class immutableClass) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void shutdown() {
            throw new UnsupportedOperationException();
        }

        @Override
        public <T> T unwrap(Class<T> cls) {
            throw new UnsupportedOperationException();
        }

        @Override
        public <K, V> ConfigurationBuilder<K, V> createConfigurationBuilderEXPERIMENTAL() {
            throw new UnsupportedOperationException();
        }

        @Override
        public <K, V> javax.cache.experimental.CacheBuilder<K, V> getCacheBuilderEXPERIMENTAL() {
            throw new UnsupportedOperationException();
        }
    }
}
