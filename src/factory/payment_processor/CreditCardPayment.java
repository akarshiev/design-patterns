package factory.payment_processor;

public class CreditCardPayment implements Payment {
    public void validate() {
        System.out.println("Validating Credit Card Payment");
    }

    @Override
    public void process(double amount) {
        validate();

        System.out.println("Processing Credit Card Payment: " + amount);
    }
}
