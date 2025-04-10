package model;

/**
 * This class represents a Combo meal, which includes a Sandwich, a Beverage, and a Side.
 */
public class Combo extends MenuItem{
    private Sandwich sandwich;
    private Beverage drink;
    private Side side;

    /**
     * Constructs a Combo with a sandwich, drink, and side.
     * @param sandwich the sandwich component
     * @param drink the drink component
     * @param side the side component
     */
    public Combo(Sandwich sandwich, Beverage drink, Side side) {
        this.sandwich = sandwich;
        this.drink = drink;
        this.side = side;

        setQuantity(sandwich.getQuantity());
    }

    /**
     * Calculates the total price of the combo meal.
     * Base price = sandwich + $2 combo up-charge (includes drink and side)
     * @return the total combo price
     */
    @Override
    public double price() {
        return (sandwich.price() + 2.00);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Combo Meal: ");
        sb.append(sandwich.toString()).append("\n");
        sb.append("  + Side: ").append(side).append("\n");
        sb.append("  + Drink: ").append(drink).append("\n");
        sb.append("Quantity: ").append(quantity).append("\n");
        sb.append(String.format("Total: $%.2f", price()));
        return sb.toString();
    }
}

