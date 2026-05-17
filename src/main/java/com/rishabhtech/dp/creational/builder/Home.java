package com.rishabhtech.dp.creational.builder;

/*
Rules

1. Create Private Constructor
2. Inner static class -> new ContructHome.Builder()
3. Inner static class constructor -> create an final instance object of ConstructHome
 */

class ContructHome{
    private int walls;
    private int doors;
    private int windows;
    private boolean hasRoof;
    private boolean hasGarage;
    private String color;

    //private constructor
    private ContructHome(){}

    @Override
    public String toString(){
        return "Constructed home : Color "+color+" Walls "+walls+" Doors "+doors+" Windows "+windows+" Roof "+hasRoof+" Garage "+hasGarage;
    }

    static class Builder
    {
        private final ContructHome home;
        public Builder()
        {
            home = new ContructHome();
        }

        Builder walls(int walls)
        {
            home.walls = walls;
            return this;
        }

        Builder doors(int doors)
        {
            home.doors = doors;
            return this;
        }

        Builder windows(int windows)
        {
            home.windows = windows;
            return this;
        }

        Builder hasRoof(Boolean hasRoof)
        {
            home.hasRoof = hasRoof;
            return this;
        }

        Builder hasGarage(Boolean hasGarage)
        {
            home.hasGarage = hasGarage;
            return this;
        }

        Builder color(String color)
        {
            home.color = color;
            return this;
        }

        ContructHome build()
        {
            return home;
        }
    }
}
public class Home {
    static void main() {
        ContructHome home = new ContructHome.Builder()
                .color("Blue")
                .doors(2)
                .hasRoof(true)
                .hasGarage(false)
                .walls(8)
                .build();
        System.out.println(home);
    }
}
