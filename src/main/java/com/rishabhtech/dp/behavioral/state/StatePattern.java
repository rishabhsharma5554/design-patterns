package com.rishabhtech.dp.behavioral.state;

/*
Allows an object to alter its behavior when its internal state changes.
 */

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
class VendingMachine
{
    VendingState machineState;

    public VendingMachine() {
        this.machineState = new IdleState();
    }

    public void insertCoin() {
        machineState.insertCoin(this);
    }

    public void dispenseItem() {
        machineState.dispenseItem(this);
    }
}

interface VendingState {
    void insertCoin(VendingMachine product);
    void dispenseItem(VendingMachine product);
}

class IdleState implements VendingState {
    @Override
    public void insertCoin(VendingMachine product) {
        //insert coin logic
        System.out.println("Coin inserted");
        product.setMachineState(new WorkingState());
    }

    @Override
    public void dispenseItem(VendingMachine product) {
        System.out.println("Insert coin first!");
    }
}

class WorkingState implements VendingState {
    @Override
    public void insertCoin(VendingMachine product) {
        System.out.println("Coin already inserted!");
    }

    @Override
    public void dispenseItem(VendingMachine product) {
        System.out.println("Item Dispensed.");
        product.setMachineState(new IdleState());
    }
}

public class StatePattern {
    public static void main(String[] args) {
        VendingMachine machine = new VendingMachine();

        machine.dispenseItem();  // Insert coin first!
        machine.insertCoin();    // Coin inserted
        machine.insertCoin();    // Coin already inserted!
        machine.dispenseItem();  // Item Dispensed.
        machine.dispenseItem();  // Insert coin first!
    }
}