package com.rishabhtech.dp.behavioral.observer;

/**
 * Observer Design Pattern (SUBJECT changes → OBSERVERS update)
 *
 * An object (Subject/Observable) maintains a list of its dependents (Observers)
 * and notifies them automatically of any state change.
 *
 * Relationship: Subject (one) ---> Observers (many)
 *
 * Use when you want to establish a one-to-many dependency between objects.
 */

import java.util.ArrayList;
import java.util.List;

// Step 1: Define the Subject interface
// The Subject has methods to register, remove, and notify observers.
interface Subject {
    void addObserver(Observer o);
    void deleteObserver(Observer o);
    void notifyObservers();
}

// Step 2: Define the Observer interface
// The Observer defines an update() method that the Subject calls on state change.
interface Observer {
    void update(float temperature, float humidity);
}

// Step 3: Concrete Subject (WeatherStation)
// Holds the current state (temperature, humidity) and notifies observers when it changes.
class WeatherStation implements Subject {
    private List<Observer> observers = new ArrayList<>();
    private float temperature;
    private float humidity;

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void deleteObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update(temperature, humidity);
        }
    }

    public void setMeasurement(float temperature, float humidity) {
        this.temperature = temperature;
        this.humidity = humidity;
        notifyObservers();
    }
}

// Step 4: Concrete Observer (WeatherDisplay)
// Updates itself whenever the WeatherStation notifies it of a change.
class WeatherDisplay implements Observer {
    private float temperature;
    private float humidity;

    @Override
    public void update(float temperature, float humidity) {
        this.temperature = temperature;
        this.humidity = humidity;
        display();
    }

    public void display() {
        System.out.println("Current conditions: " + temperature + "C degrees and " + humidity + "% humidity");
    }
}

// Step 5: Demo
public class ObserverPattern {
    static void main() {
        WeatherStation ws = new WeatherStation();

        WeatherDisplay wd1 = new WeatherDisplay();
        WeatherDisplay wd2 = new WeatherDisplay();

        ws.addObserver(wd1);
        ws.addObserver(wd2);

        ws.setMeasurement(25.5f, 65.0f);
        ws.setMeasurement(27.3f, 70.0f);
    }
}
