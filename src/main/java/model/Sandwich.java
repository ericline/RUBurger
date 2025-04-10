package model;

import java.util.ArrayList;

/**
 * Represents a customizable Sandwich menu item with bread, protein, and add-ons.
 */
public class Sandwich extends MenuItem {
    protected Bread bread;
    protected Protein protein;
    protected ArrayList<AddOns> addOns;

    /**
     * Constructs a Sandwich with specified bread, protein, and add-ons.
     * @param bread the bread type
     * @param protein the protein type
     * @param addOns list of add-on toppings
     */
    public Sandwich(Bread bread, Protein protein, ArrayList<AddOns> addOns) {
        super();
        this.bread = bread;
        this.protein = protein;
        this.addOns = addOns;
    }

    /**
     * Calculates the total price of the sandwich including add-ons.
     * @return the price
     */
    @Override
    public double price() {
        double basePrice = 0.0;

        switch (protein) {
            case ROAST_BEEF:
                basePrice = 10.99;
                break;
            case CHICKEN:
                basePrice = 8.99;
                break;
            case SALMON:
                basePrice = 9.99;
                break;
        }

        double addOnTotal = 0.0;
        if (addOns != null) {
            for (AddOns addOn : addOns) {
                addOnTotal += addOn.getPrice();
            }
        }
        return (basePrice + addOnTotal) * quantity;
    }

    public String withoutPrice() {
        StringBuilder sb = new StringBuilder();
        sb.append(protein).append(" Sandwich on ").append(bread);
        if (addOns != null && !addOns.isEmpty()) {
            sb.append(" with ");
            for (int i = 0; i < addOns.size(); i++) {
                sb.append(addOns.get(i));
                if (i < addOns.size() - 1) sb.append(", ");
            }
        }
        sb.append(" x").append(quantity);
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(protein).append(" Sandwich on ").append(bread);
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
