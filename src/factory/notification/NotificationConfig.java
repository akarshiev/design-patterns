package factory.notification;

public class NotificationConfig {

    private final String priority;
    private final int retryCount;
    private final int timeout;

    public NotificationConfig() {
        this("normal", 3, 30);
    }

    public NotificationConfig(String priority, int retryCount, int timeout) {
        if (priority == null || !priority.matches("normal|high|low")) {
            throw new IllegalArgumentException("Priority must be normal, high, or low");
        }

        if (retryCount < 0) {
            throw new IllegalArgumentException("Retry count cannot be negative");
        }

        if (timeout <= 0) {
            throw new IllegalArgumentException("Timeout must be positive");
        }

        this.priority = priority;
        this.retryCount = retryCount;
        this.timeout = timeout;
    }

    public String getPriority() {
        return priority;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public int getTimeout() {
        return timeout;
    }
}
