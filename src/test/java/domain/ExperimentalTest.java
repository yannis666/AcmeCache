package domain;

import javax.cache.Cache;
import javax.cache.CacheBuilder;
import javax.cache.CacheConfiguration;
import javax.cache.CacheLoader;
import javax.cache.CacheManager;
import javax.cache.CacheWriter;
import javax.cache.Caching;
import javax.cache.event.CacheEntryReadListener;
import javax.cache.event.NotificationScope;
import javax.cache.transaction.IsolationLevel;
import javax.cache.transaction.Mode;
import java.util.Date;

public class ExperimentalTest {
    private static final String CACHE_NAME = "myCache";


    public void foo() {
        String cacheName = "sampleCache";
        CacheManager cacheManager = Caching.getCacheManager();
        Cache<Integer, Date> cache = cacheManager.getCache(cacheName);
        if (cache == null) {
            CacheBuilder<Integer, Date> cacheBuilder = cacheManager.<Integer, Date>createCacheBuilder(cacheName);
            cache = cacheBuilder.
                    setCacheLoader(createCacheLoader()).
                    setExpiry(CacheConfiguration.ExpiryType.MODIFIED, CacheConfiguration.Duration.ETERNAL).
                    setCacheWriter(createCacheWriter()).
                    setReadThrough(true).
                    setStatisticsEnabled(true).
                    setStoreByValue(true).
                    setTransactionEnabled(IsolationLevel.TX_READ_REPEATABLE, Mode.LOCAL).
                    setWriteThrough(true).
                    registerCacheEntryListener(createListener(), NotificationScope.LOCAL, false).
                    build();
        }
        Date value1 = new Date();
        Integer key = 1;
        cache.put(key, value1);
    }

    private CacheWriter<Integer, Date> createCacheWriter() {
        throw new UnsupportedOperationException();
    }

    private CacheEntryReadListener<Integer, Date> createListener() {
        throw new UnsupportedOperationException();
    }

    private CacheLoader<Integer, Date> createCacheLoader() {
        throw new UnsupportedOperationException();
    }
}