package model;

public abstract class MenuItem {
    protected int quantity;
    public abstract double price();

    protected MenuItem() {
        this.quantity = 1;
    }

    public void setQuantity(int i) {
        quantity = i;
    }
}