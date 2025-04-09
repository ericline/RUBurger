package model;

import java.util.ArrayList;

public class Order {
    private int number; //a unique integer to identify the order
    private ArrayList<MenuItem> items;

    private static int nextNumber = 1;

    private static Order instance;

    private Order() {
        this.number = nextNumber++;
        this.items = new ArrayList<>();
    }

    public static Order getInstance() {
        if (instance == null) {
            instance = new Order();
        }
        return instance;
    }

    public void addItem(MenuItem item) {
        items.add(item);
    }

    public void removeItem(MenuItem item) {
        items.remove(item);
    }

    public double calculateTotal() {
        double total = 0.0;
        for (MenuItem item : items) {
            total += item.price();
        }
        return total;
    }
    public int getNumber() {
        return number;
    }

    public void resetOrder() {
        instance = new Order();
    }
}
