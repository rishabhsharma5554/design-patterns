package com.rishabhtech.dp.solid.dip;

import lombok.RequiredArgsConstructor;
//Dependency Inversion Principle : High level Module Should not depeend on low level module

//abstraction
interface PaymentGateway {
    void pay(double amount);
}

//low-level
class RazorpayGateway implements  PaymentGateway {
    @Override
    public void pay(double amount) {
        System.out.println("Paid amount: " + amount+" via Razorpay");
    }
}

//low-level
class CCAvenue implements  PaymentGateway {
    @Override
    public void pay(double amount) {
        System.out.println("Paid amount: " + amount+ " via CCAvenue");
    }
}

//high-level - Spring Style
@RequiredArgsConstructor
class PaymentService {
    private final PaymentGateway paymentGateway;

    public void pay(double amount) {
        paymentGateway.pay(amount);
    }
}

public class PaymentModule {
    public static void main(String[] args) {
        PaymentGateway paymentGateway1 = new RazorpayGateway();
        PaymentService paymentService1 = new PaymentService(paymentGateway1);
        paymentService1.pay(100);

        PaymentGateway paymentGateway2 = new CCAvenue();
        PaymentService paymentService2 = new PaymentService(paymentGateway2);
        paymentService2.pay(20);
    }
}
