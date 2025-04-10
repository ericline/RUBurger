import model.AddOns;
import model.Bread;
import model.Burger;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BurgerTest {
    private Burger singleBurger;
    private Burger doubleBurger;
    private ArrayList<AddOns> LOC;
    private ArrayList<AddOns> LTA;

    @BeforeEach
    void setUp() {
        // Initialize add-ons
        LOC = new ArrayList<>();
        LOC.add(AddOns.LETTUCE);
        LOC.add(AddOns.ONIONS);
        LOC.add(AddOns.CHEESE);

        LTA = new ArrayList<>();
        LTA.add(AddOns.LETTUCE);
        LTA.add(AddOns.TOMATOES);
        LTA.add(AddOns.AVOCADO);

        // Create burger instances
        singleBurger = new Burger(Bread.BRIOCHE, false, LOC);
        doubleBurger = new Burger(Bread.PRETZEL, true, LTA);
    }

    @Test
    void testSingleBurgerWithAddOns() {
        // Base price: $6.99
        // Add-ons: Lettuce ($0.30) + Onions ($0.30) + Cheese ($1.00) = $1.60
        // Expected total: $6.99 + $1.60 = $8.59
        assertEquals(8.59, singleBurger.price(), 0.001);
    }

    @Test
    void testDoubleBurgerWithAddOns() {
        // Base price: $6.99
        // DoublePatty: $2.50
        // Add-ons: Lettuce ($0.30) + Tomatoes ($0.30) + Avocado ($0.50) = $1.10
        // Expected total: $6.99 + + $2.50 + $1.10 = $10.59
        assertEquals(10.59, doubleBurger.price(), 0.001);
    }
}


