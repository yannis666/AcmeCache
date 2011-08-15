package acme.cache;

import javax.cache.Cache;
import javax.cache.CacheBuilder;
import javax.cache.CacheConfiguration;
import javax.cache.CacheException;
import javax.cache.CacheLoader;
import javax.cache.CacheManager;
import javax.cache.CacheStatistics;
import javax.cache.CacheStatus;
import javax.cache.OptionalFeature;
import javax.cache.event.CacheEntryListener;
import javax.cache.event.NotificationScope;
import javax.cache.spi.CacheManagerFactoryProvider;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Future;

public class AcmeCacheProvider implements CacheManagerFactoryProvider {
    @Override
    public CacheManager createCacheManager(String name) {
        return new CacheManagerImpl(name);
    }

    @Override
    public boolean isSupported(OptionalFeature optionalFeature) {
        return false;
    }


    private static class CacheConfigurationImpl implements CacheConfiguration {
        @Override
        public boolean isReadThrough() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setReadThrough(boolean readThrough) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isWriteThrough() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setWriteThrough(boolean writeThrough) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isStoreByValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setStoreByValue(boolean storeByValue) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isStatisticsEnabled() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setStatisticsEnabled(boolean enableStatistics) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isTransactionEnabled() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setTransactionEnabled(boolean enableTransactions) {
            throw new UnsupportedOperationException();
        }
    }

    private static class CacheImpl<K, V> implements Cache<K, V> {
        private final String name;

        public CacheImpl(String name) {
            this.name = name;
        }

        @Override
        public V get(Object key) throws CacheException {
            throw new UnsupportedOperationException();
        }

        @Override
        public Map<K, V> getAll(Collection<? extends K> keys) throws CacheException {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean containsKey(Object key) throws CacheException {
            throw new UnsupportedOperationException();
        }

        @Override
        public Future<V> load(K key, CacheLoader<K, V> specificLoader, Object loaderArgument) throws CacheException {
            throw new UnsupportedOperationException();
        }

        @Override
        public Future<Map<K, V>> loadAll(Collection<? extends K> keys, CacheLoader<K, V> specificLoader, Object loaderArgument) throws CacheException {
            throw new UnsupportedOperationException();
        }

        @Override
        public CacheStatistics getStatistics() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void put(K key, V value) throws CacheException {
            throw new UnsupportedOperationException();
        }

        @Override
        public V getAndPut(K key, V value) throws CacheException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void putAll(Map<? extends K, ? extends V> map) throws CacheException {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean putIfAbsent(K key, V value) throws CacheException {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(Object key) throws CacheException {
            throw new UnsupportedOperationException();
        }

        @Override
        public V getAndRemove(Object key) throws CacheException {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean replace(K key, V oldValue, V newValue) throws CacheException {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean replace(K key, V value) throws CacheException {
            throw new UnsupportedOperationException();
        }

        @Override
        public V getAndReplace(K key, V value) throws CacheException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void removeAll(Collection<? extends K> keys) throws CacheException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void removeAll() throws CacheException {
            throw new UnsupportedOperationException();
        }

        @Override
        public CacheConfiguration getConfiguration() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean registerCacheEntryListener(CacheEntryListener<K, V> kvCacheEntryListener, NotificationScope scope, boolean synchronous) {
            throw new UnsupportedOperationException();
        }

        @Override
        public String getName() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean unregisterCacheEntryListener(CacheEntryListener cacheEntryListener) {
            throw new UnsupportedOperationException();
        }

        @Override
        public CacheManager getCacheManager() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Iterator<Entry<K, V>> iterator() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void start() throws CacheException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void stop() throws CacheException {
            throw new UnsupportedOperationException();
        }

        @Override
        public CacheStatus getStatus() {
            throw new UnsupportedOperationException();
        }
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
        public <K, V> CacheBuilder<K, V> createCacheBuilder(String cacheName) {
            throw new UnsupportedOperationException();
        }

        @Override
        public <K, V> Cache<K, V> getCache(String cacheName) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Collection<Cache> getCaches() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeCache(String cacheName) throws IllegalStateException {
            throw new UnsupportedOperationException();
        }

        @Override
        public CacheConfiguration createCacheConfiguration() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object getUserTransaction() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void shutdown() {
            throw new UnsupportedOperationException();
        }
    }
}
