package domain;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.CacheManagerFactory;

public class ExperimentalTest {
    private static final String CACHE_NAME = "myCache";

    private void init(String cacheName, int[] keys) {
        CacheManager cacheManager = CacheManagerFactory.getCacheManager();
        Cache<Integer, String> myCache = cacheManager.<Integer, String>createCacheBuilder(cacheName).build();
        for (int key : keys) {
            myCache.put(key, "value" + key);
        }
    }

    public void doIt() {
        int[] keys = {1, 2, 3, 4, 5};
        init(CACHE_NAME, keys);
        Cache<Integer, String> myCache = CacheManagerFactory.getCacheManager().getCache(CACHE_NAME);
        for (int key : keys) {
            assert myCache.containsKey(key);
        }
    }
}