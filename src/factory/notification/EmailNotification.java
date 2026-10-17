package factory.notification;

public class EmailNotification extends Notification {

    private final String subject;

    public EmailNotification(String recipient, String message, NotificationConfig config, String subject) {
        super(recipient, message, config);
        this.subject = subject;
    }

    @Override
    public boolean send() {
        System.out.println("Sending email to " + recipient);
        System.out.println("Subject: " + subject);

        status = "sent";
        return true;
    }

    @Override
    public String formatMessage() {
        return "Dear " + recipient + ",\n\n" + message;
    }
}
