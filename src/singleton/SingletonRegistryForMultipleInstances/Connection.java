package singleton.SingletonRegistryForMultipleInstances;

import java.util.concurrent.atomic.AtomicInteger;

public class Connection {

    private static final AtomicInteger counter = new AtomicInteger(0);

    private final int id;

    public Connection() {
        this.id = counter.incrementAndGet();
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Connection-" + id;
    }
}
