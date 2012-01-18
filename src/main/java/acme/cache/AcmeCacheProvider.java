package acme.cache;

import javax.cache.Cache;
import javax.cache.CacheBuilder;
import javax.cache.CacheManager;
import javax.cache.CacheManagerFactory;
import javax.cache.OptionalFeature;
import javax.cache.Status;
import javax.cache.spi.CachingProvider;
import javax.transaction.UserTransaction;

public class AcmeCacheProvider implements CachingProvider {
    @Override
    public CacheManagerFactory getCacheManagerFactory() {
        throw new UnsupportedOperationException();
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
        public void shutdown() {
            throw new UnsupportedOperationException();
        }

        @Override
        public <T> T unwrap(Class<T> cls) {
            throw new UnsupportedOperationException();
        }
    }
}
