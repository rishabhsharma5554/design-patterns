package com.rishabhtech.dp.solid.isp;

import java.util.Arrays;
import java.util.List;

interface VegetarianMenu{
    List<String> getVegetarianItems();
}

interface NonVegetarianMenu{
    List<String> getNonVegetarianItems();
}

class Vegetarian implements VegetarianMenu
{
    @Override
    public List<String> getVegetarianItems() {
        return Arrays.asList("Dal","Rice","Chappati");
    }
}

class  NonVegetarian implements NonVegetarianMenu, VegetarianMenu
{
    @Override
    public List<String> getNonVegetarianItems() {
        return Arrays.asList("Chicken","Fish");
    }

    @Override
    public List<String> getVegetarianItems() {
        return Arrays.asList("Dal","Rice","Chappati");
    }
}

public class Restraurent {
    public static void main(String[] args) {
        Vegetarian vegetarian = new Vegetarian();
        NonVegetarian nonVegetarian = new NonVegetarian();


        System.out.println("Vegetarian Menu");
        vegetarian.getVegetarianItems().forEach(System.out::println);

        System.out.println();
        System.out.println("Non Vegetarian Menu");
        nonVegetarian.getNonVegetarianItems().forEach(System.out::println);
        nonVegetarian.getVegetarianItems().forEach(System.out::println);
    }
}
