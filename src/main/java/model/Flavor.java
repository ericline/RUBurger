package model;

public enum Flavor {
    COCA_COLA("Coca Cola"),
    DIET_COKE("Diet Coke"),
    SPRITE("Sprite"),
    ROOT_BEER("Root Beer"),
    FANTA("Fanta"),
    PEPSI("Pepsi"),
    DR_PEPPER("Dr Pepper"),
    LEMONADE("Lemonade"),
    ICED_TEA("Iced Tea"),
    MILK_SHAKE("Milk Shake"),
    WATER("Water"),
    FRUIT_PUNCH("Fruit Punch"),
    ORANGE_JUICE("Orange Juice"),
    COFFEE("Coffee"),
    RED_BULL("Red Bull");

    private final String name;

    Flavor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
