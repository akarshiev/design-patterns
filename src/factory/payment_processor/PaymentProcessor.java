package factory.payment_processor;

public class PaymentProcessor {
    public void processPayment(String type, double amount) {
        Payment paymentProcessor = PaymentFactory.createPayment(type);
        paymentProcessor.process(amount);
    }
}
