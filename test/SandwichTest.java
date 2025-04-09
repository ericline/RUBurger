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
    private ArrayList<AddOns> LTC;
    private ArrayList<AddOns> AC;
    ArrayList<AddOns> emptyAddOns;

    @BeforeEach
    void setUp() {
        // Initialize add-ons
        LTC = new ArrayList<>();
        LTC.add(AddOns.LETTUCE);
        LTC.add(AddOns.TOMATOES);
        LTC.add(AddOns.CHEESE);

        AC = new ArrayList<>();
        AC.add(AddOns.AVOCADO);
        AC.add(AddOns.CHEESE);

        emptyAddOns = new ArrayList<>();

        // Create sandwich instances
        roastBeefSandwich = new Sandwich(Bread.BRIOCHE, Protein.ROAST_BEEF, LTC);
        chickenSandwich = new Sandwich(Bread.WHEAT_BREAD, Protein.CHICKEN, AC);
        salmonSandwich = new Sandwich(Bread.SOURDOUGH, Protein.SALMON, emptyAddOns);
    }

    @Test
    void testRoastBeefWithAddOns() {
        // Base price: $10.99
        // Add-ons: Lettuce ($0.30) + Tomatoes ($0.30) + Cheese ($1.00) = $1.60
        // Expected total: $10.99 + $1.60 = $12.59
        assertEquals(12.59, roastBeefSandwich.price(), 0.001);
    }

    @Test
    void testChickenWithAddOns() {
        // Base price: $8.99
        // Add-ons: Avocado ($0.50) + Cheese ($1.00) = $1.50
        // Expected total: $8.99 + $1.50 = $10.49
        assertEquals(10.49, chickenSandwich.price(), 0.001);
    }

    @Test
    void testSalmonWithNoAddOns() {
        // Base price: $9.99
        // Add-ons: Lettuce ($0.30) + Tomatoes ($0.30) + Cheese ($1.00) = $1.60
        // Expected total: $9.99 + $1.60 = $11.59
        assertEquals(11.59, salmonSandwich.price(), 0.001);
    }

    @Test
    void testSetQuantity() {
        roastBeefSandwich.setQuantity(3);
        // Base price: $10.99
        // Add-ons: Lettuce ($0.30) + Tomatoes ($0.30) + Cheese ($1.00) = $1.60
        // Expected total: ($10.99 + $1.60) * 3 = $37.77
        assertEquals(25.18, roastBeefSandwich.price(), 0.001);
    }
}