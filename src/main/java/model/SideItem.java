package model;

public class SideItem extends MenuItem {
    private final Side side;
    private final Size size;

    public SideItem(Side side, Size size) {
        this.side = side;
        this.size = size;
    }

    @Override
    public double price() {
        double basePrice = side.getPrice();
        if (size.equals(Size.LARGE)) {
            basePrice += 1.00;
        }
        else if (size.equals(Size.MEDIUM)) {
            basePrice += 0.50;
        }
        return basePrice *= quantity;
    }

    @Override
    public String toString() {
        return side.getName() + " x" + quantity;
    }
}
