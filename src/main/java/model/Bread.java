package model;

public enum Bread {
    BRIOCHE("Brioche"),
    WHEAT_BREAD("Wheat Bread"),
    PRETZEL("Pretzel"),
    BAGEL("Bagel"),
    SOURDOUGH("Sourdough");

    private final String name;

    Bread(String name) {
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
