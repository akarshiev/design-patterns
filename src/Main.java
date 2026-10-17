/*
import singleton.ThreadSafeSingletonWDoubleCheckedLocking.Logger;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        Logger logger = Logger.getInstance();

        Runnable task = () -> {
            String threadName = Thread.currentThread().getName();

            for (int i = 1; i <= 5; i++) {
                logger.log("INFO", threadName + " - Message " + i);
            }
        };

        Thread thread1 = new Thread(task, "Thread-1");
        Thread thread2 = new Thread(task, "Thread-2");
        Thread thread3 = new Thread(task, "Thread-3");

        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();

        System.out.println("All logging tasks complete");
    }
}
*/

import singleton.SingletonWithEnum.CacheManager;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        CacheManager cache = CacheManager.INSTANCE;

        cache.put("user:1", "Abdukarim", 5);
        cache.put("app:version", "1.0", 10);

        System.out.println(cache.get("user:1"));

        System.out.println(cache.get("app:version"));

        Thread.sleep(6000);

        System.out.println(cache.get("user:1"));

        System.out.println(cache.get("app:version"));

        cache.remove("app:version");

        System.out.println(cache.get("app:version"));
    }
}
