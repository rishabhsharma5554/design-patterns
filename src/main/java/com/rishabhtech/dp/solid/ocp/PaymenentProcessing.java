package com.rishabhtech.dp.solid.ocp;

// Strategy Design Pattern promotes Open closed Design Principle

interface PaymentProcessor
{
    void processPayment(Double amount);
}

class CreditCardPaymentProcessor implements PaymentProcessor
{
    @Override
    public void processPayment(Double amount)
    {
        System.out.println("Processing credit card payment of $" + amount);
    }
}

class PaypalPaymentProcessor implements PaymentProcessor
{
    @Override
    public void processPayment(Double amount)
    {
        System.out.println("Processing paypal payment of $" + amount);
    }
}


public class PaymenentProcessing {
    public static void main(String[] args) {
        PaymentProcessor creditCardPaymentProcessor = new CreditCardPaymentProcessor();
        creditCardPaymentProcessor.processPayment(100.0);

        PaymentProcessor paypalPaymentProcessor = new PaypalPaymentProcessor();
        paypalPaymentProcessor.processPayment(100.0);
    }
}
