package test.creditsuisse;

import static com.google.common.base.Preconditions.checkArgument;

public class Order {
    private final String userId;
    private final int price;
    private final float quantity;

    public Order(String userId, int price, float quantity) {
        checkArgument(userId != null, "User Id must be supplied");
        checkArgument(price > 0, "price must be > 0");
        checkArgument(quantity > 0, "quantity must be > 0");
        this.userId = userId;
        this.price = price;
        this.quantity = quantity;
    }

    public String getUserId() {
        return userId;
    }

    public int getPrice() {
        return price;
    }

    public float getQuantity() {
        return quantity;
    }
}
