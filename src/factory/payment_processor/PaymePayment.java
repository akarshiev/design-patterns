package factory.payment_processor;

public class PaymePayment implements Payment {
    public void authenticate() {
        System.out.println("Authenticating Payme");
    }

    @Override
    public void process(double amount) {
        authenticate();

        System.out.println("Processing Credit Card Payment: " + amount);
    }
}
