package com.rishabhtech.dp.behavioral.strategy;

/*
Strategy Pattern promotes the Open/Closed Principle.

Key Idea:
- Define a family of algorithms (strategies), encapsulate each one, and make them interchangeable.
- The client (Context) can switch between strategies at runtime without changing its own code.

Real-World Analogy:
- Think of paying for an order: you can pay via Credit Card, PayPal, or UPI.
  The shopping cart doesn't care HOW you pay — it just delegates to the chosen payment strategy.

Difference between Strategy Pattern and Open/Closed Principle (OCP):
- OCP is a PRINCIPLE (rule): "Classes should be open for extension, closed for modification."
  → You can add new behavior without editing existing code. That's it.
- Strategy is a PATTERN (technique): Lets the client SWAP algorithms at RUNTIME via setStrategy().
  → It satisfies OCP, but also adds runtime flexibility.
- Summary: OCP = "don't modify old code to add features" | Strategy = "switch behavior at runtime"

OCP = a principle → "don't modify old code to add features"
Strategy = a pattern → "swap behavior at runtime via setStrategy()"

Structure:
  1. Strategy (interface)       → PaymentStrategy
  2. ConcreteStrategies         → CreditCardPayment, PayPalPayment, UPIPayment
  3. Context (uses a strategy)  → ShoppingCart
 */

// ──────────────────────────────────────────────
// 1. Strategy Interface
// ──────────────────────────────────────────────
interface PaymentStrategy {
    void pay(int amount);
}

// ──────────────────────────────────────────────
// 2. Concrete Strategies
// ──────────────────────────────────────────────
class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;

    public CreditCardPayment(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public void pay(int amount) {
        System.out.println("Paid $" + amount + " using Credit Card ending with " + cardNumber.substring(cardNumber.length() - 4));
    }
}

class PayPalPayment implements PaymentStrategy {
    private String email;

    public PayPalPayment(String email) {
        this.email = email;
    }

    @Override
    public void pay(int amount) {
        System.out.println("Paid ₹" + amount + " using PayPal account: " + email);
    }
}

class UPIPayment implements PaymentStrategy {
    private String upiId;

    public UPIPayment(String upiId) {
        this.upiId = upiId;
    }

    @Override
    public void pay(int amount) {
        System.out.println("Paid ₹" + amount + " using UPI ID: " + upiId);
    }
}

// ──────────────────────────────────────────────
// 3. Context — uses a strategy but doesn't know the details
// ──────────────────────────────────────────────
class ShoppingCart {
    private PaymentStrategy paymentStrategy;

    // Strategy can be set or changed at runtime
    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public void checkout(int amount) {
        if (paymentStrategy == null) {
            System.out.println("No payment method selected!");
            return;
        }
        paymentStrategy.pay(amount);
    }
}

// ──────────────────────────────────────────────
// 4. Client — demonstrates switching strategies at runtime
// ──────────────────────────────────────────────
public class StrategyPattern {

    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart();

        // Pay with Credit Card
        cart.setPaymentStrategy(new CreditCardPayment("1234567890124567"));
        cart.checkout(1500);

        // Switch to PayPal at runtime
        cart.setPaymentStrategy(new PayPalPayment("rishabh@example.com"));
        cart.checkout(2000);

        // Switch to UPI at runtime
        cart.setPaymentStrategy(new UPIPayment("rishabh@upi"));
        cart.checkout(500);
    }
}
