package model;

import java.util.ArrayList;

public class Burger extends Sandwich {
    private boolean doublePatty;

    /**
     * Constructs a Sandwich with specified bread, protein, and add-ons.
     *
     * @param bread   the bread type
     * @param doublePatty if single or double patty
     * @param addOns  list of add-on toppings
     */
    public Burger(Bread bread, boolean doublePatty, ArrayList<AddOns> addOns) {
        super(bread, Protein.BEEF_PATTY, addOns);
        this.doublePatty = doublePatty;
    }

    /**
     * Calculates the price of the burger, considering patty count and add-ons.
     * @return the price
     */
    @Override
    public double price() {
        double basePrice = 6.99;
        if (doublePatty) {
            basePrice += 2.50;
        }

        double addOnTotal = 0.0;
        if (addOns != null) {
            for (AddOns addOn : addOns) {
                addOnTotal += addOn.getPrice();
            }
        }
        return (basePrice + addOnTotal) * quantity;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(doublePatty ? "Double" : "Single").append(" Burger on ").append(bread);
        if (addOns != null && !addOns.isEmpty()) {
            sb.append(" with ");
            for (int i = 0; i < addOns.size(); i++) {
                sb.append(addOns.get(i));
                if (i < addOns.size() - 1) sb.append(", ");
            }
        }
        sb.append(" x").append(quantity);
        sb.append(" ($").append(String.format("%.2f", price())).append(")");
        return sb.toString();
    }
}
