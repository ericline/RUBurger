import model.AddOns;
import model.Bread;
import model.Protein;
import model.Sandwich;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SandwichTest {

    private Sandwich roastBeefSandwich;
    private Sandwich chickenSandwich;
    private Sandwich salmonSandwich;
    private ArrayList<AddOns> addOns;
    ArrayList<AddOns> emptyAddOns;

    @BeforeEach
    void setUp() {
        // Initialize add-ons
        addOns = new ArrayList<>();
        addOns.add(AddOns.LETTUCE);
        addOns.add(AddOns.TOMATOES);
        addOns.add(AddOns.CHEESE);

        emptyAddOns = new ArrayList<>();

        // Create sandwich instances
        roastBeefSandwich = new Sandwich(Bread.BRIOCHE, Protein.ROAST_BEEF, addOns);
        chickenSandwich = new Sandwich(Bread.WHEAT_BREAD, Protein.CHICKEN, addOns);
        salmonSandwich = new Sandwich(Bread.SOURDOUGH, Protein.SALMON, addOns);
    }

    @Test
    void testPrice_RoastBeefWithAddOns() {
        // Base price: $10.99
        // Add-ons: Lettuce ($0.30) + Tomatoes ($0.30) + Cheese ($1.00) = $1.60
        // Expected total: $10.99 + $1.60 = $12.59
        assertEquals(12.59, roastBeefSandwich.price(), 0.001);
    }

    @Test
    void testPrice_ChickenWithAddOns() {
        // Base price: $8.99
        // Add-ons: Lettuce ($0.30) + Tomatoes ($0.30) + Cheese ($1.00) = $1.60
        // Expected total: $8.99 + $1.60 = $10.59
        assertEquals(10.59, chickenSandwich.price(), 0.001);
    }

    @Test
    void testPrice_SalmonWithAddOns() {
        // Base price: $9.99
        // Add-ons: Lettuce ($0.30) + Tomatoes ($0.30) + Cheese ($1.00) = $1.60
        // Expected total: $9.99 + $1.60 = $11.59
        assertEquals(11.59, salmonSandwich.price(), 0.001);
    }

    @Test
    void testPrice_NoAddOns() {
        ArrayList<AddOns> emptyAddOns = new ArrayList<>();
        Sandwich plainSandwich = new Sandwich(Bread.PRETZEL, Protein.ROAST_BEEF, emptyAddOns);

        // Base price only: $10.99
        assertEquals(10.99, plainSandwich.price(), 0.001);
    }

    @Test
    void testPrice_WithQuantity() {
        roastBeefSandwich.setQuantity(2);
        // Base price: $10.99 * 2 = $21.98
        // Add-ons: (Lettuce $0.30 + Tomatoes $0.30 + Cheese $1.00) * 2 = $3.20
        // Expected total: $21.98 + $3.20 = $25.18
        assertEquals(25.18, roastBeefSandwich.price(), 0.001);
    }

    @Test
    void testToString() {
        roastBeefSandwich.setQuantity(1);
        String expected = "Roast Beef Sandwich on Brioche with Lettuce, Tomatoes, Cheese x1 ($12.59)";
        assertEquals(expected, roastBeefSandwich.toString());
    }

    @Test
    void testToString_NoAddOns() {
        ArrayList<AddOns> emptyAddOns = new ArrayList<>();
        Sandwich plainSandwich = new Sandwich(Bread.PRETZEL, Protein.ROAST_BEEF, emptyAddOns);
        plainSandwich.setQuantity(1);

        String expected = "Roast Beef Sandwich on Pretzel x1 ($10.99)";
        assertEquals(expected, plainSandwich.toString());
    }
}