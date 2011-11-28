package acme.cache;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Simple lock management
 *
 * @param <K> the type of the object to be locked
 * @author Yannis Cosmadopoulos
 * @since 1.0
 */
public class LockManager<K> {
    private final ConcurrentHashMap<K, ReentrantLock> locks = new ConcurrentHashMap<K, ReentrantLock>();
    private final LockFactory lockFactory = new LockFactory();

    /**
     * Lock the object
     *
     * @param key the key
     */
    private void lock(K key) {
        ReentrantLock lock = lockFactory.getLock();

        while (true) {
            ReentrantLock oldLock = locks.putIfAbsent(key, lock);
            if (oldLock == null) {
                return;
            }
            // there was a lock
            oldLock.lock();
            // now we have it. Because of possibility that someone had it for remove,
            // we don't re-use directly
            lockFactory.release(oldLock);
        }
    }

    /**
     * Unlock the object
     *
     * @param key the object
     */
    private void unLock(K key) {
        ReentrantLock lock = locks.remove(key);
        lockFactory.release(lock);
    }

    /**
     * Factory/pool
     *
     * @author Yannis Cosmadopoulos
     * @since 1.0
     */
    private static class LockFactory {
        private static final int CAPACITY = 100;
        private static final ArrayList<ReentrantLock> LOCKS = new ArrayList<ReentrantLock>(CAPACITY);

        private ReentrantLock getLock() {
            ReentrantLock qLock = null;
            synchronized (LOCKS) {
                if (!LOCKS.isEmpty()) {
                    qLock = LOCKS.remove(0);
                }
            }

            ReentrantLock lock = qLock != null ? qLock : new ReentrantLock();
            lock.lock();
            return lock;
        }

        private void release(ReentrantLock lock) {
            lock.unlock();
            synchronized (LOCKS) {
                if (LOCKS.size() <= CAPACITY) {
                    LOCKS.add(lock);
                }
            }
        }
    }
}
