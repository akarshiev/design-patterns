package factory.notification;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class Notification {

    protected final String recipient;
    protected final String message;
    protected final NotificationConfig config;
    protected String status = "pending";

    public Notification(String recipient, String message, NotificationConfig config) {
        if (recipient == null || recipient.isBlank()) {
            throw new IllegalArgumentException("Recipient cannot be empty");
        }

        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Message cannot be empty");
        }

        this.recipient = recipient;
        this.message = message;
        this.config = config == null ? new NotificationConfig() : config;
    }

    public abstract boolean send();

    public abstract String formatMessage();

    public Map<String, Object> getStatus() {
        Map<String, Object> result = new LinkedHashMap<>();

        result.put("type", getClass().getSimpleName());
        result.put("recipient", recipient);
        result.put("status", status);
        result.put("priority", config.getPriority());

        return result;
    }
}
