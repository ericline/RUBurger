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

    private Order(int number, ArrayList<MenuItem> items) {
        this.number = number;
        this.items = new ArrayList<>(items);
    }

    public static Order getInstance() {
        if (instance == null) {
            instance = new Order();
        }
        return instance;
    }

    public Order cloneOrder() {
        return new Order(this.number, this.items);
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

    public ArrayList<MenuItem> getItems() {
        return items;
    }

    public void resetOrder() {
        instance = new Order();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order #").append(number).append("\n");
        sb.append("----------------------------\n");

        if (items.isEmpty()) {
            sb.append("No items in this order.\n");
        } else {
            for (MenuItem item : items) {
                sb.append(item.toString()).append("\n");
            }
        }

        sb.append("----------------------------\n");
        sb.append(String.format("Total: $%.2f", calculateTotal()));

        return sb.toString();
    }
}
