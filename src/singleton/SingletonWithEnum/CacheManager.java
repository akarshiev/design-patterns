package singleton.SingletonWithEnum;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public enum CacheManager {

    INSTANCE;

    private final ConcurrentHashMap<String, CacheEntry> cache = new ConcurrentHashMap<>();

    private final ScheduledExecutorService cleanupExecutor = Executors.newSingleThreadScheduledExecutor(task -> {
        Thread thread = new Thread(task, "cache-cleanup");
        thread.setDaemon(true);
        return thread;
    });

    CacheManager() {
        startCleanupThread();
    }

    private static final class CacheEntry {
        private final Object value;
        private final long expiresAtNanos;

        private CacheEntry(Object value, int ttlSeconds) {
            this.value = value;
            this.expiresAtNanos = System.nanoTime() + TimeUnit.SECONDS.toNanos(ttlSeconds);
        }

        private boolean isExpired() {
            return System.nanoTime() - expiresAtNanos >= 0;
        }
    }

    public void put(String key, Object value, int ttlSeconds) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Key and value must not be null");
        }

        if (ttlSeconds <= 0) {
            cache.remove(key);
            return;
        }

        cache.put(key, new CacheEntry(value, ttlSeconds));
    }

    public Object get(String key) {
        if (key == null) {
            return null;
        }

        CacheEntry entry = cache.get(key);

        if (entry == null) {
            return null;
        }

        if (entry.isExpired()) {
            cache.remove(key, entry);
            return null;
        }

        return entry.value;
    }

    public void remove(String key) {
        if (key != null) {
            cache.remove(key);
        }
    }

    private void startCleanupThread() {
        cleanupExecutor.scheduleWithFixedDelay(this::cleanExpiredEntries, 1, 1, TimeUnit.SECONDS);
    }

    private void cleanExpiredEntries() {
        cache.forEach((key, entry) -> {
            if (entry.isExpired()) {
                cache.remove(key, entry);
            }
        });
    }
}
