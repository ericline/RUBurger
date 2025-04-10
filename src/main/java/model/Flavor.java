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
    JUICE("Juice"),
    COFFEE("Coffee"),
    TEA("Tea");

    private final String name;

    Flavor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Flavor fromString(String name) {
        for (Flavor flavor : Flavor.values()) {
            if (flavor.getName().equalsIgnoreCase(name)) {
                return flavor;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return name;
    }
}
