package factory.notification;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class NotificationFactory {

    @FunctionalInterface
    public interface NotificationCreator {
        Notification create(String recipient, String message, NotificationConfig config, Map<String, Object> options);
    }

    private static final Map<String, NotificationCreator> registry = new HashMap<>();

    public static void registerNotification(String name, NotificationCreator creator) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Notification name cannot be empty");
        }

        if (creator == null) {
            throw new IllegalArgumentException("Creator cannot be null");
        }

        String key = name.trim().toLowerCase(Locale.ROOT);
        registry.put(key, creator);
    }

    public static Notification createNotification(String notificationType, String recipient, String message, Map<String, Object> options) {
        if (notificationType == null || notificationType.isBlank()) {
            return null;
        }

        String key = notificationType.trim().toLowerCase(Locale.ROOT);

        NotificationCreator creator = registry.get(key);

        if (creator == null) {
            return null;
        }

        Map<String, Object> safeOptions = options == null ? Map.of() : options;

        String priority = getOption(safeOptions, "priority", "normal", String.class);

        int retryCount = getOption(safeOptions, "retry_count", 3, Integer.class);

        int timeout = getOption(safeOptions, "timeout", 30, Integer.class);

        NotificationConfig config = new NotificationConfig(priority, retryCount, timeout);

        return creator.create(recipient, message, config, safeOptions);
    }

    public static java.util.List<String> listAvailableTypes() {
        return registry.keySet().stream().sorted().toList();
    }

    private static <T> T getOption(Map<String, Object> options, String key, T defaultValue, Class<T> type) {
        Object value = options.get(key);

        if (value == null) {
            return defaultValue;
        }

        if (!type.isInstance(value)) {
            throw new IllegalArgumentException("Invalid type for option: " + key);
        }

        return type.cast(value);
    }

    private static String getStringOption(Map<String, Object> options, String key, String defaultValue) {
        return getOption(options, key, defaultValue, String.class);
    }

    private static int getIntOption(Map<String, Object> options, String key, int defaultValue) {
        return getOption(options, key, defaultValue, Integer.class);
    }
}
