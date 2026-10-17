package factory.payment_processor;

public class PaymentFactory {
    public static Payment createPayment(String type) {
        return switch (type.toLowerCase()) {
            case "creditcard" -> new CreditCardPayment();
            case "payme" -> new PaymePayment();
            case "click" -> new ClickPayment();
            default -> throw new IllegalArgumentException("Invalid type: " + type);
        };
    }
}
