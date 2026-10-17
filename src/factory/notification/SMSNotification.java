package factory.notification;

public class SMSNotification extends Notification {

    public SMSNotification(String recipient, String message, NotificationConfig config) {
        super(recipient, message, config);
    }

    @Override
    public boolean send() {
        System.out.println("Sending SMS to " + recipient);

        status = "sent";
        return true;
    }

    @Override
    public String formatMessage() {
        return "Alert: " + message;
    }
}
