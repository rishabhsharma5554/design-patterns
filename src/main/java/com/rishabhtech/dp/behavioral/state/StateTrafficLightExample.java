package com.rishabhtech.dp.behavioral.state;


/*
Let’s take a traffic light system as an example. A traffic light can be in one of three states:

Red Light: Cars must stop.
Green Light: Cars can go.
Yellow Light: Cars should prepare to stop.

 */
//Step 1: Define the state interface
interface TrafficLightState
{
    void handleRequest(TrafficLightContext context);
}

//Step 2.1: Implement Concrete States
class RedLightState implements TrafficLightState
{

    @Override
    public void handleRequest(TrafficLightContext context) {
        System.out.println("Red Light : Cars must stop.");
        context.setState(new GreenLightState());
    }
}
//Step 2.2: Implement Concrete States
class GreenLightState implements TrafficLightState
{

    @Override
    public void handleRequest(TrafficLightContext context) {
        System.out.println("Green Light : Cars can go.");
        context.setState(new YelloLightState());
    }
}

//Step 2.3: Implement Concrete States
class YelloLightState implements TrafficLightState
{

    @Override
    public void handleRequest(TrafficLightContext context) {
        System.out.println("Yello Light : Ready to stop.");
        context.setState(new RedLightState());
    }
}

// Step 3: Create the context class
class TrafficLightContext{
    private TrafficLightState currentState;
    public TrafficLightContext()
    {
        currentState = new RedLightState();
    }

    public void setState(TrafficLightState state)
    {
        this.currentState = state;
    }

    public void changeLight()
    {
        currentState.handleRequest(this);
    }
}
public class StateTrafficLightExample {
    public static void main(String[] args) {
        TrafficLightContext trafficLight = new TrafficLightContext();

        for (int i = 0; i < 6; i++) {  // Change the light multiple times
            trafficLight.changeLight();
            System.out.println();
        }
    }
}
