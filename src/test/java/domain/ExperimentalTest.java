package domain;

import org.junit.Test;
import sun.security.krb5.internal.KdcErrException;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.CacheManagerFactory;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Date;
import java.util.Set;

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

    public void getCachesExperiment() {
        CacheManagerExperimental cacheManager = getCacheManagerExperimental();
        Cache<Integer, String> cache1 = cacheManager.getCache("c1");
        Cache<Integer, Date> cache2 = cacheManager.getCache("c2");

       // we expect both cache1 and cache2 to be returned in the set
        Set<Cache> caches = cacheManager.getCaches();
        Set caches0 = cacheManager.getCachesNew0(); // Intellij converts LHS to this
        Set<? extends Cache> caches1 = cacheManager.getCachesNew1(); // Intellij converts LHS to this
        Set<Cache<?, ?>> caches2 = cacheManager.getCachesNew2(); // Intellij converts LHS to this
        Set<? extends Cache<?, ?>> caches3 = cacheManager.getCachesNew3();
    }

    public interface CacheManagerExperimental extends CacheManager {
        <K, V> Set<Cache<K,V>> getCachesNew0();
        Set<? extends Cache> getCachesNew1();
        Set<Cache<?, ?>> getCachesNew2();
        Set<? extends Cache<?, ?>> getCachesNew3();
    }

    private CacheManagerExperimental getCacheManagerExperimental() {
        throw new UnsupportedOperationException();
    }
}