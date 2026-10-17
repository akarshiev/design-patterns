package factory.payment_processor;

public class ClickPayment implements Payment {
    public void validateAddress() {
        System.out.println("Validating Click address");
    }

    @Override
    public void process(double amount) {
        validateAddress();
        System.out.println("Processing Click address: " + amount);
    }
}
