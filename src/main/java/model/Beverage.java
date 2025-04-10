package model;

/**
 * This class represents a Beverage menu item with a flavor and size.
 */
public class Beverage extends MenuItem {
    private Size size;
    private Flavor flavor;

    /**
     * Constructs a Beverage with the given size and flavor.
     * @param size the size of the beverage
     * @param flavor the flavor of the beverage
     */
    public Beverage(Size size, Flavor flavor) {
        this.size = size;
        this.flavor = flavor;
    }
    @Override
    public double price() {
        if (size.equals(Size.SMALL)) {
            return 1.99 * quantity;
        }
        else if(size.equals(Size.MEDIUM)) {
            return 2.49 * quantity;
        }
        else if(size.equals(Size.LARGE)) {
            return 2.99 * quantity;
        }
        else {
            System.out.println("Invalid size");
            return 0;
        }
    }
    @Override
    public String toString() {
        return size + " " + flavor;
    }
}

