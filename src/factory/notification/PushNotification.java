
package factory.notification;

public class PushNotification extends Notification {

    private final String deviceToken;
    private final int badgeCount;

    public PushNotification(
            String recipient,
            String message,
            NotificationConfig config,
            String deviceToken,
            int badgeCount
    ) {
        super(recipient, message, config);

        if (deviceToken == null || deviceToken.isBlank()) {
            throw new IllegalArgumentException(
                    "Device token cannot be empty"
            );
        }

        if (badgeCount < 0) {
            throw new IllegalArgumentException(
                    "Badge count cannot be negative"
            );
        }

        this.deviceToken = deviceToken;
        this.badgeCount = badgeCount;
    }

    @Override
    public boolean send() {
        String preview = deviceToken.substring(
                0, Math.min(10, deviceToken.length())
        );

        System.out.println(
                "Sending push to device " + preview + "..."
        );

        status = "sent";
        return true;
    }

    @Override
    public String formatMessage() {
        return message;
    }
}
