package test.creditsuisse;

public class Price {
    private final int price;
    private final float quantity;

    Price(int price, float quantity) {
        this.price = price;
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public float getQuantity() {
        return quantity;
    }
}
