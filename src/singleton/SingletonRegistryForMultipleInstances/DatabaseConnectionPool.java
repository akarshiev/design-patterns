package singleton.SingletonRegistryForMultipleInstances;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class DatabaseConnectionPool {

    private static final int MAX_POOL_SIZE = 10;
    private static final long CONNECTION_TIMEOUT_MS = 30_000;

    private final List<Connection> availableConnections = new ArrayList<>();

    private final List<Connection> usedConnections = new ArrayList<>();

    private DatabaseConnectionPool() {
    }

    private static class Holder {
        private static final DatabaseConnectionPool INSTANCE = new DatabaseConnectionPool();
    }

    public static DatabaseConnectionPool getInstance() {
        return Holder.INSTANCE;
    }

    public synchronized Connection getConnection() throws InterruptedException, TimeoutException {

        long deadline = System.currentTimeMillis() + CONNECTION_TIMEOUT_MS;

        while (true) {

            if (!availableConnections.isEmpty()) {
                Connection connection = availableConnections.remove(0);
                usedConnections.add(connection);
                return connection;
            }

            int totalConnections = availableConnections.size() + usedConnections.size();

            if (totalConnections < MAX_POOL_SIZE) {
                Connection connection = new Connection();
                usedConnections.add(connection);
                return connection;
            }

            long remainingTime = deadline - System.currentTimeMillis();

            if (remainingTime <= 0) {
                throw new TimeoutException("Timed out waiting for a database connection");
            }

            wait(remainingTime);
        }
    }

    public synchronized void releaseConnection(Connection connection) {
        if (connection == null) {
            return;
        }

        if (usedConnections.remove(connection)) {
            availableConnections.add(connection);
            notifyAll();
        }
    }

    public synchronized int getPoolSize() {
        return availableConnections.size() + usedConnections.size();
    }
}
